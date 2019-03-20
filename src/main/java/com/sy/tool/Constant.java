package com.sy.tool;

public class Constant {
	public static String Linux_UserName="root";
	public static String Linux_Password="root123";
	public static int TimeOut=60000;
	public static String UTF_8="UTF-8";
	public static String ISO_8859_1 = "ISO-8859-1";
	public static int SSH_port=22;
	public static String toolStatistics="http://172.20.20.160:8090/api/py/chart/toolStatistics";

	public static String svn_base = "http://172.20.21.1/svn/releases/";	
	/**version.txt*/
	public static String svn_version="/version.txt";
	/**
	 * 发送邮件
	 * */
	public static String mailServer="https://mail.abc.com/EWS/exchange.asmx";
	/**
	 * 资产API接口常量
	 * **/
	// 在线RSA公私钥
		public static String zxPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCE5nK0Wf6e7yO5JE61qJwNUmcRt/GOk1SFVBWQYGQEXYn7tz3ZiDIgNasPOYZ6g6pbic9kR2FBwTj646E8vKIC37ZLUgTaIsLLIOtxxHa3YrM7xqYaUWz5Ua4yttM6D7bdc7hOaqLXSslYvNT1NDoSkphtILPQtX2aUOmT3eoMHQIDAQAB";
		public static String zxPrivateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAITmcrRZ/p7vI7kkTrWonA1SZxG38Y6TVIVUFZBgZARdifu3PdmIMiA1qw85hnqDqluJz2RHYUHBOPrjoTy8ogLftktSBNoiwssg63HEdrdiszvGphpRbPlRrjK20zoPtt1zuE5qotdKyVi81PU0OhKSmG0gs9C1fZpQ6ZPd6gwdAgMBAAECgYAZPzYXMOmAA+oDZ/RT6j4LAdZ2tTz8Wty5n2mhTc5yTdrCqOLlCkyLdeaTM9hqOc0JKrrtT+oX9b7/WnLs+ODF5essKMnIwBoipSSedRWEgjYP3t23Ro96K1ytp1FnxyB1dfVp0eOdcd0FS0aZ8N6/dPizn8juMTQDbIIu3RkZwQJBAPbLE9oFMpLUUBQtCypjchAo4E7L4J4iz1cHqylpOmYOxZgj7FwTAlI1XIQWZHsne7PvwoUo7rYBJwPr9nUebrkCQQCJ26uF29g3AI9hT5QXduH4oW0setBK5naTqEbnnpMKvybmlUBO2d6lX6atNgxQG9XrXPGpJLBbpFQJD6Pr+7aFAkEA5/bCxvaBrY6PYhdgWkw0Zsn04zsv+ZLgbX3QvFCiylByGukQ/Q4E7X4oYiKl+TeIRv1BSWXK0RlOMZp1AWpESQJANp4mlbElN51sMQSjSqyaGLR0GZRK4/Hs9tFLzkZgQXi8Q8zMHrFo6aI82hE4zaBJn6dCQ1461QQFG1Xr/vnKNQJAeR03SnpV7Hc8XbHefV9qAba0aT1wfW3D21jQIObvkg3111q3l52Hei70bWIKPAC8COeP95lQPdTBA75ZDrPP0g==";    
		// 资产商户RSA公私钥
		public static String otherPublicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDBXmCsvWWTe2yOptt29t+uRqQxYbisUt1qcikv7Uwf0SVNEUqoqMDxAWRxrBphIbnvQIzcLaHI7nORfh6wINuPB4QVWOMGlmT4AzC3XZqRz59iu0pnb/Z8Ms6NzGgGsg5rsuAP7wduMmosX1KB3Ysf+ymoMONK4wpzRtQsXC/WEQIDAQAB";
		public static String otherPrivateKey="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAMFeYKy9ZZN7bI6m23b2365GpDFhuKxS3WpyKS/tTB/RJU0RSqiowPEBZHGsGmEhue9AjNwtocjuc5F+HrAg248HhBVY4waWZPgDMLddmpHPn2K7Smdv9nwyzo3MaAayDmuy4A/vB24yaixfUoHdix/7Kagw40rjCnNG1CxcL9YRAgMBAAECgYBrxVwAKnboYcu5HcoHXcSA7yIn25z/fhelcgb+jTT2LqypbU+8/IC8UdhXemIhbJiifcmEFXKr+Co1FqOn6kgzURxFn5im2KMWitnCEEERAEa+GGWLM0lRXydI+K65S4Dc2cVDksEyW1YhtzZsocPnyMuzWLcBY9HN0V8v5HhseQJBAOOrgdlyM5khcF2vdl/nmxqCqLPBPby7zUpAmevurVWv5diOYhbc4CbRdJuSrYFqy8cu9K2NRaRvJCxbtM/j2s8CQQDZbjFgeW2CG3W3+k1FKns7kavdJlCe4ubOej5IEJjbrJyDt/uVt+J363D0CdCEjHn2BqSfphK8z6N3SW4BK/kfAkBiXESbR0WXkOTU9Ot1f8B48Z4lGwWrNo/41nQphFKKxJXOu6URL5f/7VotpG8ljJhBk73OBUzjP8knCO/TKSPtAkEAkT28mhdDAYBaWHVJHITOIPKj/WxUum4Tg6XA6N69XTCmtI437sEQ9M4/e6T6tzAnYCL74PFM3vdM2KgiZYH8PQJAQvGrb+DGy8rHLt/VE2ufLqDoNoQq/wAOFL3BvsoHh/grhKAF5JimuWKyCOGDKIIdu/g6p9WHZ1tjAQ6O8VFYwQ==";    
		public static String merChantCode="NE1001";
		

		public static String Basic_url="http://www.abc.com";
		/**导入借款人:/loan/api/import/borrower*/
		public static String APIborrower=Basic_url+"/loan/api/import/borrower";
		/**导入借款:/loan/api/import/loan*/
		public static String APIloan=Basic_url+"/loan/api/import/loan";
		/**还款计划查询:/loan/api/query/repayPlan*/
		public static String APIrepayPlan=Basic_url+"/loan/api/query/repayPlan";
		/**放款状态查询:/loan/api/query/loanStatus*/
		public static String APIloanStatus=Basic_url+"/loan/api/query/loanStatus";
		/**借款人统计信息查询:/loan/api/query/userStatic*/
		public static String APIuserStatic=Basic_url+"/loan/api/query/userStatic";
		
	
	public static final String CHAR_ENCODING = "UTF-8";
	public static final String AES_ALGORITHM = "AES/ECB/PKCS5Padding";
	public static final String RSA_ALGORITHM = "RSA/ECB/PKCS1Padding";
	
	//资产联调相关的Dubbo接口
	//查询资产
	public static String queryLoan="com.neo.querySimpleAccountBookBySerialId";
	//推送风控
	public static String loanRisk="com.neo.loanRiskJob";
	//查询合同
	public static String queryContract="com.neo.queryLoanContract";
	//上传合同
	public static String uploadContract="com.neo.saveLoanVoucher";
	//风控审核
	public static String auditRisk="com.neo.auditRisk";
	//审核借款
	public static String auditLoan="com.neo.auditLoan";
	//定时任务--推送资产至产品系统
	public static String sendLoanAssets="com.neo.sendTheLoanAssetsTimers";
	//买标接口
	public static String invest="com.neo.invest";
	//通知资产满标
	public static String creditFull="com.neo.creditFull";
	//放款
	public static String loanPayment="com.neo.payment";
	//生成还款计划
	public static String repayPlan="com.neo.accountBookRaiseFinished";
	//查询产品ID
	public static String ProductIdByLoanKey="com.neo.getCommonProductIdByLoanKey";
	//查询用户ID
	public static String getUserId="com.neo.getByUserName";
	//定时任务--推送放款资产到财务系统
	public static String PaymentJob="com.neo.doPaymentJob";
	//定时任务--同步放款明细交易流水状态
	public static String syncPaymentDetailFlow="com.neo.syncPaymentDetailFlow";
	//定时任务--商户订单回调任务
	public static String Paycallback="com.neo.callbackTask";
	//定时任务--放款记录状态处理
	public static String processCompletedPaymentRecord="com.neo.processCompletedPaymentRecord";
}
