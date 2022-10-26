package com.yun.bisecurity.service;

import com.yun.bidatacommon.vo.Result;
import com.yun.bisecurity.entity.AccountEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yun.bisecurity.model.vo.AccountVo;

/**
 * <p>
 * 账户表 服务类
 * </p>
 *
 * @author Sober
 * @since 2022-10-26
 */
public interface AccountService extends IService<AccountEntity> {

    /**
     * 登录
     * @author Sober
     * @param email
     * @param password
     * @return com.yun.bidatacommon.vo.Result<java.lang.Object>
     */
    Result<Object> login(String email, String password);


    /**
     * 通过邮箱获取一个AccountEntity实体
     * @author Sober
     * @param email
     * @return com.yun.bisecurity.entity.AccountEntity
     */
    AccountEntity getOneByEmail(String email);

    /**
     * 新增账户
     * @author Sober
     * @param accountEntity 账户实体
     * @return com.yun.bidatacommon.vo.Result<com.yun.bisecurity.model.vo.AccountVo>
     */
    Result<AccountVo> saveAccount(AccountEntity accountEntity);
}
