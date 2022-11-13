package com.yun.bidata.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author BlessingCR
 * @date 2022/11/14
 * @description
 */
@Data
public class MinioFileVO {
    private Long id;
    /**
     * 用户ID
     */
    private Long uid;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 更新时间
     */
    private Date updatedTime;
    /**
     * 文件路径
     */
    private String filePath;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * url
     */
    private String url;
}
