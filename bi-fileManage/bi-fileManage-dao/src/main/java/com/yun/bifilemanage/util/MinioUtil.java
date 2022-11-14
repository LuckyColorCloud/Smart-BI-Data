package com.yun.bifilemanage.util;

import com.alibaba.nacos.common.utils.UuidUtils;
import com.yun.bifilemanage.config.MinioClientPooledFactory;
import com.yun.bifilemanage.config.MinioProperties;
import io.minio.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author BlessingCR
 * @date 2022/10/07
 * @description
 */
@Component
@Slf4j
public class MinioUtil {
    @Autowired
    private MinioClientPooledFactory minioClientPooledFactory;

    @Autowired
    private MinioProperties minioProperties;

    /**
     * 文件上传
     *
     * @param file 文件
     * @return filepath 文件路径
     */
    public String upload(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (StringUtils.isBlank(originalFilename)) {
            throw new RuntimeException();
        }
        String fileName = UuidUtils.generateUuid() + originalFilename.substring(originalFilename.lastIndexOf("."));
        String format = "YYYY-MM/dd";
        // DateTimeFormatter.ofPattern方法根据指定的格式输出时间
        String formatDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern(format));
        String filePath = formatDateTime + File.separator + fileName;
        try {
            PutObjectArgs objectArgs = PutObjectArgs.builder().bucket(minioProperties.getBucketName()).object(filePath)
                    .stream(file.getInputStream(), file.getSize(), -1).contentType(file.getContentType()).build();
            MinioClient minioClient = minioClientPooledFactory.create();
            minioClient.putObject(objectArgs);
        } catch (Exception e) {
            log.error("minioUtil upload obj err: {}", e.getMessage());
            return null;
        }
        return filePath;
    }

    /**
     * 文件删除
     *
     * @param filePath 文件路径
     * @return T / F
     */
    public Boolean deleteObj(String filePath) {
        try {
            // remove obj
            MinioClient minioClient = minioClientPooledFactory.create();
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(minioProperties.getBucketName())
                            .object(filePath)
                            .build());

        } catch (Exception e) {
            log.error("minioUtil del obj err: {}", e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 文件移动
     *
     * @param oldFilePath 旧文件路径
     * @param newFilePath 新文件路径
     * @return T / F
     */
    public Boolean moveObj(String oldFilePath, String newFilePath) {
        try {
            // copy obj
            MinioClient minioClient = minioClientPooledFactory.create();
            minioClient.copyObject(
                    CopyObjectArgs.builder()
                            .bucket(minioProperties.getBucketName())
                            .object(newFilePath)
                            .source(
                                    CopySource.builder()
                                            .bucket(minioProperties.getBucketName())
                                            .object(oldFilePath)
                                            .build())
                            .build());
            // remove source obj
            this.deleteObj(oldFilePath);

        } catch (Exception e) {
            log.error("minioUtil move obj err: {}", e.getMessage());
            return false;
        }
        return true;
    }

    public InputStream downloadObj(String path) {
        InputStream stream = null;
        try {
            MinioClient minioClient = minioClientPooledFactory.create();
            StatObjectResponse objectStat = minioClient
                    .statObject(StatObjectArgs.builder()
                            .bucket(minioProperties.getBucketName())
                            .object(path)
                            .build());
            if(objectStat == null) {
                return null;
            }
            stream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(minioProperties.getBucketName())
                            .object(path)
                            .build());

        }catch(Exception e) {
            log.error("minioUtil download err: {}", e.getMessage());
            return null;
        }
        return stream;
    }
}
