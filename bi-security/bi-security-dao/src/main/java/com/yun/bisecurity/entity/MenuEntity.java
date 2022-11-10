package com.yun.bisecurity.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "MenuEntity对象", description = "")
public class MenuEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty("上级菜单id")
    private Long parentId;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("菜单类型1菜单，2页面，3按钮")
    private Integer type;

    @ApiModelProperty("菜单地址，页面路由Or接口地址")
    private String path;

    @ApiModelProperty("菜单图标")
    private String icon;

    @ApiModelProperty("请求方式")
    private String mode;

    @ApiModelProperty("菜单名称，前端加载页面使用，需要对应vue页面name")
    private String name;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;

}
