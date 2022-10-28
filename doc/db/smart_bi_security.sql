/*
Navicat MySQL Data Transfer

Source Server         : bi
Source Server Version : 50738
Source Host           : 175.178.71.118:9088
Source Database       : smart_bi_security

Target Server Type    : MYSQL
Target Server Version : 50738
File Encoding         : 65001

Date: 2022-10-28 19:43:53
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for abac_policy
-- ----------------------------
DROP TABLE IF EXISTS `abac_policy`;
CREATE TABLE `abac_policy` (
  `id` bigint(20) NOT NULL,
  `name` varchar(128) NOT NULL COMMENT '规则名称',
  `route` varchar(255) NOT NULL COMMENT '规则适用路径',
  `mode` varchar(24) NOT NULL COMMENT '路径请求方式',
  `policy_json` varchar(2048) NOT NULL COMMENT '属性规则json',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of abac_policy
-- ----------------------------
INSERT INTO `abac_policy` VALUES ('1585267316084969473', 'ABAC测试规则', '/bi-security/test/abac', 'GET', '{\'isLogin\':true,\'loginId\':\'1,2,3,1585296315037323265\'}', '2022-10-26 13:49:16', '2022-10-26 13:49:16');

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `id` bigint(20) NOT NULL,
  `email` varchar(48) NOT NULL COMMENT '邮箱',
  `password` varchar(48) NOT NULL COMMENT '密码',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账户表';

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES ('1585296315037323265', 'sober1050@qq.com', '5d83326c61cc8cb1f2673bc5603a50f4');

-- ----------------------------
-- Table structure for account_info
-- ----------------------------
DROP TABLE IF EXISTS `account_info`;
CREATE TABLE `account_info` (
  `id` bigint(20) NOT NULL,
  `account_id` bigint(20) NOT NULL COMMENT '账户id',
  `icon` varchar(255) NOT NULL DEFAULT '' COMMENT '头像',
  `nickname` varchar(48) NOT NULL DEFAULT '' COMMENT '昵称',
  `gender` tinyint(1) NOT NULL DEFAULT '2' COMMENT '性别0女1男2未知',
  `created_time` datetime NOT NULL COMMENT '创建时间',
  `updated_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='账户信息表';

-- ----------------------------
-- Records of account_info
-- ----------------------------
INSERT INTO `account_info` VALUES ('1585296315490308097', '1585296315037323265', '', '', '2', '2022-10-26 15:44:30', '2022-10-26 15:44:30');
