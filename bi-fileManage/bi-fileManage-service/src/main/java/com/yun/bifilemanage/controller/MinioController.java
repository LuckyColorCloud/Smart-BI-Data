package com.yun.bifilemanage.controller;


import cn.hutool.core.bean.BeanUtil;
import com.yun.bidata.vo.MinioFileVO;
import com.yun.bidatacommon.vo.Result;
import com.yun.bifilemanage.config.MinioProperties;
import com.yun.bifilemanage.entity.FileEntity;
import com.yun.bifilemanage.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;


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

    @Autowired
    private MinioProperties minioProperties;

    @GetMapping("/file/{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", dataType = "long", required = true, value = "文件ID")
    })
    @ApiOperation("文件获取")
    public Result<MinioFileVO> getById(@PathVariable(name = "id") Long id) {
        FileEntity fileEntity = fileService.getById(id);
        MinioFileVO minioFileVO = new MinioFileVO();
        BeanUtil.copyProperties(fileEntity, minioFileVO);
        minioFileVO.setUrl(minioProperties.getEndpoint() + File.separator + fileEntity.getFilePath());
        return Result.OK(minioFileVO);
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
        return fileService.delete(id)?Result.OK():Result.ERROR("del error", "");
    }
}
