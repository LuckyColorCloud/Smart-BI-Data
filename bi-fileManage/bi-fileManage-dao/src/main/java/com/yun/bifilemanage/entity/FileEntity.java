package com.yun.bifilemanage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 文件管理
 *
 * @author Yun
 * @email 2289128964@qq.com
 * @date 2022-10-10 17:26:29
 */
@Data
@TableName("file")
public class FileEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 创建时间
     */
    private Date createdTime = new Date();
    /**
     * 更新时间
     */
    private Date updatedTime = new Date();
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件路径
     */
    private String filePath;
    /**
     * 文件大小
     */
    private Long size;
    /**
     * 文件md5
     */
    private String fileMd5;
    /**
     * 文件类型 0.csv 1.xlsx 2.json
     */
    private Integer fileType;
    /**
     * 存储数据源
     */
    private Integer sourceId;
    /**
     * 保存名称
     */
    private String saveName;
    /**
     * 是否删除
     */
    private Boolean status = false;

}
