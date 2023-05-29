package com.yun.bisecurity.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * ABAC规则表
 * </p>
 *
 * @author Sober
 * @since 2022-11-10
 */
@Data
@TableName("abac_rule")
@Schema(title = "AbacRuleEntity对象", description = "ABAC规则表")
public class AbacRuleEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @Schema(description = "规则名称")
    private String name;

    @Schema(description = "是否需要登陆1需要0不需要")
    private Boolean isLogin;

    @Schema(description = "哪些用户可访问分隔符','")
    private String loginIds;

    @Schema(description = "哪些角色可访问分隔符','")
    private String roleIds;

    @Schema(description = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    @Schema(description = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedTime;


}
