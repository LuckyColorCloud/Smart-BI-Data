package com.yun.bidata.service;

import com.yun.bidata.api.dto.QueryDataDto;

/**
 * 数据清洗接口
 * @author Yun
 */
public interface DataService {

    /**
     * 获取数据
     * @param dto 获取数据类
     * @return 结果
     */
    Object getData(QueryDataDto dto);
}
