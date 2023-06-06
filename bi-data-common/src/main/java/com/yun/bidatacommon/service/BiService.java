package com.yun.bidatacommon.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yun.bidatacommon.model.param.PageParam;
import com.yun.bidatacommon.model.vo.PageVo;

import java.util.List;
import java.util.function.Function;

/**
 * @author Sober
 */
public interface BiService<T> extends IService<T> {


    /**
     * 分页
     *
     * @param param        分页参数
     * @param queryWrapper 查询条件
     * @param convertList  数据转换
     * @param <P>          分页参数类型
     * @param <V>          数据转换后的类型
     * @return PageVo<V>
     */
    <P extends PageParam, V> PageVo<V> pageVo(P param,
                                              LambdaQueryWrapper<T> queryWrapper,
                                              Function<List<T>, List<V>> convertList);

    /**
     * 分页
     *
     * @param param       分页参数
     * @param convertList 数据转换
     * @param <P>         分页参数类型
     * @param <V>         数据转换后的类型
     * @return PageVo<V>
     */
    <P extends PageParam, V> PageVo<V> pageVo(P param,
                                              Function<List<T>, List<V>> convertList);
}

