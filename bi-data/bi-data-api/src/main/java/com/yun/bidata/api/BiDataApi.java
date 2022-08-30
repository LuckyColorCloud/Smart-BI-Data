package com.yun.bidata.api;

import com.yun.bidataconnmon.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Yun
 */
@FeignClient(
        name = "bi-data",
        path = "/apiPath"
)
public interface BiDataApi {

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public Result test();

}
