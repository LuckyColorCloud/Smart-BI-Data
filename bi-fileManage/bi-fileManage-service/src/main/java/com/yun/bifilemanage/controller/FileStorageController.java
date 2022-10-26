package com.yun.bifilemanage.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yun.bidatacommon.vo.Result;
import com.yun.bifilemanage.entity.FileEntity;
import com.yun.bifilemanage.service.FileService;
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
    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "file", dataType = "__file", required = true, value = "文件"),
            @ApiImplicitParam(paramType = "query", name = "md5", dataType = "String", required = true, value = "文件md5"),
            @ApiImplicitParam(paramType = "query", name = "sourceId", dataType = "java.lang.Integer", required = true, value = "存储数据源ID"),
            @ApiImplicitParam(paramType = "query", name = "saveName", dataType = "String", required = true, value = "保存表名称")
    })
    @ApiOperation("保存文件数据")
    public Result<Object> save(MultipartFile file, String md5, String saveName, Integer sourceId) {
        return fileService.saveAndStorage(file, md5, saveName, sourceId);
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @ApiOperation("修改文件数据")
    public Result<String> update(@RequestBody FileEntity fileEntity) {
        FileEntity save = fileService.getById(fileEntity.getId());
        save.setUpdatedTime(new Date());
        save.setStatus(fileEntity.getStatus());
        fileService.updateById(save);
        return Result.OK();
    }

    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @ApiOperation("查询文件信息")
    @ApiImplicitParam(paramType = "query", name = "id", dataType = "int", required = true, value = "文件ID")
    public Result<FileEntity> info(@PathVariable("id") Integer id) {
        return Result.OK(fileService.getById(id));
    }

    /**
     * 删除
     */
    @GetMapping("/delete")
    @ApiOperation("删除文件信息")
    @ApiImplicitParam(paramType = "query", name = "id", dataType = "int", required = true, value = "文件ID")
    public Result<Object> delete(Integer id) {
        return fileService.dropTable(id);
    }

    /**
     * 删除
     */
    @GetMapping("/restart")
    @ApiOperation("重新入库")
    @ApiImplicitParam(paramType = "query", name = "id", dataType = "int", required = true, value = "文件ID")
    public Result<String> restart(Integer id) {
        FileEntity fileEntity = fileService.getById(id);
        if (fileEntity != null) {
            fileEntity.setStatus(true);
            fileService.updateById(fileEntity);

        }
        return Result.OK();
    }
}
