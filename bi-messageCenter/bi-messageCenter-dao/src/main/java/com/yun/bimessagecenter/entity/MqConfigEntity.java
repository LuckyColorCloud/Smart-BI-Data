package com.yun.bimessagecenter.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * MQ消息配置
 *
 * @author Yun
 */
@Data
@TableName("mq_config")
public class MqConfigEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 更新时间
     */
    private Date updatedTime;
    /**
     * mq类型
     */
    private String type;
    /**
     * 项目id
     */
    private Integer projectId;
    /**
     * 生产者or消费者
     */
    private Integer consumer;
    /**
     * mq配置 需要是jsonObject
     */
    private String config;
    /**
     * 轮询时间 单位毫秒
     */
    private Integer time;
    /**
     * poll
     */
    private Integer poll;

}
