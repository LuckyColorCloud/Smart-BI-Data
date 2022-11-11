package com.yun.bisecurity.model.vo;

import com.yun.bisecurity.entity.MenuEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;
import rx.functions.Function;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Sober
 */
@Data
@Accessors(chain = true)
public class MenuVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("上级菜单id")
    private String parentId;

    @ApiModelProperty("菜单类型1菜单，2页面，3按钮")
    private Integer type;

    @ApiModelProperty("菜单地址，页面路由Or接口地址")
    private String path;

    @ApiModelProperty("菜单图标")
    private String icon;

    @ApiModelProperty("请求方式")
    private String mode;

    @ApiModelProperty("状态1生效0无效")
    private Boolean state;

    @ApiModelProperty("子菜单")
    private List<MenuVo> children;

    @ApiModelProperty("创建时间")
    private LocalDateTime createdTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updatedTime;


}
