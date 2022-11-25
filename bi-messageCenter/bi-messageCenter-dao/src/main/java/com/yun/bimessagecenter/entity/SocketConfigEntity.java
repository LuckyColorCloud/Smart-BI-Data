package com.yun.bimessagecenter.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * socket配置
 *
 * @author Yun
 */
@Data
@TableName("socket_config")
public class SocketConfigEntity implements Serializable {
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
     * 路径
     */
    private String path;
    /**
     * 消息ID
     */
    private String topicId;
    /**
     * 是否开启（上下线）
     */
    private Boolean status;

}
