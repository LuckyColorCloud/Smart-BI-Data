package com.yun.bidatacommon.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yun.bidatacommon.model.param.PageParam;
import com.yun.bidatacommon.model.vo.PageVo;

import java.util.List;
import java.util.function.Function;

/**
 * @author Sober
 */
public class BiServiceImpl<M extends BaseMapper<T>, T>
        extends ServiceImpl<M, T>
        implements BiService<T> {

    /**
     * 分页
     * @param param 分页参数
     * @param queryWrapper 查询条件
     * @param convertList 数据转换
     * @param <P> 分页参数类型
     * @param <V> 数据转换后的类型
     */
    @Override
    public <P extends PageParam, V> PageVo<V> pageVo(P param,
                                                     LambdaQueryWrapper<T> queryWrapper,
                                                     Function<List<T>, List<V>> convertList) {
        Page<T> page = page(
                getPage(param),
                queryWrapper
        );
        return PageVo.result(convertList.apply(page.getRecords()), page);
    }

    /**
     * 分页
     * @param param 分页参数
     * @param convertList 数据转换
     * @param <P> 分页参数类型
     * @param <V> 数据转换后的类型
     */
    @Override
    public <P extends PageParam, V> PageVo<V> pageVo(P param,
                                                     Function<List<T>, List<V>> convertList) {
        Page<T> page = page(
                getPage(param)
        );
        return PageVo.result(convertList.apply(page.getRecords()), page);
    }

    /**
     * 分页vo转换
     * @param param
     * @return
     */
    protected Page<T> getPage(PageParam param) {
        return new Page<>(param.getPageNumber(), param.getLimit());
    }

}
