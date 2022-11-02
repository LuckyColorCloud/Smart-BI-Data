package com.yun.bisecurity.controller;

import com.yun.bidatacommon.vo.Result;
import com.yun.bisecurity.entity.AbacPolicyEntity;
import com.yun.bisecurity.dto.AbacPolicyQueryDto;
import com.yun.bisecurity.service.AbacPolicyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
    public Result<List<AbacPolicyEntity>> queryList(AbacPolicyQueryDto abacPolicyQueryDto) {
        return Result.OK("查询成功", abacPolicyService.queryList(abacPolicyQueryDto));
    }

    /**
     * 新增abac规则
     * @param abacPolicyEntity 规则实体
     */
    @PostMapping("/save")
    @ApiOperation("新增abac规则")
    public Result<AbacPolicyEntity> save(@RequestBody AbacPolicyEntity abacPolicyEntity) {
        if (!abacPolicyService.save(abacPolicyEntity)){
            return Result.OK("遇到错误请重试");
        }
        return Result.OK("新增成功", abacPolicyEntity);
    }

    /**
     * 更新abac规则
     * @param abacPolicyEntity 规则实体
     */
    @PostMapping("/updata")
    @ApiOperation("更新abac规则")
    public Result<AbacPolicyEntity> update(@RequestBody AbacPolicyEntity abacPolicyEntity) {
        if (!abacPolicyService.updateById(abacPolicyEntity)){
            return Result.OK("遇到错误请重试");
        }
        return Result.OK("更新成功", abacPolicyEntity);
    }

    /**
     * 删除abac规则
     * @param abacPolicyId id
     */
    @GetMapping("/delete")
    @ApiOperation("删除abac规则")
    public Result<String> delete(String abacPolicyId) {
        if (!abacPolicyService.removeById(abacPolicyId)){
            return Result.OK("遇到错误请重试");
        }
        return Result.OK("删除成功", abacPolicyId);
    }


}
