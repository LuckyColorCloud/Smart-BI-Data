package com.yun.biapimanage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>
 * 项目表 
 * </p>
 *
 * @author Yun
 * @since 2022-10-26
 */
@TableName("project")
@Schema(title = "ProjectEntity对象", description = "项目表 ")
public class ProjectEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "创建时间")
    private LocalDateTime createdTime;

    @Schema(description = "更新时间")
    private LocalDateTime updatedTime;

    @Schema(description = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "项目名称")
    private String projectName;

    @Schema(description = "域名 域名前缀")
    private String domian;

    @Schema(description = "头信息 公共头信息")
    private String header;

    @Schema(description = "tokenkey token请求的key")
    private String tokenKey;


    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDomian() {
        return domian;
    }

    public void setDomian(String domian) {
        this.domian = domian;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getTokenKey() {
        return tokenKey;
    }

    public void setTokenKey(String tokenKey) {
        this.tokenKey = tokenKey;
    }

    @Override
    public String toString() {
        return "ProjectEntity{" +
        "createdTime=" + createdTime +
        ", updatedTime=" + updatedTime +
        ", id=" + id +
        ", projectName=" + projectName +
        ", domian=" + domian +
        ", header=" + header +
        ", tokenKey=" + tokenKey +
        "}";
    }
}
