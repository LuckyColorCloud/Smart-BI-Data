package com.yun.bisecurity.group.account.model.param;

import com.yun.bidatacommon.model.param.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Sober
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "账户查询模型")
public class AccountQueryParam extends PageParam implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "邮箱")
    private String email;

}
