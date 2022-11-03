create table api_manage
(
    created_time  datetime      null comment '创建时间',
    updated_time  datetime      null comment '更新时间',
    id            int auto_increment comment '主键'
        primary key,
    api_path      varchar(128)  null comment '请求路径 请求路径',
    index_id      varchar(128)  null comment '指标 多个数据集',
    type          int           null comment '类型 0.普通接口1.混合数据库接口，2.混合SQL接口',
    document      text          null comment '文档描述 文档描述',
    auto          tinyint(1)    null comment '是否鉴权 是否鉴权',
    project_id    int           null comment '所属项目 所属项目',
    static_data   text          null comment '静态数据 静态数据',
    writable      tinyint(1)    null comment '接口可写 接口可写',
    params        varchar(1024) null comment '图表参数 图表参数',
    fusion        int           null comment '融合类型 融合类型 0.http 1.数据库',
    fusion_params varchar(3072) null comment '融合类型参数 融合类型参数',
    chart_type    varchar(128)  null comment '字符类型 字符类型',
    name          varchar(1024) null comment '接口名称'
)
    comment 'api管理 ' charset = utf8mb4;

create table datasource
(
    created_time datetime      null comment '创建时间',
    updated_time datetime      null comment '更新时间',
    id           int auto_increment comment '主键'
        primary key,
    url          varchar(1024) null comment 'url',
    user_name    varchar(32)   null comment '用户名',
    pass_word    varchar(32)   null comment '密码',
    drive        varchar(32)   null comment '驱动',
    type         varchar(32)   null comment '类型'
)
    comment '数据源管理 ' charset = utf8mb4;

create table file
(
    id           bigint unsigned auto_increment,
    file_path    varchar(128)    null comment '文件路径',
    file_md5     varchar(32)     null comment '文件md5',
    status       tinyint(1)      null comment '是否删除',
    uid          bigint unsigned null comment '用户ID',
    created_time datetime        null comment '创建时间',
    updated_time datetime        null comment '更新时间',
    constraint id
        unique (id)
)
    comment '文件管理' charset = utf8mb4
;

create table `file-change`
(
    created_time datetime    null comment '创建时间',
    updated_time datetime    null comment '更新时间',
    id           int auto_increment comment '主键'
        primary key,
    file_name    varchar(32) null comment '文件名',
    file_path    varchar(32) null comment '文件路径',
    size         int         null comment '文件大小',
    file_md5     varchar(32) null comment '文件md5',
    file_type    int         null comment '文件类型',
    storage      tinyint(1)  null comment '是否存储',
    source_id    int         null comment '存储数据源',
    status       tinyint(1)  null comment '是否删除',
    save_name    int         null
)
    comment '文件管理 ' charset = utf8mb4;

create table index_config
(
    created_time   datetime        null comment '创建时间',
    updated_time   datetime        null comment '更新时间',
    id             int auto_increment comment '主键'
        primary key,
    project_id     bigint unsigned null comment '所属项目 项目id',
    url            varchar(1024)   null comment '请求路径 域名后面的路径',
    token_id       bigint unsigned null comment '使用token 使用的tokenID',
    request_type   varchar(128)    null comment '请求方式 请求方式如get，post',
    private_header varchar(32)     null comment '私有头信息 私有头信息一般是不会再有专门接口的私有信息',
    exclude        varchar(1024)   null comment '过滤字段 集合类型的需要报错的key',
    json_path      varchar(1024)   null comment 'json_path 利用jsonPath作为第一层数据处理',
    body           varchar(1024)   null comment '请求body 请求body数据',
    param          varchar(1024)   null comment '动态参数 动态请求参数从接口的body获取这里配置需要拿取的key',
    js_script      text            null comment 'JS脚本 最终处理方案',
    map_key        varchar(1024)   null comment '转换参数 转换字段',
    type           int             null comment '类型 0.静态接口1.http接口2.数据库接口3.饼图,4.柱状图',
    name           varchar(1024)   null comment 'name 名称'
)
    comment '指标表' charset = utf8mb4;

create table project
(
    created_time datetime      null comment '创建时间',
    updated_time datetime      null comment '更新时间',
    id           bigint unsigned auto_increment comment '主键'
        primary key,
    project_name varchar(1024) null comment '项目名称',
    domian       varchar(1024) null comment '域名 域名前缀',
    header       varchar(1024) null comment '头信息 公共头信息',
    token_key    varchar(32)   null comment 'tokenkey token请求的key'
)
    comment '项目表 ' charset = utf8mb4;

create table sql_script
(
    created_time datetime   null comment '创建时间',
    updated_time datetime   null comment '更新时间',
    id           int auto_increment comment '主键'
        primary key,
    `sql`        mediumtext null comment '数据库语句',
    source_id    int        null comment '数据源'
)
    comment '查询数据库 ' charset = utf8mb4;

create table storage_table
(
    created_time  datetime      null comment '创建时间',
    updated_time  datetime      null comment '更新时间',
    id            int auto_increment comment '主键'
        primary key,
    source_id     int           null comment '存储数据源 数据源id',
    save_name     varchar(128)  null comment '存储名称 表名',
    save_type     int           null comment '储存方式 1.追加2.全量3.主键',
    primary_key   varchar(1024) null comment '主键 list可以多个字段',
    storage_field varchar(3072) null comment '储存字段 list可以多个字段'
)
    comment '存储表 ' charset = utf8mb4;

create table user_role
(
    created_time     datetime        null comment '创建时间',
    updated_time     datetime        null comment '更新时间',
    id               bigint unsigned auto_increment comment '主键'
        primary key,
    url              varchar(1024)   null comment 'url',
    cache            tinyint(1)      null comment '是否缓存',
    cache_expiration int             null comment '缓存过期时间 单位分钟',
    header           varchar(32)     null comment '请求头',
    body             varchar(32)     null comment 'body',
    request_type     varchar(32)     null comment '请求方式',
    name             varchar(32)     null comment '名称',
    project_id       bigint unsigned null comment '所属项目',
    json_path        varchar(128)    null comment 'jsonPath'
)
    comment '第三方用户角色表 ' charset = utf8mb4;

