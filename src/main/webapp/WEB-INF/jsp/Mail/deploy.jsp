<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- 使用forEach标签时需要在JSP页面中引入JSTL标签库 -->
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
	<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=basePath%>css/mail/table.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>bootstrap/3.3.7/css/bootstrap.min.css">  
<script type="text/javascript" src="<%=basePath%>jQuery/2.1.4/jquery.min.js"></script>
<title>部署记录</title>
</head>
<body>
<div class="all">
			<div class="whole">
				<div class="headline">部署日志列表</div>
				<a  href="list" class="btn btn-success">返回</a>
			</div>				
			<div class="main">
				<table id="cs_table"  class="data-table"  border="1">				
					<tr class="head" style="background-color: #eee">
							<td>交付名称</td>
							<td>上线日期</td>
							<td>部署人</td>
							<td>测试环境</td>
							<td>操作时间</td>
						</tr>

					<tbody id="group_one">
					<c:if   test="${fn:length(deployInfolist) <= 0}">
							<tr>								
									<td colspan="5">无</td>									
							</tr>
					</c:if>					
						<c:forEach items="${deployInfolist}" var="e2">
							<tr>					
								<td align="left">${e2.deliver_list}</td>
								<td>${e2.online_date}</td>
								<td>${e2.user_name}</td>
								<td>${e2.test_env}</td>
								<td>${e2.createtime}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
					<hr>
			</div>
	</div>
</body>
</html>