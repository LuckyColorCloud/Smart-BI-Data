package com.yun.bidatacommon.db.query;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.lang.reflect.Field;
import java.util.Optional;

/**
 * 查询注解解析器
 * @author Sober
 */
public class QueryParser {



    public static <P, T> void parser(Field field, P param, QueryWrapper<T> queryWrapper) {
        // 设置字段可访问，以便我们可以获取其值
        field.setAccessible(true);
        // 检查字段上是否有 NPlusQuery 注解
        Optional.ofNullable(field.getAnnotation(BiQuery.class))
                .ifPresent(item -> {
                    try {
                        item.value().operate(
                                field.getName().replaceAll("([A-Z])", "_$1").toLowerCase(),
                                field.get(param),
                                queryWrapper);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
    }


}
