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
@TableName("data_model_field")
@ApiModel(value = "DataModelFieldEntity对象", description = "")
public class DataModelFieldEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty("数据模型id")
    private Long dataModelId;

    @ApiModelProperty("字段名称")
    private String name;

    @ApiModelProperty("角色id列表,分隔符','")
    private String roleIds;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDataModelId() {
        return dataModelId;
    }

    public void setDataModelId(Long dataModelId) {
        this.dataModelId = dataModelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    @Override
    public String toString() {
        return "DataModelFieldEntity{" +
        "id=" + id +
        ", dataModelId=" + dataModelId +
        ", name=" + name +
        ", roleIds=" + roleIds +
        "}";
    }
}
