package com.yun.bisecurity.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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
@TableName("role_menu")
@ApiModel(value = "RoleMenuEntity对象", description = "")
public class RoleMenuEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty("角色id")
    private Long roleId;

    @ApiModelProperty("菜单id")
    private Long menuId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    @Override
    public String toString() {
        return "RoleMenuEntity{" +
        "id=" + id +
        ", roleId=" + roleId +
        ", menuId=" + menuId +
        "}";
    }
}
