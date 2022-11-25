package com.yun.biapimanage.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yun.biapimanage.entity.ApiManageEntity;
import com.yun.biapimanage.service.ApiManageService;
import com.yun.bidatacommon.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "API管理接口")
public class ApiManageController {

    @Autowired
    private ApiManageService apiManageService;

    /**
     * 列表
     */
    @GetMapping("/page")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageNo", dataType = "int", required = true, value = "分页"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", dataType = "int", required = true, value = "数量")
    })
    @ApiOperation("查询列表")
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
    @ApiOperation("保存")
    public Result<String> save(@RequestBody ApiManageEntity apiManageEntity) {
        apiManageService.save(apiManageEntity);
        return Result.OK();
    }

    /**
     * 修改
     */
    @PutMapping
    @ApiOperation("更新")
    public Result<String> update(@RequestBody ApiManageEntity apiManageEntity) {
        apiManageService.updateById(apiManageEntity);
        return Result.OK();
    }

    /**
     * 信息
     */
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", dataType = "String", value = "API id")
    })
    @ApiOperation("查询")
    public Result<ApiManageEntity> info(@RequestParam(value = "id") Integer id) {
        return Result.OK(apiManageService.getById(id));
    }

    /**
     * 删除
     */
    @DeleteMapping
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", dataType = "String", value = "API id")
    })
    @ApiOperation("删除")
    public Result<String> delete(@RequestParam(value = "id")  Integer id) {
        apiManageService.removeById(id);
        return Result.OK();
    }
}

