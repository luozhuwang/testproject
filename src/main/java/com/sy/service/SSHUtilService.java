package com.sy.service;

import java.io.InputStream;

import com.jcraft.jsch.SftpException;

public interface SSHUtilService {
	//登陆
	public String login(String username, String password, String host, int port);
	//登出
	public void logout();
	//执行命令
	public String execCmd(String command) throws Exception;
	//上传文件
	public void upload(String directory, String sftpFileName, InputStream input) throws SftpException;
}
