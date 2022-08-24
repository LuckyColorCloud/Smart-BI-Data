package com.yun.bidata.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yun.bidata.dao.ApiPathDao;
import com.yun.bidata.entity.ApiPathEntity;
import org.springframework.stereotype.Service;
import com.yun.bidata.service.ApiPathService;


/**
 * @author Yun
 */
@Service("apiPathService")
public class ApiPathServiceImpl extends ServiceImpl<ApiPathDao, ApiPathEntity> implements ApiPathService {


}