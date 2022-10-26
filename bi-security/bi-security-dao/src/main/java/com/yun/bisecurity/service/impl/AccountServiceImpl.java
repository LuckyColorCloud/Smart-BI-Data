package com.yun.bisecurity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sobercoding.loopauth.session.carryout.LoopAuthSession;
import com.yun.bidatacommon.vo.Result;
import com.yun.bisecurity.entity.AccountEntity;
import com.yun.bisecurity.dao.AccountDao;
import com.yun.bisecurity.entity.AccountInfoEntity;
import com.yun.bisecurity.model.vo.AccountVo;
import com.yun.bisecurity.service.AccountInfoService;
import com.yun.bisecurity.service.AccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
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

    @Resource
    private AccountInfoService accountInfoService;

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
        LoopAuthSession.login(account.get().getId());
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

    /**
     * 新增账户
     * @author Sober
     * @param accountEntity 账户实体
     * @return com.yun.bidatacommon.vo.Result<com.yun.bisecurity.model.vo.AccountVo>
     */
    @Override
    public Result<AccountVo> saveAccount(AccountEntity accountEntity) {
        Optional<AccountEntity> optionalAccount = Optional
                .ofNullable(getOneByEmail(accountEntity.getEmail()));
        if (optionalAccount.isPresent()){
            return Result.ERROR("邮箱已存在",null);
        }
        // 加密密码
        accountEntity.setPassword(
                DigestUtils.md5DigestAsHex(accountEntity.getPassword().getBytes(StandardCharsets.UTF_8))
        );
        // 保存账户
        save(accountEntity);
        // 保存账户信息
        AccountInfoEntity accountInfo = AccountInfoEntity.builder()
                .accountId(accountEntity.getId())
                .gender((short) 2)
                .build();
        accountInfoService.save(accountInfo);
        return Result.OK("新增成功", AccountVo.entityToVo.call(accountEntity,accountInfo));
    }

}
