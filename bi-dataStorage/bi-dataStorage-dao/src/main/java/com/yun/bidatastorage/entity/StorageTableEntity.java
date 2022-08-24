package com.yun.bidatastorage.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 存储表
 *
 * @author Yun
 * @email 2289128964@qq.com
 * @date 2022-08-24 11:11:31
 */
@Data
@TableName("storage_table")
public class StorageTableEntity implements Serializable {
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
     * 存储数据源 数据源id
     */
    private Integer sourceId;
    /**
     * 存储名称 表名
     */
    private String saveName;
    /**
     * 储存方式 1.追加2.全量3.主键
     */
    private Integer saveType;
    /**
     * 主键 list可以多个字段
     */
    private String primaryKey;
    /**
     * 储存字段 list可以多个字段
     */
    private String storageField;

}
