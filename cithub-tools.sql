/*
 Navicat Premium Data Transfer

 Source Server         : CithubLocal
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : cithub-tools

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 23/01/2024 12:50:47
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for coveringarrays
-- ----------------------------
DROP TABLE IF EXISTS `coveringarrays`;
CREATE TABLE `coveringarrays`  (
  `CoveringArrayID` int(0) NOT NULL AUTO_INCREMENT,
  `CoveringArrayName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `CoveringArrayDescriptions` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `ModelID` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`CoveringArrayID`) USING BTREE,
  INDEX `ModelID`(`ModelID`) USING BTREE,
  CONSTRAINT `ModelID` FOREIGN KEY (`ModelID`) REFERENCES `models` (`ModelID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of coveringarrays
-- ----------------------------
INSERT INTO `coveringarrays` VALUES (10, 'c1', NULL, 1);
INSERT INTO `coveringarrays` VALUES (11, 'c2', NULL, 1);
INSERT INTO `coveringarrays` VALUES (12, 'c3', NULL, 1);

-- ----------------------------
-- Table structure for models
-- ----------------------------
DROP TABLE IF EXISTS `models`;
CREATE TABLE `models`  (
  `ModelID` int(0) NOT NULL AUTO_INCREMENT,
  `ModelName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `ModelDescriptions` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `ModelContent` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `ProjectID` int(0) NULL DEFAULT NULL,
  `CreatedTime` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `LastUpdatedTime` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`ModelID`) USING BTREE,
  INDEX `ProjectID`(`ProjectID`) USING BTREE,
  CONSTRAINT `ProjectID` FOREIGN KEY (`ProjectID`) REFERENCES `projects` (`ProjectID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of models
-- ----------------------------
INSERT INTO `models` VALUES (1, '撒领导小组检测卡', 'test', 'testcont阿斯顿撒旦', 1, '2024-01-22 21:23:20', '2024-01-22 21:49:42');
INSERT INTO `models` VALUES (2, 'm2', 'test', 'testcont', 1, '2024-01-22 21:23:21', '2024-01-22 21:23:21');
INSERT INTO `models` VALUES (3, 'm3', 'test', 'testcont', 1, '2024-01-22 21:23:22', '2024-01-22 21:23:22');
INSERT INTO `models` VALUES (4, 'm3', 'test', 'testtestcont', 1, '2024-01-22 21:23:32', '2024-01-22 21:23:32');

-- ----------------------------
-- Table structure for projects
-- ----------------------------
DROP TABLE IF EXISTS `projects`;
CREATE TABLE `projects`  (
  `ProjectID` int(0) NOT NULL AUTO_INCREMENT,
  `ProjectName` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `ProjectDescriptions` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `UserID` int(0) NULL DEFAULT NULL,
  `CreatedTime` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  `LastUpdatedTime` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`ProjectID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of projects
-- ----------------------------
INSERT INTO `projects` VALUES (1, '阿萨德卡萨丁考虑考虑', 'This is a project used for testing ', 1, '2024-01-22 19:02:03', '2024-01-22 21:59:15');
INSERT INTO `projects` VALUES (2, 'test2', '2', 1, '2024-01-22 18:40:59', '2024-01-22 18:57:37');
INSERT INTO `projects` VALUES (3, 'test3', '3', 1, '2024-01-22 18:40:59', '2024-01-22 18:40:59');
INSERT INTO `projects` VALUES (4, 'test4', '4', 1, '2024-01-22 18:41:03', '2024-01-22 18:41:03');

SET FOREIGN_KEY_CHECKS = 1;
