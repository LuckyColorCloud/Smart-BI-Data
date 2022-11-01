package com.yun.apimanage.api;

import com.yun.apimanage.dto.ProjectDto;
import com.yun.bidatacommon.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * API管理远程调用
 * @author Yun
 */
@FeignClient(
        value = "bi-apiManage",
        name = "bi-apiManage",
        path = "/open/apiManage/api"
)
public interface ApiManageFeign {
    /**
     * feign 获取项目信息
     * @param projectId 项目ID
     * @return 数据
     */
    @RequestMapping(value = "/queryProjectById", method = RequestMethod.POST)
    Result<ProjectDto> queryProjectById(@RequestBody Long projectId);

}
