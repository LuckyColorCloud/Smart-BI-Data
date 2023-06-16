package com.yun.bidatacommon.db.model;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 分页
 * @author Sober
 */
@Data
public class PageVo<T> implements Serializable {


    @Serial
    private static final long serialVersionUID = 1L;

    public PageVo(Page<T> page) {
        this.records = page.getRecords();
        this.total = page.getTotal();
        this.size = page.getSize();
        this.current = page.getCurrent();
        this.pages = page.getPages();
    }


    public PageVo(IPage<T> page) {
        this.records = page.getRecords();
        this.total = page.getTotal();
        this.size = page.getSize();
        this.current = page.getCurrent();
        this.pages = page.getPages();
    }


    public PageVo(List<T> records, Page<?> page) {
        this.total = page.getTotal();
        this.size = page.getSize();
        this.current = page.getCurrent();
        this.pages = page.getPages();
        this.records = records;
    }

    public PageVo(List<T> records, IPage<?> page) {
        this.total = page.getTotal();
        this.size = page.getSize();
        this.current = page.getCurrent();
        this.pages = page.getPages();
        this.records = records;
    }

    public static <T> PageVo<T> result(Page<T> page) {
        return new PageVo<T>(page);
    }

    public static <T> PageVo<T> result(IPage<T> page) {
        return new PageVo<T>(page);
    }

    public static <T> PageVo<T> result(List<T> records, Page<?> page) {
        return new PageVo<T>(records, page);
    }

    public static <T> PageVo<T> result(List<T> records, IPage<?> page) {
        return new PageVo<T>(records, page);
    }

    /**
     * 查询数据列表
     */
    private List<T> records = Collections.emptyList();

    /**
     * 总数
     */
    private long total = 0;

    /**
     * 每页显示条数，默认 10
     */
    private long size = 10;

    /**
     * 当前页
     */
    private long current = 1;

    /**
     * 总页数
     */
    private long pages = 0;

}
