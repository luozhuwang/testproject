package com.sy.dao;

import org.apache.ibatis.annotations.Param;

public interface MailDao {
	//添加邮件发送记录
	Integer insertMail(@Param("subject")String subject,@Param("to_mail")String to_mail,@Param("cc_mail")String cc_mail,@Param("content")String content);

}
