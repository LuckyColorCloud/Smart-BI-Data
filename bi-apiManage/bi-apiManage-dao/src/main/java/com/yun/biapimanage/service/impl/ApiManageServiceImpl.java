package com.yun.biapimanage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yun.biapimanage.dao.ApiManageDao;
import com.yun.biapimanage.entity.ApiManageEntity;
import com.yun.biapimanage.service.ApiManageService;
import org.springframework.stereotype.Service;


@Service("apiManageService")
public class ApiManageServiceImpl extends ServiceImpl<ApiManageDao, ApiManageEntity> implements ApiManageService {


}