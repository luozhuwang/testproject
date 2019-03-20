package com.sy.controller;


import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sy.entity.RequestInfo;
import com.sy.service.TelnetService;
import com.sy.service.Impl.TelnetServiceImp;
import com.sy.tool.JsonFormatTool;
import com.sy.tool.RecordIP;

@Controller
public class TelnetController {
	private  Logger log = LoggerFactory.getLogger(TelnetController.class);
	
	@Autowired
	private TelnetService telnetService;
	
	
	@RequestMapping(value="/invoke")
	public String invoke(){
		return "/Telnet/invoke";
	}
	
	@RequestMapping(value="/invokeAction",method=RequestMethod.POST, produces="text/plain;charset=utf-8")
	@ResponseBody
	public String invokeAction(RequestInfo requestinfo,HttpServletRequest request,Model model){
		String message=null;
		String ip=RecordIP.getIpAddress(request);
		log.info("Custom IP:"+ip);
		boolean connect=telnetService.connect(requestinfo.getRequestIP(), requestinfo.getRequestPort());
		if(connect){	
			String method=requestinfo.getRequestMethod();
			String command=null;
			if(method.startsWith("ls")){
				command=method;
			}else{				
				command="invoke "+requestinfo.getRequestMethod()+"("+requestinfo.getRequestParam()+")";				
			}
			String response=telnetService.execute(command);
			telnetService.disconnect();
			message=JsonFormatTool.formatJson(response);
		}
		return message;
	}
	
	@RequestMapping(value="/invokebatch",method=RequestMethod.POST, produces="text/plain;charset=utf-8")
	@ResponseBody
	public String invokebatch(RequestInfo requestinfo,HttpServletRequest request,Model model){
		String message=null;
		String ip=RecordIP.getIpAddress(request);
		log.info("Custom IP:"+ip);
		boolean connect=telnetService.connect(requestinfo.getRequestIP(), requestinfo.getRequestPort());
		String method=requestinfo.getRequestMethod();					
		String Param=requestinfo.getRequestParam();			
		if(connect){	
					String [] ParamArr= Param.split("\\|");
					for(String requ:ParamArr){					
						String command="invoke "+method+"("+requ+")";				
						telnetService.execute(command);
					}
					message="批量执行完毕";

			telnetService.disconnect();
		}
		return message;
	}
	
	public static void main(String args[]){
		TelnetServiceImp telnetService= new TelnetServiceImp();
		RequestInfo requestinfo=new RequestInfo();
		requestinfo.setRequestIP("172.20.20.215");
		requestinfo.setRequestPort(6038);
		requestinfo.setRequestMethod("com.neo.xnol.loan.facade.LoanPaymentFacade.payment");
		Date date =new Date();
		String param="{\"serialId\":\"WI0995ad89180815b720038d34f0c\",\"loanStatus\":\"HAS_BEEN_PAYMENT\",\"paymentDate\":"+date.getTime()+",\"submitPaymentDate\":"+date.getTime()+",\"remark\":\"测试接口\"}";
		requestinfo.setRequestParam(param);
		telnetService.connect(requestinfo.getRequestIP(), requestinfo.getRequestPort());
		String command="invoke "+requestinfo.getRequestMethod()+"("+requestinfo.getRequestParam()+")";	
		
		String response=telnetService.execute(command);
		telnetService.disconnect();
		
		System.out.println(response);
	}
}
