package com.yun.bisecurity.group.account.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yun.bidatacommon.model.vo.PageVo;
import com.yun.bidatacommon.model.vo.Result;
import com.yun.bisecurity.group.account.model.convert.AccountConvert;
import com.yun.bisecurity.group.account.model.entity.AccountEntity;
import com.yun.bisecurity.group.account.model.param.AccountAddParam;
import com.yun.bisecurity.group.account.model.param.AccountEditParam;
import com.yun.bisecurity.group.account.model.param.AccountQueryParam;
import com.yun.bisecurity.group.account.model.vo.AccountVo;
import com.yun.bisecurity.group.account.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Sober
 */
@Tag(name = "账户管理接口")
@Slf4j
@Validated
@RestController
@RequestMapping("/account")
public class AccountController {

    private final AccountService accountService;

    AccountController(AccountService accountService) {
        this.accountService = accountService;
    }


    /**
     * 分页查询
     * @param param 参数
     */
    @GetMapping("/page")
    @Operation(summary = "表单模型分页")
    @Parameters({
            @Parameter(name = "pageNumber", description = "页码"),
            @Parameter(name = "limit", description = "每页条数")
    })
    public Result<PageVo<AccountVo>> page(@ParameterObject @Valid AccountQueryParam param){
        return Result.OK(accountService.pageVo(
                param,
                Wrappers.<AccountEntity>lambdaQuery()
                        .like(AccountEntity::getEmail, param.getEmail()),
                AccountConvert.INSTANCE::convertList
        ));
    }

    /**
     * 新增账户
     * @param param 账户参数
     */
    @PostMapping
    @Operation(summary = "新增账户")
    public Result<AccountVo> save(@RequestBody @Valid AccountAddParam param) {

        return Result.OK(
                AccountConvert.INSTANCE
                        .convert(
                                accountService.saveAccount(
                                        AccountConvert.INSTANCE.convert(param)
                                )
                        )
        );
    }

    /**
     * 更新账户
     * @param param 账户参数
     */
    @PutMapping
    @Operation(summary = "更新账户")
    public Result<AccountVo> update(@RequestBody @Valid AccountEditParam param) {

        return Result.OK(
                AccountConvert.INSTANCE
                        .convert(
                                accountService.updateAccount(
                                        AccountConvert.INSTANCE.convert(param)
                                )
                        )
        );
    }

    /**
     * 删除
     * @param idList id列表
     */
    @DeleteMapping
    @Operation(summary = "删除账户")
    public Result<String> delete(@RequestBody @Valid @NotEmpty(message = "列表不可为空") List<Long> idList){
        accountService.deleteAccount(idList);
        return Result.OK();
    }
}
