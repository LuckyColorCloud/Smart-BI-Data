package com.yun.bisecurity.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * ABAC规则表
 * </p>
 *
 * @author Sober
 * @since 2022-11-10
 */
@TableName("abac_rule")
@ApiModel(value = "AbacRuleEntity对象", description = "ABAC规则表")
public class AbacRuleEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty("是否需要登陆1需要0不需要")
    private Boolean isLogin;

    @ApiModelProperty("哪些用户可访问分隔符','")
    private String loginIds;

    @ApiModelProperty("哪些角色可访问分隔符','")
    private String roleIds;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(Boolean isLogin) {
        this.isLogin = isLogin;
    }

    public String getLoginIds() {
        return loginIds;
    }

    public void setLoginIds(String loginIds) {
        this.loginIds = loginIds;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
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
        return "AbacRuleEntity{" +
        "id=" + id +
        ", isLogin=" + isLogin +
        ", loginIds=" + loginIds +
        ", roleIds=" + roleIds +
        ", createdTime=" + createdTime +
        ", updatedTime=" + updatedTime +
        "}";
    }
}
