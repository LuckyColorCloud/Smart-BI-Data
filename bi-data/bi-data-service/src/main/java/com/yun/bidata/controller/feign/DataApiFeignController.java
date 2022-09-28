package com.yun.bidata.controller.feign;

import com.yun.bidata.api.DataApiFeign;
import com.yun.bidata.dto.FormatDto;
import com.yun.bidata.dto.QueryDataDto;
import com.yun.bidata.service.DataService;
import com.yun.bidataconnmon.vo.Result;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Yun
 */
@RestController
@RequestMapping("/open/data/api")
@Api(tags = "Data feign服务接口")
@Slf4j
public class DataApiFeignController implements DataApiFeign {

    @Autowired
    DataService dataService;

    @Override
    @PostMapping("/getData")
    public Result<Object> getData(QueryDataDto dto) {
        return dataService.getData(dto);
    }

    @Override
    @PostMapping("/format")
    public Result<Object> format(FormatDto dto) {
        return dataService.formatConversion(dto);
    }
}
