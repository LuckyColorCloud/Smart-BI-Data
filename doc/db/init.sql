create table api_manage
(
    created_time datetime      null comment '创建时间',
    updated_time datetime      null comment '更新时间',
    id           int auto_increment comment '主键'
        primary key,
    path         varchar(1024) null comment '请求路径',
    json         varchar(1024) null comment '请求参数',
    auth         varchar(1)    null comment '是否鉴权',
    api_id       int           null comment '第三方api id 即接口转发id',
    result       mediumtext    null comment '静态数据',
    type         int default 0 null comment ' 0.接口转发1.查询数据库 2.静态数据直接返回result',
    chart_type   varchar(1024) null comment ' 参考FormatConversion.Class 该值可以未CHART或chart',
    params       varchar(1024) null comment ' 图表类型参数',
    apis         varchar(1024) null comment ' 数据融合参数 只能是一种类型',
    fusion       int default 0 null comment ' 融合类型  0.接口转发1.查询数据库',
    fusionParams         varchar(1024) null comment ' 融合参数',
)
    comment 'api管理' charset = utf8mb4;
create table api_path
(
    created_time     datetime      null comment '创建时间',
    updated_time     datetime      null comment '更新时间',
    id               int auto_increment comment '主键'
        primary key,
    url              varchar(1024) null comment '路径',
    project_id       int           null comment '所属项目',
    storage_table_id int           null comment '是否存储 为空不存储',
    user_role_id     int           null comment '用户token',
    private_header   varchar(3072) null comment '私有头信息 json',
    body             varchar(3072) null comment 'body json',
    request_type     int           null comment '请求类型',
    storage_field_id int           null comment '是否落库 空不储存',
    exclude          varchar(1024) null comment '排除 jsonlist过滤key',
    json_path        varchar(1024) null comment 'jsonPath表达式 为空时从原始数据匹配'
)
    comment '第三方api路径表 ' charset = utf8mb4;
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
create table project
(
    created_time datetime      null comment '创建时间',
    updated_time datetime      null comment '更新时间',
    id           int auto_increment comment '主键'
        primary key,
    project_name varchar(1024) null comment '项目名称'
)
    comment ' ' charset = utf8mb4;
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