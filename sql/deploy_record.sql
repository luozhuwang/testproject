CREATE TABLE `deploy_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增长ID',
  `user_code` varchar(255) DEFAULT NULL COMMENT '用户名',
  `deliver_list` varchar(255) DEFAULT NULL COMMENT '发布版本',
  `server_list` varchar(255) DEFAULT NULL COMMENT '发布机器',
  `online_date` varchar(255) DEFAULT NULL COMMENT '上线日期',
  `createtime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='部署记录';


