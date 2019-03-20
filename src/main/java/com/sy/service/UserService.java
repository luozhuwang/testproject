package com.sy.service;


import com.sy.entity.UserInfo;

public interface UserService {	
	//检查用户
	UserInfo checkUser(String userName, String password);
	//注册用户
	Integer register(String user_code,String user_pwd,String user_name,String user_ip,String email );
}
