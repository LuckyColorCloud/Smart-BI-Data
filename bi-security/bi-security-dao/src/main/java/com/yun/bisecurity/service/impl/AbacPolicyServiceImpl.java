package com.yun.bisecurity.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yun.bisecurity.entity.AbacPolicyEntity;
import com.yun.bisecurity.dao.AbacPolicyDao;
import com.yun.bisecurity.dto.AbacPolicyQueryDto;
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
     * 条件查询
     * @author Sober
     * @param abacPolicyQueryDto 查询体
     * @return java.util.List<com.yun.bisecurity.entity.AbacPolicyEntity>
     */
    @Override
    public List<AbacPolicyEntity> queryList(AbacPolicyQueryDto abacPolicyQueryDto) {
        // 组建查询参数
        QueryWrapper<AbacPolicyEntity> queryWrapper = new QueryWrapper<>();
        if (ObjectUtil.isNotEmpty(abacPolicyQueryDto.getName())){
            queryWrapper.lambda()
                    .like(AbacPolicyEntity::getName, abacPolicyQueryDto.getName());
        }
        if (ObjectUtil.isNotEmpty(abacPolicyQueryDto.getRoute())){
            queryWrapper.lambda()
                    .like(AbacPolicyEntity::getRoute, abacPolicyQueryDto.getRoute());
        }
        if (ObjectUtil.isNotEmpty(abacPolicyQueryDto.getMode())){
            queryWrapper.lambda()
                    .eq(AbacPolicyEntity::getMode, abacPolicyQueryDto.getMode());
        }
        return this.list(queryWrapper);
    }
}
