package com.yun.bisecurity.group.account.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yun.bidatacommon.exception.CommonException;
import com.yun.bidatacommon.model.vo.PageVo;
import com.yun.bidatacommon.service.BiServiceImpl;
import com.yun.bisecurity.group.account.dao.AccountDao;
import com.yun.bisecurity.group.account.model.entity.AccountEntity;
import com.yun.bisecurity.group.account.model.vo.AccountVo;
import com.yun.bisecurity.group.account.service.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 账户信息表 服务实现类
 * </p>
 *
 * @author Sober
 * @since 2022-10-26
 */
@Service
public class AccountServiceImpl extends BiServiceImpl<AccountDao, AccountEntity> implements AccountService {


    /**
     * 通过邮箱获取一个AccountEntity实体
     * @author Sober
     * @param email 邮箱
     * @return com.yun.bisecurity.entity.AccountEntity
     */
    @Override
    public AccountEntity getOneByEmail(String email) {
        return this.lambdaQuery().eq(AccountEntity::getEmail, email).one();
    }

    /**
     * 新增账户
     * @author Sober
     * @param entity 账户实体
     * @return com.yun.bidatacommon.vo.Result<com.yun.bisecurity.group.account.model.entity.AccountEntity>
     */
    @Override
    public AccountEntity saveAccount(AccountEntity entity) {
        // 加密密码
        entity.setPassWord(
                DigestUtils.md5DigestAsHex(entity.getEmail().getBytes(StandardCharsets.UTF_8))
        );
        // 保存账户
        if (!baseMapper.insertIgnore(entity)) {
            throw new CommonException("邮箱已存在");
        }
        return entity;
    }

    /**
     * 更新账户
     * @author Sober
     * @param entity 账户实体
     * @return com.yun.bidatacommon.vo.Result<com.yun.bisecurity.group.account.model.entity.AccountEntity>
     */
    @Override
    public AccountEntity updateAccount(AccountEntity entity) {
        // 更新账户
        if (!baseMapper.updateIgnoreById(entity)) {
            throw new CommonException("邮箱已存在");
        }
        return entity;
    }

    /**
     * 删除账户
     * @param idList 待删除的账户id列表
     */
    @Override
    public void deleteAccount(List<Long> idList) {
        baseMapper.deleteBatchIds(idList);
    }

}
