package com.sy.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sy.httpclient.HttpClientOperate;
import com.sy.httpclient.HttpResult;
import com.sy.service.PublishService;
import com.sy.service.SSHUtilService;
import com.sy.tool.Constant;
import com.sy.tool.RecordIP;


@Controller
public class SSHUtilController {
	private Logger log = LoggerFactory.getLogger(SSHUtilController.class);
	
	
	@Autowired
	private SSHUtilService sshUtilService;
	
	@Autowired 
	private HttpClientOperate httpClient;
	
	@Autowired
	private PublishService publishService;
	
	@RequestMapping(value="/clean")
	public String Cleaner(){
		return "/Linux/clean";
	}
	
	@RequestMapping(value="/home")
	public String home(HttpServletRequest  request) throws ClientProtocolException, IOException{
		String ip=RecordIP.getIpAddress(request);
		log.info("访问工具用户 IP:"+ip);
		Map<String, String> nameValuePairs=new HashMap<String, String>();
		nameValuePairs.put("toolId", "186");
		nameValuePairs.put("type", "active");
		nameValuePairs.put("ip", ip);
		HttpResult resut=httpClient.doPost(Constant.toolStatistics, nameValuePairs);
		String response=resut.getContent();
		log.info(response);
		return "home";
	}
	
	
	@RequestMapping(value="/CleanAction",method=RequestMethod.POST)
	public String CleanLogFile(@RequestParam String LinuxIP,@RequestParam String FileSize,HttpServletRequest  request,Model model, HttpSession session){
		String ip=RecordIP.getIpAddress(request);
		log.info("Custom IP:"+ip);
		String info="";
		String logininfo=sshUtilService.login(Constant.Linux_UserName, Constant.Linux_Password, LinuxIP, Constant.SSH_port);
		if(logininfo.contains("successfull")){			
			try {
				String fileName = session.getServletContext().getRealPath("/")+"File/CleanLogFile.sh" ;
				File file = new File(fileName);
				InputStream is = new FileInputStream(file);
				sshUtilService.upload("/log", "CleanLogFile.sh", is); 
				String execCmdinfo=sshUtilService.execCmd("/bin/bash /log/CleanLogFile.sh "+FileSize);
				sshUtilService.logout();  
				info=logininfo+execCmdinfo;
				model.addAttribute("info", info);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} 
			return "/Linux/SHHResult";
		}else{
			model.addAttribute("logininfo",logininfo);
			return "/Linux/SHHfail";
		}
	}
	
	
	 
}

