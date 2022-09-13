package com.yun.bidatastorage.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yun.bidatastorage.entity.DataSourceEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 数据源管理
 *
 * @author Yun
 * @email 2289128964@qq.com
 * @date 2022-08-24 11:11:31
 */
@Mapper
public interface DataSourceDao extends BaseMapper<DataSourceEntity> {

}
