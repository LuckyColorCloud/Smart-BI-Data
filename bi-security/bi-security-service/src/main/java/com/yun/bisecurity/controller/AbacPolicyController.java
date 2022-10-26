package com.yun.bisecurity.controller;

import com.yun.bidatacommon.vo.Result;
import com.yun.bisecurity.entity.AbacPolicyEntity;
import com.yun.bisecurity.param.AbacPolicyQueryParam;
import com.yun.bisecurity.service.AbacPolicyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Sober
 */
@Api(tags = "AbAc规则管理")
@Slf4j
@RestController
@RequestMapping("/abacPolicy")
public class AbacPolicyController {

    @Resource
    private AbacPolicyService abacPolicyService;

    /**
     * 查询abac规则
     */
    @GetMapping("/query")
    @ApiOperation("查询abac规则")
    public Result<List<AbacPolicyEntity>> queryList(AbacPolicyQueryParam abacPolicyQueryParam) {
        return Result.OK("查询成功", abacPolicyService.queryList(abacPolicyQueryParam));
    }

    /**
     * 新增abac规则
     * @param abacPolicyEntity 规则实体
     */
    @PostMapping("/entity")
    @ApiOperation("新增abac规则")
    public Result<AbacPolicyEntity> save(@RequestBody AbacPolicyEntity abacPolicyEntity) {
        if (!abacPolicyService.save(abacPolicyEntity)){
            return Result.OK("遇到错误请重试");
        }
        return Result.OK("新增成功", abacPolicyEntity);
    }

}
