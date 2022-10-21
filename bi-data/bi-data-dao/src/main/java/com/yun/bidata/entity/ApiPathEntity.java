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
 * 第三方api路径表 
 * </p>
 *
 * @author Yun
 * @since 2022-10-21
 */
@TableName("api_path")
@ApiModel(value = "ApiPathEntity对象", description = "第三方api路径表 ")
public class ApiPathEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("创建时间")
    private LocalDateTime createdTime;

    @ApiModelProperty("更新时间")
    private LocalDateTime updatedTime;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("路径")
    private String url;

    @ApiModelProperty("所属项目")
    private Integer projectId;

    @ApiModelProperty("是否存储 为空不存储")
    private Integer storageTableId;

    @ApiModelProperty("用户token")
    private Integer userRoleId;

    @ApiModelProperty("私有头信息 json")
    private String privateHeader;

    @ApiModelProperty("body json")
    private String body;

    @ApiModelProperty("请求类型")
    private Integer requestType;

    @ApiModelProperty("是否落库 空不储存")
    private Integer storageFieldId;

    @ApiModelProperty("排除 jsonlist过滤key")
    private String exclude;

    @ApiModelProperty("jsonPath表达式 为空时从原始数据匹配")
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getStorageTableId() {
        return storageTableId;
    }

    public void setStorageTableId(Integer storageTableId) {
        this.storageTableId = storageTableId;
    }

    public Integer getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Integer userRoleId) {
        this.userRoleId = userRoleId;
    }

    public String getPrivateHeader() {
        return privateHeader;
    }

    public void setPrivateHeader(String privateHeader) {
        this.privateHeader = privateHeader;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getRequestType() {
        return requestType;
    }

    public void setRequestType(Integer requestType) {
        this.requestType = requestType;
    }

    public Integer getStorageFieldId() {
        return storageFieldId;
    }

    public void setStorageFieldId(Integer storageFieldId) {
        this.storageFieldId = storageFieldId;
    }

    public String getExclude() {
        return exclude;
    }

    public void setExclude(String exclude) {
        this.exclude = exclude;
    }

    public String getJsonPath() {
        return jsonPath;
    }

    public void setJsonPath(String jsonPath) {
        this.jsonPath = jsonPath;
    }

    @Override
    public String toString() {
        return "ApiPathEntity{" +
        "createdTime=" + createdTime +
        ", updatedTime=" + updatedTime +
        ", id=" + id +
        ", url=" + url +
        ", projectId=" + projectId +
        ", storageTableId=" + storageTableId +
        ", userRoleId=" + userRoleId +
        ", privateHeader=" + privateHeader +
        ", body=" + body +
        ", requestType=" + requestType +
        ", storageFieldId=" + storageFieldId +
        ", exclude=" + exclude +
        ", jsonPath=" + jsonPath +
        "}";
    }
}
