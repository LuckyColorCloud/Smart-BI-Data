package com.yun.bisecurity.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
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
@TableName("abac_menu")
@Schema(title = "AbacMenuEntity对象", description = "")
public class AbacMenuEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    @Schema(description = "ABAC规则id")
    private String abacRuleId;

    @Schema(description = "菜单id，对应按钮类型，ps：接口")
    private String menuId;

}
