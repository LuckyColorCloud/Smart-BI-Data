package com.yun.bifilemanage.controller.feign;

import cn.hutool.core.bean.BeanUtil;
import com.yun.bidatacommon.vo.Result;
import com.yun.bifilemanage.api.MinioApiFeign;
import com.yun.bifilemanage.entity.FileEntity;
import com.yun.bifilemanage.service.FileService;
import com.yun.bifilemanage.vo.MinioFileVO;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.Resource;

/**
 * @author BlessingCR
 * @date 2022/11/13
 * @description
 */
@RestController
@RequestMapping("/open/minio/api")
@Tag(name = "Minio feign服务接口")
@Slf4j
public class MinioFeignController implements MinioApiFeign {

	@Resource
	private FileService fileService;


	@Override
	@GetMapping("/file/{id}")
	@Parameters({
			@Parameter(name = "id", required = true, description = "文件ID")
	})
	@Operation(summary = "文件获取")
	public Result<MinioFileVO> getById(@PathVariable(name = "id") Long id) {
		FileEntity fileEntity = fileService.getById(id);
		MinioFileVO minioFileVO = new MinioFileVO();
		BeanUtil.copyProperties(fileEntity, minioFileVO);
		return Result.OK(minioFileVO);
	}


	@Override
	@PostMapping("file")
	@Parameters({
			// TODO 这里文件不知道能不能生效
			@Parameter(name = "file", required = true, description = "文件上传")
	})
	@Operation(summary = "文件上传")
	public Result<Long> upload(@RequestPart(name = "file") MultipartFile file) {
		Long id = fileService.upload(file);
		return Result.OK(id);
	}

	@Override
	@DeleteMapping("/file/{id}")
	@Parameters({
			@Parameter(name = "path", required = true, description = "路径")
	})
	@Operation(summary = "文件伪删除")
	public Result<String> del(@PathVariable(name = "id") Long id) {
		return fileService.delete(id)?Result.OK():Result.ERROR(Result.ResultEnum.FILE_DOES_NOT_EXIST);
	}
}
