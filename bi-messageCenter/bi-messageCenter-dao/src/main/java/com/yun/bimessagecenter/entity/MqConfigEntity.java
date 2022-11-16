package com.yun.bimessagecenter.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * MQ配置表
 * </p>
 *
 * @author Yun
 * @since 2022-11-16
 */
@TableName("mq_config")
@ApiModel(value = "MqConfigEntity对象", description = "MQ配置表")
public class MqConfigEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("创建人")
    private String createdBy;

    @ApiModelProperty("创建时间")
    private LocalDateTime createdTime;

    @ApiModelProperty("更新人")
    private String updatedBy;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("更新时间")
    private LocalDateTime updatedTime;

    @ApiModelProperty("mq类型")
    private String type;

    @ApiModelProperty("生产者or消费者 0.生产者 1消费者")
    private Integer consumer;

    @ApiModelProperty("mq配置 需要是jsonObject")
    private String config;

    @ApiModelProperty("轮询时间 单位毫秒")
    private Integer time;

    @ApiModelProperty("poll")
    private Integer poll;


    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getConsumer() {
        return consumer;
    }

    public void setConsumer(Integer consumer) {
        this.consumer = consumer;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getPoll() {
        return poll;
    }

    public void setPoll(Integer poll) {
        this.poll = poll;
    }

    @Override
    public String toString() {
        return "MqConfigEntity{" +
        "createdBy=" + createdBy +
        ", createdTime=" + createdTime +
        ", updatedBy=" + updatedBy +
        ", id=" + id +
        ", updatedTime=" + updatedTime +
        ", type=" + type +
        ", consumer=" + consumer +
        ", config=" + config +
        ", time=" + time +
        ", poll=" + poll +
        "}";
    }
}
