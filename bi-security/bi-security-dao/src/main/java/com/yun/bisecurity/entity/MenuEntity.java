package com.yun.bisecurity.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 
 * </p>
 *
 * @author Sober
 * @since 2022-11-10
 */
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

    @ApiModelProperty("菜单名称，前端加载页面使用，需要对应vue页面name")
    private String name;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }

    @Override
    public String toString() {
        return "MenuEntity{" +
        "id=" + id +
        ", parentId=" + parentId +
        ", title=" + title +
        ", type=" + type +
        ", path=" + path +
        ", icon=" + icon +
        ", name=" + name +
        ", createdTime=" + createdTime +
        ", updatedTime=" + updatedTime +
        "}";
    }
}
