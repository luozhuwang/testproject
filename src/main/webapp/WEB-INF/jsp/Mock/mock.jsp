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
<script type="text/javascript" src="<%=basePath%>bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link href="<%=basePath%>bootstrap/3.3.7/css/bootstrap-switch.css" rel="stylesheet">
<script src="<%=basePath%>js/highlight.js"></script>
<script src="<%=basePath%>bootstrap/3.3.7/js/bootstrap-switch.js"></script>
<script src="<%=basePath%>js/main.js"></script>
<script>
function active(status,id){
	$.ajax( {  
        type : "post",  
        url : "switch",  
        data:{status:status,id:id},  
        dataType: "text",  
        success : function(result) {  	    	        
        	$("#responseContent").html(result);
        	//模态弹出框
        	$('#myModal').modal();
        	$("#btn-audit").removeAttr("disabled");	
        }  
    });  
	
}
</script>
<title>MockServer接口列表</title>
</head>
<body>
	<div class="all">
		<form action="list" method="post">
			<div class="whole">
				<div class="littletitle">查询接口</div>
				接口名称：<input type="text" placeholder="接口名称" value="${interfaceName}" name="name" id="name" style="width: 190px; height: 30px"> 				
				<button type="submit" class="btn btn-primary">查询</button>				
			</div>
			<div class="main">
				<table id="cs_table" class="data-table" border="1">
					<thead>
						<tr class="head">
							<td style="display:none;">id</td>
							<td>接口名称</td>
							<td>请求方式</td>
							<td>请求地址</td>					
							<td>更新时间</td>
							<td>描述</td>
							<td>是否启用</td>
							<td>操作</td>
						</tr>
					</thead>
					<tbody id="group_one">
					<c:if   test="${fn:length(mockInfoList.list) <= 0}">
							<tr>								
									<td colspan="8">无</td>									
							</tr>
					</c:if>	
						<c:forEach items="${mockInfoList.list}" var="e2">
							<tr>
								<td style="display:none;">${e2.id}</td>
								<td>${e2.name}</td>
								<td>${e2.method}</td>
								<td>${e2.url}</td>								
								<td>${e2.updateTime}</td>
								<td>${e2.description}</td>
								<td id="switch" onclick="active(1,1);">
								<c:choose><c:when test="${e2.status==0}"><input id="${e2.id}" name="status" type="checkbox"  style="height:20px;width:50px" value="${e2.status}" checked/></c:when><c:when test="${e2.status==1}"><input id="${e2.id}" name="status" type="checkbox"  style="height:20px;width:50px" value="${e2.status}"/></c:when></c:choose>
						
 								<script type="text/javascript"> 
 								var idvalue=${e2.id};																
									$("#"+idvalue).bootstrapSwitch(
											{
												onText : "启用",
												offText : "关闭",
												onColor : "success",
												offColor : "danger",
												size : "normal",
												onSwitchChange : function(event, state) {
													if (state == true) {
														console.log("启动");
														$("#"+idvalue).val("0");
													} else {
														console.log("停止");
														$("#"+idvalue).val("1");
													}
												}
											})
 								</script>
								</td>								
								<td>
									<a class="btn btn-warning" >编辑</a>
									<a class="btn btn-warning" >删除</a>
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
				当前${mockInfoList.pageNum}页,共${mockInfoList.pages }页,总${mockInfoList.total }条记录
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
					<c:if test="${mockInfoList.hasPreviousPage}">
						<li><a
							href="${pageContext.request.contextPath}/publish/list?deliver_list=${deliver_list}&online_date=${online_date}&deliver_match=${deliver_match}&status=${status}&pagenum=${mockInfoList.pageNum-1}">
								<span>上一页</span>
						</a></li>
					</c:if>
					<li>
						<!--遍历所有导航页码，如果遍历的页码页当前页码相等就高亮显示，如果相等就普通显示  --> <c:forEach
							items="${publishInfolist.navigatepageNums }" var="page_Nums">
							<c:if test="${page_Nums==mockInfoList.pageNum }">
								<li class="active"><a href="#">${page_Nums}</a></li>
							</c:if>
							<c:if test="${page_Nums!=mockInfoList.pageNum }">
								<li><a
									href="${pageContext.request.contextPath}/publish/list?deliver_list=${deliver_list}&online_date=${online_date}&deliver_match=${deliver_match}&status=${status}&pagenum=${page_Nums}">${page_Nums}</a></li>
							</c:if>
						</c:forEach>
					</li>
					<!-- 如果还有后页就访问当前页码+1的页面， -->
					<c:if test="${publishInfolist.hasNextPage}">
						<li><a
							href="${pageContext.request.contextPath}/publish/list?deliver_list=${deliver_list}&online_date=${online_date}&deliver_match=${deliver_match}&status=${status}&pagenum=${mockInfoList.pageNum+1}">
								<span>下一页</span>
						</a></li>
					</c:if>
					<li><a
						href="${pageContext.request.contextPath}/publish/list?deliver_list=${deliver_list}&online_date=${online_date}&deliver_match=${deliver_match}&status=${status}&pagenum=${mockInfoList.pages}">末页</a></li>
				</ul>
			</div>
		</div>
	</div>
	
			<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
					&times;
				</button>
				<h4 class="modal-title" id="myModalLabel">
					请求结果
				</h4>
			</div>
			<div class="modal-body" id="responseContent"></div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>				
			</div>
		</div>
	</div>
</div>
</body>
</html>