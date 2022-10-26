package com.yun.bidata.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yun.bidata.entity.ProjectEntity;
import com.yun.bidata.service.ProjectService;
import com.yun.bidatacommon.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author Yun
 * @email 2289128964@qq.com
 * @date 2022-08-24 11:11:31
 */
@RestController
@RequestMapping("/project")
@Api(tags = "项目相关接口")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    /**
     * 列表
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageNo", dataType = "int", required = true, value = "分页"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", dataType = "int", required = true, value = "数量")
    })
    @PostMapping("/list")
    @ApiOperation("查询列表")
    public Result<Page<ProjectEntity>> list(@Param("pageNo") int pageNo,
                                            @Param("pageSize") int pageSize) {
        Page<ProjectEntity> pageEntity = new Page<>(pageNo, pageSize);
        Page<ProjectEntity> page = projectService.page(pageEntity, new QueryWrapper<ProjectEntity>().lambda().orderByDesc(ProjectEntity::getCreatedTime));
        return Result.OK(page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @ApiOperation("查询项目信息")
    public Result<ProjectEntity> info(@PathVariable("id") Integer id) {
        return Result.OK(projectService.getById(id));
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @ApiOperation("保存项目信息")
    public Result<String> save(@RequestBody ProjectEntity project) {
        projectService.save(project);
        return Result.OK();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @ApiOperation("修改项目信息")
    public Result<String> update(@RequestBody ProjectEntity project) {
        projectService.updateById(project);
        return Result.OK();
    }

    /**
     * 删除
     */
    @GetMapping("/delete")
    @ApiOperation("删除项目信息")
    public Result<String> delete(Integer id) {
        projectService.removeById(id);
        return Result.OK();
    }

}
