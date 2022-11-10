package com.yun.bisecurity.service.impl;

import com.yun.bidatacommon.vo.Result;
import com.yun.bisecurity.dto.AbacRuleQueryDto;
import com.yun.bisecurity.entity.AbacMenuEntity;
import com.yun.bisecurity.entity.AbacRuleEntity;
import com.yun.bisecurity.dao.AbacRuleDao;
import com.yun.bisecurity.service.AbacMenuService;
import com.yun.bisecurity.service.AbacRuleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * ABAC规则表 服务实现类
 * </p>
 *
 * @author Sober
 * @since 2022-11-10
 */
@Slf4j
@Service
public class AbacRuleServiceImpl extends ServiceImpl<AbacRuleDao, AbacRuleEntity> implements AbacRuleService {

    @Resource
    private AbacRuleDao abacRuleDao;

    @Resource
    private AbacMenuService abacMenuService;

    /**
     * 查询当前请求规则
     * @param queryParam 查询实体
     * @return List<AbacRuleEntity>
     */
    @Override
    public List<AbacRuleEntity> queryNowAbacRule(AbacRuleQueryDto queryParam) {
        return abacRuleDao.queryNowAbacRule(queryParam);
    }

    /**
     * 查询规则
     * @param queryParam 查询参数
     * @return Result<List<AbacRuleEntity>>
     */
    @Override
    public Result<List<AbacRuleEntity>> queryAbacRule(AbacRuleQueryDto queryParam) {
        // 保证name不为null
        queryParam.setName(Optional.ofNullable(queryParam.getName()).orElse(""));
        return Result.OK(abacRuleDao.queryAbacRule(queryParam));
    }


    /**
     * 新增规则
     * @param abacRuleEntity ABAC规则实体
     * @return Result<AbacRuleEntity>
     */
    @Override
    public Result<AbacRuleEntity> saveAbacRule(AbacRuleEntity abacRuleEntity) {
        if (!save(abacRuleEntity)){
            // 处理失败
            return Result.ERROR("操作失败请刷新后再试",abacRuleEntity);
        }
        return Result.OK("操作成功",abacRuleEntity);
    }

    /**
     * 删除规则
     * @param id 需要删除的id
     * @return Result<Object>
     */
    @Override
    public Result<Object> removeAbacRule(String id) {
        List<AbacMenuEntity> abacMenuEntityList = abacMenuService.lambdaQuery().eq(AbacMenuEntity::getAbacRuleId,id).list();
        if (abacMenuEntityList.size() >= 1){
            return Result.ERROR("当前规则正在使用中，不可删除");
        }
        if (removeById(id)){
            return Result.OK("删除成功");
        }else {
            return Result.ERROR("操作失败请刷新后再试");
        }
    }

    /**
     * 更新规则
     * @param abacRuleEntity ABAC规则实体
     * @return Result<AbacRuleEntity>
     */
    @Override
    public Result<AbacRuleEntity> updateAbacRule(AbacRuleEntity abacRuleEntity) {
        if (updateById(abacRuleEntity)){
            return Result.OK("更新成功",abacRuleEntity);
        }else {
            return Result.ERROR("操作失败请刷新后再试",abacRuleEntity);
        }
    }
}
