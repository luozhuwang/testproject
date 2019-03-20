CREATE TABLE `operator_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增长ID',
  `content` varchar(255) DEFAULT NULL COMMENT '内容',
  `user_ip` varchar(255) DEFAULT NULL COMMENT '用户名',
  `operate` varchar(255) DEFAULT NULL,
  `createtime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='操作记录表';





