package com.yun.bimessagecenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yun.bimessagecenter.entity.SocketConfigEntity;

/**
 * socket配置
 *
 * @author Yun
 */
public interface SocketConfigService extends IService<SocketConfigEntity> {

    /**
     * 上线WebSocket
     *
     * @param socketConfigEntity socket配置
     */
    void online(SocketConfigEntity socketConfigEntity);

    /**
     * 初始化
     */
    void init();

    /**
     * 下线
     *
     * @param socketConfigEntity socket配置
     */
    void offline(SocketConfigEntity socketConfigEntity);
}

