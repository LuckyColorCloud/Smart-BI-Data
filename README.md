# Smart-BI-Data

## 写在前面
项目发布初期，希望大家多多点点`Star`
- [Gitee](https://gitee.com/new_sonw/Smart-BI-Data)
- [GitHub](https://github.com/ShelikeSnow/Smart-BI-Data)
- [官网地址](https://smart-bi-data.sobercoding.com/)
## 项目流程 定位 优劣势
![项目流程](/doc/png/Smart-Bi-Data.png)
#### 介绍
1. 基于大屏类业务 而产生的 数据中台 配置即用 简单配置 运维可上手
2. 目前包含如下功能：
- 实时接口转发
- 数据库查询直接输出接口
- 数据拦截
- 数据落库 表不存在自动匹配类型创建表
- 静态接口 (对应大屏业务的比如标题等)
- 缓存机制(第三方token缓存)
- 支持多角色第三方token模式
- 文档聚合
- 限流,熔断策略
- 数据融合(目前支持数据合并 根据主键合并,数组合并(以最小数组为准，多余会舍弃 具体看方法 dataFusion),主键融合)
- 独立服务 独立报错代码
- 大屏数据模板(集合对象转基本图表类型,对象转集合类型，柱状图) 具体代码中也有注释
- 代码生成器(不是最终版,目前可以直接生成对应实体类,控制层到各个分体的包中直接使用)
- 文件入库(支持csv,json,xlsx,根据后缀自动判断,*删除时 会删除表!!!)
- 完善的版本依赖管理
- 大部分代码遵循阿里规范  注释完善
- 目前还不属于正式开放阶段 欢迎各位提出问题 提交pr 加入开发。
```
对象转list集合并添加元素,{"fireProbability":"10%","flood":"5%"} ===> "[{"name":"火灾","value":"10%"},{"name":"洪水","value":"5%"}]"      params===> "[{"fireProbability":"value","join":{"name":"火灾"}},{"flood":"value","join":{"name":"洪水"}}]"
基本类型图表   {"a":"a","c":"c","b":"b"}===>{"name":"a","value":"b"} 集合类型同理  params===>{"a":"name","b":"value"}
柱状图  不存在非集合情况!!!
将一个集合 转为前端柱状图形式,
{"total":123,"list":[{"name":张三,"age":18,"value":1},{"name":李四,"age":23,"value":2},{"name":王五,"age":18,"value":3}]}===>{"total":123,"data":[18,23,18],"name":["张三","李四","王五"]}===>
params===>{"ppr":[{"key":"total","jsonPath":"$..total"}],"name":"name","data":"data"} 理论上只能有一个key{"key":"total","jsonPath":"$..total"} 这里面除了jsonPath只会有一个
可同时做字段转换名称
```
### 怎么让项目跑起来???
首先先下载Nacos 2.1.1 
这里浅浅放上一个下载地址 - [nacos](https://github.com/alibaba/nacos/releases)
```  
     *  解压后   
     *  Linux/Unix/Mac
     *  启动命令(standalone代表着单机模式运行，非集群模式):
     *  sh startup.sh -m standalone
     *  如果您使用的是ubuntu系统，或者运行脚本报错提示[[符号找不到，可尝试如下运行：
     *  bash startup.sh -m standalone
     *  Windows
     *  启动命令(standalone代表着单机模式运行，非集群模式):
     *  startup.cmd -m standalone
```
运行成功后看到以下
![运行成功](/doc/png/nacos.png)

接下来将 doc/yaml/下所有yaml复制到 nacos 或者打成压缩包导入
![配置图](/doc/png/nacosp.png)
```
然后就是在MySQL下执行 /doc/db/init.sql
接下来就是修改各个服务的
namespace===>nacos的
```
![namespace](/doc/png/namespace.png)
```
右侧这一段长的像UUID的东西
server-addr====>服务器地址 默认端口8848
接下来就启动所有服务
访问网关文档聚合地址 http://127.0.0.1:8607/doc.html 即可愉快的玩耍了
```

### 默认账号
- 账号：sober1050@qq.com
- 密码：sober1050

### 代码生成器
![generation](/doc/png/generation.png)
### 待开发
- 前端管理页面(优先,开发中) [地址](https://gitee.com/lucky-color/LuckyColor)
- 文件服务(ico,图片,视频等...不做重点开发)
- 数据融合(混合计算)
- 接入GoView
- 权限中心
- 消息中心
- 调度中心
- 日志中心
- 其他待定(欢迎各位提出需求)``

### 项目历程
2022.10.10 加入3位小伙伴,项目将迎来 快速发展 冲!!!
### 软件架构
软件架构说明(没画 脑补先)

