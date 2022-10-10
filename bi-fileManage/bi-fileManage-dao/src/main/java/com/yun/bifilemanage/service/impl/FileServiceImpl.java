package com.yun.bifilemanage.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yun.bifilemanage.dao.FileDao;
import com.yun.bifilemanage.entity.FileEntity;
import org.springframework.stereotype.Service;
import com.yun.bifilemanage.service.FileService;


/**
 * @author Yun
 */
@Service("fileService")
public class FileServiceImpl extends ServiceImpl<FileDao, FileEntity> implements FileService {


}