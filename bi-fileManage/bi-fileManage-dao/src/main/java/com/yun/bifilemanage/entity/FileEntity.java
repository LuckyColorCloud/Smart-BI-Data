package com.yun.bifilemanage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
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
@Builder
@TableName("file")
public class FileEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 用户ID
     */
    private Long uid;
    /**
     * 创建时间
     */
    private Date createdTime = new Date();
    /**
     * 更新时间
     */
    private Date updatedTime = new Date();
    /**
     * 文件路径
     */
    private String filePath;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件md5
     */
    private String fileMd5;
    /**
     * 是否删除
     */
    private Boolean status;

}
