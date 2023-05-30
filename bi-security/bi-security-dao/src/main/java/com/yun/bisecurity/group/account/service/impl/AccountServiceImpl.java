package com.yun.bisecurity.group.account.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sobercoding.loopauth.session.carryout.LoopAuthSession;
import com.yun.bidatacommon.vo.Result;
import com.yun.bisecurity.group.account.model.convert.AccountConvert;
import com.yun.bisecurity.group.account.model.entity.AccountEntity;
import com.yun.bisecurity.group.account.dao.AccountDao;
import com.yun.bisecurity.group.account.service.AccountService;
import com.yun.bisecurity.group.account.model.vo.AccountVo;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * <p>
 * 账户信息表 服务实现类
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
    public Result<String> login(String email, String password) {
        // 密码加密
        password = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        // 获取Account数据实体
        Optional<AccountEntity> account = Optional.ofNullable(getOneByEmail(email));
        // 账户是否存在
        if (account.isEmpty()){
            return Result.ERROR("账户不存在");
        }
        if (!password.equals(account.get().getPassWord())){
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
        // 加密密码
        accountEntity.setPassWord(
                DigestUtils.md5DigestAsHex(accountEntity.getPassWord().getBytes(StandardCharsets.UTF_8))
        );
        // 保存账户
        if (!baseMapper.insertIgnore(accountEntity)) {
            return Result.ERROR("邮箱已存在");
        }
        return Result.OK("新增成功", AccountConvert.INSTANCE.convert(accountEntity));
    }

}
