<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<html>
<head>
<!-- 配置文件 -->  
<script type="text/javascript" src="<%=basePath%>ueditor1_4_3_3/ueditor.config.js"></script>  
<!-- 编辑器源码文件 -->  
<script type="text/javascript" src="<%=basePath%>ueditor1_4_3_3/ueditor.all.js"></script>  
  
<script type="text/javascript" charset="utf-8" src="<%=basePath%>ueditor1_4_3_3/lang/zh-cn/zh-cn.js"></script>  
  
<script type="text/javascript">  
    //实例化编辑器   
    var ue = UE.getEditor('editor');  
</script> 
</head>
<body>
<div>  
    <script id="editor" type="text/plain" style="width:700px;height:300px;"></script>  
</div> 
</body>
</html>