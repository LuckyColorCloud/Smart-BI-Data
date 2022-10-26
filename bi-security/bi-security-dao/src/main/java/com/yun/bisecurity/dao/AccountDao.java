package com.yun.bisecurity.dao;

import com.yun.bisecurity.entity.AccountEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 账户表 Mapper 接口
 * </p>
 *
 * @author Sober
 * @since 2022-10-26
 */
@Mapper
public interface AccountDao extends BaseMapper<AccountEntity> {

}
