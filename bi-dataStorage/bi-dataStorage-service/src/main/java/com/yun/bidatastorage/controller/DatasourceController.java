package com.yun.bidatastorage.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yun.bidata.api.BiDataApi;
import com.yun.bidataconnmon.vo.Result;
import com.yun.bidatastorage.entity.DatasourceEntity;
import com.yun.bidatastorage.service.DatasourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 数据源管理
 *
 * @author Yun
 * @email 2289128964@qq.com
 * @date 2022-08-24 11:11:31
 */
@RestController
@RequestMapping("/datasource")
@Api(tags = "查询数据源相关接口")
public class DatasourceController {

    @Autowired
    private DatasourceService datasourceService;

    /**
     * 列表
     */
    @PostMapping("/list")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageNo", dataType = "int", required = true, value = "分页"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", dataType = "int", required = true, value = "数量")
    })
    @ApiOperation("查询列表")
    public Result<Page<DatasourceEntity>> list(@Param("pageNo") int pageNo,
                                               @Param("pageSize") int pageSize) {
        Page<DatasourceEntity> datasourceEntityPage = new Page<>(pageNo, pageSize);
        Page<DatasourceEntity> page = datasourceService.page(datasourceEntityPage, new QueryWrapper<DatasourceEntity>().lambda().orderByDesc(DatasourceEntity::getCreatedTime));
        return Result.OK(page);
    }


    /**
     * 保存
     */
    @PostMapping("/save")
    @ApiOperation("保存数据源")
    public Result<String> save(@RequestBody DatasourceEntity datasource) {
        datasourceService.save(datasource);
        return Result.OK();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @ApiOperation("修改数据源")
    public Result<String> update(@RequestBody DatasourceEntity datasource) {
        datasourceService.updateById(datasource);
        return Result.OK();
    }

    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @ApiOperation("查询数据源信息")
    @ApiImplicitParam(paramType = "query", name = "id", dataType = "int", required = true, value = "数据源ID")
    public Result<DatasourceEntity> info(@PathVariable("id") Integer id) {
        return Result.OK(datasourceService.getById(id));
    }

    /**
     * 删除
     */
    @GetMapping("/delete")
    @ApiOperation("删除数据源信息")
    @ApiImplicitParam(paramType = "query", name = "id", dataType = "int", required = true, value = "数据源ID")
    public Result<String> delete(Integer id) {
        datasourceService.removeById(id);
        return Result.OK();
    }

    @Resource
    BiDataApi biDataApi;

    /**
     * 删除
     */
    @GetMapping("/test")
    @ApiOperation("test")
    public Result<String> test() {
        return biDataApi.test();
    }
}
