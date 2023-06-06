package com.yun.bidata.service;

import com.yun.bidata.dto.FormatDto;
import com.yun.bidata.dto.QueryDataDto;
import com.yun.bidatacommon.model.vo.Result;

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
     * @param dto 数据
     * @return 转换结果
     */
    Result<Object> formatConversion(FormatDto dto);


}
