package com.sy.controller;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.cookie.Cookie;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sy.dao.MockDao;
import com.sy.entity.MessageResult;
import com.sy.entity.MockInfo;
import com.sy.entity.reqData;
import com.sy.entity.data;



@Controller
public class MockController {
//	MockServerClient mockClient = null;
	private static ClientAndServer mockServer=null;
	private MockInfo mockInfo=null;
	
	@Autowired
	private MockDao mockDao;
	
	/**
	 * 异常延迟
	 * @param	second
	 * */
	@Async
	public void Delay(int second){
		try {
			int time=second*1000;
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 请求JSON格式的数据,返回JSON
	 * */
	@RequestMapping(value="/query/json",method=RequestMethod.POST)
	@ResponseBody
	public  String userInfo(@RequestBody Map<String, Object> params){
		String userName=params.get("userName").toString();
		String passWd=params.get("passWd").toString();

		System.out.println(userName);
		System.out.println(passWd);
		String result="{\"userRole\":\"2\",\"mobile\":\"132xxxxx\",\"customerNo\":\"56\",\"status\":\"1\",\"auditState\":\"2\",\"issueDate\":\"2018-06-07\",\"authList\":[]}";
		Delay(3);
		return result;
	}
	/**
	 * 请求文本数据,返回JSON
	 * */
	@RequestMapping(value="/query/text",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	public  String requesttext(@RequestParam Map<String, Object> params){
		StringBuffer sb =new StringBuffer();
		for (String key : params.keySet()) {
//			System.out.println(key + "=" + params.get(key));
		      sb.append(key + "=" + params.get(key)+"&");		   
		 }
		String ss=sb.substring(0, sb.length()-1);
		System.out.println("完整数据2："+ss);
		String result="{\"requestNo\":\"req20170512200457240\",\"respCode\":\"10015\",\"respMsg\":\"用户未绑卡或已解绑\",\"status\":\"FAIL\",\"userNo\":\"User6135\"}";
		return result;
	}
	
	/**
	 * 请求文本数据,返回JSON
	 * */
	@RequestMapping(value="/mandao/PERSONAL_REGISTER",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	public  String personRegister(@RequestParam Map<String, Object> params){
		String result=null;
		String ss=params.get("reqData").toString();
		System.out.println("ss:"+ss);
		reqData request=JSON.parseObject(ss, new TypeReference<reqData>(){});
		
		String requestNo=request.getRequestNo();
		String userNo=request.getUserNo();
		String realName=request.getRealName();
		String idCardType=request.getIdCardType();
		String idCardNo=request.getIdCardNo();
		String userRole=request.getUserRole();
		String mobile=request.getMobile();
		String bankCardNo=request.getBankCardNo();
		String authList=request.getAuthList();
		String bankCode=null;
		
		if(bankCardNo.startsWith("6222020")){
			bankCode="ICBC";
		}else if (bankCardNo.startsWith("6227000")){
			bankCode="CCB";
		}else if (bankCardNo.startsWith("6214830")){
			bankCode="CMB";
		}
		
		if(idCardType.equals("PRC_ID")){
			System.out.println("证件类型为身份证:"+idCardNo);
			if(idCardNo.equals("432146199001072012")){
				System.out.println("开户失败");	
				result="{\"requestNo\":\""+requestNo+"\",\"respCode\":\"10005\",\"respMsg\":\"用户注册错误\",\"status\":\"FAIL\",\"userNo\":\""+userNo+"\"}";
			}else{
				if(bankCode.equals(null)){
					result="{\"requestNo\":\""+requestNo+"\",\"respCode\":\"10041\",\"respMsg\":\"不支持该银行卡\",\"status\":\"FAIL\",\"userNo\":\""+userNo+"\"}";
				}
				result="{\"requestNo\":\""+requestNo+"\",\"userNo\":\""+userNo+"\",\"realName\":\""+realName+"\",\"idCardType\":\""+idCardType+"\",\"idCardNo\":\""+idCardNo+"\",\"userRole\":\""+userRole+"\",\"mobile\":\""+mobile+"\",\"bankCardNo\":\""+bankCardNo+"\",\"bankCode\":\""+bankCode+"\",\"accessType\":\"FOUR_FACTOR_CHECKED\",\"authAmount\":\"200000.00\",\"authDeadLine\":\"2050-12-31\",\"authList\":\""+authList+"\",\"respCode\":\"00000\",\"respMsg\":\"成功\",\"status\":\"SUCCESS\"}";
			}
			
		}else if(idCardType.equals("PASSPORT")){
			System.out.println("护照:"+idCardNo);
		}
		
//		String result="{\"requestNo\":\"req20170512200457240\",\"respCode\":\"10015\",\"respMsg\":\"用户未绑卡或已解绑\",\"status\":\"FAIL\",\"userNo\":\"User6135\"}";
		return result;
	}
	
	/**
	 * 返回文本数据：html/XML/text
	 * 参考文献：https://blog.csdn.net/zhanyu1/article/details/78117725
	 * */
	@RequestMapping(value="/query/list",method=RequestMethod.GET,produces="text/html;charset=UTF-8")
	@ResponseBody
	public void requestget(HttpServletResponse  response,@RequestParam(required=true)  Long parentId){		
		response.addHeader("expires", "Tue, 28 Aug 2018 02:29:40 GMT");		
		response.setStatus(response.SC_OK);
		try {
			response.setContentType("text/html;charset=utf-8"); 
			PrintWriter out = response.getWriter();
			out.println("<html><head></head><body>1111</body></html>");
//			out.println("张三");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 下载文件,方法一
	 * */
	@RequestMapping(value="/query/down",method=RequestMethod.GET)
	public void downfile(HttpServletRequest request  ,HttpServletResponse  response) throws IOException{
		 try {
//			 System.out.println("路径："+request.getServletContext().getRealPath("/image/blue.jpg"));
			//获取要下载的文件的绝对路径,JPG/JS/HTML都可以
			 String realPath=request.getServletContext().getRealPath("/jQuery/jquery.validate.js");
			 String fileName = realPath.substring(realPath.lastIndexOf("\\")+1);//获取要下载的文件名
			 //设置content-disposition响应头控制浏览器以下载的形式打开文件，中文文件名要使用URLEncoder.encode方法进行编码，否则会出现文件名乱码
			response.setHeader("content-disposition", "attachment;filename="+URLEncoder.encode(fileName, "UTF-8"));
			InputStream in = new FileInputStream(realPath);//获取文件输入流
			int len = 0;
			byte[] buffer = new byte[2048];
			OutputStream out = response.getOutputStream();
			while ((len = in.read(buffer)) > 0) {
				out.write(buffer,0,len);//将缓冲区的数据输出到客户端浏览器
			}
			in.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 下载文件,方法二
	 * */
	@RequestMapping(value="/query/SpringMVCdown",method=RequestMethod.GET)
	public String download( HttpServletRequest request, HttpServletResponse response){
		//获取要下载的文件的绝对路径,JPG/JS/HTML都可以
		String realPath=request.getServletContext().getRealPath("/image/blue.jpg");
		//显示文本
//		response.setContentType("text/html;charset=utf-8");
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
				
		java.io.BufferedInputStream bis = null;
		java.io.BufferedOutputStream bos = null;
		
		try {
			long fileLength = new File(realPath).length();
//			response.setContentType("application/x-msdownload;");
			response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(realPath, "UTF-8"));
			response.setHeader("Content-Length", String.valueOf(fileLength));
			bis = new BufferedInputStream(new FileInputStream(realPath));
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bis != null)
				try {
					bis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if (bos != null)
				try {
					bos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return null;	
	}
	
/*	
	@RequestMapping(value="/mock/switch")
	public void mockswitch(String status,String id,Model model){
		if(mockServer ==null){
			mockServer=startClientAndServer(1080);
		}
		mockInfo=mockDao.getRecordById(id);
		if(status.equals("1")){	
			mockServer.when(
			request().withKeepAlive(true).withPath(mockInfo.getUrl()).withMethod(mockInfo.getMethod()))
			.respond(response().withStatusCode(mockInfo.getResponseStatusCode()).withBody(mockInfo.getResponseBody()));			
		}else{
			mockServer.stop();
		}
		//更新开启状态
		mockDao.updateStatus(id, status);
	}


	@RequestMapping(value="/mock/list")
	public String mockList(String name,@RequestParam(value="pagenum",defaultValue="1") Integer pagenum,Model model){
		PageHelper.startPage(pagenum, 15);
		List<MockInfo> mockInfo=mockDao.getMockRecord(name);
		if(mockServer ==null){
			mockServer=startClientAndServer(1080);
		}
		
		for(MockInfo mock:mockInfo){
			mockServer.when(request()
					.withMethod(mock.getMethod())
					.withPath(mock.getUrl())
					.withBody(mock.getParameters()!=null?mock.getParameters():null)
			).respond(response()	
					.withStatusCode(mock.getResponseStatusCode())
					.withBody(mock.getResponseBody())
			);
			
		}
		
		PageInfo<MockInfo> pageinfo=new PageInfo<MockInfo>(mockInfo);
		model.addAttribute("mockInfoList",pageinfo);
		model.addAttribute("interfaceName",name);

		return "Mock/mock";
	}
*/
		
	
	public static void main(String args[]){
		String ss="{\"userNo\":\"00202000000005894051\",\"requestNo\":\"80000010180829102455624829000515\",\"realName\":\"陈真\",\"idCardType\":\"PRC_ID\",\"idCardNo\":\"432146199001072012\",\"userRole\":\"BORROWERS\",\"mobile\":\"18827032841\",\"bankCardNo\":\"6222020000000008848\",\"verifyCardChannel\":\"XINYAN\",\"checkType\":\"LIMIT\",\"callbackUrl\":\"http://cunguan.xiaoniu88.com.cn:9038/loan/front/openAccount/callback\",\"userLimitType\":\"ID_CARD_NO_UNIQUE\",\"authList\":\"REPAYMENT,COMPENSATORY_REPAYMENT\",\"expired\":\"20180829103455\",\"authAmount\":\"200000.00\",\"authDeadLine\":\"2050-12-31\"}";
		
		
		System.out.println("ss:"+ss);
		reqData request=JSON.parseObject(ss, new TypeReference<reqData>(){});
		
		String requestNo=request.getRequestNo();
		String userNo=request.getUserNo();
		
		System.out.println(requestNo);
		System.out.println(userNo);
	}
}




