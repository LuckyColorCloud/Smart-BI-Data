package com.yun.bisecurity.service;

import com.yun.bidatacommon.vo.Result;
import com.yun.bisecurity.dto.AbacRuleQueryDto;
import com.yun.bisecurity.entity.AbacRuleEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * <p>
 * ABAC规则表 服务类
 * </p>
 *
 * @author Sober
 * @since 2022-11-10
 */
public interface AbacRuleService extends IService<AbacRuleEntity> {

    /**
     * 查询当前请求规则
     * @param queryParam 查询参数
     * @return List<AbacRuleEntity>
     */
    List<AbacRuleEntity> queryNowAbacRule(AbacRuleQueryDto queryParam);

    /**
     * 查询规则
     * @param queryParam 查询参数
     * @return Result<List<AbacRuleEntity>>
     */
    Result<List<AbacRuleEntity>> queryAbacRule(AbacRuleQueryDto queryParam);

    /**
     * 保存规则
     * @param abacRuleEntity 实体
     * @return AbacRuleEntity
     */
    Result<AbacRuleEntity> saveAbacRule(AbacRuleEntity abacRuleEntity);

    /**
     * 删除规则
     * @param id 需要删除的id
     * @return Result<Object>
     */
    Result<Object> removeAbacRule(String id);

    /**
     * 更新规则
     * @param abacRuleEntity ABAC规则实体
     * @return Result<AbacRuleEntity>
     */
    Result<AbacRuleEntity> updateAbacRule(AbacRuleEntity abacRuleEntity);
}
