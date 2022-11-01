CREATE TABLE index_config(
    created_time DATETIME    COMMENT '创建时间' ,
    updated_time DATETIME    COMMENT '更新时间' ,
    id INT NOT NULL AUTO_INCREMENT  COMMENT '主键' ,
    project_id bigint unsigned     COMMENT '所属项目 项目id' ,
    url VARCHAR(1024)    COMMENT '请求路径 域名后面的路径' ,
    token_id bigint unsigned     COMMENT '使用token 使用的tokenID' ,
    request_type VARCHAR(128)    COMMENT '请求方式 请求方式如get，post' ,
    private_header VARCHAR(32)    COMMENT '私有头信息 私有头信息一般是不会再有专门接口的私有信息' ,
    exclude VARCHAR(1024)    COMMENT '过滤字段 集合类型的需要报错的key' ,
    json_path VARCHAR(1024)    COMMENT 'json_path 利用jsonPath作为第一层数据处理' ,
    body VARCHAR(1024)    COMMENT '请求body 请求body数据' ,
    param VARCHAR(1024)    COMMENT '动态参数 动态请求参数从接口的body获取这里配置需要拿取的key' ,
    js_script TEXT    COMMENT 'JS脚本 最终处理方案' ,
    map_key VARCHAR(1024)    COMMENT '转换参数 转换字段' ,
    type INT    COMMENT '类型 0.普通类型 1.分组' ,
    name VARCHAR(1024)    COMMENT 'name 名称' ,
    PRIMARY KEY (id)
) COMMENT = '指标表'  charset = utf8mb4;
CREATE TABLE user_role(
    created_time DATETIME    COMMENT '创建时间' ,
    updated_time DATETIME    COMMENT '更新时间' ,
    id bigint unsigned  NOT NULL AUTO_INCREMENT  COMMENT '主键' ,
    url VARCHAR(1024)    COMMENT 'url' ,
    cache tinyint(1)    COMMENT '是否缓存' ,
    cache_expiration INT    COMMENT '缓存过期时间 单位分钟' ,
    header VARCHAR(32)    COMMENT '请求头' ,
    body VARCHAR(32)    COMMENT 'body' ,
    request_type VARCHAR(32)    COMMENT '请求方式' ,
    name VARCHAR(32)    COMMENT '名称' ,
    project_id bigint unsigned     COMMENT '所属项目' ,
    json_path VARCHAR(128)    COMMENT 'jsonPath' ,
    PRIMARY KEY (id)
) COMMENT = '第三方用户角色表 ' charset = utf8mb4;
CREATE TABLE project(
    created_time DATETIME    COMMENT '创建时间' ,
    updated_time DATETIME    COMMENT '更新时间' ,
    id bigint unsigned NOT NULL AUTO_INCREMENT  COMMENT '主键' ,
    project_name VARCHAR(1024)    COMMENT '项目名称' ,
    domian VARCHAR(1024)    COMMENT '域名 域名前缀' ,
    header VARCHAR(1024)    COMMENT '头信息 公共头信息' ,
    token_key VARCHAR(32)    COMMENT 'tokenkey token请求的key' ,
    PRIMARY KEY (id)
) COMMENT = '项目表 '  charset = utf8mb4;
CREATE TABLE api_manage(
    created_time DATETIME    COMMENT '创建时间' ,
    updated_time DATETIME    COMMENT '更新时间' ,
    id INT NOT NULL AUTO_INCREMENT  COMMENT '主键' ,
    api_path VARCHAR(128)    COMMENT '请求路径 请求路径' ,
    index_id VARCHAR(128)    COMMENT '指标 多个数据集' ,
    type INT    COMMENT '类型 0.普通接口1.混合数据库接口，2.混合SQL接口' ,
    document TEXT    COMMENT '文档描述 文档描述' ,
    auto tinyint(1)    COMMENT '是否鉴权 是否鉴权' ,
    project_id INT    COMMENT '所属项目 所属项目' ,
    static_data TEXT    COMMENT '静态数据 静态数据' ,
    writable tinyint(1)    COMMENT '接口可写 接口可写' ,
    params VARCHAR(1024)    COMMENT '图表参数 图表参数' ,
    fusion INT    COMMENT '类型 0.接口转发1.查询数据库 2.静态数据直接返回result 3.数据融合(根据id) 4.数据融合(数组合并)' ,
    fusion_params VARCHAR(3072)    COMMENT '融合类型参数 融合类型参数' ,
    chart_type VARCHAR(128)    COMMENT '字符类型 字符类型' ,
    name VARCHAR(1024)    COMMENT '接口名称' ,
    PRIMARY KEY (id)
) COMMENT = 'api管理 ' charset = utf8mb4;
create table datasource
(
    id           bigint unsigned auto_increment comment '主键',
    created_time datetime      null comment '创建时间',
    updated_time datetime      null comment '更新时间',
    url          varchar(1024) null comment 'url',
    user_name    varchar(32)   null comment '用户名',
    pass_word    varchar(32)   null comment '密码',
    drive        varchar(32)   null comment '驱动',
    type         varchar(32)   null comment '类型',
    CONSTRAINT pk_id   primary key(id)
)
    comment '数据源管理 ' charset = utf8mb4;
create table sql_script
(
    id           bigint unsigned  auto_increment comment '主键',
    created_time datetime   null comment '创建时间',
    updated_time datetime   null comment '更新时间',
        primary key,
    `sql`        mediumtext null comment '数据库语句',
    source_id    bigint unsigned         null comment '数据源',
    CONSTRAINT pk_id   primary key(id)
)
    comment '查询数据库 ' charset = utf8mb4;
create table storage_table
(
    id            bigint unsigned  auto_increment comment '主键',
    created_time  datetime      null comment '创建时间',
    updated_time  datetime      null comment '更新时间',
    source_id     bigint unsigned            null comment '存储数据源 数据源id',
    save_name     varchar(128)  null comment '存储名称 表名',
    save_type     bigint unsigned            null comment '储存方式 1.追加2.全量3.主键',
    primary_key   varchar(1024) null comment '主键 list可以多个字段',
    storage_field varchar(3072) null comment '储存字段 list可以多个字段',
    CONSTRAINT pk_id   primary key(id)
)
    comment '存储表 ' charset = utf8mb4;
CREATE TABLE file(
    id bigint unsigned NOT NULL AUTO_INCREMENT  COMMENT '主键' ,
    created_time DATETIME    COMMENT '创建时间' ,
    updated_time DATETIME    COMMENT '更新时间' ,
    file_name VARCHAR(1024)    COMMENT '文件名' ,
    file_path VARCHAR(1024)    COMMENT '文件路径' ,
    size bigint unsigned     COMMENT '文件大小' ,
    file_md5 VARCHAR(1024)    COMMENT '文件md5' ,
    file_type bigint unsigned     COMMENT '文件类型' ,
    source_id bigint unsigned     COMMENT '存储数据源' ,
    status tinyint(1)    COMMENT '是否删除' ,
    save_name VARCHAR(1024)    COMMENT '保存名称',
    CONSTRAINT pk_id   primary key(id)
) COMMENT = '文件管理 ' charset = utf8mb4;