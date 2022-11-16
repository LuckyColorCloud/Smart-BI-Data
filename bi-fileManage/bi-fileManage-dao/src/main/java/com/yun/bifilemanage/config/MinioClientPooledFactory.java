package com.yun.bifilemanage.config;

import io.minio.MinioClient;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
/**
 * @author BlessingCR
 * @date 2022/10/27
 * @description MinioClient 池化
 */
@Component
public class MinioClientPooledFactory extends BasePooledObjectFactory<MinioClient> {
    @Resource
    private MinioProperties minioProperties;

    @Override
    public MinioClient create() throws Exception {
        return MinioClient.builder()
                .endpoint(minioProperties.getEndpoint())
                .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                .build();
    }

    @Override
    public PooledObject<MinioClient> wrap(MinioClient minioClient) {
        return new DefaultPooledObject<>(minioClient);
    }
}
