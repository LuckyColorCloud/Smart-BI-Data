package com.yun.bidata.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yun.bidata.entity.IndexConfigEntity;
import com.yun.bidata.service.IndexConfigService;
import com.yun.bidatacommon.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 指标表 前端控制器
 * </p>
 *
 * @author Yun
 * @since 2022-10-26
 */
@RestController
@RequestMapping("/indexConfig")
@Api(tags = "查询指标相关数据")
public class IndexConfigController {

    @Autowired
    private IndexConfigService indexConfigService;

    /**
     * 列表
     */
    @PostMapping("/list")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageNo", dataType = "int", required = true, value = "分页"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", dataType = "int", required = true, value = "数量")
    })
    @ApiOperation("查询列表")
    public Result<Page<IndexConfigEntity>> list(@Param("pageNo") int pageNo,
                                                @Param("pageSize") int pageSize) {
        Page<IndexConfigEntity> indexConfigEntityPage = new Page<>(pageNo, pageSize);
        Page<IndexConfigEntity> page = indexConfigService.page(indexConfigEntityPage, new QueryWrapper<IndexConfigEntity>().lambda().orderByDesc(IndexConfigEntity::getCreatedTime));
        return Result.OK(page);
    }


    /**
     * 保存
     */
    @PostMapping("/save")
    @ApiOperation("保存")
    public Result<String> save(@RequestBody IndexConfigEntity datasource) {
        indexConfigService.save(datasource);
        return Result.OK();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @ApiOperation("修改")
    public Result<String> update(@RequestBody IndexConfigEntity datasource) {
        indexConfigService.updateById(datasource);
        return Result.OK();
    }

    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @ApiOperation("查询信息")
    @ApiImplicitParam(paramType = "query", name = "id", dataType = "int", required = true, value = "数据源ID")
    public Result<IndexConfigEntity> info(@PathVariable("id") Integer id) {
        return Result.OK(indexConfigService.getById(id));
    }

    /**
     * 删除
     */
    @GetMapping("/delete")
    @ApiOperation("删除信息")
    @ApiImplicitParam(paramType = "query", name = "id", dataType = "int", required = true, value = "数据源ID")
    public Result<String> delete(Integer id) {
        indexConfigService.removeById(id);
        return Result.OK();
    }
}

