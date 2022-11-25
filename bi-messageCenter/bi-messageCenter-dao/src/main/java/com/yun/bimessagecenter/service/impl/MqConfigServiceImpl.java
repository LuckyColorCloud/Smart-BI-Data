package com.yun.bimessagecenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yun.bimessagecenter.dao.MqConfigDao;
import com.yun.bimessagecenter.entity.MqConfigEntity;
import com.yun.bimessagecenter.service.MqConfigService;
import org.springframework.stereotype.Service;


/**
 * @author Yun
 */
@Service("mqConfigService")
public class MqConfigServiceImpl extends ServiceImpl<MqConfigDao, MqConfigEntity> implements MqConfigService {

}