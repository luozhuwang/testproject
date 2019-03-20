package com.sy.controller;

import java.io.IOException;  
import java.io.PrintWriter;  
import java.util.ArrayList;  
import java.util.List;  

import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;  
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.web.bind.annotation.RequestMethod;  
import org.springframework.web.bind.annotation.RequestParam;  
import org.springframework.web.bind.annotation.ResponseBody;  

import com.alibaba.fastjson.JSON;  
import com.sy.entity.User;
import com.sy.entity.UserInfo;
import com.sy.service.UserService;
import com.sy.tool.RecordIP;

  
@Controller  
public class UserController {
	private Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	
	@RequestMapping("/login")
	public String Login(){
		return "/User/login";
	}
	
	@RequestMapping("/loginAction")
	public String LoginAction(UserInfo user,Model model,HttpSession session){
		UserInfo loginUser=userService.checkUser(user.getUser_code(), user.getUser_pwd());
		//session保存登陆 用户
		if(loginUser!=null){
			session.setAttribute("user_name", loginUser.getUser_name());
			return "redirect:/home";
		}else{		
			model.addAttribute("errorMsg", "用户名或密码错误，请重新输入");
			return "/User/login";
		}		
	}
	
	@RequestMapping("/register")
	public String register(){
		return "/User/register";
	}
	
	@RequestMapping("/registerAction")
	public String registerAction(UserInfo user,HttpServletRequest request,Model model){
		String ip=RecordIP.getIpAddress(request);
		log.info("Custom IP:"+ip);		
		if(StringUtils.isBlank(user.getUser_code()) || StringUtils.isBlank(user.getUser_name())){
			model.addAttribute("errorMsg", "注册用户不能为空");
			return "/User/register";	
		}else{
			int count=userService.register(user.getUser_code(),user.getUser_pwd(),user.getUser_name(), ip, user.getEmail());			
			if(count<=0){
				model.addAttribute("errorMsg", "注册失败，请重新注册");
				return "redirect:/home";
			}else{
				return "/User/login";
			}	
		}
		
	}
	
	
	@RequestMapping("/outLogin")
	public String Longinout(HttpSession session){
		// 通过session.invalidata()方法来注销当前的session
		session.invalidate();
		return "/User/login";
	}
	
	
	@RequestMapping("/demo")
	public String demo(){
		return "/test/demo";
	}
      
	@RequestMapping("/ajax")
	public String ajax(){
		return "/test/ajax";
	}
    @RequestMapping(value = "ajax1", method = RequestMethod.GET)  
    public @ResponseBody String ajax1(@RequestParam(value="username", required=true) String username,   
                    @RequestParam(value="password", required=true) String password){  
        System.out.println("ajax");  
        System.out.println(username);  
        System.out.println(password);  
        System.out.println("ok");  
        return "ass我是谁";  
    }  
      
    @RequestMapping(value="ajax2", method = RequestMethod.GET)  
    public @ResponseBody String ajax2(HttpServletRequest request){  
        String username = request.getParameter("username");  
        String password = request.getParameter("password");  
        System.out.println(username);  
        System.out.println(password);  
        return "哈哈哈";  
    }  
      
    @RequestMapping(value="ajax3", method = RequestMethod.GET)  
    public @ResponseBody void ajax3(HttpServletRequest request, HttpServletResponse response) throws IOException{  
        String username = request.getParameter("username");  
        String password = request.getParameter("password");  
        System.out.println(username);  
        System.out.println(password);  
          
        //解决乱码  
        response.setContentType("text/html;charset=utf-8");  
        PrintWriter out = response.getWriter();  
        out.write("哈哈哈");  
    }  
      
    @RequestMapping(value="ajax4", method = RequestMethod.GET)  
    public @ResponseBody String ajax4(@RequestParam(value="username", required=true) String username,   
            @RequestParam(value="password", required=true) String password){  
        System.out.println("ajax4");  
        System.out.println(username);  
        System.out.println(password);  
        return JSON.toJSONString(new User("1", "我试试"));  
    }  
      
    @RequestMapping(value="ajax5", method = RequestMethod.POST)  
    public @ResponseBody String ajax5(@RequestParam(value="username", required=true) String username,   
            @RequestParam(value="password", required=true) String password){  
        System.out.println("ajax5");  
        System.out.println(username);  
        System.out.println(password);  
        return JSON.toJSONString(new User("1", "我试试"));  
    }  
  
    @RequestMapping(value="ajax6", method = RequestMethod.GET)  
    public @ResponseBody String ajax6(){  
        System.out.println("ajax6");  
        List<User> userList = new ArrayList<User>();  
        userList.add(new User("1", "我试试"));  
        userList.add(new User("2", "阿达"));  
        userList.add(new User("3", "a都是"));  
        return JSON.toJSONString(userList);  
    }  
}  