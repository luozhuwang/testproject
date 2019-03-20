package com.sy.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.sy.entity.AuditLoanResult;
import com.sy.entity.DetailsResult;
import com.sy.entity.MessageResult;
import com.sy.entity.UserPerson;
import com.sy.entity.data;
import com.sy.service.LoanService;
import com.sy.service.TelnetService;
import com.sy.tool.RecordIP;

@Controller
@RequestMapping("/loan")
public class LoanController {
	private Logger log = LoggerFactory.getLogger(LoanController.class);
	
	private String serialId=null;
	private long loanId=0; 
	private String message=null;

	@Autowired
	TelnetService telnetService;
	
	@Autowired
	LoanService loanService;
	
	@RequestMapping("/audit")
	public String audit(){
		return "/loan/audit";
	}
	
	//借款审核
	@RequestMapping(value="/auditAction",method=RequestMethod.POST, produces="text/plain;charset=utf-8")
	@ResponseBody
	public String loanAuditAction(String RequestIp,String transId,String  merchantCode,String certNo,String status,HttpServletRequest request,Model model){
		String ip=RecordIP.getIpAddress(request);
		log.info("Custom IP:"+ip);
		log.info("*********开始借款审核*********");
		telnetService.connect(RequestIp, 6038);
		String result=loanService.getLoan(transId, merchantCode);

		MessageResult<data> messageResult=JSON.parseObject(result, new TypeReference<MessageResult<data>>(){});
		String responseMessage=messageResult.getResponseMessage();
		if(responseMessage==null){
			serialId=messageResult.getData().getSerialId();
			loanId=messageResult.getData().getId();			
			loanService.loanRisk();
			loanService.auditRisk(serialId);
			String auditResponse=loanService.auditLoan(loanId, status);
			AuditLoanResult auditLoanResult=JSON.parseObject(auditResponse, AuditLoanResult.class);
			int success=auditLoanResult.getSuccess();
			if(success ==1){				
				loanService.sendLoan();
				message="审核完成";
			}else{
				message="审核失败，请确认以下条件：1.是否开通存管户\n2.是否授权\n3.请核实借款状态！（如需合同签章，请上传完合同在审核！）";
			}
		}else{						
			message=responseMessage;	
		}
		log.info("*********结束借款审核:"+message);
		telnetService.disconnect();
		return message;
	}
	
	//资产放款
	@RequestMapping(value="loanPayAction",method=RequestMethod.POST, produces="text/plain;charset=utf-8")
	@ResponseBody
	public String LoanPayAction(String RequestIp,String payTransId,String  PayMerchantCode,String loanStatus,HttpServletRequest request, Model model){
		String ip=RecordIP.getIpAddress(request);
		log.info("Custom IP:"+ip);
		log.info("*********开始资产放款*********");
		telnetService.connect(RequestIp, 6038);
		String result=loanService.getLoan(payTransId, PayMerchantCode);
		MessageResult<data> messageResult=JSON.parseObject(result, new TypeReference<MessageResult<data>>(){});
		String responseMessage=messageResult.getResponseMessage();
		if(responseMessage==null){
			serialId=messageResult.getData().getSerialId();
			String repayresult=loanService.repayPlan(serialId);
			if(repayresult.contains("400000")){
				message="贷款不存在或不处于已审核通过状态";				
			}else{				
				String loanPaymentresult=loanService.loanPayment(serialId, loanStatus);
				MessageResult<Integer> loanPayment=JSON.parseObject(loanPaymentresult, new TypeReference<MessageResult<Integer>>(){});
				String loanstatus=loanPayment.getStatus();
				if(loanstatus.contains("SUCCESS")){
					message="放款操作完成";					
				}else{
					message=loanPayment.getResponseMessage();				
				}
			}
		}else{						
			message=responseMessage;	
		}
		log.info("*********结束资产放款:"+message);
		telnetService.disconnect();
		return message;
	}
	
	//存管--买标、放款
	@RequestMapping(value="investLoanPay",method=RequestMethod.POST, produces="text/plain;charset=utf-8")
	@ResponseBody
	public String investLoanPay(String RequestIp,String investTransId,String  investMerchantCode, String userName,HttpServletRequest request,Model model){
		String ip=RecordIP.getIpAddress(request);
		log.info("Custom IP:"+ip);
		log.info("*********开始买标--资产放款*********");
		telnetService.connect(RequestIp, 6038);
		String result=loanService.getLoan(investTransId, investMerchantCode);
		telnetService.disconnect();
		MessageResult<data> messageResult=JSON.parseObject(result, new TypeReference<MessageResult<data>>(){});
		String responseMessage=messageResult.getResponseMessage();
		if(responseMessage==null){
			serialId=messageResult.getData().getSerialId();
			int buyAmout=messageResult.getData().getAmount();
			//买标需要调用产品服务
			telnetService.connect(RequestIp, 6041);
			String productId=loanService.productId(serialId);
			telnetService.disconnect();
			if(!productId.equals("null")){	
				//查询用户ID
				telnetService.connect(RequestIp, 6001);
				String userResult=loanService.getUserId(userName);
				telnetService.disconnect();
				if(userResult.equals("null")){	
					message="用户不存在,请重新输入";					
				}else{
					UserPerson userPerson=JSON.parseObject(userResult, UserPerson.class);
					int userId=userPerson.getId();
					telnetService.connect(RequestIp, 6041);
					String investResult=loanService.invest(userId, productId, buyAmout);
					DetailsResult<Object> detailsResult=JSON.parseObject(investResult, new TypeReference<DetailsResult<Object>>(){});
					String errorCode=detailsResult.getErrorCode();
					if(errorCode.equals("PRODUCT_RESULT_SUCCESS")){						
						try {
							loanService.creditFull(productId);
							telnetService.disconnect();
							telnetService.connect(RequestIp, 6038);
							loanService.PaymentJob();
							telnetService.disconnect();
							log.info("等待存管响应中......");
							Thread.sleep(12000);
							telnetService.connect(RequestIp, 6010);
							loanService.syncPaymentDetailFlow();
							telnetService.disconnect();							
							message="买标成功，存管已放款";	
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
					}else{
						message=detailsResult.getErrorDetails();
					}
				}	
			}else{
				message="未匹配此产品，请确认资产状态";
			}
		}else{						
			message=responseMessage;			
		}
		log.info("*********结束存管买标--资产放款:"+message);
		return message;
	
	}
	//买标--资产放款
	@RequestMapping(value="investAction",method=RequestMethod.POST, produces="text/plain;charset=utf-8")
	@ResponseBody
	public String investAction(String RequestIp,String investTransId,String  investMerchantCode, String userName,HttpServletRequest request,Model model){
		String ip=RecordIP.getIpAddress(request);
		log.info("Custom IP:"+ip);
		log.info("*********开始买标--资产放款*********");
		telnetService.connect(RequestIp, 6038);
		String result=loanService.getLoan(investTransId, investMerchantCode);
		telnetService.disconnect();
		MessageResult<data> messageResult=JSON.parseObject(result, new TypeReference<MessageResult<data>>(){});
		String responseMessage=messageResult.getResponseMessage();
		if(responseMessage==null){
			serialId=messageResult.getData().getSerialId();
			int buyAmout=messageResult.getData().getAmount();
			//买标需要调用产品服务
			telnetService.connect(RequestIp, 6041);
			String productId=loanService.productId(serialId);
			telnetService.disconnect();
			if(!productId.equals("null")){	
				//查询用户ID
				telnetService.connect(RequestIp, 6001);
				String userResult=loanService.getUserId(userName);
				telnetService.disconnect();
				if(userResult.equals("null")){	
					message="用户不存在,请重新输入";					
				}else{
					UserPerson userPerson=JSON.parseObject(userResult, UserPerson.class);
					int userId=userPerson.getId();
					telnetService.connect(RequestIp, 6041);
					String investResult=loanService.invest(userId, productId, buyAmout);
					telnetService.disconnect();
					DetailsResult<Object> detailsResult=JSON.parseObject(investResult, new TypeReference<DetailsResult<Object>>(){});
					String errorCode=detailsResult.getErrorCode();
					if(errorCode.equals("PRODUCT_RESULT_SUCCESS")){
						try {
							Thread.sleep(5000);
							//放款
							telnetService.connect(RequestIp, 6038);
							String loanPaymentresult=loanService.loanPayment(serialId, "1");
							MessageResult<Integer> loanPayment=JSON.parseObject(loanPaymentresult, new TypeReference<MessageResult<Integer>>(){});
							String loanstatus=loanPayment.getStatus();
							if(loanstatus.contains("SUCCESS")){
								message="买标成功-放款操作完成";
							}else{
								message="买标成功"+loanPayment.getResponseMessage();
							}
							telnetService.disconnect();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						
					}else{
						message=detailsResult.getErrorDetails();
					}
				}	
			}else{
				message="未匹配此产品，请确认资产状态";
			}
		}else{						
			message=responseMessage;			
		}
		log.info("*********结束买标--资产放款:"+message);
		return message;
	}
	
	//上传合同
	@RequestMapping(value="LoanVoucherAction",method=RequestMethod.POST, produces="text/plain;charset=utf-8")
	@ResponseBody
	public String LoanVoucherAction(String RequestIp,String voucherTransId,String  voucherMerchantCode,String voucherCertNo,HttpServletRequest request,Model model){
		String ip=RecordIP.getIpAddress(request);
		log.info("Custom IP:"+ip);
		log.info("*********开始上传合同*********");
		telnetService.connect(RequestIp, 6038);
		String result=loanService.getLoan(voucherTransId, voucherMerchantCode);
		MessageResult<data> messageResult=JSON.parseObject(result, new TypeReference<MessageResult<data>>(){});
		String responseMessage=messageResult.getResponseMessage();
		if(responseMessage==null){
			loanService.saveLoanVoucher(voucherMerchantCode, voucherTransId, voucherCertNo);
			message="合同上传完成";
		}else{
			message=responseMessage;
		}
		log.info("*********结束上传合同:"+message);
		return message;
	}
}
