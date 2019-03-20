<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
	String user_name= (String)request.getSession().getAttribute("user_name");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>小牛在线工具</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/mainframe.css" />
<script type="text/javascript"  src="<%=basePath%>jQuery/2.1.1/jquery.js"></script>
<script  type="text/javascript" src="<%=basePath%>jQuery/2.1.1/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/fastLiveFilter.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>bootstrap/3.3.7/css/bootstrap.min.css"></link>
<script type="text/javascript" src="<%=basePath%>bootstrap/3.3.7/js/bootstrap.min.js"></script>
<%-- <link type="text" href="<%=basePath%>bootstrap/3.2.0/fonts/glyphicons-halflings-regular.woff"></link> --%>
<script>
$(".nav li").click(function() {
    $(".active").removeClass('active');
    $(this).addClass("active");
});
</script>
<script>
$(function() {
        $('#search_input').fastLiveFilter('.nav.nav-list.collapse.secondmenu');
});
</script>
</head>
<body>
<!--顶部导航栏部分-->
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" title="小牛在线工具" href="http://www.xiaoniu88.com"><font color="white">小牛在线工具</font></a>
       </div>
       <div class="collapse navbar-collapse">
           <ul class="nav navbar-nav navbar-right">
               <li role="presentation">
                   <a class="glyphicon glyphicon-user"><font color="white">用户：<%=user_name%></font></a>
               </li>
               <li>
                   <a href="outLogin"  class="glyphicon glyphicon-lock">
                         <font color="white">退出登录</font></a>
                </li>
            </ul>
       </div>
    </div>      
</nav>
<!-- 中间主体内容部分 -->
<div class="pageContainer">
     <!-- 左侧导航栏 -->
     <div class="pageSidebar">
     <!-- 菜单过滤，待完善 -->
         <input type="text"  class="form-control" id="search_input" name="search_input" placeholder="Search ..." />
         <ul id="main-nav" class="nav nav-tabs nav-stacked" style="Display:inline">
					<li ><a href="#FirstMenu" class="nav-header collapsed"
						data-toggle="collapse"><span class="glyphicon glyphicon-align-justify" aria-hidden="true"></span>常用工具 <span
							class="pull-right glyphicon glyphicon-chevron-down"></span>
					</a>
						<ul id="FirstMenu" class="nav nav-list collapse secondmenu" >
							<li><a id="links" target="iframename" href="clean"  >★Linux清空日志文件</a></li>
							<li><a id="links" target="iframename" href="publish/list"  >★部署交付信息</a></li>
							<li><a id="links" target="iframename" href="invoke"  >★请求Dubbo接口</a></li>
							<li><a id="links" target="iframename" href="loan/audit"  >★资产联调工具</a></li>
							<!--用户数据加解密:http://172.20.20.160:8030/testproject/security -->
							<li><a id="links" target="iframename"  href="<%=basePath%>html/Data_generation.html">★数据生成器</a></li>
						</ul></li>

					<li><a href="#LastMenu" class="nav-header collapsed"
						data-toggle="collapse"><span class="glyphicon glyphicon-link" aria-hidden="true"></span>系统链接 <span class="pull-right glyphicon glyphicon-chevron-down"></span>
					</a>
						<ul id="LastMenu" class="nav nav-list collapse secondmenu" style="height: 0px;">
							<li><a id="links" href="http://172.20.20.113:8280/xn-springmvc-prj/prjcheck.html" target="_blank">★测试开发组项目在线审阅</a></li>
							<li><a id="links" href="http://172.20.20.113:8011/APIPlatform/home" target="_blank">★在线接口测试平台</a></li>
						</ul></li>
				</ul>
     </div>
     <!-- 左侧导航和正文内容的分隔线 -->
     <div class="splitter"></div>
     <!-- 正文内容部分 -->
     <div class="pageContent">
         <iframe src="<%=basePath%>html/welcome.html" id="iframename" name="iframename" frameborder="0" width="100%"  height="100%" frameBorder="0" ></iframe>
     </div>
 </div>
 <!-- 底部页脚部分 -->
 <div class="footer">
     <p class="text-center">
         2018 &copy; Luojuwang
     </p>
 </div>
</body>
</html>