package com.sy.entity;

import java.io.Serializable;

public class UserPerson implements Serializable{

	private static final long serialVersionUID = 6021052657556420441L;
	
	private String idCardNo;
	private int id;
	private String email;
	public String getIdCardNo() {
		return idCardNo;
	}
	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
