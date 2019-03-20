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
<script type="text/javascript" src="<%=basePath%>js/user/page_regist.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/user/register.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>bootstrap/3.3.7/css/bootstrap-datepicker3.min.css">
<script type="text/javascript" src="<%=basePath%>bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container">
		<h2 class="text-center">用户注册</h2>
		<form action="registerAction" method="post" class="form-horizontal">
			<div class="form-group">
				<label for="username" class="col-md-2 col-md-offset-3 control-label">用户名<b>*</b></label>
				<div class="col-md-3">
					<input id="user_code" name="user_code" type="text"
						placeholder="4-10个英文字母或数字" class="form-control">
				</div>
				<div class="col-md-4">
					<label id="errorname" class="control-label errstyle"></label>
				</div>
			</div>
			<div class="form-group">
				<label for="password" class="col-md-2 col-md-offset-3 control-label">密码<b>*</b></label>
				<div class="col-md-3">
					<input id="user_pwd" name="user_pwd" type="password"
						placeholder="8-16个英文字母或数字" class="form-control">
				</div>
				<div class="col-md-4">
					<label id="errorpassword" class="control-label errstyle"></label>
				</div>
			</div>
			<div class="form-group">
				<label for="name" class="col-md-2 col-md-offset-3 control-label">名字<b>*</b></label>
				<div class="col-md-3">
					<input id="user_name" name="user_name" type="text" placeholder="名字"
						class="form-control">
				</div>
				<div class="col-md-4">
					<label id="errorname" class="control-label errstyle"></label>
				</div>
			</div>
			<!--             <div class="form-group"> -->
			<!--                 <label class="col-md-2 col-md-offset-3 control-label">性别</label> -->
			<!--                 <div class="col-md-3 radio"> -->
			<!--                     <label><input name="gender" type="radio" value="1" checked>男</label> -->
			<!--                     <label><input name="gender" type="radio" value="0">女</label> -->
			<!--                 </div> -->
			<!--             </div> -->
			<div class="form-group">
				<label for="email" class="col-md-2 col-md-offset-3 control-label">电子邮箱<b>*</b></label>
				<div class="col-md-3">
					<input type="text" id="email" name="email" placeholder="电子邮箱"
						class="form-control">
				</div>
				<div class="col-md-4">
					<label id="erroremail" class="control-label errstyle"></label>
				</div>
			</div>
			<div class="col-md-2 col-md-offset-5 text-center">
			<p id="errMsg" class="po err" style="color:red">${errorMsg}</p>
				<button class="btn btn-primary" id="submit">提交</button>
<!-- 				<span> </span> -->
<!-- 				<button class="btn btn-primary" type="reset">清空</button> -->
<!-- 				<span> </span> -->
				<a class="btn btn-primary" href="/testproject/home">返回登陆</a>
			</div>
		</form>
	</div>
</body>
</html>
