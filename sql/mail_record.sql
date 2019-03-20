CREATE TABLE `mail_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增长ID',
  `subject` varchar(255) DEFAULT NULL COMMENT '邮件标题',
  `to_mail` varchar(255) DEFAULT NULL COMMENT '收件人',
  `cc_mail` varchar(255) DEFAULT NULL COMMENT '抄送人',
  `content` longtext COMMENT '邮件正文',
  `createtime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='邮件发送记录';



