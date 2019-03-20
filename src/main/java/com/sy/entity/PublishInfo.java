package com.sy.entity;

import java.io.Serializable;

public class PublishInfo implements Serializable{

	private static final long serialVersionUID = -5763022051629736651L;
	
	private int id;
	private String online_date;
	private String deliver_list;
	private String user_code;
	private String server_list;
	private String createtime;
	private String deliver_match;
	private String deliver_type;
	private String status;
	private String updatetime;
	private String user_name;
	private String test_env;
	private String operate;

	public String getOperate() {
		return operate;
	}
	public void setOperate(String operate) {
		this.operate = operate;
	}
	public String getTest_env() {
		return test_env;
	}
	public void setTest_env(String test_env) {
		this.test_env = test_env;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getDeliver_match() {
		return deliver_match;
	}
	public void setDeliver_match(String deliver_match) {
		this.deliver_match = deliver_match;
	}
	public String getDeliver_type() {
		return deliver_type;
	}
	public void setDeliver_type(String deliver_type) {
		this.deliver_type = deliver_type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getOnline_date() {
		return online_date;
	}
	public void setOnline_date(String online_date) {
		this.online_date = online_date;
	}
	public String getDeliver_list() {
		return deliver_list;
	}
	public void setDeliver_list(String deliver_list) {
		this.deliver_list = deliver_list;
	}
	public String getUser_code() {
		return user_code;
	}
	public void setUser_code(String user_code) {
		this.user_code = user_code;
	}
	public String getServer_list() {
		return server_list;
	}
	public void setServer_list(String server_list) {
		this.server_list = server_list;
	}

	
}
