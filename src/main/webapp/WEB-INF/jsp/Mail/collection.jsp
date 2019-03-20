<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- 使用forEach标签时需要在JSP页面中引入JSTL标签库 -->
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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="<%=basePath%>css/mail/table.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>bootstrap/3.3.7/css/bootstrap.min.css">
<script type="text/javascript" src="<%=basePath%>jQuery/2.1.4/jquery.min.js"></script>
</head>
<title>汇总测试部署信息</title>
<body>
	<div class="whole">
		<div class="littletitle">汇总部署信息</div>
		<span style='color: #ff0000;'> 上线日期：${online_date} </span>
<!-- 		<a  href="list" class="btn btn-success">返回</a> -->
		<a href="javascript:window.opener=null;window.close();" class="btn btn-danger">关闭</a>
	</div>
	<p style='font-weight: bold; padding-left: 20px;'>
		今日部署全量汇总信息 <span style='color: #ff0000;'>(草稿)</span>
	</p>
	<div class="main">
		<table id="cs_table_draft" class="table table-bordered" style='width: 98%;background-color: #eee'
			border="1">
			<tr>
				<td style='width: 25%'>
					<table>
						<tr align="left" style='color: blue;'>
							<td>部署类型：nfs</td>
						</tr>
						<c:if test="${fn:length(draftNFSlist) <= 0}">
							<tr>
								<td>无</td>
							</tr>
						</c:if>
						<c:forEach items="${draftNFSlist}" var="draf_nfs">
							<tr>
								<td align="left">${draf_nfs.deliver_list}</td>
							</tr>
						</c:forEach>
					</table>
				</td>
				<td style='width: 25%'>
					<table>
						<tr class="head" align="left" style='color: blue;'>
							<td>部署类型：docker.app</td>
						</tr>
						<c:if test="${fn:length(draftAPPlist) <= 0}">
							<tr>
								<td>无</td>
							</tr>
						</c:if>
						<c:forEach items="${draftAPPlist}" var="draf_app">
							<tr>
								<td align="left">${draf_app.deliver_list}</td>

							</tr>
						</c:forEach>
					</table>
				</td>
				<td style='width: 25%'>
					<table>
						<tr class="head" align="left" style='color: blue;'>
							<td>部署类型：docker.web</td>
						</tr>
						<c:if test="${fn:length(draftWEBlist) <= 0}">
							<tr>
								<td>无</td>
							</tr>
						</c:if>
						<c:forEach items="${draftWEBlist}" var="draf_web">
							<tr>
								<td align="left">${draf_web.deliver_list}</td>
							</tr>
						</c:forEach>
					</table>
				</td>
				<td style='width: 25%'>
					<table>
						<tr class="head" align="left" style='color: blue;'>
							<td>部署类型：other</td>
						</tr>
						<tr>
							<td align="left">无</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
	<p style='font-weight: bold; padding-left: 20px;'>
		今日部署全量汇总信息 <span style='color: #ff0000;'>(已确认)</span>
	</p>
	<div class="main">
		<table id="cs_table" class="table table-bordered" style='width: 98%;background-color: #eee'
			border="1">
			<tr>
				<td style='width: 25%'>
					<table>
						<tr align="left" style='color: blue;'>
							<td>部署类型：nfs</td>
						</tr>
						<c:if test="${fn:length(collectionNFSlist) <= 0}">
							<tr>
								<td>无</td>
							</tr>
						</c:if>
						<c:forEach items="${collectionNFSlist}" var="nfs">
							<tr>
								<td align="left">${nfs.deliver_list}</td>
							</tr>
						</c:forEach>
					</table>
				</td>
				<td style='width: 25%'>
					<table>
						<tr class="head" align="left" style='color: blue;'>
							<td>部署类型：docker.app</td>
						</tr>
						<c:if test="${fn:length(collectionAPPlist) <= 0}">
							<tr>
								<td>无</td>
							</tr>
						</c:if>
						<c:forEach items="${collectionAPPlist}" var="app">
							<tr>
								<td align="left">${app.deliver_list}</td>

							</tr>
						</c:forEach>
					</table>
				</td>
				<td style='width: 25%'>
					<table>
						<tr class="head" align="left" style='color: blue;'>
							<td>部署类型：docker.web</td>
						</tr>
						<c:if test="${fn:length(collectionWEBlist) <= 0}">
							<tr>
								<td>无</td>
							</tr>
						</c:if>
						<c:forEach items="${collectionWEBlist}" var="web">
							<tr>
								<td align="left">${web.deliver_list}</td>
							</tr>
						</c:forEach>
					</table>
				</td>
				<td style='width: 25%'>
					<table>
						<tr class="head" align="left" style='color: blue;'>
							<td>部署类型：other</td>
						</tr>
						<tr>
							<td align="left">无</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>
