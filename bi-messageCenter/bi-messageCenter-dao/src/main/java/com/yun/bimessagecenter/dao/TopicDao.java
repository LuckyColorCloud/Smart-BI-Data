package com.yun.bimessagecenter.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yun.bimessagecenter.entity.TopicEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 主题配置
 * 
 * @author Yun
 */
@Mapper
public interface TopicDao extends BaseMapper<TopicEntity> {
	
}
