/*package com.sy.controller;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import java.net.InetSocketAddress;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.net.httpserver.HttpServer;
import com.sy.dao.MockDao;
import com.sy.entity.MockInfo;
import com.sy.service.MockService;
import org.mockserver.integration.ClientAndServer;
*/
/**
 * 在web.xml中配置随tomcat一起启动
 * **/
/*
public class mockmain implements ServletContextListener{
	ClientAndServer mockServer=null;
	
	private ApplicationContext applicationContext;  
	
	
//	@Resource
//	private MockService mockService;
	
//	public static void main(String[] args) {
//		 ClientAndServer mockServer=startClientAndServer(1080);
//		 
//			String expected = "{ message: 'incorrect username and password combination' }";	
//		
//			mockServer
//			.when(
//			request()
//			.withMethod("POST")
//			.withPath("/postUsers")
//			.withBody("{username:'foo', password:'bar'}")
//			)
//			.respond(
//			response()
//			.withStatusCode(200)
//			.withHeaders(
//			new Header("Content-Type", "application/json; charset=utf-8"),
//			new Header("Cache-Control", "public, max-age=86400")
//			)
//			.withBody("{ \"apply_id\": \"000001\", \"overdued\": \"Y\" }")
//			);
//		
//			mockServer.when(
//			request()
//			.withPath("/getUser")
//			.withMethod("GET")
//			).respond(
//			response()
//			.withStatusCode(200)
//			.withBody(expected)
//			);  
//
//			
//	}	

}
*/