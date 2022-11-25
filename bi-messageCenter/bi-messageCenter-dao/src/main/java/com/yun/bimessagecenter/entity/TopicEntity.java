package com.yun.bimessagecenter.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 主题配置
 *
 * @author Yun
 */
@Data
@TableName("topic")
public class TopicEntity implements Serializable {
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
     * 项目id
     */
    private Integer projectId;
    /**
     * socket_id
     */
    private String socketId;
    /**
     * 主题
     */
    private String topic;
    /**
     * 数据清洗方式 0.普通1分组
     */
    private Integer type;
    /**
     * jsonPath
     */
    private String exclude;
    /**
     * 转换参数
     */
    private String mapKey;
    /**
     * mqID
     */
    private Integer mqId;
    /**
     * 参数
     */
    @TableField(exist = false)
    private String path;
}
