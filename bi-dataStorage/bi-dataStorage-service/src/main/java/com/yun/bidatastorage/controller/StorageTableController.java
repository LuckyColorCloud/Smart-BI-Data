package com.yun.bidatastorage.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yun.bidatacommon.model.vo.Result;
import com.yun.bidatastorage.entity.StorageTableEntity;
import com.yun.bidatastorage.service.StorageTableService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 存储表
 *
 * @author Yun
 * @email 2289128964@qq.com
 * @date 2022-08-24 11:11:31
 */
@RestController
@RequestMapping("/storagetable")
@Tag(name = "查询数据存储相关接口")
public class StorageTableController {

    @Autowired
    private StorageTableService storageTableService;

    /**
     * 列表
     */
    @PostMapping("/list")
    @Parameters({
            @Parameter(name = "pageNo", required = true, description = "分页"),
            @Parameter(name = "pageSize", required = true, description = "数量")
    })
    @Operation(summary = "查询列表")
    public Result<Page<StorageTableEntity>> list(@Param("pageNo") int pageNo,
                                                 @Param("pageSize") int pageSize) {
        Page<StorageTableEntity> datasourceEntityPage = new Page<>(pageNo, pageSize);
        Page<StorageTableEntity> page = storageTableService.page(datasourceEntityPage, new QueryWrapper<StorageTableEntity>().lambda().orderByDesc(StorageTableEntity::getCreatedTime));
        return Result.OK(page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @Operation(summary = "查询存储表")
    @Parameter(name = "id", required = true, description = "存储表ID")
    public Result<StorageTableEntity> info(@PathVariable("id") Integer id) {
        return Result.OK(storageTableService.getById(id));
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @Operation(summary = "保存存储表")
    public Result<String> save(@RequestBody StorageTableEntity storageTable) {
        storageTableService.save(storageTable);
        return Result.OK();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @Operation(summary = "修改存储表")
    public Result<String> update(@RequestBody StorageTableEntity storageTable) {
        storageTableService.updateById(storageTable);
        return Result.OK();
    }

    /**
     * 删除
     */
    @GetMapping("/delete")
    @Operation(summary = "删除存储表")
    @Parameter(name = "id", required = true, description = "存储表ID")
    public Result<String> delete(Integer id) {
        storageTableService.removeById(id);
        return Result.OK();
    }

}
