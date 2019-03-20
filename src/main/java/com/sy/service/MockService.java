package com.sy.service;

import java.util.List;
import com.sy.entity.MockInfo;

public interface MockService {
	//获取接口列表
	List<MockInfo> getMockRecord(String name);
}
