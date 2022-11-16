/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

CREATE TABLE `api_manage` (
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `api_path` varchar(128) DEFAULT NULL COMMENT '请求路径 请求路径',
  `index_id` varchar(128) DEFAULT NULL COMMENT '指标 多个数据集',
  `type` int(11) DEFAULT NULL COMMENT '类型 0.普通接口1.混合数据库接口，2.混合SQL接口',
  `document` text COMMENT '文档描述 文档描述',
  `auto` tinyint(1) DEFAULT NULL COMMENT '是否鉴权 是否鉴权',
  `project_id` int(11) DEFAULT NULL COMMENT '所属项目 所属项目',
  `static_data` text COMMENT '静态数据 静态数据',
  `writable` tinyint(1) DEFAULT NULL COMMENT '接口可写 接口可写',
  `params` varchar(1024) DEFAULT NULL COMMENT '图表参数 图表参数',
  `fusion` int(11) DEFAULT NULL COMMENT '融合类型 融合类型 0.http 1.数据库',
  `fusion_params` varchar(3072) DEFAULT NULL COMMENT '融合类型参数 融合类型参数',
  `chart_type` varchar(128) DEFAULT NULL COMMENT '字符类型 字符类型',
  `name` varchar(1024) DEFAULT NULL COMMENT '接口名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COMMENT='api管理 ';

CREATE TABLE `datasource` (
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `url` varchar(1024) DEFAULT NULL COMMENT 'url',
  `user_name` varchar(32) DEFAULT NULL COMMENT '用户名',
  `pass_word` varchar(32) DEFAULT NULL COMMENT '密码',
  `drive` varchar(32) DEFAULT NULL COMMENT '驱动',
  `type` varchar(32) DEFAULT NULL COMMENT '类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='数据源管理 ';

CREATE TABLE `file` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `file_name` varchar(128) DEFAULT NULL COMMENT '文件名',
  `file_path` varchar(128) DEFAULT NULL COMMENT '文件路径',
  `file_md5` varchar(32) DEFAULT NULL COMMENT '文件md5',
  `status` tinyint(1) DEFAULT '0' COMMENT '是否删除',
  `uid` bigint(20) unsigned DEFAULT NULL COMMENT '用户ID',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `file_storage` (
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `file_name` varchar(32) DEFAULT NULL COMMENT '文件名',
  `file_path` varchar(32) DEFAULT NULL COMMENT '文件路径',
  `size` int(11) DEFAULT NULL COMMENT '文件大小',
  `file_md5` varchar(32) DEFAULT NULL COMMENT '文件md5',
  `file_type` int(11) DEFAULT NULL COMMENT '文件类型',
  `storage` tinyint(1) DEFAULT NULL COMMENT '是否存储',
  `source_id` int(11) DEFAULT NULL COMMENT '存储数据源',
  `status` tinyint(1) DEFAULT NULL COMMENT '是否删除',
  `save_name` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文件管理 ';

CREATE TABLE `index_config` (
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `project_id` bigint(20) unsigned DEFAULT NULL COMMENT '所属项目 项目id',
  `url` varchar(1024) DEFAULT NULL COMMENT '请求路径 域名后面的路径',
  `token_id` bigint(20) unsigned DEFAULT NULL COMMENT '使用token 使用的tokenID',
  `request_type` varchar(128) DEFAULT NULL COMMENT '请求方式 请求方式如get，post',
  `private_header` varchar(32) DEFAULT NULL COMMENT '私有头信息 私有头信息一般是不会再有专门接口的私有信息',
  `exclude` varchar(1024) DEFAULT NULL COMMENT '过滤字段 集合类型的需要报错的key',
  `json_path` varchar(1024) DEFAULT NULL COMMENT 'json_path 利用jsonPath作为第一层数据处理',
  `body` varchar(1024) DEFAULT NULL COMMENT '请求body 请求body数据',
  `param` varchar(1024) DEFAULT NULL COMMENT '动态参数 动态请求参数从接口的body获取这里配置需要拿取的key',
  `js_script` text COMMENT 'JS脚本 最终处理方案',
  `map_key` varchar(1024) DEFAULT NULL COMMENT '转换参数 转换字段',
  `type` int(11) DEFAULT NULL COMMENT '类型 0.静态接口1.http接口2.数据库接口3.饼图,4.柱状图',
  `name` varchar(1024) DEFAULT NULL COMMENT 'name 名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='指标表';

CREATE TABLE `project` (
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `project_name` varchar(1024) DEFAULT NULL COMMENT '项目名称',
  `domian` varchar(1024) DEFAULT NULL COMMENT '域名 域名前缀',
  `header` varchar(1024) DEFAULT NULL COMMENT '头信息 公共头信息',
  `token_key` varchar(32) DEFAULT NULL COMMENT 'tokenkey token请求的key',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='项目表 ';

CREATE TABLE `sql_script` (
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `sql` mediumtext COMMENT '数据库语句',
  `source_id` int(11) DEFAULT NULL COMMENT '数据源',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='查询数据库 ';

CREATE TABLE `storage_table` (
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `source_id` int(11) DEFAULT NULL COMMENT '存储数据源 数据源id',
  `save_name` varchar(128) DEFAULT NULL COMMENT '存储名称 表名',
  `save_type` int(11) DEFAULT NULL COMMENT '储存方式 1.追加2.全量3.主键',
  `primary_key` varchar(1024) DEFAULT NULL COMMENT '主键 list可以多个字段',
  `storage_field` varchar(3072) DEFAULT NULL COMMENT '储存字段 list可以多个字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='存储表 ';

CREATE TABLE `user_role` (
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `url` varchar(1024) DEFAULT NULL COMMENT 'url',
  `cache` tinyint(1) DEFAULT NULL COMMENT '是否缓存',
  `cache_expiration` int(11) DEFAULT NULL COMMENT '缓存过期时间 单位分钟',
  `header` varchar(32) DEFAULT NULL COMMENT '请求头',
  `body` varchar(32) DEFAULT NULL COMMENT 'body',
  `request_type` varchar(32) DEFAULT NULL COMMENT '请求方式',
  `name` varchar(32) DEFAULT NULL COMMENT '名称',
  `project_id` bigint(20) unsigned DEFAULT NULL COMMENT '所属项目',
  `json_path` varchar(128) DEFAULT NULL COMMENT 'jsonPath',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='第三方用户角色表 ';



/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;