package com.yun.bidatastorage.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yun.bidatacommon.vo.Result;
import com.yun.bidatastorage.entity.StorageTableEntity;
import com.yun.bidatastorage.service.StorageTableService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "查询数据存储相关接口")
public class StorageTableController {

    @Autowired
    private StorageTableService storageTableService;

    /**
     * 列表
     */
    @PostMapping("/list")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageNo", dataType = "int", required = true, value = "分页"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", dataType = "int", required = true, value = "数量")
    })
    @ApiOperation("查询列表")
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
    @ApiOperation("查询存储表")
    @ApiImplicitParam(paramType = "query", name = "id", dataType = "int", required = true, value = "存储表ID")
    public Result<StorageTableEntity> info(@PathVariable("id") Integer id) {
        return Result.OK(storageTableService.getById(id));
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @ApiOperation("保存存储表")
    public Result<String> save(@RequestBody StorageTableEntity storageTable) {
        storageTableService.save(storageTable);
        return Result.OK();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @ApiOperation("修改存储表")
    public Result<String> update(@RequestBody StorageTableEntity storageTable) {
        storageTableService.updateById(storageTable);
        return Result.OK();
    }

    /**
     * 删除
     */
    @GetMapping("/delete")
    @ApiOperation("删除存储表")
    @ApiImplicitParam(paramType = "query", name = "id", dataType = "int", required = true, value = "存储表ID")
    public Result<String> delete(Integer id) {
        storageTableService.removeById(id);
        return Result.OK();
    }

}
