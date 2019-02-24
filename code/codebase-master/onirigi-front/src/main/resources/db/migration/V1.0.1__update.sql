SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for people
-- ----------------------------

CREATE TABLE `people`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(40) CHARACTER SET utf8 COLLATE utf8_hungarian_ci NOT NULL COMMENT '名字',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '创建人id',
  `age` int(11) NOT NULL COMMENT '年龄',
  `company` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 38 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of people
-- ----------------------------
INSERT INTO `people` VALUES (2, 'ninini', '2019-02-13 16:39:46', 2, 0, NULL);
INSERT INTO `people` VALUES (3, 'dsds', '2019-02-19 16:20:43', 3, 18, 'bb');
INSERT INTO `people` VALUES (4, 'www', '2019-02-20 11:41:49', 4, 4, 'aa');
INSERT INTO `people` VALUES (5, 'qqq', '2019-02-20 11:41:59', 5, 5, 'aa');
INSERT INTO `people` VALUES (6, 'aaa', '2019-02-20 11:42:09', 6, 6, NULL);
INSERT INTO `people` VALUES (7, 'bbb', '2019-02-20 11:42:18', 7, 7, NULL);
INSERT INTO `people` VALUES (8, 'wowow', '2019-02-20 11:42:28', 8, 8, NULL);
INSERT INTO `people` VALUES (9, 'wowowo', '2019-02-20 11:42:44', 9, 9, NULL);
INSERT INTO `people` VALUES (10, 'nini', '2019-02-20 11:42:55', 10, 10, NULL);
INSERT INTO `people` VALUES (11, 'wo', '2019-02-20 11:43:35', 11, 11, NULL);
INSERT INTO `people` VALUES (14, 'xiaohei', '2019-02-20 16:32:27', 20, 45, NULL);
INSERT INTO `people` VALUES (15, 'ruiqi', '2019-02-22 17:17:28', NULL, 15, 'tuixiang');
INSERT INTO `people` VALUES (16, 'xiaohui', '2019-02-20 17:48:15', 20, 44, NULL);
INSERT INTO `people` VALUES (17, 'xiaolv', '2019-02-20 17:48:53', 1, 44, NULL);
INSERT INTO `people` VALUES (18, 'xiaolan', '2019-02-20 17:49:34', 1, 44, NULL);
INSERT INTO `people` VALUES (19, 'rruiqi1', '2019-02-20 17:58:39', 101, 22, 'tt');
INSERT INTO `people` VALUES (20, 'rruiqi2', '2019-02-20 17:58:50', 102, 25, 'tt');
INSERT INTO `people` VALUES (21, 'rruiqi3', '2019-02-20 17:59:02', 103, 25, 'tt');
INSERT INTO `people` VALUES (22, 'rruiqi4', '2019-02-20 17:59:08', 104, 25, 'tt');
INSERT INTO `people` VALUES (23, 'rruiqi5', '2019-02-20 17:59:13', 105, 25, 'tt');
INSERT INTO `people` VALUES (24, 'rruiqi6', '2019-02-20 17:59:18', 106, 25, 'tt');
INSERT INTO `people` VALUES (25, 'rruiqi7', '2019-02-20 17:59:22', 107, 25, 'tt');
INSERT INTO `people` VALUES (26, 'rruiqi8', '2019-02-20 17:59:27', 108, 25, 'tt');
INSERT INTO `people` VALUES (28, 'rruiqi10', '2019-02-20 17:59:37', 110, 25, 'tt');
INSERT INTO `people` VALUES (29, 'xiaozi', '2019-02-20 20:32:27', NULL, 44, NULL);
INSERT INTO `people` VALUES (30, '', '2019-02-20 20:40:39', 20, 44, NULL);
INSERT INTO `people` VALUES (31, '', '2019-02-20 20:41:50', 20, 44, NULL);
INSERT INTO `people` VALUES (32, '', '2019-02-20 20:43:08', 20, 44, NULL);
INSERT INTO `people` VALUES (33, '', '2019-02-20 20:48:09', 21, 44, NULL);
INSERT INTO `people` VALUES (34, '', '2019-02-20 20:49:04', 26, 44, NULL);
INSERT INTO `people` VALUES (35, '', '2019-02-20 20:58:53', 26, 44, NULL);
INSERT INTO `people` VALUES (36, 're', '2019-02-20 21:01:22', 29, 44, NULL);
INSERT INTO `people` VALUES (37, 'de', '2019-02-20 21:08:19', 87, 44, NULL);

-- ----------------------------
-- Table structure for test
-- ----------------------------

CREATE TABLE `test`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(40) CHARACTER SET utf8 COLLATE utf8_hungarian_ci NOT NULL COMMENT '名字',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '创建人id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of test
-- ----------------------------
INSERT INTO `test` VALUES (2, 'ninini', '2019-02-13 16:39:46', 2);
INSERT INTO `test` VALUES (3, 'xiaoming', '2019-02-18 18:44:39', 1);
INSERT INTO `test` VALUES (4, 'wowo', '2019-02-19 11:30:07', 3);

SET FOREIGN_KEY_CHECKS = 1;