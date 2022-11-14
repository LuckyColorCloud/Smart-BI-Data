package com.yun.bifilemanage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yun.bifilemanage.entity.FileEntity;
import com.yun.bifilemanage.vo.MinioFileVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * 文件管理
 *
 * @author Yun
 * @email 2289128964@qq.com
 * @date 2022-10-10 17:26:29
 */
public interface FileService extends IService<FileEntity> {


    /** * 上传文件到MinIIO
     * @param file 待上传文件
     * @return 文件url
     */
    Long upload(MultipartFile file);

    /** * MinIIO伪删除文件
     * @param id 文件id
     * @return T/F
     */
    Boolean delete(Long id);

    /**
     * 查询文件信息
     * @param id
     * @return
     */
    MinioFileVO queryById(Long id);

    /**
     * 获取文件
     * @param id
     * @return
     */
    File queryFileById(Long id);
}

