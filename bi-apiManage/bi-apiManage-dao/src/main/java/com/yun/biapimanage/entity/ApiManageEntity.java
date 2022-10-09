package com.yun.biapimanage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * api管理
 *
 * @author Yun
 * @email 2289128964@qq.com
 * @date 2022-08-30 10:27:44
 */
@Data
@TableName("api_manage")
public class ApiManageEntity implements Serializable {
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
     * 请求路径
     */
    private String path;
    /**
     * 第三方转发id 或 sql id
     */
    private Integer apiId;
    /**
     * 请求参数
     */
    private String json;
    /**
     * 静态数据
     */
    private String result;
    /**
     * 是否鉴权
     */
    private boolean auth;
    /**
     * 接口类型 0.接口转发1.查询数据库 2.静态数据直接返回result 3.数据融合(根据id)4.数据融合(数组合并)
     */
    private Integer type;
    /**
     * 图表类型
     */
    private String chartType;
    /**
     * 图表参数
     */
    private String params;
    /**
     * 数据融合参数 只能是一种类型
     */
    private String apis;
    /**
     * 融合类型  0.接口转发1.查询数据库
     */
    private Integer fusion;

    /**
     * 融合参数=====>
     */
    private String  fusionParams;
}
