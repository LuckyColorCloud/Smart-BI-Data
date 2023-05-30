package com.yun.bisecurity.controller;


import com.yun.bidatacommon.vo.Result;
import com.yun.bisecurity.group.menu.model.entity.MenuEntity;
import com.yun.bisecurity.group.menu.model.vo.MenuVo;
import com.yun.bisecurity.group.menu.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Sober
 * @since 2022-11-10
 */
@Tag(name = "菜单管理接口")
@Slf4j
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Resource
    private MenuService menuService;

    /**
     * 查询菜单
     * @param title 标题
     * @param state 状态
     * @return Result<List<MenuDto>>
     */
    @GetMapping("/list")
    @Parameters({
            @Parameter(name = "title", description = "菜单标题"),
            @Parameter(name = "state", description = "1启用0停用")
    })
    @Operation(summary = "查询菜单")
    public Result<List<MenuVo>> query(@RequestParam(value = "title", required = false, defaultValue = "") String title,
                                      @RequestParam(value = "state", required = false, defaultValue = "2") int state) {
        return Result.OK(MenuEntity.listEntityToTreeVo.apply(menuService.queryMenu(title,state)));
    }

    /**
     * 查询菜单根据id
     * @param id 标题
     * @return Result<List<MenuDto>>
     */
    @GetMapping
    @Parameters({
            @Parameter(name = "id", description = "主键")
    })
    @Operation(summary = "查询菜单")
    public Result<MenuVo> one(@RequestParam(value = "id") String id) {
        return Result.OK(MenuEntity.entityToVo.apply(menuService.getById(id)));
    }

    /**
     * 新增菜单
     * @param menuEntity 菜单实体
     * @return Result<MenuEntity>
     */
    @PostMapping
    @Operation(summary = "新增菜单")
    public Result<MenuEntity> save(@RequestBody MenuEntity menuEntity) {
        return menuService.save(menuEntity)? Result.OK(menuEntity): Result.ERROR(Result.ResultEnum.ERROR);
    }

    /**
     * 更新菜单
     * @param menuEntity 菜单实体
     */
    @PutMapping
    @Operation(summary = "更新菜单")
    public Result<MenuEntity> update(@RequestBody MenuEntity menuEntity) {
        return menuService.updateById(menuEntity)? Result.OK(menuEntity): Result.ERROR(Result.ResultEnum.ERROR);
    }

    /**
     * 删除菜单
     * @param id 需要删除的id
     */
    @DeleteMapping
    @Parameters({
            @Parameter(name = "id", description = "主键")
    })
    @Operation(summary = "删除菜单")
    public Result<Object> remove(@RequestParam(value = "id") String id) {
        return menuService.removeMenu(id)? Result.OK("删除成功"): Result.ERROR("删除失败，此数据已被使用");
    }


}

