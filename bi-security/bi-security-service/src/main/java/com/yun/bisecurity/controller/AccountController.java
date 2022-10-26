package com.yun.bisecurity.controller;

import com.yun.bidatacommon.vo.Result;
import com.yun.bisecurity.entity.AccountEntity;
import com.yun.bisecurity.service.AccountService;
import com.yun.bisecurity.model.vo.AccountVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Sober
 */
@Api(tags = "账户管理接口")
@Slf4j
@RestController
@RequestMapping("/account")
public class AccountController {

    @Resource
    private AccountService accountService;

    /**
     * 新增账户
     * @param accountEntity 账户实体
     */
    @PostMapping("/entity")
    @ApiOperation("新增账户")
    public Result<AccountVo> save(@RequestBody AccountEntity accountEntity) {
        return accountService.saveAccount(accountEntity);
    }
}
