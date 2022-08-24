package com.yun.bidata.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yun.bidata.dao.UserRoleDao;
import com.yun.bidata.entity.UserRoleEntity;
import org.springframework.stereotype.Service;
import com.yun.bidata.service.UserRoleService;


/**
 * @author Yun
 */
@Service("userRoleService")
public class UserRoleServiceImpl extends ServiceImpl<UserRoleDao, UserRoleEntity> implements UserRoleService {


}