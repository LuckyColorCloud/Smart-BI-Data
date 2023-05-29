package com.yun.bifilemanage.controller;


import com.yun.bidatacommon.vo.Result;
import com.yun.bifilemanage.service.FileService;
import com.yun.bifilemanage.vo.MinioFileVO;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.Resource;


/**
 * @author BlessingCR
 * @date 2022/09/26
 * @description
 */
@RestController
@RequestMapping("/minio")
@Tag(name = "MINIO相关接口")
public class MinioController {


    @Resource
    private FileService fileService;


    @GetMapping("/file/{id}")
    @Parameters({
            @Parameter(name = "id", required = true, description = "文件ID")
    })
    @Operation(summary = "文件获取")
    public Result<MinioFileVO> getById(@PathVariable(name = "id") Long id) {
        return Result.OK(fileService.queryById(id));
    }

    @PostMapping("file")
    @Parameters({
            // TODO 这里文件不知道能生效
            @Parameter(name = "file", required = true, description = "文件上传")
    })
    @Operation(summary = "文件上传")
    public Result<Long> upload(@RequestPart(name = "file") MultipartFile file) {
        Long id = fileService.upload(file);
        return Result.OK(id);
    }

    @DeleteMapping("/file/{id}")
    @Parameters({
            @Parameter(name = "path", required = true, description = "路径")
    })
    @Operation(summary = "文件伪删除")
    public Result<String> del(@PathVariable(name = "id") Long id) {
        // todo: 规范化返回
        return fileService.delete(id) ? Result.OK() : Result.ERROR("del error", "");
    }
}
