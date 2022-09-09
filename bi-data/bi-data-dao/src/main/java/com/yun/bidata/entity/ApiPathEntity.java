package com.yun.bidata.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 第三方api路径表
 *
 * @author Yun
 * @email 2289128964@qq.com
 * @date 2022-08-24 11:11:31
 */
@Data
@TableName("api_path")
public class  ApiPathEntity implements Serializable {
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
     * 路径
     */
    private String url;
    /**
     * 所属项目
     */
    private Integer projectId;
    /**
     * 是否存储 为空不存储
     */
    private Integer storageTableId;
    /**
     * 用户token
     */
    private Integer userRoleId;
    /**
     * 私有头信息 json
     */
    private String privateHeader;
    /**
     * body json
     */
    private String body;
    /**
     * 请求类型 0.GET 1.POST
     */
    private Integer requestType;
    /**
     * 是否落库 空不储存
     */
    private Integer storageFieldId;
    /**
     * 保存 需要的key  listJson
     */
    private String exclude;
    /**
     * jsonPath表达式 为空时从原始数据匹配
     */
    private String jsonPath;

}
