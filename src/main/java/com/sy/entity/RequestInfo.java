package com.sy.entity;

import java.io.Serializable;

public class RequestInfo implements Serializable{
	private static final long serialVersionUID = -3888134270211194134L;
	
	private String requestIP;
	private int requestPort;
	private String requestMethod;
	private String requestParam;
	public String getRequestIP() {
		return requestIP;
	}
	public void setRequestIP(String requestIP) {
		this.requestIP = requestIP;
	}
	public int getRequestPort() {
		return requestPort;
	}
	public void setRequestPort(int requestPort) {
		this.requestPort = requestPort;
	}
	public String getRequestMethod() {
		return requestMethod;
	}
	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}
	public String getRequestParam() {
		return requestParam;
	}
	public void setRequestParam(String requestParam) {
		this.requestParam = requestParam;
	}
	
	
}
