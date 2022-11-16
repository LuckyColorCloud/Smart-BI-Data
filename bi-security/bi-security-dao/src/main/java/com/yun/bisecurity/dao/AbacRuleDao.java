package com.yun.bisecurity.dao;

import com.yun.bisecurity.dto.AbacRuleQueryDto;
import com.yun.bisecurity.entity.AbacRuleEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * ABAC规则表 Mapper 接口
 * </p>
 *
 * @author Sober
 * @since 2022-11-10
 */
public interface AbacRuleDao extends BaseMapper<AbacRuleEntity> {

    /**
     * 查询当前请求规则
     * @param queryParam 查询条件
     * @return List<AbacRuleEntity>
     */
    List<AbacRuleEntity> queryNowAbacRule(@Param(value="queryParam") AbacRuleQueryDto queryParam);

    /**
     * 查询规则
     * @param queryParam 查询条件
     * @return List<AbacRuleEntity>
     */
    List<AbacRuleEntity> queryAbacRule(@Param(value="queryParam") AbacRuleQueryDto queryParam);

}
