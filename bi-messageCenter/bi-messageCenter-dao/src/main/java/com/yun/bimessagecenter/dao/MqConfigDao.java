package com.yun.bimessagecenter.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yun.bimessagecenter.entity.MqConfigEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * MQ消息配置
 * 
 * @author Yun
 */
@Mapper
public interface MqConfigDao extends BaseMapper<MqConfigEntity> {
	
}
