package com.yun.bifilemanage.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yun.bifilemanage.entity.FileEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文件管理 
 * 
 * @author Yun
 * @email 2289128964@qq.com
 * @date 2022-10-10 17:26:29
 */
@Mapper
public interface FileDao extends BaseMapper<FileEntity> {
	
}
