CREATE TABLE `admin`  (
  `admin_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `admin_name` varchar(40) CHARACTER SET utf8 COLLATE utf8_hungarian_ci NOT NULL COMMENT '管理员名字',
  `admin_grade` int(11) NULL DEFAULT NULL COMMENT '管理员等级 0超级管理员 1普通管理员',
  `admin_email` varchar(20) CHARACTER SET utf8 COLLATE utf8_hungarian_ci NOT NULL COMMENT '邮箱地址',
  `admin_phone_num` varchar(11) CHARACTER SET utf8 COLLATE utf8_hungarian_ci NOT NULL COMMENT '管理员手机号',
  `admin_identity_num` varchar(18) CHARACTER SET utf8 COLLATE utf8_hungarian_ci NOT NULL COMMENT '管理员身份证号',
  `admin_password` varchar(50) CHARACTER SET utf8 COLLATE utf8_hungarian_ci NOT NULL COMMENT '管理员密码',
  `admin_salt`  varchar(6)  CHARACTER SET utf8 COLLATE utf8_hungarian_ci NOT NULL COMMENT '随机数加密管理员密码',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建日期',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '更新日期',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '创建人id',
  PRIMARY KEY (`admin_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;