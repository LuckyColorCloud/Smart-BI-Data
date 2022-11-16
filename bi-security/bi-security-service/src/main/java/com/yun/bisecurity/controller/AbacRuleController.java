package com.yun.bisecurity.controller;


import com.yun.bidatacommon.vo.Result;
import com.yun.bisecurity.dto.AbacRuleQueryDto;
import com.yun.bisecurity.entity.AbacRuleEntity;
import com.yun.bisecurity.service.AbacRuleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

/**
 *
 * ABAC规则控制器
 *
 * @author Sober
 * @since 2022-11-10
 */

@Api(tags = "ABAC规则管理接口")
@Slf4j
@RestController
@RequestMapping("/abacRule")
public class AbacRuleController {

    @Resource
    private AbacRuleService abacRuleService;

    /**
     * 查询规则
     * @param abacRuleQueryDto ABAC规则实体
     */
    @GetMapping("/query")
    @ApiOperation("查询规则")
    public Result<List<AbacRuleEntity>> query(AbacRuleQueryDto abacRuleQueryDto) {
        return abacRuleService.queryAbacRule(abacRuleQueryDto);
    }

    /**
     * 新增规则
     * @param abacRuleEntity ABAC规则实体
     */
    @PostMapping("/save")
    @ApiOperation("新增规则")
    public Result<AbacRuleEntity> save(@RequestBody AbacRuleEntity abacRuleEntity) {
        return abacRuleService.saveAbacRule(abacRuleEntity);
    }

    /**
     * 更新规则
     * @param abacRuleEntity ABAC规则实体
     */
    @PostMapping("/updata")
    @ApiOperation("更新规则")
    public Result<AbacRuleEntity> updata(@RequestBody AbacRuleEntity abacRuleEntity) {
        return abacRuleService.updateAbacRule(abacRuleEntity);
    }

    /**
     * 删除规则
     * @param id 需要删除的id
     */
    @GetMapping("/remove")
    @ApiOperation("删除规则")
    public Result<Object> remove(String id) {
        return abacRuleService.removeAbacRule(id);
    }

}

