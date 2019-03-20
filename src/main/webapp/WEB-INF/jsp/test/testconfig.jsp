<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- 使用forEach标签时需要在JSP页面中引入JSTL标签库 -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<title>执行Invoke命令</title>
<body style="background-color: #eff3f6">
    ${mail_user}</br>
    ${mail_pwd}</br>
    ${linux_IP}</br>
    ${svn_user}</br>
</body>
</html>    