package com.yun.bifilemanage.exception;

/**
 * 数据异常
 * @author Yun
 */
public class FileManageException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public FileManageException() {
        super("该文件类型不存在!!!");
    }
}
