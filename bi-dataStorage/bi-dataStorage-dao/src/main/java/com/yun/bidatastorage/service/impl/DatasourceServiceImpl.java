package com.yun.bidatastorage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yun.bidatastorage.dao.DatasourceDao;
import com.yun.bidatastorage.entity.DatasourceEntity;
import org.springframework.stereotype.Service;
import com.yun.bidatastorage.service.DatasourceService;


/**
 * @author Yun
 */
@Service("datasourceService")
public class DatasourceServiceImpl extends ServiceImpl<DatasourceDao, DatasourceEntity> implements DatasourceService {


}