package com.yun.bidatacommon.db.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yun.bidatacommon.db.query.QueryParser;
import com.yun.bidatacommon.db.service.BiService;
import com.yun.bidatacommon.model.param.PageParam;
import com.yun.bidatacommon.db.model.PageVo;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Function;

/**
 * @author Sober
 */
public class BiServiceImpl<M extends BaseMapper<T>, T>
        extends ServiceImpl<M, T>
        implements BiService<T> {


    @Override
    public <P extends PageParam> PageVo<T> autoPageVo(P param) {
        return null;
    }

    @Override
    public <P extends PageParam> PageVo<T> pageVo(P param) {
        Page<T> page = page(getPage(param));
        return PageVo.result(page.getRecords(), page);
    }

    @Override
    public <P extends PageParam> PageVo<T> pageVo(P param,
                                                  LambdaQueryWrapper<T> queryWrapper) {
        Page<T> page = page(
                getPage(param),
                queryWrapper
        );
        return toPageVo(page);
    }

    @Override
    public <P extends PageParam, V> PageVo<V> autoPageVo(P param, Function<List<T>, List<V>> convertList) {
        // 获取 MyBean 类的所有字段
        Field[] fields = param.getClass().getDeclaredFields();
        QueryWrapper<T> queryWrapper = Wrappers.query();
        for (Field field : fields) {
            QueryParser.parser(field, param, queryWrapper);
        }
        Page<T> page = page(
                getPage(param),
                queryWrapper
        );
        return toPageVo(page, convertList);
    }

    @Override
    public <P extends PageParam, V> PageVo<V> pageVo(P param,
                                                     LambdaQueryWrapper<T> queryWrapper,
                                                     Function<List<T>, List<V>> convertList) {
        Page<T> page = page(
                getPage(param),
                queryWrapper
        );
        return toPageVo(page, convertList);
    }

    @Override
    public <P extends PageParam, V> PageVo<V> pageVo(P param,
                                                     Function<List<T>, List<V>> convertList) {
        Page<T> page = page(
                getPage(param)
        );
        return toPageVo(page, convertList);
    }


    /**
     * 分页vo转换
     * @param param 分页参数
     */
    protected Page<T> getPage(PageParam param) {
        return new Page<>(param.getPageNumber(), param.getLimit());
    }


    /**
     * 分页转换
     *
     * @param page       分页数据
     * @param convertList 数据转换
     * @param <T1>         分页参数类型
     * @param <V>         数据转换后的类型
     * @return PageVo<V>
     */
    protected <V, T1> PageVo<V> toPageVo(Page<T1> page, Function<List<T1>, List<V>> convertList) {
        return PageVo.result(convertList.apply(page.getRecords()), page);
    }

    /**
     * 分页转换
     *
     * @param page       分页数据
     * @param convertList 数据转换
     * @param <T1>         分页参数类型
     * @param <V>         数据转换后的类型
     * @return PageVo<V>
     */
    protected <V, T1> PageVo<V> toPageVo(IPage<T1> page, Function<List<T1>, List<V>> convertList) {
        return PageVo.result(convertList.apply(page.getRecords()), page);
    }

    /**
     * 分页转换
     *
     * @param page       分页数据
     * @param <V>         数据转换后的类型
     * @return PageVo<V>
     */
    protected <V> PageVo<V> toPageVo(Page<V> page) {
        return PageVo.result(page);
    }

    /**
     * 分页转换
     *
     * @param page       分页数据
     * @param <V>         数据转换后的类型
     * @return PageVo<V>
     */
    protected <V> PageVo<V> toPageVo(IPage<V> page) {
        return PageVo.result(page);
    }

}
