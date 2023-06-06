package com.yun.bisecurity.group.account.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yun.bidatacommon.service.BiService;
import com.yun.bisecurity.group.account.model.entity.AccountEntity;
import com.yun.bisecurity.group.account.model.param.AccountQueryParam;

import java.util.List;

/**
 * <p>
 * 账户表 服务类
 * </p>
 *
 * @author Sober
 * @since 2022-10-26
 */
public interface AccountService extends BiService<AccountEntity> {


    /**
     * 通过邮箱获取一个AccountEntity实体
     * @author Sober
     * @param email 邮箱
     * @return com.yun.bisecurity.entity.AccountEntity
     */
    AccountEntity getOneByEmail(String email);

    /**
     * 新增账户
     * @author Sober
     * @param entity 账户实体
     * @return com.yun.bidatacommon.vo.Result<com.yun.bisecurity.model.vo.AccountVo>
     */
    AccountEntity saveAccount(AccountEntity entity);

    /**
     * 更新账户
     * @author Sober
     * @param entity 账户实体
     * @return com.yun.bidatacommon.vo.Result<com.yun.bisecurity.model.vo.AccountVo>
     */
    AccountEntity updateAccount(AccountEntity entity);

    /**
     * 删除账户
     * @param idList 待删除的账户id列表
     */
    void deleteAccount(List<Long> idList);

}
