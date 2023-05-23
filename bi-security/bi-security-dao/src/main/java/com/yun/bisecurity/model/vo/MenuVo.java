package com.yun.bisecurity.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Sober
 */
@Data
@Accessors(chain = true)
public class MenuVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "上级菜单id")
    private String parentId;

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

    @Schema(description = "子菜单")
    private List<MenuVo> children;

    @Schema(description = "创建时间")
    private LocalDateTime createdTime;

    @Schema(description = "更新时间")
    private LocalDateTime updatedTime;


}
