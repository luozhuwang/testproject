package com.sy.service.Impl;


import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sy.service.LoanService;
import com.sy.service.TelnetService;
import com.sy.tool.Constant;

@Service
public class LoanServiceImp implements LoanService{
	private Logger log = LoggerFactory.getLogger(LoanServiceImp.class);
	
	
	@Autowired
	TelnetService telnetService;

	@Override
	public String getLoan(String transId, String merchantCode) {		
		String param="\""+transId+"\""+","+"\""+merchantCode+"\"";
		String result=telnetService.execute("invoke "+Constant.queryLoan+"("+param+")");
		return result;
	}

	
	@Override
	public void loanRisk() {
		telnetService.execute("invoke "+Constant.loanRisk+"()");

	}

	/**
	 * 将配置中心上传合同类型去掉，不用上传合同即可审核
	 * */
	@Override
	public void saveLoanVoucher(String merchantCode, String transId,String contractId) {
		//借款合同
		String loanContract="{\"merchantCode\":\""+merchantCode+"\",\"transId\":\""+transId+"\",\"contractId\":\""+contractId+"\",\"fileName\":\"/loan-sign-xnol/5afe8bec0dfb29005c00f57f.pdf\",\"fileExt\":\"pdf\",\"fileType\":15,\"fileIndex\":1,\"path\":\"/loan-sign-xnol/5afe8bec0dfb29005c00f57f.pdf\",\"original\":1}";
		telnetService.execute("invoke "+Constant.uploadContract+"("+loanContract+")");
		//居间合同
		String intermedContract="{\"merchantCode\":\""+merchantCode+"\",\"transId\":\""+transId+"\",\"contractId\":\""+contractId+"\",\"fileName\":\"/loan-sign-xnol/5afe8bec0dfb29005c00f57f.pdf\",\"fileExt\":\"pdf\",\"fileType\":16,\"fileIndex\":1,\"path\":\"/loan-sign-xnol/5afe8bec0dfb29005c00f57f.pdf\",\"original\":1}";
		telnetService.execute("invoke "+Constant.uploadContract+"("+intermedContract+")");
		//电子授权书
		String electronicAuthorization="{\"merchantCode\":\""+merchantCode+"\",\"transId\":\""+transId+"\",\"contractId\":\""+contractId+"\",\"fileName\":\"/loan-sign-xnol/5afe8bec0dfb29005c00f57f.pdf\",\"fileExt\":\"pdf\",\"fileType\":17,\"fileIndex\":1,\"path\":\"/loan-sign-xnol/5afe8bec0dfb29005c00f57f.pdf\",\"original\":1}";
		telnetService.execute("invoke "+Constant.uploadContract+"("+electronicAuthorization+")");
	}


	@Override
	public String auditRisk(String serialId) {
		try {
			Thread.sleep(3000);
			String First="{\"list\":[\""+serialId+"\"],\"auditType\":\"RISK_FIRST\",\"status\":\"PASS\",\"detail\":\"风控初审核通过\",\"auditUser\":\"0\"}";
			String Second="{\"list\":[\""+serialId+"\"],\"auditType\":\"RISK_SECOND\",\"status\":\"PASS\",\"detail\":\"风控复审通过\",\"auditUser\":\"0\"}";
			telnetService.execute("invoke "+Constant.auditRisk+"("+First+")");
			telnetService.execute("invoke "+Constant.auditRisk+"("+Second+")");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String auditLoan(Long data, String approveStatus) {
		String remark=null;
		if(approveStatus.equals("1")){
			remark="审核通过";
		}else if(approveStatus.equals("6")){
			remark="审核不通过";
		}
		String param="{\"operatorId\":0,\"approveStatus\":\""+approveStatus+"\",\"remark\":\""+remark+"\",\"assetType\":4,\"data\":["+data+"],\"isIgnoreException\":false}";
		String result=telnetService.execute("invoke "+Constant.auditLoan+"("+param+")");
		return result;
	}

	@Override
	public void sendLoan() {
		for(int i=0;i<2;i++){
			try {
				Thread.sleep(1000);
				log.info("第"+i+"次执行推送产品定时任务");
				telnetService.execute("invoke "+Constant.sendLoanAssets+"()");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public String invest(int userId, String productId, int buyAmout) {
		String param="{\"userId\":"+userId+",\"productId\":"+productId+",\"buyAmount\":"+buyAmout+",\"platform\":2,\"isPushMessage\":false}";
		String result=telnetService.execute("invoke "+Constant.invest+"("+param+")");
		return result;
	}
	
	@Override
	public void creditFull(String productId) {
		telnetService.execute("invoke "+Constant.creditFull+"("+productId+")");	
		
	}


	@Override
	public void PaymentJob() {
		try {
			Thread.sleep(2000);
			telnetService.execute("invoke "+Constant.PaymentJob+"()");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void syncPaymentDetailFlow() {
		for(int i=0;i<2;i++){			
			try {
				telnetService.execute("invoke "+Constant.syncPaymentDetailFlow+"()");
				Thread.sleep(2500);
				telnetService.execute("invoke "+Constant.processCompletedPaymentRecord+"()");
				Thread.sleep(1000);
				telnetService.execute("invoke "+Constant.Paycallback+"()");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}


	@Override
	public String loanPayment(String serialId, String status) {
		Date date=new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String PaymentDate= df.format(date);
		String loanStatus=null;
		if(status.equals("1")){
			//已放款
			loanStatus="HAS_BEEN_PAYMENT";
		}else if(status.equals("2")){
			//放款失败
			loanStatus="PAYMENT_FAIL";
		}else if(status.equals("3")){
			//退款
			loanStatus="REFUNDS";
		}
		String param="{\"serialId\":\""+serialId+"\",\"loanStatus\":\""+loanStatus+"\",\"paymentDate\":\""+PaymentDate+"\",\"submitPaymentDate\":\""+PaymentDate+"\",\"remark\":\"测试联调\"}";
		String result=telnetService.execute("invoke "+Constant.loanPayment+"("+param+")");
		return result;
	}

	
	@Override
	public String repayPlan(String serialId) {
		Date date=new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String valueDate= df.format(date);
		String param="{\"serialId\":\""+serialId+"\",\"loanTitle\":\"测试联调还款\",\"valueDate\":\""+valueDate+"\"}";
		String result=telnetService.execute("invoke "+Constant.repayPlan+"("+param+")");
		return result;
	}


	@Override
	public String productId(String serialId) {
		String result=telnetService.execute("invoke "+Constant.ProductIdByLoanKey+"(\""+serialId+"\")");
		return result;
	}


	@Override
	public String getUserId(String userName) {
		String result=telnetService.execute("invoke "+Constant.getUserId+"(\""+userName+"\")");
		return result;
	}


}
