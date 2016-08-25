/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50713
 Source Host           : 127.0.0.1
 Source Database       : orcl

 Target Server Type    : MySQL
 Target Server Version : 50713
 File Encoding         : utf-8

 Date: 08/24/2016 10:23:21 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `t_department`
-- ----------------------------
DROP TABLE IF EXISTS `t_department`;
CREATE TABLE `t_department` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `bak` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `dname` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `dsortNum` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `parentId` bigint(20) DEFAULT NULL,
  `zcgkid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Records of `t_department`
-- ----------------------------
BEGIN;
INSERT INTO `t_department` VALUES ('0', null, '请选择上级部门', '0', '-1', null), ('2', '', '研发部', '0', '0', '2'), ('35', '', '数据测试', '0', '2', '2');
COMMIT;

-- ----------------------------
--  Table structure for `t_dictype`
-- ----------------------------
DROP TABLE IF EXISTS `t_dictype`;
CREATE TABLE `t_dictype` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `bak` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `sortnum` int(11) DEFAULT NULL,
  `text` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `value` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Records of `t_dictype`
-- ----------------------------
BEGIN;
INSERT INTO `t_dictype` VALUES ('7', '', '0', 'xx小区', 'xxxq');
COMMIT;

-- ----------------------------
--  Table structure for `t_dicvalue`
-- ----------------------------
DROP TABLE IF EXISTS `t_dicvalue`;
CREATE TABLE `t_dicvalue` (
  `vid` bigint(20) NOT NULL AUTO_INCREMENT,
  `bak` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `id` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `softcode` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `sortnum` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `text` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `tid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`vid`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Records of `t_dicvalue`
-- ----------------------------
BEGIN;
INSERT INTO `t_dicvalue` VALUES ('21', '', 'yhl', '1', '0', '1', '一号楼', '7'), ('22', '', 'eel', '2', '1', '1', '二号楼', '7');
COMMIT;

-- ----------------------------
--  Table structure for `t_logbook`
-- ----------------------------
DROP TABLE IF EXISTS `t_logbook`;
CREATE TABLE `t_logbook` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ldate` datetime DEFAULT NULL,
  `description` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ip` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `mark` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `requestUri` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `userName` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=923 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Table structure for `t_navigation`
-- ----------------------------
DROP TABLE IF EXISTS `t_navigation`;
CREATE TABLE `t_navigation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `iconCls` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `parentId` bigint(20) DEFAULT NULL,
  `sortNum` int(11) DEFAULT NULL,
  `text` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `url` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Records of `t_navigation`
-- ----------------------------
BEGIN;
INSERT INTO `t_navigation` VALUES ('0', null, '-1', null, '请选择父级菜单', null), ('2', 'icon-advancedsettings', '0', '10', '系统设置', '#'), ('7', 'icon-book-open-mark', '2', '0', '数据字典', '/tdictypecon/dictypeindex'), ('8', 'icon-application-side-tree', '2', '1', '导航菜单', '/navigation/navigationindex'), ('9', 'icon-chart-organisation', '2', '2', '部门管理', '/deptcon/deptindex'), ('10', 'icon-user-business-boss', '2', '3', '岗位管理', '/stationscon/stationindex'), ('11', 'icon-users', '2', '4', '用户管理', '/tusercon/userindex'), ('12', 'icon-mouse', '2', '5', '权限分配', '/authcon/authindex'), ('13', 'icon-page-error', '2', '6', '操作日志', '/tlogbookcon/logbookindex');
COMMIT;

-- ----------------------------
--  Table structure for `t_stations`
-- ----------------------------
DROP TABLE IF EXISTS `t_stations`;
CREATE TABLE `t_stations` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `stationCode` varchar(255) COLLATE utf8_bin NOT NULL,
  `stationName` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `stationRemark` varchar(1000) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `stationCode` (`stationCode`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Records of `t_stations`
-- ----------------------------
BEGIN;
INSERT INTO `t_stations` VALUES ('1', '1', '公司员工', null), ('2', '21', 'test21', 'sdfsd21');
COMMIT;

-- ----------------------------
--  Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `deptId` bigint(20) DEFAULT NULL,
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `navIds` varchar(3000) COLLATE utf8_bin DEFAULT NULL,
  `psw` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `stationId` bigint(20) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `userName` varchar(200) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `userName` (`userName`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Records of `t_user`
-- ----------------------------
BEGIN;
INSERT INTO `t_user` VALUES ('1', '2', '超级管理员', '2,7,8,9,10,11,12,13', '202cb962ac59075b964b07152d234b70', '1', '1', 'admin'), ('10', '35', '发送接口了的', null, '202cb962ac59075b964b07152d234b70', '2', '1', 'test');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
