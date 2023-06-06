package com.yun.bisecurity.group.login.model.param;

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
@Schema(description = "登录参数")
public class LoginParam implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "邮箱")
    @NotBlank(message = "邮箱不能为空")
    private String email;

    @Schema(description = "密码")
    @NotBlank(message = "密码不能为空")
    private String passWord;
}
