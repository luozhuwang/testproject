package com.sy.service.Impl;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sy.dao.PublishDao;
import com.sy.entity.PublishInfo;
import com.sy.entity.UserInfo;
import com.sy.service.PublishService;

@Service
public class PublishServiceImp implements PublishService{
	
	@Autowired
	private PublishDao publishDao;
		


	@Override
	public List<PublishInfo> getOperatorRecord(String deliver_list) {
		List<PublishInfo>  publishInfolist=publishDao.getOperatorRecord(deliver_list);
		return publishInfolist;
	}

	@Override
	public Integer updateDeliverStatus(String deliver_list) {
		int count=publishDao.updateDeliverStatus(deliver_list);
		return count;
	}

	@Override
	public Integer insertlog(String content,String user_ip, String operate) {
		int count=publishDao.insertlog(content,user_ip, operate);
		return count;
	}

	@Override
	public UserInfo getUserInfo(String user_ip) {
		UserInfo userInfo=publishDao.getUserInfo(user_ip);
		return userInfo;
	}

	@Override
	public List<PublishInfo> collectionBymatch(String online_date,String deliver_type, String status, String status2) {
		List<PublishInfo>  publishInfolist=publishDao.collectionBymatch(online_date,deliver_type,status,status2);
		return publishInfolist;
	}

	@Override
	public List<PublishInfo> getDeliverlist(String online_date,String deliver_match) {
		List<PublishInfo>  publishInfolist=publishDao.getDeliverlist(online_date,deliver_match);
		return publishInfolist;
	}

//	public List<PublishInfo> collection(String online_date,String deliver_type,String status,String status2) {
//		List<PublishInfo>  publishInfolist=publishDao.collection(online_date,deliver_type,status,status2);
//		return publishInfolist;
//	}

	@Override
	public List<PublishInfo> getPublishInfo(PublishInfo publishInfo) {
		List<PublishInfo>  publishInfolist=publishDao.getPublishInfo(publishInfo);
		return publishInfolist;
	}
	@Override
	public List<PublishInfo> getDeployInfo(PublishInfo publishInfo) {
		List<PublishInfo>  publishInfolist=publishDao.getDeployInfo(publishInfo);
		return publishInfolist;
	}
	@Override
	public Integer Publish(String user_code, String deliver_list,
			String server_list, String online_date, String deliver_match, String deliver_type, String status) {
		int count=publishDao.insertPublish(user_code, deliver_list, server_list, online_date,deliver_match,deliver_type,status);
		return count;
	}
	@Override
	public Integer Deploy(String user_code, String deliver_list,
			String server_list, String online_date) {
		int count=publishDao.insertDeploy(user_code, deliver_list, server_list, online_date);
		return count;
	}

	@Override
	public Integer updateDeliverInfo(PublishInfo publishInfo) {
		int count=publishDao.updateDeliverInfo(publishInfo);
		return count;
	}

}
