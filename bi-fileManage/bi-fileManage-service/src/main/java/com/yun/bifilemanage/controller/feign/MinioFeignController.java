package com.yun.bifilemanage.controller.feign;

import cn.hutool.core.bean.BeanUtil;
import com.yun.bidatacommon.vo.Result;
import com.yun.bifilemanage.api.MinioApiFeign;
import com.yun.bifilemanage.entity.FileEntity;
import com.yun.bifilemanage.service.FileService;
import com.yun.bifilemanage.vo.MinioFileVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * @author BlessingCR
 * @date 2022/11/13
 * @description
 */
@RestController
@RequestMapping("/open/minio/api")
@Api(tags = "Minio feign服务接口")
@Slf4j
public class MinioFeignController implements MinioApiFeign {

	@Resource
	private FileService fileService;


	@Override
	@GetMapping("/file/{id}")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "id", dataType = "long", required = true, value = "文件ID")
	})
	@ApiOperation("文件获取")
	public Result<MinioFileVO> getById(@PathVariable(name = "id") Long id) {
		FileEntity fileEntity = fileService.getById(id);
		MinioFileVO minioFileVO = new MinioFileVO();
		BeanUtil.copyProperties(fileEntity, minioFileVO);
		return Result.OK(minioFileVO);
	}


	@Override
	@PostMapping("file")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "form", name = "file", dataType = "__file", required = true, value = "文件上传")
	})
	@ApiOperation("文件上传")
	public Result<Long> upload(@RequestPart(name = "file") MultipartFile file) {
		Long id = fileService.upload(file);
		return Result.OK(id);
	}

	@Override
	@DeleteMapping("/file/{id}")
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "path", dataType = "string", required = true, value = "路径")
	})
	@ApiOperation("文件伪删除")
	public Result<String> del(@PathVariable(name = "id") Long id) {
		return fileService.delete(id)?Result.OK():Result.ERROR(Result.ResultEnum.FILE_DOES_NOT_EXIST);
	}
}
