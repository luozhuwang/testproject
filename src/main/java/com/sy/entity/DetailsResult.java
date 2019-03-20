package com.sy.entity;

import java.io.Serializable;

public class DetailsResult<T> implements Serializable{
	

	private static final long serialVersionUID = 4580501543314425901L;
	
	private String errorDetails;
	private int status;
	private T data;
	private String errorCode;
	public String getErrorDetails() {
		return errorDetails;
	}
	public void setErrorDetails(String errorDetails) {
		this.errorDetails = errorDetails;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	
}
