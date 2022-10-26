package com.yun.bisecurity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p>
 * 账户信息表
 * </p>
 *
 * @author Sober
 * @since 2022-10-26
 */
@Data
@TableName("account_info")
public class AccountInfoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 账户id
     */
    private Long accountId;

    /**
     * 头像
     */
    private String icon;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 性别0女1男
     */
    private Boolean gender;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;


}
