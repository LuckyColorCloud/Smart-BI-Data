package com.yun.bisecurity.controller;


import com.yun.bidatacommon.vo.Result;
import com.yun.bisecurity.entity.AbacRuleEntity;
import com.yun.bisecurity.model.vo.MenuVo;
import com.yun.bisecurity.entity.MenuEntity;
import com.yun.bisecurity.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Sober
 * @since 2022-11-10
 */
@Api(tags = "菜单管理接口")
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
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "title", dataType = "String", value = "菜单标题"),
            @ApiImplicitParam(paramType = "query", name = "state", dataType = "int", value = "1启用0停用")
    })
    @ApiOperation("查询菜单")
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
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", dataType = "String", value = "主键")
    })
    @ApiOperation("查询菜单")
    public Result<MenuVo> one(@RequestParam(value = "id") String id) {
        return Result.OK(MenuEntity.entityToVo.apply(menuService.getById(id)));
    }

    /**
     * 新增菜单
     * @param menuEntity 菜单实体
     * @return Result<MenuEntity>
     */
    @PostMapping
    @ApiOperation("新增菜单")
    public Result<MenuEntity> save(@RequestBody MenuEntity menuEntity) {
        return menuService.save(menuEntity)? Result.OK(menuEntity): Result.ERROR(Result.ResultEnum.ERROR);
    }

    /**
     * 更新菜单
     * @param menuEntity 菜单实体
     */
    @PutMapping
    @ApiOperation("更新菜单")
    public Result<MenuEntity> updata(@RequestBody MenuEntity menuEntity) {
        return menuService.updateById(menuEntity)? Result.OK(menuEntity): Result.ERROR(Result.ResultEnum.ERROR);
    }

    /**
     * 删除菜单
     * @param id 需要删除的id
     */
    @DeleteMapping
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", dataType = "String", value = "主键")
    })
    @ApiOperation("删除菜单")
    public Result<Object> remove(@RequestParam(value = "id") String id) {
        return menuService.removeMenu(id)? Result.OK("删除成功"): Result.ERROR("删除失败，此数据已被使用");
    }


}

