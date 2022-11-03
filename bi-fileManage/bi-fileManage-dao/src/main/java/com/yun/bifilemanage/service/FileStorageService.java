package com.yun.bifilemanage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yun.bidatacommon.vo.Result;
import com.yun.bifilemanage.entity.FileStorageEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author BlessingCR
 * @date 2022/11/03
 * @description
 */
public interface FileStorageService extends IService<FileStorageEntity> {
    /**
     * 保存数据并远程调用存储服务
     * @param file 文件
     * @param md5 md5
     * @param saveName 保存名称
     * @param sourceId 数据源Id
     * @return 结果
     */
    Result<Object> saveAndStorage(MultipartFile file, String md5, String saveName, Integer sourceId);

    /**
     * 删除数据 并删除表
     * @param id 删除id
     * @return 结果
     */
    Result<Object> dropTable(Integer id);
}
