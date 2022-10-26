package com.yun.bisecurity.service.impl;

import com.yun.bisecurity.entity.AbacPolicyEntity;
import com.yun.bisecurity.dao.AbacPolicyDao;
import com.yun.bisecurity.service.AbacPolicyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Sober
 * @since 2022-10-26
 */
@Service
public class AbacPolicyServiceImpl extends ServiceImpl<AbacPolicyDao, AbacPolicyEntity> implements AbacPolicyService {

    /**
     * 根据路由即请求方法获取abac规则
     * @author Sober
     * @param route 路由
     * @param mode 请求方法
     * @return java.util.List<com.yun.bisecurity.entity.AbacPolicyEntity>
     */
    @Override
    public List<AbacPolicyEntity> getPolicySet(String route, String mode) {
        return lambdaQuery().eq(AbacPolicyEntity::getRoute, route)
                .eq(AbacPolicyEntity::getMode, mode)
                .list();
    }
}
