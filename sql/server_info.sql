CREATE TABLE `server_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增长ID',
  `test_env` varchar(255) DEFAULT NULL COMMENT '测试环境',
  `server_list` varchar(255) DEFAULT NULL COMMENT '服务器IP',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='测试环境信息';




