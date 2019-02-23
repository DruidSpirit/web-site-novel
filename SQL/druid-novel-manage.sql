/*
 Navicat Premium Data Transfer

 Source Server         : druidelf-novel
 Source Server Type    : MySQL
 Source Server Version : 50717
 Source Host           : localhost:3306
 Source Schema         : druid-novel-manage

 Target Server Type    : MySQL
 Target Server Version : 50717
 File Encoding         : 65001

 Date: 23/02/2019 23:06:37
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for druid_admin_fix_down_load_resource
-- ----------------------------
DROP TABLE IF EXISTS `druid_admin_fix_down_load_resource`;
CREATE TABLE `druid_admin_fix_down_load_resource`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `error_type` int(2) NOT NULL COMMENT '错误类型',
  `re_fix_link` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '再次下载或修复错误的链接地址',
  `store_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '下载成功后的地址',
  `status` tinyint(1) NOT NULL DEFAULT 0 COMMENT '修复成功的状态0-失败;1-成功',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for druid_admin_navigation
-- ----------------------------
DROP TABLE IF EXISTS `druid_admin_navigation`;
CREATE TABLE `druid_admin_navigation`  (
  `id` int(2) NOT NULL AUTO_INCREMENT,
  `pid` int(2) NOT NULL DEFAULT 0 COMMENT '链接到子菜单的id',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单名称',
  `icon` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '0' COMMENT '菜单对应图标',
  `url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单链接',
  `sort` int(2) NULL DEFAULT NULL COMMENT '排布顺序',
  `is_jump` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '是否跳转新的页面(0-是，1否)',
  `status` int(1) NOT NULL DEFAULT 0 COMMENT '首页菜单状态',
  `type` int(3) NOT NULL COMMENT '类型',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for druid_admin_security_permission
-- ----------------------------
DROP TABLE IF EXISTS `druid_admin_security_permission`;
CREATE TABLE `druid_admin_security_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限名称',
  `permission` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for druid_admin_security_role
-- ----------------------------
DROP TABLE IF EXISTS `druid_admin_security_role`;
CREATE TABLE `druid_admin_security_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for druid_admin_security_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `druid_admin_security_role_permission`;
CREATE TABLE `druid_admin_security_role_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `permission` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '权限',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for druid_admin_user
-- ----------------------------
DROP TABLE IF EXISTS `druid_admin_user`;
CREATE TABLE `druid_admin_user`  (
  `id` int(3) NOT NULL AUTO_INCREMENT,
  `admin_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员账号',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `role_id` int(5) NOT NULL COMMENT '角色id',
  `contact` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系方式',
  `add_time` bigint(15) NOT NULL COMMENT '注册时间',
  `status` tinyint(1) NOT NULL COMMENT '是否锁定',
  `ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '注册IP地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
