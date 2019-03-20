package com.sy.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sy.service.Config;
import com.sy.service.TelnetService;
import com.sy.tool.RecordIP;

@Controller
public class SecurityController {
	private Logger log=LoggerFactory.getLogger(SecurityController.class); 
	
	@Autowired
	TelnetService telnetService;
	
	@Autowired
	private Config config;
	
	@RequestMapping(value="/security")
	public String invoke(){
		return "/Telnet/security";
	}
	
	@RequestMapping(value="/encryptionAction",method=RequestMethod.POST)
	@ResponseBody
	public String encryptionAction(HttpServletRequest  request,String Data,String type){
		String ip=RecordIP.getIpAddress(request);
		log.info("访问加密IP:"+ip);
		telnetService.connect(config.linux_IP, 6028);
		String command=null;
		//type为0:手机号、邮箱、姓名
		if(type.equals("0")){			
			command="invoke com.neo.encryptData(\""+Data+"\")";
		}else if(type.equals("1")){
			//type为1:身份证			
			command="invoke com.neo.encryptIdNo(\""+Data+"\")";
		}else if(type.equals("2")){
			//type为2:银行卡			
			command="invoke com.neo.encryptCardNo(\""+Data+"\")";
		}
		String response=telnetService.execute(command);
		telnetService.disconnect();
		
		return response.replace("\"", "");
	}
	
	@RequestMapping(value="/decryptAction",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String decryptAction(HttpServletRequest  request,String Data,String type){
		String ip=RecordIP.getIpAddress(request);
		log.info("访问解密IP:"+ip);
		telnetService.connect(config.linux_IP, 6028);
		String command=null;
		//type为0:手机号、邮箱、姓名
		if(type.equals("0")){			
			command="invoke com.neo.decryptData(\""+Data+"\")";
		}else if(type.equals("1")){
			//type为1:身份证			
			command="invoke com.neo.decryptIdNo(\""+Data+"\")";
		}else if(type.equals("2")){
			//type为2:银行卡			
			command="invoke com.neo.decryptCardNo(\""+Data+"\")";
		}
		String response=telnetService.execute(command);
		telnetService.disconnect();
		
		return response.replace("\"", "");
	}
}
