package com.yun.bigateway.fallback;

import com.yun.bidatacommon.model.vo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 响应超时熔断处理器
 *
 * @author Yun
 */
@RestController
public class FallbackController {

    /**
     * 全局熔断处理
     *
     * @return
     */
    @RequestMapping("/fallback")
    public Result<String> fallback() {
        return Result.ERROR(Result.ResultEnum.REQUEST_TIMED_OUT);
    }
}
