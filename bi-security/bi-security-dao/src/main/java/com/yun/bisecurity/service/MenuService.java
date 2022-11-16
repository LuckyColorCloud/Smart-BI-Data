package com.yun.bisecurity.service;

import com.yun.bisecurity.entity.AbacRuleEntity;
import com.yun.bisecurity.entity.MenuEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Sober
 * @since 2022-11-10
 */
public interface MenuService extends IService<MenuEntity> {

    /**
     * 查询菜单
     * @param title 标题
     * @param state 状态
     * @return List<MenuEntity>
     */
    List<MenuEntity> queryMenu(String title, int state);

    /**
     * 删除菜单
     * @param id 需要删除的id
     */
    boolean removeMenu(String id);
}
