package com.sy.entity;

import java.io.Serializable;

public class reqData implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5277935664189366543L;
	
	private String userNo	;
	private String requestNo;	
	private String realName;	
	private String idCardType;
	private String idCardNo;
	private String userRole;
	private String mobile;
	private String bankCardNo;
	private String verifyCardChannel;
	private String checkType;
	private String callbackUrl;
	private String userLimitType;
	private String authList;
	private String expired;
	private String authAmount;
	private String authDeadLine;
	
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getRequestNo() {
		return requestNo;
	}
	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getIdCardType() {
		return idCardType;
	}
	public void setIdCardType(String idCardType) {
		this.idCardType = idCardType;
	}
	public String getIdCardNo() {
		return idCardNo;
	}
	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getBankCardNo() {
		return bankCardNo;
	}
	public void setBankCardNo(String bankCardNo) {
		this.bankCardNo = bankCardNo;
	}
	public String getVerifyCardChannel() {
		return verifyCardChannel;
	}
	public void setVerifyCardChannel(String verifyCardChannel) {
		this.verifyCardChannel = verifyCardChannel;
	}
	public String getCheckType() {
		return checkType;
	}
	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}
	public String getCallbackUrl() {
		return callbackUrl;
	}
	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}
	public String getUserLimitType() {
		return userLimitType;
	}
	public void setUserLimitType(String userLimitType) {
		this.userLimitType = userLimitType;
	}
	public String getAuthList() {
		return authList;
	}
	public void setAuthList(String authList) {
		this.authList = authList;
	}
	public String getExpired() {
		return expired;
	}
	public void setExpired(String expired) {
		this.expired = expired;
	}
	public String getAuthAmount() {
		return authAmount;
	}
	public void setAuthAmount(String authAmount) {
		this.authAmount = authAmount;
	}
	public String getAuthDeadLine() {
		return authDeadLine;
	}
	public void setAuthDeadLine(String authDeadLine) {
		this.authDeadLine = authDeadLine;
	}
	
	
	
}
