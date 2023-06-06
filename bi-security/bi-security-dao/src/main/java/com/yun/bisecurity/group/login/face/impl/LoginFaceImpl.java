package com.yun.bisecurity.group.login.face.impl;

import com.sobercoding.loopauth.session.carryout.LoopAuthSession;
import com.yun.bidatacommon.model.vo.Result;
import com.yun.bisecurity.group.account.model.convert.AccountConvert;
import com.yun.bisecurity.group.account.model.entity.AccountEntity;
import com.yun.bisecurity.group.account.service.AccountService;
import com.yun.bisecurity.group.login.face.LoginFace;
import com.yun.bisecurity.group.login.model.param.LoginParam;
import com.yun.bisecurity.group.login.model.vo.LoginInfoVo;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * @author Sober
 */
@Component
public class LoginFaceImpl implements LoginFace {

    private final AccountService accountService;

    LoginFaceImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * 登录
     * @author Sober
     * @param param 登录参数
     * @return com.yun.bidatacommon.vo.Result<java.lang.Object>
     */
    @Override
    public Result<LoginInfoVo> login(LoginParam param) {
        // 获取Account数据实体
        Optional<AccountEntity> account = Optional.ofNullable(accountService.getOneByEmail(param.getEmail()));
        // 账户是否存在
        if (account.isEmpty()){
            return Result.ERROR("账户不存在");
        }
        // 密码加密
        String password = DigestUtils.md5DigestAsHex(param.getPassWord().getBytes(StandardCharsets.UTF_8));
        // 验证密码
        if (!password.equals(account.get().getPassWord())){
            return Result.ERROR("密码错误");
        }

        // 执行登录
        LoopAuthSession.login(account.get().getId());

        return Result.OK(
                "登录成功",
                LoginInfoVo.builder()
                        .token(LoopAuthSession.getTokenModel().getValue())
                        .account(AccountConvert.INSTANCE.convert(account.get()))
                        .build()
        );
    }
}
