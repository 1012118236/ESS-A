/*
 Navicat Premium Data Transfer

 Source Server         : TMR
 Source Server Type    : MySQL
 Source Server Version : 80013
 Source Host           : localhost:3306
 Source Schema         : tmr

 Target Server Type    : MySQL
 Target Server Version : 80013
 File Encoding         : 65001

 Date: 14/08/2019 14:04:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for menus
-- ----------------------------
DROP TABLE IF EXISTS `menus`;
CREATE TABLE `menus`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) NULL DEFAULT NULL,
  `menuName` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单名',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `order_value` int(11) NULL DEFAULT NULL COMMENT '排序',
  `image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menus
-- ----------------------------
INSERT INTO `menus` VALUES (1, NULL, '系统管理', '/config/', 0, 'el-icon-setting');
INSERT INTO `menus` VALUES (2, 1, '菜单管理', '/config/menus', 0, 'el-icon-menu');
INSERT INTO `menus` VALUES (3, NULL, '内容管理', '/text/', 1, 'el-icon-setting');
INSERT INTO `menus` VALUES (5, 1, '用户管理', '/config/user', 1, 'el-icon-setting');
INSERT INTO `menus` VALUES (6, 1, '角色管理', '/config/role', 2, 'el-icon-setting');
INSERT INTO `menus` VALUES (7, 1, '权限管理', '/config/permission', 3, 'el-icon-setting');

-- ----------------------------
-- Table structure for menus_role
-- ----------------------------
DROP TABLE IF EXISTS `menus_role`;
CREATE TABLE `menus_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `menus_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 163 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menus_role
-- ----------------------------
INSERT INTO `menus_role` VALUES (157, 1, 1);
INSERT INTO `menus_role` VALUES (158, 2, 1);
INSERT INTO `menus_role` VALUES (159, 5, 1);
INSERT INTO `menus_role` VALUES (160, 6, 1);
INSERT INTO `menus_role` VALUES (161, 7, 1);
INSERT INTO `menus_role` VALUES (162, 5, 2);

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `descritpion` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `status` int(11) NULL DEFAULT 0,
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES (1, 'permissionUpdate', '编辑权限', '/permission/saveOrUpdatePermission', 0, '编辑权限');
INSERT INTO `permission` VALUES (2, 'useradd', '新增用户', '/user/add', 0, '备注而已啦');
INSERT INTO `permission` VALUES (3, 'userUpdate', '测试1', '/user/saveOrUpdateUser', 1, '');
INSERT INTO `permission` VALUES (4, 'test2', '测试2', '/*/test2', 0, '');

-- ----------------------------
-- Table structure for permission_role
-- ----------------------------
DROP TABLE IF EXISTS `permission_role`;
CREATE TABLE `permission_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL,
  `permission_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 104 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permission_role
-- ----------------------------
INSERT INTO `permission_role` VALUES (50, 2, 2);
INSERT INTO `permission_role` VALUES (101, 1, 2);
INSERT INTO `permission_role` VALUES (102, 1, 3);
INSERT INTO `permission_role` VALUES (103, 1, 4);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `role_chinese_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'ROLE_ADMIN', '系统管理员');
INSERT INTO `role` VALUES (2, 'ROLE_USER', '用户');
INSERT INTO `role` VALUES (3, 'ROLE_TEST', '测试用户');

-- ----------------------------
-- Table structure for schedule_job
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job`;
CREATE TABLE `schedule_job`  (
  `id` int(11) NOT NULL,
  `jobName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `cronExpression` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `springId` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `methodName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `jobStatus` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of schedule_job
-- ----------------------------
INSERT INTO `schedule_job` VALUES (1, '任务调度', '00 * * * * ?', 'taskInfo', 'execute', 1);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_uuid` varchar(70) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `telephone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `last_ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `last_time` timestamp(0) NULL DEFAULT NULL,
  `is_dvailable` int(11) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_username_uindex`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (4, NULL, 'sj1012118236', '$2a$10$vpNo.SLc8pzcygeh6hxaQ.rsiuQjTGFGq9elScMUh8Qfql768/BYm', '1012118236@qq.com', '17695558236', NULL, '127.0.0.1', '2019-08-14 11:25:40', 0);
INSERT INTO `user` VALUES (5, NULL, '17695558236', '$2a$10$vpNo.SLc8pzcygeh6hxaQ.rsiuQjTGFGq9elScMUh8Qfql768/BYm', '1012118236@qq.com', '17695558236', NULL, '127.0.0.1', '2019-08-14 11:25:46', 0);
INSERT INTO `user` VALUES (8, NULL, 'hahahaha', '$2a$10$vpNo.SLc8pzcygeh6hxaQ.rsiuQjTGFGq9elScMUh8Qfql768/BYm', '1012118236@qq.com', '12341234', NULL, NULL, NULL, 0);
INSERT INTO `user` VALUES (21, NULL, 'test1', '$2a$10$UhpASdUG40BVccf7rhiHpu7KrBTUUg8/3HzKE03HPUUZdiihQgyZa', '1012118236@qq.com', '123', NULL, NULL, NULL, 0);

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 4, 1);
INSERT INTO `user_role` VALUES (2, 4, 2);
INSERT INTO `user_role` VALUES (6, 4, 3);
INSERT INTO `user_role` VALUES (9, 5, 2);
INSERT INTO `user_role` VALUES (19, 8, 2);
INSERT INTO `user_role` VALUES (20, 21, 2);

SET FOREIGN_KEY_CHECKS = 1;
