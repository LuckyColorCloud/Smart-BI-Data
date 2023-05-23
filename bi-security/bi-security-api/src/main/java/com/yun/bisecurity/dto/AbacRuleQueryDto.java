package com.yun.bisecurity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Sober
 */
@Data
public class AbacRuleQueryDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 规则名称
     */
    @Schema(description = "规则名称")
    private String name;

    /**
     * 规则适用路径
     */
    @Schema(description = "规则适用路径")
    private String route;

    /**
     * 路径请求方式
     */
    @Schema(description = "路径请求方式")
    private String mode;

}
