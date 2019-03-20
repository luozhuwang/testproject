package com.sy.service;


public interface LoanService {
	//查询资产
	public String getLoan(String transId,String merchantCode);
	//推送风控
	public void loanRisk();
	//上传合同
	public void saveLoanVoucher(String merchantCode,String transId,String contractId);
	//风控审核
	public String auditRisk(String serialId);
	//审核借款
	public String auditLoan(Long data,String approveStatus);
	//推送资产
	public void sendLoan();
	//买标
	public String invest(int userId,String productId,int buyAmout);
	//通知资产满标
	public void creditFull(String productId);
	//放款
	public String loanPayment(String serialId,String loanStatus);
	//还款计划
	public String repayPlan(String serialId);
	//查询产品ID
	public String productId(String serialId);
	//查询用户ID
	public String getUserId(String userName);
	//定时任务--推送放款资产到财务系统
	public void PaymentJob();
	//定时任务--同步放款明细交易流水状态 / 放款记录状态处理/商户订单回调任务
	public void syncPaymentDetailFlow();
}
