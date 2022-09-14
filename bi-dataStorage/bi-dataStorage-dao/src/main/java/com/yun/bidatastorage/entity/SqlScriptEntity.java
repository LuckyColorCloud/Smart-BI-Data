package com.yun.bidatastorage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 查询数据库
 *
 * @author Yun
 * @email 2289128964@qq.com
 * @date 2022-09-14 09:18:16
 */
@Data
@TableName("sql_script")
public class SqlScriptEntity implements Serializable {
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
     * 数据库语句
     */
    private String sql;
    /**
     * 数据源
     */
    private Integer sourceId;

}
