package com.yun.bidatastorage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yun.bidatastorage.dao.DataSourceDao;
import com.yun.bidatastorage.entity.DataSourceEntity;
import com.yun.bidatastorage.service.DataSourceService;
import org.springframework.stereotype.Service;


/**
 * @author Yun
 */
@Service("datasourceService")
public class DatasourceServiceImpl extends ServiceImpl<DataSourceDao, DataSourceEntity> implements DataSourceService {


}