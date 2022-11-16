package com.yun.bifilemanage.api;

import com.yun.bifilemanage.vo.MinioFileVO;
import com.yun.bidatacommon.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author BlessingCR
 * @date 2022/11/08
 * @description
 */
@FeignClient(
        value = "bi-minio",
        name = "bi-minio",
        path = "/open/minio/api"
)
public interface MinioApiFeign {
	/**
	 * feign调用获取文件接口
	 *
	 * @param id
	 * @return 数据
	 */
	@RequestMapping(value = "/file/{id}", method = RequestMethod.GET)
	Result<MinioFileVO> getById(Long id);

	/**
	 * feign调用上传文件接口
	 *
	 * @param file
	 * @return 数据
	 */
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	Result<Long> upload(@RequestPart(name = "file") MultipartFile file);

	/**
	 * feign调用删除文件接口
	 *
	 * @param id
	 * @return 数据
	 */
	@RequestMapping(value = "/del/{id}", method = RequestMethod.DELETE)
	Result<String> del(Long id);

}
