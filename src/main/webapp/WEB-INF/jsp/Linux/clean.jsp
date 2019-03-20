<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<!-- 使用forEach标签时需要在JSP页面中引入JSTL标签库 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript"  src="<%=basePath%>jQuery/2.1.1/jquery.js"></script>
<script  type="text/javascript" src="<%=basePath%>jQuery/2.1.1/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>bootstrap/3.3.7/css/bootstrap.min.css"></link>
<script type="text/javascript" src="<%=basePath%>bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script>
function disableBtn()   
{       
	var ok=document.getElementById("clean");   
	ok.disabled=true; 
	ok.click();
}
</script>
<title>清除Linux  /Log下日志文件</title>
</head>
<body style="background-color: #FFFFFF">
<h1 align="center">清除Linux  /Log下日志文件</h1>
<div>
<form id="clean" action="/testproject/CleanAction" method="post">
		<table align="center" height="100" width="400">
			<tr>
				<td>机器IP：</td>
				<td>
				<select id="LinuxIP" name="LinuxIP">
					<option value="172.20.20.215">172.20.20.215</option>
				</select>
				</td>
			</tr>
			<tr>
				<td>文件大小：</td>
				<td><input id="FileSize" type="text" name="FileSize" /></td>
			</tr>
			<tr>
				<td ><input class="btn btn-default" type="submit" value="清除" /></td>
			</tr>
		</table>
		
	</form>
</div>
<div>
	<h3 align="center" style="color:red">文件大小示例：10K/100M,如果不填写默认为500M)</h3>
</div>
</body>
</html>    