package com.cloudtrading.collection.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudtrading.collection.dao.CorundumDataDao;
import com.cloudtrading.collection.entity.CorundumData;
import com.huisa.common.exception.ServiceException;

@Service
public class CorundumDataService {

	@Autowired
	private CorundumDataDao baseDataDao;
	
	public CorundumData saveBaseData(CorundumData baseData) throws ServiceException {
		Integer id=baseDataDao.addReturnGeneratedKey(baseData);
		baseData.setID(id);
		return baseData;
	}
	
}
