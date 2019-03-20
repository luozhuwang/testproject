CREATE TABLE `publish_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增长ID',
  `user_code` varchar(255) DEFAULT NULL COMMENT '用户名',
  `user_pwd` varchar(255) DEFAULT '123456' COMMENT '密码',
  `user_name` varchar(255) DEFAULT NULL COMMENT '姓名',
  `user_ip` varchar(255) DEFAULT NULL COMMENT '用户IP',
  `email` varchar(255) DEFAULT NULL COMMENT '邮箱',
  `createtime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_code` (`user_code`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='发布用户表';




