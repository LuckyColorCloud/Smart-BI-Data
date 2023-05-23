package com.yun.bisecurity.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * 账户表
 * </p>
 *
 * @author Sober
 * @since 2022-10-26
 */
@Data
@TableName("account")
public class AccountEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    private String email;

    /**
     * 密码
     */
    @Schema(description = "密码")
    private String password;

}
