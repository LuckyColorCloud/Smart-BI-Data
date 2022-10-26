package com.yun.bisecurity.dao;

import com.yun.bisecurity.entity.AccountInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 账户信息表 Mapper 接口
 * </p>
 *
 * @author Sober
 * @since 2022-10-26
 */
@Mapper
public interface AccountInfoDao extends BaseMapper<AccountInfoEntity> {

}
