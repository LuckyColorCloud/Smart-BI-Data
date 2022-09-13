package com.yun.bidatastorage.api;

/**
 * 远程调用接口
 *
 * @author Yun
 */

import com.sun.xml.internal.bind.v2.TODO;
import com.yun.bidataconnmon.vo.Result;
import com.yun.bidatastorage.dto.QuerySourceDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Yun
 */
@FeignClient(
        value = "bi-dataStorage",
        name = "bi-dataStorage",
        path = "/open/storage/api"
)
public interface DataStorageApiFeign {
    /**
     * feign调用获取数据接口
     *
     * @return 数据源列表
     */
    @RequestMapping(value = "/querySourceList", method = RequestMethod.POST)
    Result<Object> querySourceList();
    /**
     * 保存数据到本地
     * @return 保存结果
     */
    @RequestMapping(value = "/saveData", method = RequestMethod.POST)
    Result<Object> saveData();
     /**
     * 查询数据库
     * @return 保存结果
     */
    @RequestMapping(value = "/querySql", method = RequestMethod.POST)
    Result<Object> querySql(@RequestBody QuerySourceDto querySourceDto);

}
