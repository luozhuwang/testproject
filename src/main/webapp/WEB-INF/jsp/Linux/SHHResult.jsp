<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>清空文件</title>
<script type="text/javascript"  src="<%=basePath%>jQuery/2.1.1/jquery.js"></script>
<script  type="text/javascript" src="<%=basePath%>jQuery/2.1.1/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>bootstrap/3.3.7/css/bootstrap.min.css"></link>
<script type="text/javascript" src="<%=basePath%>bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body style="background-color: #FFFFFF">
	<h3>执行成功：</h3>
	${info}
	<br>
	<a class="btn btn-success" href="clean">继续清除</a>
	
</body>
</html>