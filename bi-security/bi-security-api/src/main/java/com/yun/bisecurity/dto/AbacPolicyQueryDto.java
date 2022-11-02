package com.yun.bisecurity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Sober
 */
@Data
public class AbacPolicyQueryDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 规则名称
     */
    @ApiModelProperty("规则名称")
    private String name;

    /**
     * 规则适用路径
     */
    @ApiModelProperty("规则适用路径")
    private String route;

    /**
     * 路径请求方式
     */
    @ApiModelProperty("路径请求方式")
    private String mode;

}
