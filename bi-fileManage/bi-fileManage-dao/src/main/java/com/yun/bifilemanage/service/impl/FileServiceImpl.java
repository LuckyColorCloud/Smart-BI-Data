package com.yun.bifilemanage.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yun.bifilemanage.dao.FileDao;
import com.yun.bifilemanage.entity.FileEntity;
import com.yun.bifilemanage.service.FileService;
import com.yun.bifilemanage.util.MinioUtil;
import lombok.extern.slf4j.Slf4j;
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

    @Override
    public String upload(MultipartFile file) {
        String path = null;
        try {
            // todo： 规范化，并且鉴权
            path = minioUtil.upload(file);
            FileEntity fileEntity = FileEntity.builder()
                    .fileMd5(SecureUtil.md5(file.getInputStream()))
                    .filePath(path)
                    .build();
            fileDao.insert(fileEntity);
        } catch (Exception e) {
            path = null;
        }

        return path;
    }

    @Override
    public Boolean delete(String path) {
        QueryWrapper<FileEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(FileEntity::getFilePath, path).eq(FileEntity::getStatus, false);
        List<FileEntity> resList = this.list(queryWrapper);
        if (resList.size() == 1) {
            try {
                // todo： 规范化，并且鉴权
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
}

