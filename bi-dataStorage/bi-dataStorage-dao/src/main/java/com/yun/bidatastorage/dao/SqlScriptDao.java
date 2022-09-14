package com.yun.bidatastorage.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yun.bidatastorage.entity.SqlScriptEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 查询数据库 
 * 
 * @author Yun
 * @email 2289128964@qq.com
 * @date 2022-09-14 09:18:16
 */
@Mapper
public interface SqlScriptDao extends BaseMapper<SqlScriptEntity> {
	
}
