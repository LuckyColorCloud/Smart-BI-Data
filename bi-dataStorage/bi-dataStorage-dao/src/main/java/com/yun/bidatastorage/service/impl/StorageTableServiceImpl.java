package com.yun.bidatastorage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yun.bidatastorage.dao.StorageTableDao;
import com.yun.bidatastorage.entity.StorageTableEntity;
import org.springframework.stereotype.Service;
import com.yun.bidatastorage.service.StorageTableService;


/**
 * @author Yun
 */
@Service("storageTableService")
public class StorageTableServiceImpl extends ServiceImpl<StorageTableDao, StorageTableEntity> implements StorageTableService {


}