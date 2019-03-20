package com.sy.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sy.dao.MockDao;
import com.sy.entity.MockInfo;
import com.sy.service.MockService;

@Service
public class MockServiceImp implements MockService{
	@Autowired
	private MockDao mockDao;
	
	@Override
	public List<MockInfo> getMockRecord(String name) {
		
		return mockDao.getMockRecord(name);
	}

	
}
