package com.yun.bisecurity.service;

import com.yun.bidatacommon.vo.Result;
import com.yun.bisecurity.entity.AccountEntity;
import com.baomidou.mybatisplus.extension.service.IService;

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

}
