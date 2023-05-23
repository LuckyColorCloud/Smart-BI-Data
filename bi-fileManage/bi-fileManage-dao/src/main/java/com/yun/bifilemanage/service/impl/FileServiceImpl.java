package com.yun.bifilemanage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yun.bidatacommon.constant.CommonConstant;
import com.yun.bifilemanage.config.MinioProperties;
import com.yun.bifilemanage.dao.FileDao;
import com.yun.bifilemanage.entity.FileEntity;
import com.yun.bifilemanage.service.FileService;
import com.yun.bifilemanage.util.MinioUtil;
import com.yun.bifilemanage.vo.MinioFileVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.Resource;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.List;

/**
 * @author Yun
 */
@Service("fileService")
@Slf4j
public class FileServiceImpl extends ServiceImpl<FileDao, FileEntity> implements FileService {

    @Resource
    private FileDao fileDao;

    @Resource
    private MinioUtil minioUtil;


    @Autowired
    private MinioProperties minioProperties;

    @Value("minio.tmpDir")
    private String tmpDir;

    @Override
    public Long upload(MultipartFile file) {
        Long id = null;
        try {
            String path = minioUtil.upload(file);
            FileEntity fileEntity = FileEntity.builder()
                    .fileName(file.getOriginalFilename())
                    .fileMd5(SecureUtil.md5(file.getInputStream()))
                    .filePath(path)
                    .build();
            fileDao.insert(fileEntity);
            id = fileEntity.getId();
        } catch (Exception e) {
            return null;
        }
        return id;
    }


    @Override
    public Boolean delete(Long id) {
        LambdaQueryWrapper<FileEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FileEntity::getId, id).eq(FileEntity::getStatus, false);
        List<FileEntity> resList = this.list(queryWrapper);
        if (resList.size() == 1) {
            try {
                String path = resList.get(0).getFilePath();
                minioUtil.moveObj(path, CommonConstant.DEL + File.separator + path);
                resList.get(0).setStatus(true);
                fileDao.updateById(resList.get(0));
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }


    @Override
    public MinioFileVO queryById(Long id) {
        FileEntity fileEntity = this.getById(id);
        if (fileEntity == null || fileEntity.getStatus()) {
            return null;
        }
        MinioFileVO minioFileVO = new MinioFileVO();
        BeanUtil.copyProperties(fileEntity, minioFileVO);
        minioFileVO.setUrl(minioProperties.getEndpoint() + File.separator + fileEntity.getFilePath());
        return minioFileVO;
    }

    @Override
    public InputStream queryInputStreamById(Long id) {
        FileEntity fileEntity = this.getById(id);
        if (fileEntity == null || fileEntity.getStatus()) {
            return null;
        }
        return minioUtil.downloadObj(fileEntity.getFilePath());
    }

    @Override
    public File queryTmpFileById(Long id) {
        FileEntity fileEntity = this.getById(id);
        InputStream inputStream = queryInputStreamById(id);
        File tmpFile = null;
        if (inputStream != null) {
            try {
                byte[] buffer = new byte[inputStream.available()];
                inputStream.read(buffer);
                tmpFile = new File("src/main/resources/targetFile.tmp");
                OutputStream outStream = Files.newOutputStream(new File(tmpDir + fileEntity.getFileName()).toPath());
                outStream.write(buffer);
            } catch (Exception e) {
                log.error("minio service inputStream 2 file err");
                return null;
            }
        }
        return tmpFile;
    }

    @Override
    public void delTmpFile(File tmp) {
        if (tmp != null) {
            tmp.delete();
        }
    }
}

