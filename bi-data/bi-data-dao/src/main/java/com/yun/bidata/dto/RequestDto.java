package com.yun.bidata.dto;

import lombok.Data;

/**
 * @author Yun
 */
@Data
public class RequestDto {
    /**
     * 请求地址
     */
    private String url;
    /**
     * 请求头方式
     */
    private String headers;
    /**
     * 请求body
     */
    private String body;
    /**
     * 请求超时
     */
    private Long timeOut;

}
