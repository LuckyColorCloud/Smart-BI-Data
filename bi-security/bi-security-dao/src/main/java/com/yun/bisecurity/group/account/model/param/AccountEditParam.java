package com.yun.bisecurity.group.account.model.param;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.yun.bidatacommon.verification.constraints.EnumValue;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Sober
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "账户编辑模型")
public class AccountEditParam implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @NotBlank(message = "主键不能为空")
    private String id;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    @NotBlank(message = "邮箱不能为空")
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
    @NotBlank(message = "昵称不能为空")
    private String nickName;

    /**
     * 性别0女1男
     */
    @Schema(description = "性别0女1男")
    @EnumValue(intValues = { 0, 1 }, message = "性别参数非法")
    private Short gender;
}
