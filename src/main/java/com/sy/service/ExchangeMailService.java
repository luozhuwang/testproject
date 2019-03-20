package com.sy.service;

import java.util.ArrayList;
import microsoft.exchange.webservices.data.core.service.item.EmailMessage;

public interface ExchangeMailService {
	//用户认证
	public void Certified(String user, String password);
	//收取max封邮件
	public ArrayList<EmailMessage> receive(int max);
	//收取所有邮件
	public ArrayList<EmailMessage> receive();
	//发送带邮件	
	public Integer send(String subject, String to_mail, String cc_mail, String content, String[] attachmentPaths);
	//发送不带附件
	public Integer send(String subject, String to_mail, String cc_mail, String content);
}
