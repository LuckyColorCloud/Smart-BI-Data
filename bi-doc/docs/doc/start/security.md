---
title: 鉴权中心
---

# 鉴权相关说明

## 鉴权设计分析

### 表模型

- ABAC规则表
  - is_login（需登录）
  - login_id（哪些用户可访问）
  - role_id（哪些角色可访问）
  - ...更多自定义规则字段

- 菜单、接口表
  - title（菜单标题）
  - type（菜单类型，按钮Or页面Or菜单）
  - path（菜单地址，页面路由Or接口地址）
  - icon（菜单图标）
  - name（菜单名称，前端加载页面使用，需要对应vue页面name）
  - parent_id（上级菜单id）

- ABAC权限to菜单关联表
  - abac_id（ABAC规则id）
  - menu_id（菜单id，对应按钮类型，ps：接口）

- 角色表
    - name（角色名称）
    - code（角色代码）

- 角色to菜单关联表（这里只做动态菜单业务-不涉及鉴权）
  - role_id（角色id）
  - menu_id（菜单id）

- 数据模型表
  - name（名称）

- 字段权限表
  - data_model_id（数据模型id）
  - field_name（字段名称）
  - role_ids（角色id列表）

- 数据模型to菜单关联表
  - data_model_id（数据模型id）
  - menu_id（菜单id，对应按钮类型，ps：接口）

### 动态菜单
- `角色表`And`菜单、接口表`及其关联表
- 控制用户可视菜单及其新增删除等按钮

### 接口鉴权
- `菜单、接口表`And`ABAC规则表`及其关联表
- 控制一个Or多个接口的访问规则，及符合`ABAC规则表`内属性才可访问

### 字段权限
- `数据模型表`And`字段权限表`及`数据模型to菜单关联表`
- 每个业务的接口不管是`CRUD`Or`输入`Or`输出`的数据模型大相径庭
- 因此可以不复杂的业务即可维护一个`数据模型表`And`字段权限表`
- `CRUD`时通过用户角色获取相关数据模型的字段权限，通过前置Or后置过滤，进行字段权限的控制

## 鉴权中心做了什么

- 用户中心
- 接口鉴权
- oauth2认证鉴权(待开发)

## 鉴权流程

- 网关处拦截，Feign调用鉴权（具体可查看`SmartBiAuthFilter`）
- 鉴权中心认证，返回基础会话信息
- 网关处加载获得的会话信息到请求头，提供下游服务查询
- 下游服务如需使用会话信息，如`loginId`等，可直接调用

## 如何获得会话信息

### Feign接口加载配置

> 网关将请求头传递到后续服务时，可以直接获取会话信息。  
> 当服务内调用其他服务时，如还需传递会话信息需要进行此配置。

- 不必在所有Feign加载
- 只有在业务需要获取会话信息时加载
- 代码如下

```java
@FeignClient(
        // ...你的其他配置
        configuration = FeignConfig.class
)
```

### 获取会话信息

- 会话信息通过`common`公共模块内`UserSessionInfo`类包装
- 获取代码如下

```java
UserSessionInfo info = UserSessionInfo.get();
```