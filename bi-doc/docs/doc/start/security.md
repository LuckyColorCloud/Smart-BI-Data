---
title: 鉴权中心
---

# 鉴权相关说明

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