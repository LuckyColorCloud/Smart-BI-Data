package com.yun.bidata.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 第三方用户角色表 
 * </p>
 *
 * @author Yun
 * @since 2022-10-26
 */
@TableName("user_role")
@ApiModel(value = "UserRoleEntity对象", description = "第三方用户角色表 ")
public class UserRoleEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("创建时间")
    private LocalDateTime createdTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updatedTime;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("url")
    private String url;

    @ApiModelProperty("是否缓存")
    private Boolean cache;

    @ApiModelProperty("缓存过期时间 单位分钟")
    private Integer cacheExpiration;

    @ApiModelProperty("请求头")
    private String header;

    @ApiModelProperty("body")
    private String body;

    @ApiModelProperty("请求方式")
    private String requestType;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("所属项目")
    private Long projectId;

    @ApiModelProperty("jsonPath")
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
