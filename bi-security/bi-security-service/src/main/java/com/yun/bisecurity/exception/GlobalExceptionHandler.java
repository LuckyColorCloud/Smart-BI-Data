package com.yun.bisecurity.exception;

import com.sobercoding.loopauth.exception.LoopAuthException;
import com.yun.bidatacommon.security.UserSessionInfo;
import com.yun.bidatacommon.model.vo.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Yun
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 鉴权异常拦截
     * @param e
     * @return
     */
    @ExceptionHandler
    public Result<UserSessionInfo> handlerException(LoopAuthException e) {
        return Result.ERROR(Result.ResultEnum.ILLEGAL_REQUEST);
    }

}
