package com.yun.bidatastorage.dto;

import lombok.Data;

/**
 * @author Yun
 */
@Data
public class SaveFileDto {
    /**
     * 保存表名
     */
    private String saveName;
    /**
     * 数据源ID
     */
    private Integer sourceId;
    /**
     * 数据集
     */
    private byte[] context;
    /**
     * 字符编码
     */
    private String charset;
}
