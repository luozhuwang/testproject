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
<link rel="stylesheet" href="<%=basePath%>css/mail/table.css" />
<script>
function encryInfo() {
	var Data=$("#Data_encryInfo").val();
	if(Data==null||Data==undefined||Data==""){
		 alert("请输入手机号、邮箱、姓名");
		 return false;
	}else{
		$.ajax( {  
	        type : "post",    
	        url : "encryptionAction",  
	        data:{Data:Data,type:0},  
	        dataType: "text",  
	        success : function(result) { 
	        	$("#encryResultInfo").html(result);	        	
	        }  
	    });  
	}
}
function decryInfo() {
	var Data=$("#Data_decryInfo").val();
	if(Data==null||Data==undefined||Data==""){
		 alert("请输入手机号、邮箱、姓名密文");
		 return false;
	}else{
		$.ajax( {  
	        type : "post",    
	        url : "decryptAction",  
	        data:{Data:Data,type:0},  
	        dataType: "text",  
	        success : function(result) { 
	        	$("#decryResultInfo").html(result);	        	
	        }  
	    });  
	}
}
function encryIdCard() {
	var Data=$("#Data_encryIdCard").val();
	if(Data==null||Data==undefined||Data==""){
		 alert("请输入身份证");
		 return false;
	}else{
		$.ajax( {  
	        type : "post",    
	        url : "encryptionAction",  
	        data:{Data:Data,type:1},  
	        dataType: "text",  
	        success : function(result) { 
	        	$("#encryResultIdCard").html(result);	        	
	        }  
	    });  
	}
}
function decryIdCard() {
	var Data=$("#Data_decryIdCard").val();
	if(Data==null||Data==undefined||Data==""){
		 alert("请输入身份证密文");
		 return false;
	}else{
		$.ajax( {  
	        type : "post",    
	        url : "decryptAction",  
	        data:{Data:Data,type:1},  
	        dataType: "text",  
	        success : function(result) { 
	        	$("#decryResultIdCard").html(result);	        	
	        }  
	    });  
	}
}

function encryptCardNo() {
	var Data=$("#Data_encryptCardNo").val();
	if(Data==null||Data==undefined||Data==""){
		 alert("请输入银行卡");
		 return false;
	}else{
		$.ajax( {  
	        type : "post",    
	        url : "encryptionAction",  
	        data:{Data:Data,type:2},  
	        dataType: "text",  
	        success : function(result) { 
	        	$("#encryptResultCardNo").html(result);	        	
	        }  
	    });  
	}
}
function decryptCardNo() {
	var Data=$("#Data_decryptCardNo").val();
	if(Data==null||Data==undefined||Data==""){
		 alert("请输入银行卡密文");
		 return false;
	}else{
		$.ajax( {  
	        type : "post",    
	        url : "decryptAction",  
	        data:{Data:Data,type:2},  
	        dataType: "text",  
	        success : function(result) { 
	        	$("#decryptResultCardNo").html(result);	        	
	        }  
	    });  
	}
}
</script>
</head>
<title>用户数据加/解密</title>
<body style="background-color: #FFFFFF">
 <div class="row" style="width: 95%;">
<div class="col-md-8">
   	<table>
   		<tbody>
   		<!-- 手机号、邮箱、姓名 -->
   			<tr>
   			<td>
   				<input type="text" style="height:30px" placeholder="手机号、邮箱、姓名" id="Data_encryInfo" name="Data" class="text">
				<input id="encryInfo" type="submit" style="height:30px" class="btn btn-primary" value="加密(手机号、邮箱、姓名)" onclick="encryInfo();" >
   			</td>
			<td id="encryResultInfo" style="color:red"></td>
   			</tr>  			
			<tr>
   			<td>
   				<input type="text" style="height:30px" placeholder="手机号、邮箱、姓名密文" id="Data_decryInfo" name="Data" class="text">  			
   				<input id="decryInfo" type="submit" style="height:30px" class="btn btn-success" value="解密(手机号、邮箱、姓名)" onclick="decryInfo();" >
   			</td>
			<td id="decryResultInfo" style="color:red"></td>
   			</tr>
   			<!-- 身份证 -->
   			<tr>
   			<td>
   				<input type="text" style="height:30px" placeholder="身份证" id="Data_encryIdCard" name="Data" class="text">
				<input id="encryIdCard" type="submit" style="height:30px" class="btn btn-primary" value="加密身份证" onclick="encryIdCard();" >
   			</td>
			<td id="encryResultIdCard" style="color:red"></td>
   			</tr>
   			<tr>
   			<td> 
   				<input type="text" style="height:30px" placeholder="身份证密文" id="Data_decryIdCard" name="Data" class="text">
   			
   				<input id="decryIdCard" type="submit" style="height:30px" class="btn btn-success" value="解密身份证" onclick="decryIdCard();" >
   			</td>
			<td id="decryResultIdCard" style="color:red"></td>
   			</tr>
   			<!-- 银行卡 -->
   			<tr>
   			<td>
   				<input type="text" style="height:30px" placeholder="银行卡" id="Data_encryptCardNo" name="Data" class="text">
				<input id="encryptCardNo" type="submit" style="height:30px" class="btn btn-primary" value="加密银行卡" onclick="encryptCardNo();" >
   			</td>
			<td id="encryptResultCardNo" style="color:red"></td>
   			</tr>
   			<tr>
   			<td> 
   				<input type="text" style="height:30px" placeholder="银行卡密文" id="Data_decryptCardNo" name="Data" class="text">   			
   				<input id="decryptCardNo" type="submit" style="height:30px" class="btn btn-success" value="解密银行卡" onclick="decryptCardNo();" >
   			</td>
			<td id="decryptResultCardNo" style="color:red"></td>
   			</tr>
   		</tbody>
   	</table>
</div>
 </div>
</body>
</html>    