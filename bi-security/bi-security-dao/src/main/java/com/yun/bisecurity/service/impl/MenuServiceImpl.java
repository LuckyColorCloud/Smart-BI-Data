package com.yun.bisecurity.service.impl;

import com.yun.bisecurity.entity.MenuEntity;
import com.yun.bisecurity.dao.MenuDao;
import com.yun.bisecurity.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Sober
 * @since 2022-11-10
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuDao, MenuEntity> implements MenuService {

    /**
     * 查询菜单
     * @param title 标题
     * @param state 状态
     * @return List<MenuDto>
     */
    @Override
    public List<MenuEntity> queryMenu(String title, int state) {
        // 查询所有状态
        int stateAll = 2;

        return lambdaQuery().func(item -> {
            if (!"".equals(title)){
                item.like(MenuEntity::getTitle, title);
            }
        }).func(item -> {
            if (state != stateAll){
                item.eq(MenuEntity::getState, state);
            }
        }).list();
    }

    /**
     * 删除菜单
     * @param id 需要删除的id
     */
    @Override
    public boolean removeMenu(String id) {
        // TODO
        // 需要验证菜单是否被role使用
        return removeById(id);
    }

}
