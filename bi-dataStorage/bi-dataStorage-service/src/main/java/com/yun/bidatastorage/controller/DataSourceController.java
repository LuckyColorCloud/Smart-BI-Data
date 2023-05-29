package com.yun.bidatastorage.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yun.bidatacommon.vo.Result;
import com.yun.bidatastorage.entity.DataSourceEntity;
import com.yun.bidatastorage.service.DataSourceService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 数据源管理
 *
 * @author Yun
 * @email 2289128964@qq.com
 * @date 2022-08-24 11:11:31
 */
@RestController
@RequestMapping("/datasource")
@Tag(name = "查询数据源相关接口")
public class DataSourceController {

    @Autowired
    private DataSourceService dataSourceService;

    /**
     * 列表
     */
    @PostMapping("/list")
    @Parameters({
            @Parameter(name = "pageNo", required = true, description = "分页"),
            @Parameter(name = "pageSize", required = true, description = "数量")
    })
    @Operation(summary = "查询列表")
    public Result<Page<DataSourceEntity>> list(@Param("pageNo") int pageNo,
                                               @Param("pageSize") int pageSize) {
        Page<DataSourceEntity> dataSourceEntityPage = new Page<>(pageNo, pageSize);
        Page<DataSourceEntity> page = dataSourceService.page(dataSourceEntityPage, new QueryWrapper<DataSourceEntity>().lambda().orderByDesc(DataSourceEntity::getCreatedTime));
        return Result.OK(page);
    }


    /**
     * 保存
     */
    @PostMapping("/save")
    @Operation(summary = "保存数据源")
    public Result<String> save(@RequestBody DataSourceEntity datasource) {
        dataSourceService.save(datasource);
        return Result.OK();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @Operation(summary = "修改数据源")
    public Result<String> update(@RequestBody DataSourceEntity datasource) {
        dataSourceService.updateById(datasource);
        return Result.OK();
    }

    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @Operation(summary = "查询数据源信息")
    @Parameter(name = "id", required = true, description = "数据源ID")
    public Result<DataSourceEntity> info(@PathVariable("id") Integer id) {
        return Result.OK(dataSourceService.getById(id));
    }

    /**
     * 删除
     */
    @GetMapping("/delete")
    @Operation(summary = "删除数据源信息")
    @Parameter(name = "id", required = true, description = "数据源ID")
    public Result<String> delete(Integer id) {
        dataSourceService.removeById(id);
        return Result.OK();
    }

}
