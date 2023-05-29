package com.yun.biapimanage.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yun.biapimanage.entity.ApiManageEntity;
import com.yun.biapimanage.service.ApiManageService;
import com.yun.bidatacommon.vo.Result;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * api管理  前端控制器
 * </p>
 *
 * @author Yun
 * @since 2022-10-26
 */
@RestController
@RequestMapping("/apiManage")
@Tag(name = "API管理接口")
public class ApiManageController {

    @Autowired
    private ApiManageService apiManageService;

    /**
     * 列表
     */
    @GetMapping("/page")
    @Parameters({
            @Parameter(name = "pageNo", required = true, description = "分页"),
            @Parameter(name = "pageSize", required = true, description = "数量")
    })
    @Operation(summary = "查询列表")
    public Result<Page<ApiManageEntity>> page(@RequestParam("pageNo") int pageNo,
                                              @RequestParam("pageSize") int pageSize) {
        Page<ApiManageEntity> apiManagePage = new Page<>(pageNo, pageSize);
        Page<ApiManageEntity> page = apiManageService.page(apiManagePage, new QueryWrapper<ApiManageEntity>().lambda().orderByDesc(ApiManageEntity::getCreatedTime));
        return Result.OK(page);
    }


    /**
     * 保存
     */
    @PostMapping("/save")
    @Operation(summary = "保存")
    public Result<String> save(@RequestBody ApiManageEntity apiManageEntity) {
        apiManageService.save(apiManageEntity);
        return Result.OK();
    }

    /**
     * 修改
     */
    @PutMapping
    @Operation(summary = "更新")
    public Result<String> update(@RequestBody ApiManageEntity apiManageEntity) {
        apiManageService.updateById(apiManageEntity);
        return Result.OK();
    }

    /**
     * 信息
     */
    @GetMapping
    @Parameters({
            @Parameter(name = "id", description = "API id")
    })
    @Operation(summary = "查询")
    public Result<ApiManageEntity> info(@RequestParam(value = "id") Integer id) {
        return Result.OK(apiManageService.getById(id));
    }

    /**
     * 删除
     */
    @DeleteMapping
    @Parameters({
            @Parameter(name = "id", description = "API id")
    })
    @Operation(summary = "删除")
    public Result<String> delete(@RequestParam(value = "id")  Integer id) {
        apiManageService.removeById(id);
        return Result.OK();
    }
}

