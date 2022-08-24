package com.yun.bidata.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yun.bidata.entity.ApiPathEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 第三方api路径表 
 * 
 * @author Yun
 * @email 2289128964@qq.com
 * @date 2022-08-24 11:11:31
 */
@Mapper
public interface ApiPathDao extends BaseMapper<ApiPathEntity> {
	
}
