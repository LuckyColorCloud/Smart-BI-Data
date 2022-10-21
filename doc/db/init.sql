create table api_manage
(
    id           bigint unsigned auto_increment comment '主键',
    created_time datetime      null comment '创建时间',
    updated_time datetime      null comment '更新时间',
    path         varchar(1024) null comment '请求路径',
    json         varchar(1024) null comment '请求参数',
    auth         varchar(1)    null comment '是否鉴权',
    api_id       bigint unsigned           null comment '第三方api id 即接口转发id',
    result       mediumtext    null comment '静态数据',
    type         bigint unsigned  default 0 null comment ' 0.接口转发1.查询数据库 2.静态数据直接返回result',
    chart_type   varchar(1024) null comment ' 参考FormatConversion.Class 该值可以未CHART或chart',
    params       varchar(1024) null comment ' 图表类型参数',
    apis         varchar(1024) null comment ' 数据融合参数 只能是一种类型',
    fusion       bigint unsigned default 0 null comment ' 融合类型  0.接口转发1.查询数据库',
    fusionParams         varchar(1024) null comment ' 融合参数',
    CONSTRAINT pk_id   primary key(id)
)
    comment 'api管理' charset = utf8mb4;
create table api_path
(
    id               bigint unsigned auto_increment comment '主键',
    created_time     datetime      null comment '创建时间',
    updated_time     datetime      null comment '更新时间',
    url              varchar(1024) null comment '路径',
    project_id       bigint unsigned           null comment '所属项目',
    storage_table_id bigint unsigned           null comment '是否存储 为空不存储',
    user_role_id     bigint unsigned           null comment '用户token',
    private_header   varchar(3072) null comment '私有头信息 json',
    body             varchar(3072) null comment 'body json',
    request_type     bigint unsigned           null comment '请求类型',
    storage_field_id bigint unsigned           null comment '是否落库 空不储存',
    exclude          varchar(1024) null comment '排除 jsonlist过滤key',
    json_path        varchar(1024) null comment 'jsonPath表达式 为空时从原始数据匹配',
    CONSTRAINT pk_id   primary key(id)
)
    comment '第三方api路径表 ' charset = utf8mb4;
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
create table project
(
    id           bigint unsigned  auto_increment comment '主键',
    created_time datetime      null comment '创建时间',
    updated_time datetime      null comment '更新时间',
    project_name varchar(1024) null comment '项目名称',
    CONSTRAINT pk_id   primary key(id)
)
    comment ' ' charset = utf8mb4;
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
CREATE TABLE user_role
(
    id bigint unsigned NOT NULL AUTO_INCREMENT  COMMENT '主键' ,
    created_time DATETIME    COMMENT '创建时间' ,
    updated_time DATETIME    COMMENT '更新时间' ,
    url VARCHAR(1024)    COMMENT 'url' ,
    cache tinyint(1)    COMMENT '是否缓存' ,
    cache_expiration bigint unsigned    COMMENT '缓存过期时间 单位分钟' ,
    header VARCHAR(32)    COMMENT '请求头' ,
    body VARCHAR(32)    COMMENT 'body' ,
    request_type VARCHAR(32)    COMMENT '请求方式' ,
    name VARCHAR(32)    COMMENT '名称' ,
    project_id bigint unsigned    COMMENT '所属项目' ,
     CONSTRAINT pk_id   primary key(id)
) COMMENT = '第三方用户角色表 ' charset = utf8mb4;

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