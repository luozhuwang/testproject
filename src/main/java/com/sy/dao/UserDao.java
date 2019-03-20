package com.sy.dao;

import org.apache.ibatis.annotations.Param;

import com.sy.entity.UserInfo;

public interface UserDao {
	
	
	/**
	 * 查找用户名和密码
	 * @param username 登录用户名 
	 * @param password 密码
	 * @return
	 */
	UserInfo findByUsername(String user_code,String user_pwd);
	
	/**
	 * 注册用户
	 * */
	Integer registeredUser(@Param("user_code")String user_code,@Param("user_pwd")String user_pwd,@Param("user_name")String user_name,@Param("user_ip")String user_ip,@Param("email")String email );
}
