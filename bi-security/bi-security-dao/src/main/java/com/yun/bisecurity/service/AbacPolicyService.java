package com.yun.bisecurity.service;

import com.yun.bisecurity.entity.AbacPolicyEntity;
import com.baomidou.mybatisplus.extension.service.IService;

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
     * 根据路由即请求方法获取abac规则
     * @author Sober
     * @param route 路由
     * @param mode 请求方法
     * @return java.util.List<com.yun.bisecurity.entity.AbacPolicyEntity>
     */
    List<AbacPolicyEntity> getPolicySet(String route, String mode);
}
