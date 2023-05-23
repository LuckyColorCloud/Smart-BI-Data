package com.yun.bisecurity.controller;


import com.yun.bidatacommon.vo.Result;
import com.yun.bisecurity.dto.AbacRuleQueryDto;
import com.yun.bisecurity.entity.AbacRuleEntity;
import com.yun.bisecurity.service.AbacRuleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import java.util.List;

/**
 *
 * ABAC规则控制器
 *
 * @author Sober
 * @since 2022-11-10
 */

@Tag(name = "ABAC规则管理接口")
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
    @Operation(summary = "查询规则")
    public Result<List<AbacRuleEntity>> query(AbacRuleQueryDto abacRuleQueryDto) {
        return abacRuleService.queryAbacRule(abacRuleQueryDto);
    }

    /**
     * 新增规则
     * @param abacRuleEntity ABAC规则实体
     */
    @PostMapping("/save")
    @Operation(summary = "新增规则")
    public Result<AbacRuleEntity> save(@RequestBody AbacRuleEntity abacRuleEntity) {
        return abacRuleService.saveAbacRule(abacRuleEntity);
    }

    /**
     * 更新规则
     * @param abacRuleEntity ABAC规则实体
     */
    @PostMapping("/updata")
    @Operation(summary = "更新规则")
    public Result<AbacRuleEntity> updata(@RequestBody AbacRuleEntity abacRuleEntity) {
        return abacRuleService.updateAbacRule(abacRuleEntity);
    }

    /**
     * 删除规则
     * @param id 需要删除的id
     */
    @GetMapping("/remove")
    @Operation(summary = "删除规则")
    public Result<Object> remove(String id) {
        return abacRuleService.removeAbacRule(id);
    }

}

