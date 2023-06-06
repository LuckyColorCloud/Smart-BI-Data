package com.yun.bimessagecenter.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yun.bidatacommon.constant.CommonConstant;
import com.yun.bimessagecenter.dao.SocketConfigDao;
import com.yun.bimessagecenter.entity.MqConfigEntity;
import com.yun.bimessagecenter.entity.SocketConfigEntity;
import com.yun.bimessagecenter.entity.TopicEntity;
import com.yun.bimessagecenter.mq.kafka.KafKaConsumerEntity;
import com.yun.bimessagecenter.service.MqConfigService;
import com.yun.bimessagecenter.service.SocketConfigService;
import com.yun.bimessagecenter.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author Yun
 */
@Service("socketConfigService")
class SocketConfigServiceImpl extends ServiceImpl<SocketConfigDao, SocketConfigEntity> implements SocketConfigService {
    @Autowired
    TopicService topicService;
    @Autowired
    MqConfigService mqConfigService;
    private final Map<Integer, KafKaConsumerEntity> MAP = new ConcurrentHashMap<>();

    @Override
    public void online(SocketConfigEntity socketConfigEntity) {
        TopicEntity topicEntity = topicService.getById(socketConfigEntity.getTopicId());
        if (topicEntity == null) {
            throw new RuntimeException("主题不存在!");
        }
        MqConfigEntity mqConfigEntity = mqConfigService.getById(topicEntity.getMqId());
        if (mqConfigEntity == null) {
            throw new RuntimeException("MQ配置不存在!");
        }
        KafKaConsumerEntity kafKaConsumerEntity = MAP.getOrDefault(mqConfigEntity.getId(), new KafKaConsumerEntity());
        kafKaConsumerEntity.init(mqConfigEntity);
        kafKaConsumerEntity.subscribe(CommonConstant.SOCKET + socketConfigEntity.getPath(), topicEntity.getTopic());
        kafKaConsumerEntity.execute();
        MAP.put(mqConfigEntity.getId(), kafKaConsumerEntity);
        this.updateById(socketConfigEntity);
    }

    @Override
    public void init() {
        List<SocketConfigEntity> socketConfigEntities = this.getBaseMapper().selectList(new QueryWrapper<SocketConfigEntity>().lambda().eq(SocketConfigEntity::getStatus, true));
        socketConfigEntities.parallelStream().forEach(t -> {
            TopicEntity topicEntity = topicService.getById(t.getTopicId());
            if (topicEntity != null) {
                MqConfigEntity mqConfigEntity = mqConfigService.getById(topicEntity.getMqId());
                if (mqConfigEntity == null) {
                    return;
                }
                KafKaConsumerEntity kafKaConsumerEntity = MAP.getOrDefault(mqConfigEntity.getId(), new KafKaConsumerEntity());
                kafKaConsumerEntity.init(mqConfigEntity);
                kafKaConsumerEntity.subscribe(CommonConstant.SOCKET + t.getPath(), topicEntity.getTopic());
                kafKaConsumerEntity.execute();
                MAP.put(mqConfigEntity.getId(), kafKaConsumerEntity);
            }
        });
    }

    @Override
    public void offline(SocketConfigEntity socketConfigEntity) {
        TopicEntity topicEntity = topicService.getById(socketConfigEntity.getTopicId());
        if (topicEntity == null) {
            throw new RuntimeException("TOPIC不存在,下线失败!");
        }
        MqConfigEntity mqConfigEntity = mqConfigService.getById(topicEntity.getMqId());
        if (mqConfigEntity == null) {
            throw new RuntimeException("MQ配置不存在,下线失败!");
        }
        KafKaConsumerEntity kafKaConsumerEntity = MAP.getOrDefault(mqConfigEntity.getId(), new KafKaConsumerEntity());
        kafKaConsumerEntity.rmTopic(topicEntity.getTopic());
        MAP.put(mqConfigEntity.getId(), kafKaConsumerEntity);
    }


}