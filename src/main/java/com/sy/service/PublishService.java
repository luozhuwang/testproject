package com.sy.service;

import java.util.List;

import com.sy.entity.PublishInfo;
import com.sy.entity.UserInfo;


public interface PublishService {
	//添加部署
	public Integer Publish(String user_code,String deliver_list,String server_list,String online_date, String deliver_match, String deliver_type, String status);
	//添加部署
	public Integer Deploy(String user_code,String deliver_list,String server_list,String online_date);
	//添加日志
	public Integer insertlog(String content,String user_ip,String operate);
	//获取发布记录
	public List<PublishInfo> getPublishInfo(PublishInfo publishInfo);
	//获取发布记录
	public List<PublishInfo> getDeployInfo(PublishInfo publishInfo);
	//获取部署服务
	public List<PublishInfo> getDeliverlist(String online_date,String deliver_match);
	//汇总上线
	public List<PublishInfo> collectionBymatch(String online_date,String deliver_type,String status,String status2);
	//获取用户信息
	public UserInfo getUserInfo(String user_ip);
	//修改发布版本状态
	public Integer updateDeliverStatus(String deliver_list);
	//修改发布版本状态、IP、部署人
	public Integer updateDeliverInfo(PublishInfo publishInfo);
	//获取操作记录
	public List<PublishInfo>  getOperatorRecord(String deliver_list);
}
