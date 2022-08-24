package com.yun.bidata.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yun.bidata.entity.UserRoleEntity;
import com.yun.bidata.service.UserRoleService;
import com.yun.bidataconnmon.vo.Result;
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
@RequestMapping("/userRole")
@Api(tags = "角色token相关表")
public class UserRoleController {
    @Autowired
    private UserRoleService userRoleService;

    /**
     * 列表
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageNo", dataType = "int", required = true, value = "分页"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", dataType = "int", required = true, value = "数量")
    })
    @PostMapping("/list")
    @ApiOperation("查询列表")
    public Result<Page<UserRoleEntity>> list(@Param("pageNo") int pageNo,
                                             @Param("pageSize") int pageSize) {
        Page<UserRoleEntity> pageEntity = new Page<>(pageNo, pageSize);
        Page<UserRoleEntity> page = userRoleService.page(pageEntity, new QueryWrapper<UserRoleEntity>().lambda().orderByDesc(UserRoleEntity::getCreatedTime));
        return Result.OK(page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public Result<UserRoleEntity> info(@PathVariable("id") Integer id) {
        return Result.OK(userRoleService.getById(id));
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public Result<String> save(@RequestBody UserRoleEntity userRole) {
        userRoleService.save(userRole);
        return Result.OK();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public Result<String> update(@RequestBody UserRoleEntity userRole) {
        userRoleService.updateById(userRole);
        return Result.OK();
    }

    /**
     * 删除
     */
    @GetMapping("/delete")
    @ApiImplicitParam("删除用户角色信息")
    public Result<String> delete(Integer id) {
        userRoleService.removeById(id);
        return Result.OK();
    }

}
