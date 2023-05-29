package com.yun.bisecurity.model.vo;

import com.yun.bisecurity.entity.AccountEntity;
import com.yun.bisecurity.entity.AccountInfoEntity;
import lombok.Builder;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Sober
 */
@Builder
public class AccountVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 邮箱
     */
    private String email;

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
    private Short gender;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;

}
