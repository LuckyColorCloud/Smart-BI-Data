package com.yun.bifilemanage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yun.bifilemanage.config.MinioProperties;
import com.yun.bifilemanage.dao.FileDao;
import com.yun.bifilemanage.entity.FileEntity;
import com.yun.bifilemanage.service.FileService;
import com.yun.bifilemanage.util.MinioUtil;
import com.yun.bifilemanage.vo.MinioFileVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
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

    @Override
    public Long upload(MultipartFile file) {
        Long id = null;
        try {
            // todo： 规范化，并且鉴权
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
        QueryWrapper<FileEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(FileEntity::getId, id).eq(FileEntity::getStatus, false);
        List<FileEntity> resList = this.list(queryWrapper);
        if (resList.size() == 1) {
            try {
                // todo： 规范化，并且鉴权
                String path = resList.get(0).getFilePath();
                minioUtil.moveObj(path, "del" + File.separator + path);
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
        MinioFileVO minioFileVO = new MinioFileVO();
        BeanUtil.copyProperties(fileEntity, minioFileVO);
        minioFileVO.setUrl(minioProperties.getEndpoint() + File.separator + fileEntity.getFilePath());
        return minioFileVO;
    }

    @Override
    public File queryFileById(Long id) {
        //TODO Cr 实现
        return null;
    }
}

