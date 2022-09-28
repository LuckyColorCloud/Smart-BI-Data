package com.yun.bidata.dto;

import lombok.Data;

/**
 * @author Yun
 */
@Data
public class FormatDto {
    /**
     * 图表类型
     */
    private String chartType;
    /**
     * 图表参数
     */
    private String params;
    /**
     * 数据
     */
    private String data;
}
