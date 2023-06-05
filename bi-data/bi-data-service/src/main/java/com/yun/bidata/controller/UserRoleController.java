package com.yun.bidata.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yun.bidata.entity.UserRoleEntity;
import com.yun.bidata.service.UserRoleService;
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
 * 第三方用户角色表  前端控制器
 * </p>
 *
 * @author Yun
 * @since 2022-10-26
 */
@RestController
@RequestMapping("/userRoleEntity")
@Tag(name = "查询第三方角色表")
public class UserRoleController {

    @Autowired
    private UserRoleService userRoleService;

    /**
     * 列表
     */
    @PostMapping("/list")
    @Parameters({
            @Parameter(name = "pageNo", required = true, description = "分页"),
            @Parameter(name = "pageSize", required = true, description = "数量")
    })
    @Operation(summary = "查询列表")
    public Result<Page<UserRoleEntity>> list(@Param("pageNo") int pageNo,
                                             @Param("pageSize") int pageSize) {
        Page<UserRoleEntity> userRoleEntityPage = new Page<>(pageNo, pageSize);
        Page<UserRoleEntity> page = userRoleService.page(userRoleEntityPage, new QueryWrapper<UserRoleEntity>().lambda().orderByDesc(UserRoleEntity::getCreatedTime));
        return Result.OK(page);
    }


    /**
     * 保存
     */
    @PostMapping("/save")
    @Operation(summary = "保存")
    public Result<String> save(@RequestBody UserRoleEntity userRoleEntity) {
        userRoleService.save(userRoleEntity);
        return Result.OK();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @Operation(summary = "修改")
    public Result<String> update(@RequestBody UserRoleEntity userRoleEntity) {
        userRoleService.updateById(userRoleEntity);
        return Result.OK();
    }

    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @Operation(summary = "查询信息")
    @Parameter(name = "id", required = true, description = "数据源ID")
    public Result<UserRoleEntity> info(@PathVariable("id") Integer id) {
        return Result.OK(userRoleService.getById(id));
    }

    /**
     * 删除
     */
    @GetMapping("/delete")
    @Operation(summary = "删除信息")
    @Parameter(name = "id", required = true, description = "数据源ID")
    public Result<String> delete(Integer id) {
        userRoleService.removeById(id);
        return Result.OK();
    }
}

