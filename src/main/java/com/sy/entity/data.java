package com.sy.entity;

import java.io.Serializable;

public class data implements Serializable{	
	
	private static final long serialVersionUID = -4137080013348814058L;
	
	private long id;
	private String serialId;
	private int amount;
	
	
	
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getSerialId() {
		return serialId;
	}
	public void setSerialId(String serialId) {
		this.serialId = serialId;
	}
	
}
