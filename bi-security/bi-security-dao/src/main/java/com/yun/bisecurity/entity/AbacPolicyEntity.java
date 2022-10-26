package com.yun.bisecurity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
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
    private Long id;

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
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;

}
