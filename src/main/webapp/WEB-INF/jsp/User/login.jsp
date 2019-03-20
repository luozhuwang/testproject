<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
 <title>小牛在线工具</title>
 <script type="text/javascript" src="<%=basePath%>jQuery/2.1.4/jquery.js"></script>
<script type="text/javascript" src="<%=basePath%>jQuery/2.1.4/jquery.min.js"></script>
 <script type="text/javascript" src="<%=basePath%>js/user/page_login.js"></script>
 <link rel="stylesheet" type="text/css" href="<%=basePath%>bootstrap/3.3.7/css/bootstrap.min.css">
 <link rel="stylesheet" type="text/css" href="<%=basePath%>css/user/login.css" />
</head>
<body>
<div class="login_box">
      <div class="login_l_img"><img src="<%=basePath%>image/user/login-img.png" /></div>
      <div class="login">
          <div class="login_logo"><a href="#"><img src="<%=basePath%>image/user/login_logo.png" /></a></div>
          <div class="login_name">
               <p>小牛在线工具</p>
          </div>
          <form method="post" action="/testproject/loginAction" >	  	
				<table>
					<tr id="usertext">
						<td><input id="username" name="user_code" placeholder="用户名" type="text" style="width:280px; height: 50px"></td>						       
					</tr>
					<tr>
						<td><input id="password" name="user_pwd" placeholder="密码" type="password" style="width:280px; height: 50px"></td>
					</tr>
					<tr >
						<td >
          					<p id="errMsg" class="po err" style="color:red">${errorMsg}</p>
							<input value="登录"  type="submit" style="width: 140px; height: 50px"></input>
							<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span>
							<a href="register"  style="width: 140px; height: 50px">注册</a>
						</td>
					</tr>
				</table>
          </form>
      </div>
</div>
</body>
</html>