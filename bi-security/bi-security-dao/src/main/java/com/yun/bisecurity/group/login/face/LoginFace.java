package com.yun.bisecurity.group.login.face;

import com.yun.bidatacommon.model.vo.Result;
import com.yun.bisecurity.group.login.model.param.LoginParam;
import com.yun.bisecurity.group.login.model.vo.LoginInfoVo;

/**
 * @author Sober
 */
public interface LoginFace {

    /**
     * 登录
     * @author Sober
     * @param param
     * @return com.yun.bidatacommon.vo.Result<java.lang.Object>
     */
    Result<LoginInfoVo> login(LoginParam param);
}
