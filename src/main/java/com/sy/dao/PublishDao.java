package com.sy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sy.entity.PublishInfo;
import com.sy.entity.UserInfo;

public interface PublishDao {
	//添加部署
	Integer insertPublish(@Param("user_code")String user_code,@Param("deliver_list")String deliver_list,@Param("server_list")String server_list,@Param("online_date")String online_date,@Param("deliver_match")String deliver_match,@Param("deliver_type")String deliver_type,@Param("status")String status);
	//添加日志
	Integer insertlog(@Param("content")String content,@Param("user_ip")String user_IP,@Param("operate")String operate);
	//添加部署
	Integer insertDeploy(@Param("user_code")String user_code,@Param("deliver_list")String deliver_list,@Param("server_list")String server_list,@Param("online_date")String online_date);
	//获取发布记录
	List<PublishInfo> getPublishInfo(PublishInfo publishInfo);
	//获取发布记录
	List<PublishInfo> getDeployInfo(PublishInfo publishInfo);
	//获取部署服务
	List<PublishInfo> getDeliverlist(String online_date,String deliver_match);
	//汇总上线
	List<PublishInfo> collectionBymatch(String online_date,String deliver_type,String status,String status2);
	//获取用户信息
	UserInfo getUserInfo(String user_ip);
	//修改发布版本状态
	Integer updateDeliverStatus(String deliver_list);
	//修改发布版本状态、IP、部署人
	Integer updateDeliverInfo(PublishInfo publishInfo);
	//获取操作记录
	List<PublishInfo> getOperatorRecord(String deliver_list);
}
