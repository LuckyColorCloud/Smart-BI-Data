package com.yun.bifilemanage.exception;

import com.yun.bidatacommon.vo.Result;

/**
 * 数据异常
 *
 * @author Yun
 */
public class FileManageException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public FileManageException() {
        super("该文件类型不存在!!!");
    }

    public FileManageException(Result.ResultEnum resultEnum) {
        super(resultEnum.getRespDesc());
    }

    public FileManageException(String msg) {
        super(msg);
    }
}
