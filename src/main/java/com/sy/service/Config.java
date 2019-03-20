package com.sy.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

//参考文献：http://www.jb51.net/article/106740.htm
@Configuration
@PropertySource(value="classpath:config.properties")
public class Config {
	
	@Value("${mail_user}")
	public String  mail_user;
	
	@Value("${mail_pwd}")
	public String mail_pwd;
	
	@Value("${mail_to}")
	public String mail_to;

	@Value("${mail_cc}")
	public String mail_cc;
	
	@Value("${svn_user}")
	public String svn_user;
	
	@Value("${svn_passwd}")
	public String svn_passwd;
	
	@Value("${linux_IP}")
	public String linux_IP;
}
