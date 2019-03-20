package com.sy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sy.service.Config;

@Controller
public class ConfigController {

	@Autowired
	private Config config;
	
	@RequestMapping("/testconfig")
	public String  test(Model model ){
		model.addAttribute("mail_user", config.mail_user);
		model.addAttribute("mail_pwd", config.mail_pwd);
		model.addAttribute("linux_IP", config.linux_IP);
		model.addAttribute("svn_user", config.svn_user);
		
		System.out.println("mail_user:"+config.mail_user);
		System.out.println("mail_pwd:"+config.mail_pwd);
		System.out.println("linux_IP:"+config.linux_IP);
		System.out.println("svn_user:"+config.svn_user);
		return "/test/testconfig";
	}
}
