package com.yun.biapimanage.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yun.biapimanage.entity.ProjectEntity;
import com.yun.biapimanage.service.ProjectService;
import com.yun.bidatacommon.model.vo.Result;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 项目表  前端控制器
 * </p>
 *
 * @author Yun
 * @since 2022-10-26
 */
@RestController
@RequestMapping("/projectEntity")
@Tag(name = "项目管理")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    /**
     * 列表
     */
    @PostMapping("/list")
    @Parameters({
            @Parameter(name = "pageNo", required = true, description = "分页"),
            @Parameter(name = "pageSize", required = true, description = "数量")
    })
    @Operation(summary = "查询列表")
    public Result<Page<ProjectEntity>> list(@Param("pageNo") int pageNo,
                                            @Param("pageSize") int pageSize) {
        Page<ProjectEntity> projectPage = new Page<>(pageNo, pageSize);
        Page<ProjectEntity> page = projectService.page(projectPage, new QueryWrapper<ProjectEntity>().lambda().orderByDesc(ProjectEntity::getCreatedTime));
        return Result.OK(page);
    }


    /**
     * 保存
     */
    @PostMapping("/save")
    @Operation(summary = "保存")
    public Result<String> save(@RequestBody ProjectEntity projectEntity) {
        projectService.save(projectEntity);
        return Result.OK();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @Operation(summary = "修改")
    public Result<String> update(@RequestBody ProjectEntity projectEntity) {
        projectService.updateById(projectEntity);
        return Result.OK();
    }

    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @Operation(summary = "查询信息")
    @Parameter(name = "id", required = true, description = "数据源ID")
    public Result<ProjectEntity> info(@PathVariable("id") Integer id) {
        return Result.OK(projectService.getById(id));
    }

    /**
     * 删除
     */
    @GetMapping("/delete")
    @Operation(summary = "删除信息")
    @Parameter(name = "id", required = true, description = "数据源ID")
    public Result<String> delete(Integer id) {
        projectService.removeById(id);
        return Result.OK();
    }
}

