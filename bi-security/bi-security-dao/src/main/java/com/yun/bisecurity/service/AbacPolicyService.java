package com.yun.bisecurity.service;

import com.yun.bisecurity.entity.AbacPolicyEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yun.bisecurity.param.AbacPolicyQueryParam;

import java.util.List;


/**
 * <p>
 *  abac规则服务类
 * </p>
 *
 * @author Sober
 * @since 2022-10-26
 */
public interface AbacPolicyService extends IService<AbacPolicyEntity> {

    /**
     * 条件查询
     * @author Sober
     * @param abacPolicyQueryParam 查询体
     * @return java.util.List<com.yun.bisecurity.entity.AbacPolicyEntity>
     */
    List<AbacPolicyEntity> queryList(AbacPolicyQueryParam abacPolicyQueryParam);
}
