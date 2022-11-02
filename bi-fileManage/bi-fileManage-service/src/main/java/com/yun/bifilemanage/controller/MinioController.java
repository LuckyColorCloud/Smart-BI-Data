package com.yun.bifilemanage.controller;


import com.yun.bidatacommon.vo.Result;
import com.yun.bifilemanage.service.FileService;
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


    @PostMapping("file")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "form", name = "file", dataType = "__file", required = true, value = "文件上传")
    })
    @ApiOperation("文件上传")
    public Result<String> upload(@RequestPart(name = "file") MultipartFile file) {
        String url = fileService.upload(file);
        return Result.OK(url);
    }

    @DeleteMapping("file")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "path", dataType = "string", required = true, value = "路径")
    })
    @ApiOperation("文件伪删除")
    public Result<String> del(@RequestParam(name = "path") String path) {
        // todo: 规范化返回
        return fileService.delete(path)?Result.OK():Result.ERROR("del error", "");
    }
}
