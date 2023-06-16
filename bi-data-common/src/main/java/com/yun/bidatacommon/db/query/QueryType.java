package com.yun.bidatacommon.db.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.Collection;
import java.util.Optional;

/**
 * @author Sober
 */
public enum QueryType {

    /**
     * 等于
     */
    EQ {
        @Override
        public <T> void operate(String field, Object param, QueryWrapper<T> queryWrapper) {
            Optional.ofNullable(param)
                    .ifPresent(item -> {
                        if (!item.toString().isEmpty()) {
                            queryWrapper.eq(field, item);
                        }
                    });
        }
    },
    /**
     * 模糊查询
     */
    LIKE {
        @Override
        public <T> void operate(String field, Object param, QueryWrapper<T> queryWrapper) {
            Optional.ofNullable(param)
                    .ifPresent(item -> {
                        if (!item.toString().isEmpty()) {
                            queryWrapper.like(field, item);
                        }
                    });
        }
    },
    /**
     * 包含
     */
    IN {
        @Override
        public <T> void operate(String field, Object param, QueryWrapper<T> queryWrapper) {
            if (param instanceof Collection<?> collection) {
                if (!collection.isEmpty()) {
                    queryWrapper.in(field, collection);
                }
            }
        }
    };

    public abstract <T> void operate(String field, Object param, QueryWrapper<T> queryWrapper);

}
