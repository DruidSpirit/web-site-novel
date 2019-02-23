/*
 Navicat Premium Data Transfer

 Source Server         : druidelf-novel
 Source Server Type    : MySQL
 Source Server Version : 50717
 Source Host           : localhost:3306
 Source Schema         : druid-novel

 Target Server Type    : MySQL
 Target Server Version : 50717
 File Encoding         : 65001

 Date: 23/02/2019 23:06:24
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for country
-- ----------------------------
DROP TABLE IF EXISTS `country`;
CREATE TABLE `country`  (
  `type` int(2) NULL DEFAULT NULL,
  `people` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for druid_area
-- ----------------------------
DROP TABLE IF EXISTS `druid_area`;
CREATE TABLE `druid_area`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `nid` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `pid` int(11) NOT NULL DEFAULT 0,
  `domain` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sort` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `nid`(`nid`) USING BTREE,
  INDEX `nidp`(`nid`, `pid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3577 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '地区字典表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for druid_novel_resource
-- ----------------------------
DROP TABLE IF EXISTS `druid_novel_resource`;
CREATE TABLE `druid_novel_resource`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(2) NULL DEFAULT NULL COMMENT '资源类型',
  `link_txt` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资源链接地址(txt格式)',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资源名称',
  `intro` varchar(5000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资源介绍',
  `link_resource_adress` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '链接网站来源',
  `add_time` bigint(30) NULL DEFAULT NULL COMMENT '添加时间',
  `popular` bigint(30) NULL DEFAULT NULL COMMENT '受欢迎程度',
  `link_read_line` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '在线阅读链接地址',
  `link_cut_down` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资源链接地址(压缩格式格式)',
  `link_other` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '资源链接地址(其他格式)',
  `status` int(2) NULL DEFAULT NULL COMMENT '状态0-完结 1连载中',
  `site_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '网站地址',
  `link_src` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片链接地址',
  `size` double(5, 2) NULL DEFAULT NULL COMMENT '文件大小',
  `turn_over_time` bigint(30) NULL DEFAULT NULL COMMENT '跟新时间',
  `author` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '作者',
  `crawl_start_link` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '开始爬取的起点链接',
  `has_loaddown` tinyint(1) UNSIGNED ZEROFILL NULL DEFAULT 0 COMMENT 'txt是否下载 0-否 1-是',
  `repository_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'txt下载存放路径',
  `src_has_loaddown` tinyint(1) NULL DEFAULT 0 COMMENT '图片是否下载 0-否 1-是',
  `src_repository_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图片下载存放路径',
  `chapter_repository_path` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '章节存放地址',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `link_read_line_index`(`link_read_line`) USING BTREE,
  INDEX `type`(`type`) USING BTREE,
  INDEX `popular`(`popular`) USING BTREE,
  INDEX `turn_over_time`(`turn_over_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 694 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for druid_security_permission
-- ----------------------------
DROP TABLE IF EXISTS `druid_security_permission`;
CREATE TABLE `druid_security_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限名称',
  `permission` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for druid_security_role
-- ----------------------------
DROP TABLE IF EXISTS `druid_security_role`;
CREATE TABLE `druid_security_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for druid_security_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `druid_security_role_permission`;
CREATE TABLE `druid_security_role_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `permission` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for druid_send_code_record
-- ----------------------------
DROP TABLE IF EXISTS `druid_send_code_record`;
CREATE TABLE `druid_send_code_record`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(2) NOT NULL COMMENT '信息发送类型0-邮件发送；1-手机发送',
  `be_use_to` int(2) UNSIGNED ZEROFILL NOT NULL DEFAULT 00 COMMENT '短信发送时用于何种功能',
  `send_user_account` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '发送账号',
  `code` int(11) NOT NULL COMMENT '发送码',
  `add_time` bigint(30) NOT NULL COMMENT '发送时间',
  `ip_address` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '发送请求用户IP地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '信息发送记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for druid_user
-- ----------------------------
DROP TABLE IF EXISTS `druid_user`;
CREATE TABLE `druid_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名称',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户密码',
  `email` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户邮箱',
  `salt` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '加密盐值',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否激活0-否；1-是',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 41 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for druid_website_navigation
-- ----------------------------
DROP TABLE IF EXISTS `druid_website_navigation`;
CREATE TABLE `druid_website_navigation`  (
  `id` int(2) NOT NULL AUTO_INCREMENT,
  `pid` int(2) NOT NULL DEFAULT 0 COMMENT '链接到子菜单的id',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单名称',
  `url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单链接',
  `sort` int(2) NULL DEFAULT NULL COMMENT '排布顺序',
  `is_jump` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '是否跳转新的页面(0-是，1否)',
  `status` int(1) NOT NULL DEFAULT 0 COMMENT '首页菜单状态',
  `type` int(3) NOT NULL COMMENT '类型',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for test_one
-- ----------------------------
DROP TABLE IF EXISTS `test_one`;
CREATE TABLE `test_one`  (
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
