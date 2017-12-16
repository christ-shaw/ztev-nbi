CREATE TABLE if NOT EXISTS `registry_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `operator_id` varchar(255) DEFAULT NULL,
  `secret_key` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of registry_info
-- ----------------------------

INSERT IGNORE INTO `registry_info` VALUES (1, 'HunanHaiLong', '10001', 'NZFfwBv53OGt9FFh');
INSERT IGNORE INTO `registry_info` VALUES (2, 'ZheJiangGongzong', '10002', 'vp2BDUGdfUDPIRXs');
