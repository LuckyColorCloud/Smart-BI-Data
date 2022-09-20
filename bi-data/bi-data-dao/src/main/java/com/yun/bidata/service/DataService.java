package com.yun.bidata.service;

import com.yun.bidata.dto.QueryDataDto;
import com.yun.bidataconnmon.vo.Result;

/**
 * 数据清洗接口
 *
 * @author Yun
 */
public interface DataService {

    /**
     * 获取数据
     *
     * @param dto 获取数据类
     * @return 结果
     */
    Result<Object> getData(QueryDataDto dto);

    /**
     * 数据格式转换
     * @param data 数据
     * @param type 转换类型
     * @return 转换结果
     */
    Result<Object> formatConversion(Object data,Integer type);
}
