package com.yun.bisecurity.group.account.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Sober
 */
@Data
public class AccountVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Schema(description = "主键")
    private String id;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    private String email;

    /**
     * 头像
     */
    @Schema(description = "头像")
    private String icon;

    /**
     * 昵称
     */
    @Schema(description = "昵称")
    private String nickName;

    /**
     * 性别0女1男
     */
    @Schema(description = "性别0女1男")
    private Short gender;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedTime;

}
