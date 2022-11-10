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
@TableName("abac_menu")
@ApiModel(value = "AbacMenuEntity对象", description = "")
public class AbacMenuEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty("ABAC规则id")
    private Long abacRuleId;

    @ApiModelProperty("菜单id，对应按钮类型，ps：接口")
    private Long menuId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAbacRuleId() {
        return abacRuleId;
    }

    public void setAbacRuleId(Long abacRuleId) {
        this.abacRuleId = abacRuleId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    @Override
    public String toString() {
        return "AbacMenuEntity{" +
        "id=" + id +
        ", abacRuleId=" + abacRuleId +
        ", menuId=" + menuId +
        "}";
    }
}
