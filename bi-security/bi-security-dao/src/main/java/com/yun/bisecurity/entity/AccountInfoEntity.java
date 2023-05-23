package com.yun.bisecurity.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

/**
 * <p>
 * 账户信息表
 * </p>
 *
 * @author Sober
 * @since 2022-10-26
 */
@Getter
@Setter
@Builder
@TableName("account_info")
public class AccountInfoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 账户id
     */
    @Schema(description = "账户id")
    private String accountId;

    /**
     * 头像
     */
    @Schema(description = "头像")
    private String icon;

    /**
     * 昵称
     */
    @Schema(description = "昵称")
    private String nickname;

    /**
     * 性别0女1男
     */
    @Schema(description = "性别0女1男")
    private Short gender;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedTime;



}
