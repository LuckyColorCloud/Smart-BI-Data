package com.yun.bisecurity.group.account.model.param;

import com.yun.bidatacommon.db.query.BiQuery;
import com.yun.bidatacommon.db.query.QueryType;
import com.yun.bidatacommon.model.param.PageParam;
import com.yun.bidatacommon.verification.constraints.EnumValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.validation.annotation.Validated;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Sober
 */
@Validated
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "账户查询模型")
public class AccountQueryParam extends PageParam implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @BiQuery(QueryType.LIKE)
    @Schema(description = "邮箱")
    private String email;

    /**
     * 昵称
     */
    @BiQuery(QueryType.LIKE)
    @Schema(description = "昵称")
    private String nickName;

    /**
     * 性别0女1男
     */
    @BiQuery(QueryType.EQ)
    @Schema(description = "性别0女1男2保密")
    @EnumValue(intValues = {0, 1, 2}, message = "性别非法")
    private Short gender;

}
