---
title: 基础Service
---

# 基础Service
> 在项目中，我们对`Mybatis Plus`提供的`Service`进行了再次封装。
> 
> 简化了分页查询时参数的组装，并集成数据转换。
> 
> 类路径`com.yun.bidatacommon.db.service.BiService`

## 参数解释
- `P`: 具体的查询入参对象
- `T`: 具体的查询结果对象（表对应实体）
- `V`: 转换后的查询结果对象

## 分页查询
- 确保查询入参对象继承`PageParam`
- 确保返回分页对象为`PageVo<T>`

### 方法

- `<P extends PageParam> PageVo<T> pageVo(P param)`
- `<P extends PageParam> PageVo<T> pageVo(P param, LambdaQueryWrapper<T> queryWrapper)`
- `<P extends PageParam, V> PageVo<V> pageVo(P param, LambdaQueryWrapper<T> queryWrapper, Function<List<T>, List<V>> convertList)`
- `<P extends PageParam, V> PageVo<V> pageVo(P param, Function<List<T>, List<V>> convertList)`

## 自适应查询

### 方法
- `<P extends PageParam> PageVo<T> autoPageVo(P param)`
- `<P extends PageParam, V> PageVo<V> autoPageVo(P param, Function<List<T>, List<V>> convertList)`

使用`@BiQuery`注解，加在`PageParam`的实现类字段上，后调用`autoPageVo`查询，即可自动组装分页查询参数。

## 自定义Dao层分页的查询数据转换

### 方法

- `<V, T1> PageVo<V> toPageVo(Page<T1> page, Function<List<T1>, List<V>> convertList)`
- `<V> PageVo<V> toPageVo(Page<V> page)`
- `<V, T1> PageVo<V> toPageVo(IPage<T1> page, Function<List<T1>, List<V>> convertList)`
- `<V> PageVo<V> toPageVo(IPage<V> page)`

