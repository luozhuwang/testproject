package com.sy.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sy.entity.MockInfo;
import com.sy.entity.UserInfo;

public interface MockDao {
	//获取接口列表
	List<MockInfo> getMockRecord(@Param("name")String name);

	MockInfo getRecordById(@Param("id")String id);
	
	Integer updateStatus(@Param("id")String id,@Param("status")String status);
	
}
