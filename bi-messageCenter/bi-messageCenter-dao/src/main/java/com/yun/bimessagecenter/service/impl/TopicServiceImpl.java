package com.yun.bimessagecenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yun.bimessagecenter.dao.TopicDao;
import com.yun.bimessagecenter.entity.TopicEntity;
import com.yun.bimessagecenter.service.TopicService;
import org.springframework.stereotype.Service;


/**
 * @author Yun
 */
@Service("topicService")
public class TopicServiceImpl extends ServiceImpl<TopicDao, TopicEntity> implements TopicService {
}