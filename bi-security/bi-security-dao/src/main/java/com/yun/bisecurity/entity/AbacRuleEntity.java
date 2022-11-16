package com.yun.bisecurity.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Function;
import com.sobercoding.loopauth.abac.model.Policy;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.cglib.beans.BeanMap;

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
@ApiModel(value = "AbacRuleEntity对象", description = "ABAC规则表")
public class AbacRuleEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @ApiModelProperty("规则名称")
    private String name;

    @ApiModelProperty("是否需要登陆1需要0不需要")
    private Boolean isLogin;

    @ApiModelProperty("哪些用户可访问分隔符','")
    private String loginIds;

    @ApiModelProperty("哪些角色可访问分隔符','")
    private String roleIds;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedTime;

    public static Function<AbacRuleEntity, Policy> abacRuleToPolicy = (abacRuleEntity) -> {
        Policy policy = new Policy().setName(abacRuleEntity.getName());
        BeanMap map = BeanMap.create(abacRuleEntity);
        map.keySet().stream()
                // 过滤非规则类id
                .filter(item -> !item.toString().equals("createdTime") &&
                        !item.toString().equals("id") &&
                        !item.toString().equals("updatedTime"))
                // 载入到Policy
                .forEach(item ->
                    Optional.ofNullable(map.get(item)).ifPresent(
                            value -> policy.setProperty(item.toString(),value)
                    )
                );
        return policy;
    };

}
