package com.yun.bimessagecenter.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yun.bimessagecenter.entity.SocketConfigEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * socket配置
 * 
 * @author Yun
 */
@Mapper
public interface SocketConfigDao extends BaseMapper<SocketConfigEntity> {
	
}
