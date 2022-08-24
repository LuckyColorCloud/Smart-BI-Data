package com.yun.bidata.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yun.bidata.entity.ApiPathEntity;
import com.yun.bidata.service.ApiPathService;
import com.yun.bidataconnmon.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 第三方api路径表
 *
 * @author Yun
 * @email 2289128964@qq.com
 * @date 2022-08-24 11:11:31
 */
@RestController
@RequestMapping("/apiPath")
@Api(tags = "第三方api路径相关接口")
public class ApiPathController {
    @Autowired
    private ApiPathService apiPathService;

    /**
     * 列表
     */
    @PostMapping("/list")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageNo", dataType = "int", required = true, value = "分页"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", dataType = "int", required = true, value = "数量")
    })
    @ApiOperation("查询列表")
    public Result<Page<ApiPathEntity>> list(@Param("pageNo") int pageNo,
                                            @Param("pageSize") int pageSize) {
        Page<ApiPathEntity> datasourceEntityPage = new Page<>(pageNo, pageSize);
        Page<ApiPathEntity> page = apiPathService.page(datasourceEntityPage, new QueryWrapper<ApiPathEntity>().lambda().orderByDesc(ApiPathEntity::getCreatedTime));
        return Result.OK(page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @ApiOperation("查询api路径信息")
    public Result<ApiPathEntity> info(@PathVariable("id") Integer id) {
        return Result.OK(apiPathService.getById(id));
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @ApiOperation("保存api路径信息")
    public Result<String> save(@RequestBody ApiPathEntity apiPath) {
        apiPathService.save(apiPath);
        return Result.OK();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @ApiOperation("修改api路径信息")
    public Result<String> update(@RequestBody ApiPathEntity apiPath) {
        apiPathService.updateById(apiPath);
        return Result.OK();
    }

    /**
     * 删除
     */
    @GetMapping("/delete")
    @ApiOperation("删除api路径信息")
    public Result<String> delete(Integer id) {
        apiPathService.removeById(id);
        return Result.OK();
    }

}
