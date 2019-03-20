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
<script type="text/javascript"  src="<%=basePath%>jQuery/2.1.1/jquery.js"></script>
<script  type="text/javascript" src="<%=basePath%>jQuery/2.1.1/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>bootstrap/3.3.7/css/bootstrap.min.css"></link>
<script type="text/javascript" src="<%=basePath%>bootstrap/3.3.7/js/bootstrap.min.js"></script>
<style>
		 .leftarea
        {
            float: left;
            width: 50%;
            height: 50%;
        }
        .rightarea
        {
            float: right;
            width: 50%;
            height: 50%;
        }
</style>
<script>
function invokeclick() {
	var requestIP=$("#requestIP").val();
	var requestPort=$("#requestPort").val();
	var requestMethod=$("#requestMethod").val();
	var requestParam=$("#requestParam").val();
	
	var re=/^(\d+)\.(\d+)\.(\d+)\.(\d+)$/;//正则表达式
	 if(re.test(requestIP)){   
	       if( RegExp.$1<256 && RegExp.$2<256 && RegExp.$3<256 && RegExp.$4<256)  
	    	$.ajax( {  
	    	        type : "post",  
//	    	         url : "http://127.0.0.1:8080/testproject/invokeAction",  
	    	        url : "invokeAction",  
	    	        data:{requestIP:requestIP,requestPort:requestPort,requestMethod:requestMethod,requestParam:requestParam},  
	    	        dataType: "text",  
	    	        success : function(result) {  
	    	        	$("#responseContent").val(result);
	    	        }  
	    	    });  
	   }else{		   
		   alert("IP有误，请重新输入！");     
	}   
	
 
}
function batchinvoke() {
	var requestIP=$("#requestIP").val();
	var requestPort=$("#requestPort").val();
	var requestMethod=$("#requestMethod").val();
	var requestParam=$("#requestParam").val();
	
	var re=/^(\d+)\.(\d+)\.(\d+)\.(\d+)$/;//正则表达式
	 if(re.test(requestIP)){   
	       if( RegExp.$1<256 && RegExp.$2<256 && RegExp.$3<256 && RegExp.$4<256)  
	    	$.ajax( {  
	    	        type : "post",  
//	    	         url : "http://127.0.0.1:8080/testproject/invokeAction",  
	    	        url : "invokebatch",  
	    	        data:{requestIP:requestIP,requestPort:requestPort,requestMethod:requestMethod,requestParam:requestParam},  
	    	        dataType: "text",  
	    	        success : function(result) {  
	    	        	$("#responseContent").val(result);
	    	        }  
	    	    });  
	   }else{		   
		   alert("IP有误，请重新输入！");     
	}   
	
 
}
function clean() {
	$("#requestIP").val("");
	$("#requestPort").val("");
	$("#requestMethod").val("");
	$("#requestParam").val(""); 
	$("#responseContent").val("");
}
</script>
</head>
<title>执行Invoke命令</title>
<body style="background-color: #FFFFFF">
    <div class="leftarea">
<!-- 		<form method="post"> -->
				<div style="width:80%">
					请求IP：<input type="text" style="height:30px" placeholder="请输入IP" id="requestIP" name="requestIP" class="text">
					请求端口：<input type="text" style="height:30px" placeholder="请输入端口" id="requestPort" name="requestPort" class="text">
					<input id="invoke" type="submit" style="height:30px" class="btn btn-success" value="提交" onclick="invokeclick();" >
					<input id="batchinvoke" type="submit" style="height:30px" class="btn btn-danger" value="批量执行" onclick="batchinvoke();" >
					
					<button id="clean" class="btn btn-default" style="height:30px;" onclick="clean();" >清空</button >
				</div>	
				<div>
				<div>
					请求方法：
				</div>
					<input  style="height:30px;width:80%" placeholder="请输入方法" id="requestMethod" name="requestMethod"  class="text">
				</div>				
				<div >
					<div>
						请求参数：
					</div>
					<textarea id="requestParam" name="requestParam"  style="height:500px;width:80%;resize:none" placeholder="请输入参数" ></textarea>
				</div>				
<!-- 		</form> -->
    </div>
    <div class="rightarea"> 
		<div>响应：</div>
		<div>
			<textarea  id="responseContent" style="height:800px;width:90%;resize:none" placeholder="响应结果" >${response}</textarea>
		</div>
    </div>    
<h4  style="color:red">	
	示例：获取所有产品<br>
	IP:&nbsp;&nbsp;172.20.20.215<br>
	Port(服务端口):&nbsp;&nbsp;6041<br>
	请求方法(包名+类名+方法名)：<br>com.neo.findProductList<br>
	请求参数：{"productType":1, 0, 10<br>
	批量执行参数：531483,0,0|531486,0,0
</h4>
</body>
</html>    