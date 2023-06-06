package com.yun.bisecurity.group.menu.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import com.yun.bisecurity.group.menu.model.vo.MenuVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author Sober
 * @since 2022-11-10
 */
@Data
@TableName("menu")
@Schema(title = "MenuEntity对象", description = "")
public class MenuEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @Schema(description = "上级菜单id")
    private String parentId;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "菜单类型1菜单，2页面，3按钮")
    private Integer type;

    @Schema(description = "菜单地址，页面路由Or接口地址")
    private String path;

    @Schema(description = "菜单图标")
    private String icon;

    @Schema(description = "请求方式")
    private String mode;

    @Schema(description = "状态1生效0无效")
    private Boolean state;

    @Schema(description = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    @Schema(description = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedTime;


    /**
     * 转可视菜单树
     */
    public static Function<List<MenuEntity>, List<MenuVo>> listEntityToTreeVo = (menuList) -> {
        // 加载一级菜单
        List<MenuVo> menuVoList = menuList.stream()
                .filter(item -> "0".equals(item.getParentId()))
                .map(MenuEntity.entityToVo)
                .collect(Collectors.toList());
        // 递归插入子菜单
        return menuVoList.stream()
                .peek(menuVo -> toTree(menuVo, menuList))
                .collect(Collectors.toList());
    };

    /**
     * entity转vo
     */
    public static Function<MenuEntity, MenuVo> entityToVo = (menu) ->
            new MenuVo()
                    .setPath(menu.getPath())
                    .setId(menu.getId())
                    .setMode(menu.getMode())
                    .setIcon(menu.getIcon())
                    .setParentId(menu.getParentId())
                    .setChildren(new ArrayList<>())
                    .setState(menu.getState())
                    .setTitle(menu.getTitle())
                    .setType(menu.getType())
                    .setUpdatedTime(menu.getUpdatedTime())
                    .setCreatedTime(menu.getCreatedTime());

    private static void toTree(MenuVo menuVo, List<MenuEntity> menuList){
        menuVo.setChildren(
                menuList.stream()
                    .filter(menu -> menuVo.getId().equals(menu.getParentId()))
                    .map(MenuEntity.entityToVo)
                    .peek(itemMenuVo -> toTree(itemMenuVo,menuList))
                    .collect(Collectors.toList())
        );
    }
}
