package com.cloudtrading.collection.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudtrading.collection.dao.SilverAlloyDataDao;
import com.cloudtrading.collection.entity.CorundumData;
import com.cloudtrading.collection.entity.SilverAlloyData;
import com.huisa.common.exception.ServiceException;

@Service
public class SilverAlloyDataService {

	@Autowired
	private SilverAlloyDataDao silverAlloyDataDao;
	
	public SilverAlloyData saveBaseData(SilverAlloyData baseData) throws ServiceException {
		Integer id=silverAlloyDataDao.addReturnGeneratedKey(baseData);
		baseData.setID(id);
		return baseData;
	}
	
}
