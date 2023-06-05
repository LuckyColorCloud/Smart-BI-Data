package com.yun.bisecurity.group.account.dao;

import com.yun.bisecurity.group.account.model.entity.AccountEntity;
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

    /**
     * 插入 忽略报错，通过布尔返回成功状态
     * @param entity
     * @return
     */
    boolean insertIgnore(AccountEntity entity);

    /**
     * 根据id更新账户 忽略报错，通过布尔返回成功状态
     * @param entity
     * @return
     */
    boolean updateIgnoreById(AccountEntity entity);
}
