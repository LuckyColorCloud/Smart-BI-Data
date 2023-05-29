package com.yun.biapimanage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * api管理
 * </p>
 *
 * @author Yun
 * @since 2022-10-26
 */
@TableName("api_manage")
@Schema(title = "ApiManageEntity对象", description = "api管理 ")
@Data
public class ApiManageEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "创建时间")
    private LocalDateTime createdTime;

    @Schema(description = "更新时间")
    private LocalDateTime updatedTime;
    @Schema(description = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @Schema(description = "请求路径 请求路径")
    private String apiPath;
    @Schema(description = "指标 多个数据集")
    private String indexId;
    @Schema(description = "类型 0.接口转发1.查询数据库 2.静态数据直接返回result 3.数据融合(根据id) 4.数据融合(数组合并)")
    private Integer type;
    @Schema(description = "文档描述 文档描述")
    private String document;
    @Schema(description = "是否鉴权 是否鉴权")
    private Boolean auto;
    @Schema(description = "所属项目 所属项目")
    private Integer projectId;
    @Schema(description = "静态数据 静态数据")
    private String staticData;
    @Schema(description = "接口可写 接口可写")
    private Boolean writable;
    @Schema(description = "图表参数")
    private String params;
    @Schema(description = "融合类型 0.http 1.数据库")
    private Integer fusion;
    @Schema(description = "融合类型参数")
    private String fusionParams;
    @Schema(description = "字符类型")
    private String chartType;
    @Schema(description = "接口名称")
    private String name;
}
