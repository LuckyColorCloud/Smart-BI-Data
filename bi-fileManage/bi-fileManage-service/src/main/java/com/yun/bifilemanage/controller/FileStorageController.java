package com.yun.bifilemanage.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yun.bidatacommon.vo.Result;
import com.yun.bifilemanage.entity.FileStorageEntity;
import com.yun.bifilemanage.service.FileStorageService;
import com.yun.bifilemanage.vo.FileVo;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.Operation;
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
@Tag(name = "文件入库存表")
public class FileStorageController {

    @Autowired
    FileStorageService fileStorageService;

    /**
     * 列表
     */
    @PostMapping("/list")
    @Parameters({
            @Parameter(name = "pageNo", required = true, description = "分页"),
            @Parameter(name = "pageSize", required = true, description = "数量")
    })
    @Operation(summary = "查询列表")
    public Result<Page<FileVo>> list(@Param("pageNo") int pageNo,
                                     @Param("pageSize") int pageSize) {
        return Result.OK(fileStorageService.list(new Page<>(pageNo, pageSize)));
    }


    /**
     * 保存
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @Parameters({
            // TODO 这里文件不知道能不能生效
            @Parameter(name = "file", required = true, description = "文件"),
            @Parameter(name = "sourceId", required = true, description = "存储数据源ID"),
            @Parameter(name = "saveName",  required = true, description = "保存表名称")
    })
    @Operation(summary = "保存文件数据")
    public Result<Object> save(MultipartFile file, String saveName, Integer sourceId) {
        return fileStorageService.saveAndStorage(file, saveName, sourceId);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @Operation(summary = "修改文件数据")
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
    @Operation(summary = "查询文件信息")
    @Parameter(name = "id", required = true, description = "文件ID")
    public Result<FileStorageEntity> info(@PathVariable("id") Integer id) {
        return Result.OK(fileStorageService.getById(id));
    }

    /**
     * 删除
     */
    @GetMapping("/delete")
    @Operation(summary = "删除文件信息")
    @Parameter(name = "id", required = true, description = "文件ID")
    public Result<Object> delete(Integer id) {
        return fileStorageService.dropTable(id);
    }

    /**
     * 重新入库
     */
    @GetMapping("/restart")
    @Operation(summary = "重新入库")
    @Parameter(name = "id", required = true, description = "文件ID")
    public Result<String> restart(Integer id) {
        FileStorageEntity fileStorageEntity = fileStorageService.getById(id);
        if (fileStorageEntity != null) {
            fileStorageEntity.setStatus(true);
            fileStorageService.restart(fileStorageEntity);
        }
        return Result.OK();
    }
}
