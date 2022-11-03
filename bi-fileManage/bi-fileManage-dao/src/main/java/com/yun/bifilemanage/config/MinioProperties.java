package com.yun.bifilemanage.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author BlessingCR
 * @Date 2022/09/23
 * @Description
 */
@Data
@Component
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {
    /** 服务地址 **/
    private String endpoint;

    /** 账户 **/
    private String accessKey;

    /** 密码 **/
    private String secretKey;

    /** Bucket **/
    private String bucketName;

    /** 图片的最大大小 */
    private Long imageSize;

    /** 其他文件的最大大小 */
    private Long fileSize;

}
