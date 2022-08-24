package com.yun.bidatastorage.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yun.bidatastorage.entity.StorageTableEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 存储表 
 * 
 * @author Yun
 * @email 2289128964@qq.com
 * @date 2022-08-24 11:11:31
 */
@Mapper
public interface StorageTableDao extends BaseMapper<StorageTableEntity> {
	
}
