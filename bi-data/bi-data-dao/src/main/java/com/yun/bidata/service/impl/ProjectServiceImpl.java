package com.yun.bidata.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yun.bidata.dao.ProjectDao;
import com.yun.bidata.entity.ProjectEntity;
import org.springframework.stereotype.Service;
import com.yun.bidata.service.ProjectService;


/**
 * @author Yun
 */
@Service("projectService")
public class ProjectServiceImpl extends ServiceImpl<ProjectDao, ProjectEntity> implements ProjectService {

}