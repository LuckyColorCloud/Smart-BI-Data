package com.yun.bidatacommon.model.param;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Sober
 */
@EqualsAndHashCode(callSuper=false)
@Data
public class PageParam {

    /**
     * 页码
     */
    @Schema(description = "页码")
    private Long pageNumber = 1L;

    /**
     * 每页条数
     */
    @Schema(description = "每页条数")
    private Long limit = 10L;
}
