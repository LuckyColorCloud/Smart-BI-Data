package com.yun.bifilemanage.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yun.bidataconnmon.vo.Result;
import com.yun.bifilemanage.entity.FileEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.yun.bifilemanage.service.FileService;

import java.util.Date;

/**
 * @author Yun
 */
@RestController
@RequestMapping("/fileStorage")
@Api(tags = "文件入库存表")
public class FileStorageController {

    @Autowired
    FileService fileService;


    /**
     * 列表
     */
    @PostMapping("/list")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageNo", dataType = "int", required = true, value = "分页"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", dataType = "int", required = true, value = "数量")
    })
    @ApiOperation("查询列表")
    public Result<Page<FileEntity>> list(@Param("pageNo") int pageNo,
                                         @Param("pageSize") int pageSize) {
        Page<FileEntity> fileEntityPage = new Page<>(pageNo, pageSize);
        Page<FileEntity> page = fileService.page(fileEntityPage, new QueryWrapper<FileEntity>().lambda().eq(FileEntity::getStatus, false).orderByDesc(FileEntity::getCreatedTime));
        return Result.OK(page);
    }


    /**
     * 保存
     */
    @PostMapping("/save")
    @ApiOperation("保存数据源")
    public Result<String> save(@RequestBody FileEntity fileEntity) {
        fileService.save(fileEntity);
        return Result.OK();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @ApiOperation("修改数据源")
    public Result<String> update(@RequestBody FileEntity fileEntity) {
        fileEntity.setUpdatedTime(new Date());
        fileService.updateById(fileEntity);
        return Result.OK();
    }

    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @ApiOperation("查询数据源信息")
    @ApiImplicitParam(paramType = "query", name = "id", dataType = "int", required = true, value = "数据源ID")
    public Result<FileEntity> info(@PathVariable("id") Integer id) {
        return Result.OK(fileService.getById(id));
    }

    /**
     * 删除
     */
    @GetMapping("/delete")
    @ApiOperation("删除数据源信息")
    @ApiImplicitParam(paramType = "query", name = "id", dataType = "int", required = true, value = "数据源ID")
    public Result<String> delete(Integer id) {
        FileEntity fileEntity = fileService.getById(id);
        if (fileEntity != null) {
            fileEntity.setStatus(true);
            fileService.updateById(fileEntity);
        }
        return Result.OK();
    }
}
