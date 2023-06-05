package com.yun.bisecurity.group.login.model.vo;

import com.yun.bisecurity.group.account.model.vo.AccountVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Sober
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "登录成功后返回的信息")
public class LoginInfoVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "账户信息")
    private AccountVo account;

    @Schema(description = "token")
    private String token;

}
