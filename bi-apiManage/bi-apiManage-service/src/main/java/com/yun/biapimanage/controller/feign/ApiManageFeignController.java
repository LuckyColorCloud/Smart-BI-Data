package com.yun.biapimanage.controller.feign;

import com.yun.apimanage.api.ApiManageFeign;
import com.yun.apimanage.dto.ProjectDto;
import com.yun.biapimanage.entity.ProjectEntity;
import com.yun.biapimanage.service.ProjectService;
import com.yun.bidatacommon.vo.Result;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * API服务feign接口
 *
 * @author Yun
 */
@RestController
@RequestMapping("/open/apiManage/api")
@Api(tags = "API feign服务接口")
@Slf4j
public class ApiManageFeignController implements ApiManageFeign {

    @Autowired
    ProjectService projectService;


    @Override
    @PostMapping("/queryProjectById")
    public Result<ProjectDto> queryProjectById(Long projectId) {
        ProjectEntity projectEntity = projectService.getById(projectId);
        ProjectDto projectDto = new ProjectDto();
        projectDto.setDomain(projectEntity.getDomian());
        projectDto.setHeaders(projectEntity.getHeader());
        projectDto.setTokenKey(projectEntity.getTokenKey());
        return Result.OK(projectDto);
    }


}
