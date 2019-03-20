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
<link rel="stylesheet" type="text/css" href="<%=basePath%>bootstrap/3.3.7/css/bootstrap.min.css">
<script type="text/javascript" src="<%=basePath%>jQuery/2.1.4/jquery.min.js"></script>
</head>
<title>响应结果</title>
<body style="background-color: #eff3f6">
<div align="center">
		<a href="javascript:window.opener=null;window.close();" class="btn btn-danger">关闭</a>
</div>
<div  align="center">
	<h1>${message}</h1>
</div>
</body>
</html>    