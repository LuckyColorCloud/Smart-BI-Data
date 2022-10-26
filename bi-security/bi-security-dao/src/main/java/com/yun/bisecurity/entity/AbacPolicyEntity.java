package com.yun.bisecurity.entity;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;

import com.sobercoding.loopauth.abac.model.Policy;
import lombok.Data;

/**
 * <p>
 * abac规则表
 * </p>
 *
 * @author Sober
 * @since 2022-10-26
 */
@Data
@TableName("abac_policy")
public class AbacPolicyEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 规则名称
     */
    private String name;

    /**
     * 规则适用路径
     */
    private String route;

    /**
     * 路径请求方式
     */
    private String mode;

    /**
     * 属性规则json
     */
    private String policyJson;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedTime;

    /**
     * AbacPolicyEntity 转 Policy
     */
    public static Function<AbacPolicyEntity, Policy> abacPolicyToPolicy = abacPolicyEntity -> {
        // 载入规则名称
        Policy policy = new Policy()
                .setName(abacPolicyEntity.getName());
        // 格式化Json
        JSONObject jsonObject = JSONUtil.parseObj(JSONUtil.toJsonStr(abacPolicyEntity.getPolicyJson()));
        // 获取Json的key
        Set<String> keys = jsonObject.keySet();
        // 迭代载入规则属性的 名称 And 值
        keys.forEach(key -> policy.setProperty(key,jsonObject.get(key)));
        return policy;
    };

}
