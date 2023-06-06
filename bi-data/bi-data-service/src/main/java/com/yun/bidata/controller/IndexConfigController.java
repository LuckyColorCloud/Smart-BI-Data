package com.yun.bidata.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yun.bidata.entity.IndexConfigEntity;
import com.yun.bidata.service.IndexConfigService;
import com.yun.bidatacommon.model.vo.Result;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
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
@Tag(name = "查询指标相关数据")
public class IndexConfigController {

    @Autowired
    private IndexConfigService indexConfigService;

    /**
     * 列表
     */
    @PostMapping("/list")
    @Parameters({
            @Parameter(name = "pageNo", required = true, description = "分页"),
            @Parameter(name = "pageSize", required = true, description = "数量")
    })
    @Operation(summary = "查询列表")
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
    @Operation(summary = "保存")
    public Result<String> save(@RequestBody IndexConfigEntity indexConfigEntity) {
        indexConfigService.save(indexConfigEntity);
        return Result.OK();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @Operation(summary = "修改")
    public Result<String> update(@RequestBody IndexConfigEntity indexConfigEntity) {
        indexConfigService.updateById(indexConfigEntity);
        return Result.OK();
    }

    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @Operation(summary = "查询信息")
    @Parameter(name = "id", required = true, description = "数据源ID")
    public Result<IndexConfigEntity> info(@PathVariable("id") Integer id) {
        return Result.OK(indexConfigService.getById(id));
    }

    /**
     * 删除
     */
    @GetMapping("/delete")
    @Operation(summary = "删除信息")
    @Parameter(name = "id", required = true, description = "数据源ID")
    public Result<String> delete(Integer id) {
        indexConfigService.removeById(id);
        return Result.OK();
    }
}

