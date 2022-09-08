package com.yun.bidata.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @author Yun
 * @email 2289128964@qq.com
 * @date 2022-08-24 11:11:31
 */
@Data
@TableName("user_role")
public class UserRoleEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 更新时间
     */
    private Date updatedTime;
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * url
     */
    private String url;
    /**
     * domain
     */
    private String domain;
    /**
     * jsonPath
     */
    private String jsonPath;
    /**
     * 是否缓存
     */
    private boolean cache;
    /**
     * 缓存过期时间 单位分钟
     */
    private Integer cacheExpiration;
    /**
     * 请求头
     */
    private String header;
    /**
     * body
     */
    private String body;
    /**
     * 请求方式 0.GET 1.POST
     */
    private Integer requestType;
    /**
     * 名称
     */
    private String name;
    /**
     * 所属项目
     */
    private Integer projectId;

}
