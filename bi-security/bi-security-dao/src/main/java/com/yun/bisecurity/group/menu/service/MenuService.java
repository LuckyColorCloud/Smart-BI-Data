package com.yun.bisecurity.group.menu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yun.bisecurity.group.menu.model.entity.MenuEntity;

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
