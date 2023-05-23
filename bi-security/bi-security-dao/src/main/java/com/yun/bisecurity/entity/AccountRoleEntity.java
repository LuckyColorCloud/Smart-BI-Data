package com.yun.bisecurity.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>
 * 
 * </p>
 *
 * @author Sober
 * @since 2022-11-10
 */
@TableName("account_role")
@Schema(title = "AccountRoleEntity对象", description = "")
public class AccountRoleEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @Schema(description = "账户id")
    private Long accountId;

    @Schema(description = "角色id")
    private Long roleId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "AccountRoleEntity{" +
        "id=" + id +
        ", accountId=" + accountId +
        ", roleId=" + roleId +
        "}";
    }
}
