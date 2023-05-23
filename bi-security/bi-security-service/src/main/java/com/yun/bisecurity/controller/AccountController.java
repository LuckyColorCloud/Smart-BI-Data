package com.yun.bisecurity.controller;

import com.yun.bidatacommon.vo.Result;
import com.yun.bisecurity.entity.AccountEntity;
import com.yun.bisecurity.service.AccountService;
import com.yun.bisecurity.model.vo.AccountVo;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;

/**
 * @author Sober
 */
@Tag(name = "账户管理接口")
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
    @Operation(summary = "新增账户")
    public Result<AccountVo> save(@RequestBody AccountEntity accountEntity) {
        return accountService.saveAccount(accountEntity);
    }
}
