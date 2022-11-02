package com.yun.bifilemanage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yun.bifilemanage.entity.FileEntity;
import org.springframework.web.multipart.MultipartFile;

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
    public String upload(MultipartFile file);

    /** * MinIIO伪删除文件
     * @param path 文件路径
     * @return T/F
     */
    public Boolean delete(String path);
}

