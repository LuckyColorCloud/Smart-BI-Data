package com.yun.bidatastorage.dto;

import lombok.Data;

/**
 * @author Yun
 */
@Data
public class SaveDataDto {
    /**
     * 字符集 类型
     */
    private String charset;
    /**
     * 内容
     */
    private byte[] context;
    /**
     * 保存表的id
     */
    private Integer storageId;
}
