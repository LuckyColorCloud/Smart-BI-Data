package com.yun.bifilemanage.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yun.bidatacommon.vo.Result;
import com.yun.bifilemanage.entity.FileStorageEntity;
import com.yun.bifilemanage.service.FileStorageService;
import com.yun.bifilemanage.vo.FileVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * @author Yun
 */
@RestController
@RequestMapping("/fileStorage")
@Api(tags = "文件入库存表")
public class FileStorageController {

    @Autowired
    FileStorageService fileStorageService;

    /**
     * 列表
     */
    @PostMapping("/list")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageNo", dataType = "int", required = true, value = "分页"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", dataType = "int", required = true, value = "数量")
    })
    @ApiOperation("查询列表")
    public Result<Page<FileVo>> list(@Param("pageNo") int pageNo,
                                     @Param("pageSize") int pageSize) {
        return Result.OK(fileStorageService.list(new Page<>(pageNo, pageSize)));
    }


    /**
     * 保存
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "file", dataType = "__file", required = true, value = "文件"),
            @ApiImplicitParam(paramType = "query", name = "sourceId", dataType = "java.lang.Integer", required = true, value = "存储数据源ID"),
            @ApiImplicitParam(paramType = "query", name = "saveName", dataType = "String", required = true, value = "保存表名称")
    })
    @ApiOperation("保存文件数据")
    public Result<Object> save(MultipartFile file, String saveName, Integer sourceId) {
        return fileStorageService.saveAndStorage(file, saveName, sourceId);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @ApiOperation("修改文件数据")
    public Result<String> update(@RequestBody FileStorageEntity fileStorageEntity) {
        FileStorageEntity save = fileStorageService.getById(fileStorageEntity.getId());
        save.setUpdatedTime(new Date());
        save.setStatus(fileStorageEntity.getStatus());
        fileStorageService.updateById(save);
        return Result.OK();
    }

    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @ApiOperation("查询文件信息")
    @ApiImplicitParam(paramType = "query", name = "id", dataType = "int", required = true, value = "文件ID")
    public Result<FileStorageEntity> info(@PathVariable("id") Integer id) {
        return Result.OK(fileStorageService.getById(id));
    }

    /**
     * 删除
     */
    @GetMapping("/delete")
    @ApiOperation("删除文件信息")
    @ApiImplicitParam(paramType = "query", name = "id", dataType = "int", required = true, value = "文件ID")
    public Result<Object> delete(Integer id) {
        return fileStorageService.dropTable(id);
    }

    /**
     * 重新入库
     */
    @GetMapping("/restart")
    @ApiOperation("重新入库")
    @ApiImplicitParam(paramType = "query", name = "id", dataType = "int", required = true, value = "文件ID")
    public Result<String> restart(Integer id) {
        FileStorageEntity fileStorageEntity = fileStorageService.getById(id);
        if (fileStorageEntity != null) {
            fileStorageEntity.setStatus(true);
            fileStorageService.restart(fileStorageEntity);
        }
        return Result.OK();
    }
}
