package com.yun.bidata.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * <p>
 * 第三方用户角色表 
 * </p>
 *
 * @author Yun
 * @since 2022-10-26
 */
@TableName("user_role")
@Schema(title = "UserRoleEntity对象", description = "第三方用户角色表 ")
public class UserRoleEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "创建时间")
    private LocalDateTime createdTime;

    @Schema(description = "更新时间")
    private LocalDateTime updatedTime;

    @Schema(description = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "url")
    private String url;

    @Schema(description = "是否缓存")
    private Boolean cache;

    @Schema(description = "缓存过期时间 单位分钟")
    private Integer cacheExpiration;

    @Schema(description = "请求头")
    private String header;

    @Schema(description = "body")
    private String body;

    @Schema(description = "请求方式")
    private String requestType;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "所属项目")
    private Long projectId;

    @Schema(description = "jsonPath")
    private String jsonPath;


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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean isCache() {
        return cache;
    }

    public void setCache(Boolean cache) {
        this.cache = cache;
    }

    public Integer getCacheExpiration() {
        return cacheExpiration;
    }

    public void setCacheExpiration(Integer cacheExpiration) {
        this.cacheExpiration = cacheExpiration;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getJsonPath() {
        return jsonPath;
    }

    public void setJsonPath(String jsonPath) {
        this.jsonPath = jsonPath;
    }

    @Override
    public String toString() {
        return "UserRoleEntity{" +
        "createdTime=" + createdTime +
        ", updatedTime=" + updatedTime +
        ", id=" + id +
        ", url=" + url +
        ", cache=" + cache +
        ", cacheExpiration=" + cacheExpiration +
        ", header=" + header +
        ", body=" + body +
        ", requestType=" + requestType +
        ", name=" + name +
        ", projectId=" + projectId +
        ", jsonPath=" + jsonPath +
        "}";
    }
}
