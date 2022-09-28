package com.yun.bidata.api;

import com.yun.bidata.dto.FormatDto;
import com.yun.bidata.dto.QueryDataDto;
import com.yun.bidataconnmon.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Yun
 */
@FeignClient(
        value = "bi-data",
        name = "bi-data",
        path = "/open/data/api"
)
public interface DataApiFeign {
    /**
     * feign调用获取数据接口
     *
     * @param dto
     * @return 数据
     */
    @RequestMapping(value = "/getData", method = RequestMethod.POST)
    Result<Object> getData(QueryDataDto dto);

    /**
     * feign调用获取数据接口
     *
     * @param dto
     * @return 数据
     */
    @RequestMapping(value = "/format", method = RequestMethod.POST)
    Result<Object> format(FormatDto dto);
}
