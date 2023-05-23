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
@TableName("data_model_menu")
@Schema(title = "DataModelMenuEntity对象", description = "")
public class DataModelMenuEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @Schema(description = "数据模型id")
    private Long dataModelId;

    @Schema(description = "菜单id")
    private Long menuId;


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

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    @Override
    public String toString() {
        return "DataModelMenuEntity{" +
        "id=" + id +
        ", dataModelId=" + dataModelId +
        ", menuId=" + menuId +
        "}";
    }
}
