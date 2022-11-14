package com.yun.bifilemanage.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author Yun
 */
@Data
public class FileVo {
    /**
     * 创建时间
     */
    private Date createdTime = new Date();
    /**
     * 更新时间
     */
    private Date updatedTime = new Date();
    /**
     * 文件类型 0.csv 1.xlsx 2.json
     */
    private Integer fileType;
    /**
     * 存储数据源
     */
    private Integer sourceId;
    /**
     * 保存表名
     */
    private String saveName;
    /**
     * 文件路径
     */
    private String filePath;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件md5
     */
    private String fileMd5;
}
