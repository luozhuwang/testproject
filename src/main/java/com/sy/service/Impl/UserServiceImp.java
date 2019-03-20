package com.sy.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sy.dao.UserDao;
import com.sy.entity.UserInfo;
import com.sy.service.UserService;

@Service
public class UserServiceImp implements UserService{
	
	@Autowired
	private UserDao userDao;

	@Override
	public UserInfo checkUser(String userName, String password) {
		UserInfo user=userDao.findByUsername(userName, password);
		if (user != null) {
			return user;
		}
		return null;
	}

	@Override
	public Integer register(String user_code, String user_pwd,String user_name, String user_ip, String email) {
		int count=userDao.registeredUser(user_code, user_pwd, user_name, user_ip, email);
		return count;
	}

	
}
