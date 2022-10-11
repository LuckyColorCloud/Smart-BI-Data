package com.yun.bidatastorage.api;

/**
 * 远程调用接口
 *
 * @author Yun
 */

import com.yun.bidataconnmon.vo.Result;
import com.yun.bidatastorage.dto.DropTableDto;
import com.yun.bidatastorage.dto.SaveDataDto;
import com.yun.bidatastorage.dto.SaveFileDto;
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
     *
     * @param saveDataDto 保存对象
     * @return 保存结果
     */
    @RequestMapping(value = "/saveData", method = RequestMethod.POST)
    Result<Object> saveData(@RequestBody SaveDataDto saveDataDto);

    /**
     * 查询数据库
     *
     * @param sqlId
     * @return 查询结果
     */
    @RequestMapping(value = "/querySql", method = RequestMethod.POST)
    Result<Object> querySql(@RequestBody Integer sqlId);

    /**
     * 保存文件到数据库
     *
     * @param saveFileDto 保存文件对象
     * @return 结果
     */
    @RequestMapping(value = "/saveFile", method = RequestMethod.POST)
    Result<Object> saveFile(@RequestBody SaveFileDto saveFileDto);

    /**
     * 删除数据源的某个表
     *
     * @param dropTable 保存文件对象
     * @return 结果
     */
    @RequestMapping(value = "/dropTable", method = RequestMethod.POST)
    Result<Object> dropTable(@RequestBody DropTableDto dropTable);
}
