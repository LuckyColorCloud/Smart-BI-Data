package com.yun.bisecurity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sobercoding.loopauth.session.carryout.LoopAuthSession;
import com.yun.bidatacommon.vo.Result;
import com.yun.bisecurity.entity.AccountEntity;
import com.yun.bisecurity.dao.AccountDao;
import com.yun.bisecurity.service.AccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * <p>
 * 账户表 服务实现类
 * </p>
 *
 * @author Sober
 * @since 2022-10-26
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountDao, AccountEntity> implements AccountService {


    /**
     * 登录
     * @author Sober
     * @param email
     * @param password
     * @return com.yun.bidatacommon.vo.Result<java.lang.Object>
     */
    @Override
    public Result<Object> login(String email, String password) {
        // 密码加密
        password = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        // 获取Account数据实体
        Optional<AccountEntity> account = Optional.ofNullable(getOneByEmail(email));
        // 账户是否存在
        if (!account.isPresent()){
            return Result.ERROR("账户不存在");
        }
        if (!password.equals(account.get().getPassword())){
            return Result.ERROR("密码错误");
        }
        LoopAuthSession.login(account.get().getId().toString());
        return Result.OK("登录成功",LoopAuthSession.getTokenModel().getValue());
    }

    /**
     * 通过邮箱获取一个AccountEntity实体
     * @author Sober
     * @param email
     * @return com.yun.bisecurity.entity.AccountEntity
     */
    @Override
    public AccountEntity getOneByEmail(String email) {
        return this.lambdaQuery().eq(AccountEntity::getEmail, email).one();
    }

}
