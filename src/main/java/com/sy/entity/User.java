package com.sy.entity;

import java.io.Serializable;

public class User implements Serializable{
	private static final long serialVersionUID = 4078980907201152021L;
	

	private String id;
	
	private String username;
	
	private String password;
	
	
	public User(String id, String username) {  
        this.id = id;  
        this.username = username;  
    } 

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
