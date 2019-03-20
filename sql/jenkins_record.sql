CREATE TABLE `jenkins_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增长ID',
  `user_code` varchar(255) DEFAULT NULL COMMENT '用户名',
  `deliver_list` varchar(255) DEFAULT NULL COMMENT '发布版本',
  `server_list` varchar(255) DEFAULT NULL COMMENT '发布机器',
  `online_date` varchar(255) DEFAULT NULL COMMENT '上线日期',
  `deliver_match` varchar(255) DEFAULT NULL COMMENT '服务名',
  `deliver_type` varchar(255) DEFAULT NULL COMMENT '类型:docker.app、nfs、docker.web、other',
  `status` varchar(255) DEFAULT NULL COMMENT '状态:confirm(确认)、publish(部署)、draft(草稿)、abandoned(废弃)',
  `createtime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updatetime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='jenkins发布记录';


