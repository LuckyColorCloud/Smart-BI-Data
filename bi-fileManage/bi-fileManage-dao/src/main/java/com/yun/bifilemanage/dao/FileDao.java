package com.yun.bifilemanage.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yun.bifilemanage.entity.FileEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文件
 * 
 * @author BlessingCR
 * @date 2022-11-03 17:26:29
 */
@Mapper
public interface FileDao extends BaseMapper<FileEntity> {
	
}
