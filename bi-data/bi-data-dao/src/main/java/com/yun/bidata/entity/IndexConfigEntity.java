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
 * 指标表
 * </p>
 *
 * @author Yun
 * @since 2022-10-26
 */
@TableName("index_config")
@Schema(title = "IndexConfigEntity对象", description = "指标表")
public class IndexConfigEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "创建时间")
    private LocalDateTime createdTime;

    @Schema(description = "更新时间")
    private LocalDateTime updatedTime;

    @Schema(description = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Schema(description = "所属项目 项目id")
    private Long projectId;

    @Schema(description = "请求路径 域名后面的路径")
    private String url;

    @Schema(description = "使用token 使用的tokenID")
    private Long tokenId;

    @Schema(description = "请求方式 请求方式如get，post")
    private String requestType;

    @Schema(description = "私有头信息 私有头信息一般是不会再有专门接口的私有信息")
    private String privateHeader;

    @Schema(description = "过滤字段 集合类型的需要报错的key")
    private String exclude;

    @Schema(description = "json_path 利用jsonPath作为第一层数据处理")
    private String jsonPath;

    @Schema(description = "请求body 请求body数据")
    private String body;

    @Schema(description = "动态参数 动态请求参数从接口的body获取这里配置需要拿取的key")
    private String param;

    @Schema(description = "JS脚本 最终处理方案")
    private String jsScript;

    @Schema(description = "转换参数 转换字段")
    private String mapKey;

    @Schema(description = "类型 0.普通类型 1.分组")
    private Integer type;

    @Schema(description = "name 名称")
    private String name;


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

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getTokenId() {
        return tokenId;
    }

    public void setTokenId(Long tokenId) {
        this.tokenId = tokenId;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getPrivateHeader() {
        return privateHeader;
    }

    public void setPrivateHeader(String privateHeader) {
        this.privateHeader = privateHeader;
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getJsScript() {
        return jsScript;
    }

    public void setJsScript(String jsScript) {
        this.jsScript = jsScript;
    }

    public String getMapKey() {
        return mapKey;
    }

    public void setMapKey(String mapKey) {
        this.mapKey = mapKey;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "IndexConfigEntity{" +
        "createdTime=" + createdTime +
        ", updatedTime=" + updatedTime +
        ", id=" + id +
        ", projectId=" + projectId +
        ", url=" + url +
        ", tokenId=" + tokenId +
        ", requestType=" + requestType +
        ", privateHeader=" + privateHeader +
        ", exclude=" + exclude +
        ", jsonPath=" + jsonPath +
        ", body=" + body +
        ", param=" + param +
        ", jsScript=" + jsScript +
        ", mapKey=" + mapKey +
        ", type=" + type +
        ", name=" + name +
        "}";
    }
}
