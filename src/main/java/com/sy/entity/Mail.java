package com.sy.entity;

import java.io.Serializable;

public class Mail implements Serializable{
	
	private static final long serialVersionUID = -2585748724408505276L;
	
	private String id;
	private String user;
	private String password;
	private String subject;
	private String to_mail;
	private String cc_mail;
	private String content;
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	private String createtime;
	private String[] attachmentPaths;
	
	
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getTo_mail() {
		return to_mail;
	}
	public void setTo_mail(String to_mail) {
		this.to_mail = to_mail;
	}
	public String getCc_mail() {
		return cc_mail;
	}
	public void setCc_mail(String cc_mail) {
		this.cc_mail = cc_mail;
	}

	public String[] getAttachmentPaths() {
		return attachmentPaths;
	}
	public void setAttachmentPaths(String[] attachmentPaths) {
		this.attachmentPaths = attachmentPaths;
	}

	
	

}
