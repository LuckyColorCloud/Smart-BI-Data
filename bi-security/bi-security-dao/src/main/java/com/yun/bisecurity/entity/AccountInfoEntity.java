package com.yun.bisecurity.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty("账户id")
    private String accountId;

    /**
     * 头像
     */
    @ApiModelProperty("头像")
    private String icon;

    /**
     * 昵称
     */
    @ApiModelProperty("昵称")
    private String nickname;

    /**
     * 性别0女1男
     */
    @ApiModelProperty("性别0女1男")
    private Short gender;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedTime;



}
