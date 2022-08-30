package com.yun.biapimanage.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yun.biapimanage.entity.ApiManageEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * api管理
 * 
 * @author Yun
 * @email 2289128964@qq.com
 * @date 2022-08-30 10:27:44
 */
@Mapper
public interface ApiManageDao extends BaseMapper<ApiManageEntity> {
	
}
