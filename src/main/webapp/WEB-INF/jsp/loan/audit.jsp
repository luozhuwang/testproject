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
<script type="text/javascript" src="<%=basePath%>jQuery/2.1.4/jquery.js"></script>
<script type="text/javascript" src="<%=basePath%>jQuery/2.1.4/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=basePath%>bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>bootstrap/3.3.7/css/bootstrap-datepicker3.min.css">
<script type="text/javascript" src="<%=basePath%>bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/common.js"></script>
<script>
function auditAction() {
	$("#responseContent").html("");
	var RequestIp=$("#RequestIp").val();
	var merchantCode=$("#merchantCode").val();
	var certNo=$("#certNo").val();
	var transId=$("#transId").val();
	var status=document.getElementsByName("status");

	var statusid;
	 for(var i = 0; i < status.length; i++)
	{
	     if(status[i].checked)
	    	 statusid=status[i].value;
	 }
	 if(merchantCode==null||merchantCode==undefined||merchantCode==""){
		 alert("请输入商户编号");
		 return false;
	}if(certNo==null||certNo==undefined||certNo==""){
		 alert("请输入合同编号");
		 return false;
	}if(transId==null||transId==undefined||transId==""){
		 alert("请输入交易流水");
		 return false;
	}if(statusid==null||statusid==undefined||statusid==""){
		 alert("请选择审核状态");
		 return false;
	}
	var re=/^(\d+)\.(\d+)\.(\d+)\.(\d+)$/;//正则表达式
	 if(re.test(RequestIp)){  
	       if( RegExp.$1<256 && RegExp.$2<256 && RegExp.$3<256 && RegExp.$4<256)  
		 $("#btn-audit").attr("disabled","true");
	    	$.ajax( {  
	    	        type : "post",  
	    	        url : "auditAction",  
	    	        data:{RequestIp:RequestIp,transId:transId,merchantCode:merchantCode,certNo:certNo,status:statusid},  
	    	        dataType: "text",  
	    	        success : function(result) {  	    	        
	    	        	$("#responseContent").html(result);
	    	        	//模态弹出框
	    	        	$('#myModal').modal();
	    	        	$("#btn-audit").removeAttr("disabled");	
	    	        }  
	    	    });  
	   }else{		   
			   alert("请选择环境");   
	}   
	
}
function loanPayAction() {
	$("#responseContent").html("");
	var RequestIp=$("#RequestIp").val();
	var PayMerchantCode=$("#PayMerchantCode").val();
	var PaycertNo=$("#PaycertNo").val();
	var payTransId=$("#payTransId").val();
	var status=document.getElementsByName("loanStatus");

	
	var loanStatus;
	 for(var i = 0; i < status.length; i++)
	{
	     if(status[i].checked)
	    	 loanStatus=status[i].value;
	 }
	if(PayMerchantCode==null||PayMerchantCode==undefined||PayMerchantCode==""){
		 alert("请输入商户编号");
		 return false;
	}if(PaycertNo==null||PaycertNo==undefined||PaycertNo==""){
		 alert("请输入合同编号");
		 return false;
	}if(payTransId==null||payTransId==undefined||payTransId==""){
		 alert("请输入交易流水");
		 return false;
	}if(loanStatus==null||loanStatus==undefined||loanStatus==""){
		 alert("请选择放款状态");
		 return false;
	}
	 
	var re=/^(\d+)\.(\d+)\.(\d+)\.(\d+)$/;//正则表达式
	 if(re.test(RequestIp)){   
	       if( RegExp.$1<256 && RegExp.$2<256 && RegExp.$3<256 && RegExp.$4<256)  
	    	   $("#btn-loanPay").attr("disabled","true");
	    	$.ajax( {  
	    	        type : "post",  
	    	        url : "loanPayAction",  
	    	        data:{RequestIp:RequestIp,payTransId:payTransId,PayMerchantCode:PayMerchantCode,loanStatus:loanStatus},  
	    	        dataType: "text",  
	    	        success : function(result) {  
	    	        	$("#responseContent").html(result);
	    	        	//模态弹出框
	    	        	$('#myModal').modal();
	    	        	$("#btn-loanPay").removeAttr("disabled");	
	    	        }  
	    	    });  
	   }else{		   
			   alert("请选择环境"); 
	}   
	
}
function investAction() {
	$("#responseContent").html("");
	var RequestIp=$("#RequestIp").val();
	var investMerchantCode=$("#investMerchantCode").val();
	var investCertNo=$("#investCertNo").val();
	var investTransId=$("#investTransId").val();
	var userName=$("#userName").val();
	
	if(investMerchantCode==null||investMerchantCode==undefined||investMerchantCode==""){
		 alert("请输入商户编号");
		 return false;
	}if(investCertNo==null||investCertNo==undefined||investCertNo==""){
		 alert("请输入合同编号");
		 return false;
	}if(investTransId==null||investTransId==undefined||investTransId==""){
		 alert("请输入交易流水");
		 return false;
	}if(userName==null||userName==undefined||userName==""){
		 alert("请输入用户名");
		 return false;
	}
	
	var re=/^(\d+)\.(\d+)\.(\d+)\.(\d+)$/;//正则表达式
	 if(re.test(RequestIp)){   
	       if( RegExp.$1<256 && RegExp.$2<256 && RegExp.$3<256 && RegExp.$4<256)  
	    	   $("#btn-invest").attr("disabled","true");
	    	$.ajax( {  
	    	        type : "post",  
	    	        url : "investAction",  
	    	        data:{RequestIp:RequestIp,investMerchantCode:investMerchantCode,investTransId:investTransId,userName:userName},  
	    	        dataType: "text",  
	    	        success : function(result) {  
	    	        	$("#responseContent").html(result);
	    	        	//模态弹出框
	    	        	$('#myModal').modal();
	    	        	$("#btn-invest").removeAttr("disabled");	
	    	        }  
	    	    });  
	   }else{		   
			alert("请选择环境");  
	}   
	
}
function investLoanPay() {
	$("#responseContent").html("");
	var RequestIp=$("#RequestIp").val();
	var investMerchantCode=$("#loanPayMerchantCode").val();
	var investCertNo=$("#loanPayCertNo").val();
	var investTransId=$("#loanPayTransId").val();
	var userName=$("#loanPayUserName").val();
	
	if(investMerchantCode==null||investMerchantCode==undefined||investMerchantCode==""){
		 alert("请输入商户编号");
		 return false;
	}if(investCertNo==null||investCertNo==undefined||investCertNo==""){
		 alert("请输入合同编号");
		 return false;
	}if(investTransId==null||investTransId==undefined||investTransId==""){
		 alert("请输入交易流水");
		 return false;
	}if(userName==null||userName==undefined||userName==""){
		 alert("请输入用户名");
		 return false;
	}
	
	var re=/^(\d+)\.(\d+)\.(\d+)\.(\d+)$/;//正则表达式
	 if(re.test(RequestIp)){   
	       if( RegExp.$1<256 && RegExp.$2<256 && RegExp.$3<256 && RegExp.$4<256)  
	    	   $("#btn-LoanPay").attr("disabled","true");
	    	$.ajax( {  
	    	        type : "post",  
	    	        url : "investLoanPay",  
	    	        data:{RequestIp:RequestIp,investMerchantCode:investMerchantCode,investTransId:investTransId,userName:userName},  
	    	        dataType: "text",  
	    	        success : function(result) {  
	    	        	$("#responseContent").html(result);
	    	        	//模态弹出框
	    	        	$('#myModal').modal();
	    	        	$("#btn-LoanPay").removeAttr("disabled");	
	    	        }  
	    	    });  
	   }else{		   
			alert("请选择环境");  
	}   
	
}
function LoanVoucherAction() {
	$("#responseContent").html("");
	var RequestIp=$("#RequestIp").val();
	var voucherMerchantCode=$("#voucherMerchantCode").val();
	var voucherCertNo=$("#voucherCertNo").val();
	var voucherTransId=$("#voucherTransId").val();

	if(voucherMerchantCode==null||voucherMerchantCode==undefined||voucherMerchantCode==""){
		 alert("请输入商户编号");
		 return false;
	}if(voucherCertNo==null||voucherCertNo==undefined||voucherCertNo==""){
		 alert("请输入合同编号");
		 return false;
	}if(voucherTransId==null||voucherTransId==undefined||voucherTransId==""){
		 alert("请输入交易流水");
		 return false;
	}
	var re=/^(\d+)\.(\d+)\.(\d+)\.(\d+)$/;//正则表达式
	 if(re.test(RequestIp)){   
	       if( RegExp.$1<256 && RegExp.$2<256 && RegExp.$3<256 && RegExp.$4<256)  
	    	   $("#btn-LoanVoucher").attr("disabled","true");
	    	$.ajax( {  
	    	        type : "post",    
	    	        url : "LoanVoucherAction",  
	    	        data:{RequestIp:RequestIp,voucherMerchantCode:voucherMerchantCode,voucherCertNo:voucherCertNo,voucherTransId:voucherTransId},  
	    	        dataType: "text",  
	    	        success : function(result) { 
	    	        	$("#responseContent").html(result);
	    	        	//模态弹出框
	    	        	$('#myModal').modal();
	    	        	$("#btn-LoanVoucher").removeAttr("disabled");	
	    	        }  
	    	    });  
	   }else{		   
		   alert("请选择环境");
	   }
	}   
</script>
</head>
<title>资产联调工具</title>
<body style="background-color: #FFFFFF">
<div class="row" style="width: 95%;">
            <div class="form-group" align="center" >
            <br>
              <label for="lastname"  style="width: 100px; height: 35px;color: red;font-size: 20px" >请求环境:</label>
				<select id="RequestIp" style="width: 190px; height: 35px"  name="RequestIp">
					<option value="0">请选择环境</option>
					<option value="172.20.77.16">准生产环境</option>
					<option value="172.20.20.215">172.20.20.21环境</option>
				</select>
            </div>
	<div id="audit" class="col-md-4"  align="center" style="width: 25%;" >
			<h3 >资产--借款审核</h3>
            <div class="form-group">
              <label for="lastname" style="width: 100px; height: 30px"  class="col-sm-3 control-label">商户编号：</label>
                <input type="text" style="width: 190px; height: 30px" class="form-control" id="merchantCode" name="merchantCode" placeholder="商户编号">
            </div>
            <div class="form-group">
              <label for="lastname" style="width: 100px; height: 30px"  class="col-sm-3 control-label">合同编号：</label>
                      <input type="text" style="width: 190px; height: 30px" class="form-control" id="certNo" name="certNo" placeholder="合同编号">
            </div>
            <div class="form-group">
              <label for="lastname" style="width: 100px; height: 30px"  class="col-sm-3 control-label">交易流水：</label>
                <input type="text"  style="width: 190px; height: 30px" class="form-control" id="transId" name="transId" placeholder="交易流水">
            </div>
            <div class="form-group">
              <label for="lastname" style="width: 100px; height: 30px"  class="col-sm-3 control-label">审核状态:</label>
                <label class="checkbox-inline">
                  <input type="radio" name="status" id="status_on" value="1" >通过
                </label>
                <label class="checkbox-inline">
                  <input type="radio" name="status" id="status_off" value="6" >不通过
                </label>
            </div>
            	<button  class="btn btn-primary btn-lg"  id="btn-audit" style="width: 120px; height:40px"  onclick="auditAction();" >提交审核</button>
	</div>	
	<div id="loanPay" class="col-md-4" align="center" style="width: 25%;" >
			<h3>资产--放款</h3>
            <div class="form-group">
              <label for="lastname" style="width: 100px; height: 30px" class="col-sm-3 control-label">商户编号：</label>
                <input type="text"  style="width: 190px; height: 30px" class="form-control" id="PayMerchantCode" name="PayMerchantCode" placeholder="商户编号">
            </div>
            <div class="form-group">
              <label for="lastname" style="width: 100px; height: 30px"  class="col-sm-3 control-label">合同编号：</label>
                      <input type="text" style="width: 190px; height: 30px" class="form-control" id="PaycertNo" name="PaycertNo" placeholder="合同编号">
            </div>
            <div class="form-group">
              <label for="lastname" style="width: 100px; height: 30px" class="col-sm-3 control-label">交易流水：</label>
                <input type="text" style="width: 190px; height: 30px"  class="form-control" id="payTransId" name="payTransId" placeholder="交易流水">
            </div>
            <div class="form-group">
              <label for="lastname" style="width: 100px; height: 30px" class="col-sm-3 control-label">放款状态:</label>
                <label class="checkbox-inline">
                  <input type="radio" name="loanStatus"  value="1" >已放款
                </label>
                <label class="checkbox-inline">
                  <input type="radio" name="loanStatus"  value="2" >放款失败
                </label>
                <label class="checkbox-inline">
                  <input type="radio" name="loanStatus"  value="3" >退票
                </label>
            </div>
            <button class="btn btn-primary btn-lg"  style="width: 120px; height:40px" id="btn-loanPay" onclick="loanPayAction();" >提交放款</button>
	</div>
	<div id="invest" class="col-md-4" align="center" style="width: 25%;" >
			<h3>买标-自动放款成功</h3>
            <div class="form-group">
              <label for="lastname" style="width: 150px; height: 30px" class="col-sm-3 control-label">商户编号：</label>
                <input type="text" style="width: 190px; height: 30px" class="form-control" id="investMerchantCode" name="investMerchantCode" placeholder="商户编号">
            </div>
            <div class="form-group">
              <label for="lastname" style="width: 150px; height: 30px"  class="col-sm-3 control-label">合同编号：</label>
                      <input type="text" style="width: 190px; height: 30px" class="form-control" id="investCertNo" name="investCertNo" placeholder="合同编号">
            </div>
            <div class="form-group">
              <label for="lastname" style="width: 150px; height: 30px" class="col-sm-3 control-label">交易流水：</label>
                <input type="text" style="width: 190px; height: 30px" class="form-control" id="investTransId" name="investTransId" placeholder="交易流水">
            </div>
             <div class="form-group">
              <label for="lastname" style="width: 150px; height: 30px" class="col-sm-3 control-label">用户名(投资人)：</label>
                <input type="text" style="width: 190px; height: 30px" class="form-control" id="userName" name="userName" placeholder="用户名">
            </div>
             <button class="btn btn-primary btn-lg"   style="width: 120px; height:40px" id="btn-invest"  onclick="investAction();" >购买标的</button>
	</div>
	<div id="investLoanPay" class="col-md-4" align="center" style="width: 25%;" >
			<h3>存管买标-自动放款</h3>
            <div class="form-group">
              <label for="lastname" style="width: 150px; height: 30px" class="col-sm-3 control-label">商户编号：</label>
                <input type="text" style="width: 190px; height: 30px" class="form-control" id="loanPayMerchantCode" name="loanPayMerchantCode" placeholder="商户编号">
            </div>
            <div class="form-group">
              <label for="lastname" style="width: 150px; height: 30px"  class="col-sm-3 control-label">合同编号：</label>
                      <input type="text" style="width: 190px; height: 30px" class="form-control" id="loanPayCertNo" name="loanPayCertNo" placeholder="合同编号">
            </div>
            <div class="form-group">
              <label for="lastname" style="width: 150px; height: 30px" class="col-sm-3 control-label">交易流水：</label>
                <input type="text" style="width: 190px; height: 30px" class="form-control" id="loanPayTransId" name="loanPayTransId" placeholder="交易流水">
            </div>
             <div class="form-group">
              <label for="lastname" style="width: 150px; height: 30px" class="col-sm-3 control-label">用户名(投资人)：</label>
              <!--21测试用户： xnwp5998162 -->
                <input type="text" style="width: 190px; height: 30px" class="form-control" id="loanPayUserName" name="loanPayUserName" placeholder="用户名">
            </div>
             <button class="btn btn-danger btn-lg"   style="width: 120px; height:40px" id="btn-LoanPay"  onclick="investLoanPay();" >存管买标</button>
	</div>
	<div id="LoanVoucher" class="col-md-4" style="width: 25%;" align="center">
			<h3>上传合同-外部联调请勿使用</h3>
            <div class="form-group">
              <label for="lastname" style="width: 100px; height: 30px" class="col-sm-3 control-label">商户编号：</label>
                <input type="text" style="width: 190px; height: 30px" class="form-control" id="voucherMerchantCode" name="voucherMerchantCode" placeholder="商户编号">
            </div>
            <div class="form-group">
              <label for="lastname" style="width: 100px; height: 30px" class="col-sm-3 control-label">合同编号：</label>
                      <input type="text" style="width: 190px; height: 30px" class="form-control" id="voucherCertNo" name="voucherCertNo" placeholder="合同编号">
            </div>
            <div class="form-group">
              <label for="lastname" style="width: 100px; height: 30px" class="col-sm-3 control-label">交易流水：</label>
                <input type="text" style="width: 190px; height: 30px" class="form-control" id="voucherTransId" name="voucherTransId" placeholder="交易流水">
            </div>           
	<button class="btn btn-primary btn-lg"  style="width: 120px; height:40px" id="btn-LoanVoucher"  onclick="LoanVoucherAction();" >上传合同</button>
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
