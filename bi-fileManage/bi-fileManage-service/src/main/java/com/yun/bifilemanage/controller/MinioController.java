package com.yun.bifilemanage.controller;


import com.yun.bidatacommon.vo.Result;
import com.yun.bifilemanage.service.FileService;
import com.yun.bifilemanage.vo.MinioFileVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;


/**
 * @author BlessingCR
 * @date 2022/09/26
 * @description
 */
@RestController
@RequestMapping("/minio")
@Api(tags = "MINIO相关接口")
public class MinioController {


    @Resource
    private FileService fileService;


    @GetMapping("/file/{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", dataType = "long", required = true, value = "文件ID")
    })
    @ApiOperation("文件获取")
    public Result<MinioFileVO> getById(@PathVariable(name = "id") Long id) {
        return Result.OK(fileService.queryById(id));
    }

    @PostMapping("file")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", name = "file", dataType = "__file", required = true, value = "文件上传")
    })
    @ApiOperation("文件上传")
    public Result<Long> upload(@RequestPart(name = "file") MultipartFile file) {
        Long id = fileService.upload(file);
        return Result.OK(id);
    }

    @DeleteMapping("/file/{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "path", dataType = "string", required = true, value = "路径")
    })
    @ApiOperation("文件伪删除")
    public Result<String> del(@PathVariable(name = "id") Long id) {
        // todo: 规范化返回
        return fileService.delete(id) ? Result.OK() : Result.ERROR("del error", "");
    }
}
