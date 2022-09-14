package com.yun.bidatastorage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yun.bidatastorage.dao.SqlScriptDao;
import com.yun.bidatastorage.entity.SqlScriptEntity;
import com.yun.bidatastorage.service.SqlScriptService;
import org.springframework.stereotype.Service;



/**
 * @author Yun
 */
@Service("sqlScriptService")
public class SqlScriptServiceImpl extends ServiceImpl<SqlScriptDao, SqlScriptEntity> implements SqlScriptService {



}