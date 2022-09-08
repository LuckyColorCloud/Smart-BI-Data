package com.yun.bidata.exception;

/**
 * 数据异常
 * @author Yun
 */
public class DataException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DataException() {
        super("请求数据异常,请核对!!!");
    }
}
