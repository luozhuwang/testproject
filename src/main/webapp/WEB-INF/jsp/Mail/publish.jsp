<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<script type="text/javascript" src="<%=basePath%>jQuery/2.1.4/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>bootstrap/3.3.7/css/bootstrap-datepicker3.min.css">
<script type="text/javascript" src="<%=basePath%>bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath%>bootstrap/3.3.7/js/bootstrap-datepicker.min.js"></script>
<script type="text/javascript" src="<%=basePath%>bootstrap/3.3.7/js/bootstrap-datepicker.zh-CN.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/common.js"></script>
<script>
$(function () {
    $("#online_date").datepicker({
    	format: 'yyyy-mm-dd', //日期格式
    	language:'zh-CN',//中文
    	autoclose:true,//选择后自动关闭
    	todayHighlight:true//当天日期高亮
    });
});
</script>
<script>
function clean() {
	$("#deliver_list").val("");
	$("#online_date").val("");
	$("#deliver_match").val("");
	$("#status").val(""); 
}
function checkDate(){
	var online_date=$("#online_date").val();
	if(online_date.length==0){
		alert("请先选择上线日期才能生成范围！");	
		return false;
	}else{
		get_url("collection?online_date="+online_date);
	}
}
function onlinecheckDate(){
	var online_date=$("#online_date").val();
	if(online_date.length==0){
		alert("请先选择上线日期才能生成范围！");	
		return false;
	}else{		
		var parames = new Array();
        parames.push({ name: "online_date", value: online_date});
		post_submit("sendmail", parames);
	}
}
</script>
<title>部署交付信息</title>
</head>
<body>
	<div class="all">
		<form action="list" method="post">
			<div class="whole">
				<div class="littletitle">部署交付信息</div>
				交付名称：<input type="text" placeholder="交付名称" value="${deliver_list}" name="deliver_list" id="deliver_list" style="width: 190px; height: 30px"> 
				上线日期：<input type="text" style="width: 190px; height: 30px" value="${online_date}" name="online_date" id="online_date">
				应用类型：<input type="text" style="width: 190px; height: 30px" value="${deliver_match}" placeholder="应用类型" id="deliver_match" name="deliver_match"> 
				状态： <select id="status"
					name="status" style="height: 30px">
					<option value="">全部</option>
					<option value="draft"
						<c:if test="${status eq 'draft'}">selected="selected"</c:if>>草稿</option>
					<option value="publish"
						<c:if test="${status eq 'publish'}">selected="selected"</c:if>>已发送</option>
					<option value="confirm"
						<c:if test="${status eq 'confirm'}">selected="selected"</c:if>>已确认</option>
				</select>
				<button type="submit" class="btn btn-primary">查询</button>
				<a class="btn btn-default" onclick="clean();">重置</a>
<%-- 				<a href="collection?online_date=${online_date}" class="btn btn-success" onclick="if(!checkDate()){return false;}">汇总部署</a> --%>
				<a class="btn btn-success" onclick="checkDate();" >汇总部署</a>
				<a class="btn btn-success" onclick="onlinecheckDate();" id="online">汇总上线</a>
			</div>
			<div class="main">
				<table id="cs_table" class="data-table" border="1">
					<thead>
						<tr class="head">
							<td>交付名称</td>
							<td>上线日期</td>
							<td>应用类型</td>
							<td>服务器类型</td>
							<td>交付人</td>
							<td>状态</td>
							<td>操作时间</td>
							<td>操作</td>
						</tr>
					</thead>
					<tbody id="group_one">
					<c:if   test="${fn:length(publishInfolist.list) <= 0}">
							<tr>								
									<td colspan="8">无</td>									
							</tr>
					</c:if>	
						<c:forEach items="${publishInfolist.list}" var="e2">
							<tr>
								<td align="left">${e2.deliver_list}</td>
								<td>${e2.online_date}</td>
								<td>${e2.deliver_match}</td>
								<td>${e2.deliver_type}</td>
								<td>${e2.user_name}</td>
								<td>${e2.status}</td>
								<td>${e2.createtime}</td>
								<td><a class="btn btn-warning"
									href="operate?deliver_list=${e2.deliver_list}">操作记录</a> <a class="btn btn-info"
									href="deploy?deliver_list=${e2.deliver_list}&online_date=${e2.online_date}">部署日志</a>
									<%--a class="btn btn-danger" href="View/${e2.deliver_list}">修改</a> --%>
								</td>
							</tr>
						</c:forEach>
					</tbody>

				</table>
				<hr>
			</div>
		</form>
		<div align='right'>
			<!-- 分页文字信息 ：拿到控制器处理请求时封装在pageInfo里面的分页信息-->
			<div class="col-md-6">
				当前${publishInfolist.pageNum}页,共${publishInfolist.pages }页,总${publishInfolist.total }条记录
			</div>
			<!-- 分页码 -->
			<div class="col-md-6">

				<ul class="pagination">
					<!-- 
                        1.pageContext.request.contextPath表示当前项目路径，采用的是绝对路径表达方式。一般为http:localhost:8080/项目名 。
                        2.首页，末页的逻辑：pn=1访问第一次，pn=${pageInfo.pages}访问最后一页
                      -->
					<li><a
						href="${pageContext.request.contextPath}/publish/list?deliver_list=${deliver_list}&online_date=${online_date}&deliver_match=${deliver_match}&status=${status}&pagenum=1">首页</a>
					</li>
					<!-- 如果还有前页就访问当前页码-1的页面， -->
					<c:if test="${publishInfolist.hasPreviousPage}">
						<li><a
							href="${pageContext.request.contextPath}/publish/list?deliver_list=${deliver_list}&online_date=${online_date}&deliver_match=${deliver_match}&status=${status}&pagenum=${publishInfolist.pageNum-1}">
								<span>上一页</span>
						</a></li>
					</c:if>
					<li>
						<!--遍历所有导航页码，如果遍历的页码页当前页码相等就高亮显示，如果相等就普通显示  --> <c:forEach
							items="${publishInfolist.navigatepageNums }" var="page_Nums">
							<c:if test="${page_Nums==publishInfolist.pageNum }">
								<li class="active"><a href="#">${page_Nums}</a></li>
							</c:if>
							<c:if test="${page_Nums!=publishInfolist.pageNum }">
								<li><a
									href="${pageContext.request.contextPath}/publish/list?deliver_list=${deliver_list}&online_date=${online_date}&deliver_match=${deliver_match}&status=${status}&pagenum=${page_Nums}">${page_Nums}</a></li>
							</c:if>
						</c:forEach>
					</li>
					<!-- 如果还有后页就访问当前页码+1的页面， -->
					<c:if test="${publishInfolist.hasNextPage}">
						<li><a
							href="${pageContext.request.contextPath}/publish/list?deliver_list=${deliver_list}&online_date=${online_date}&deliver_match=${deliver_match}&status=${status}&pagenum=${publishInfolist.pageNum+1}">
								<span>下一页</span>
						</a></li>
					</c:if>
					<li><a
						href="${pageContext.request.contextPath}/publish/list?deliver_list=${deliver_list}&online_date=${online_date}&deliver_match=${deliver_match}&status=${status}&pagenum=${publishInfolist.pages}">末页</a></li>
				</ul>
			</div>
		</div>
	</div>
</body>
</html>