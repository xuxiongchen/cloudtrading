package com.cloudtrading.collection.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudtrading.collection.dao.GoldCopperDataDao;
import com.cloudtrading.collection.entity.CorundumData;
import com.cloudtrading.collection.entity.GoldCopperData;
import com.huisa.common.exception.ServiceException;

@Service
public class GoldCopperDataService {

	@Autowired
	private GoldCopperDataDao goldCopperDataDao;
	
	public GoldCopperData saveBaseData(GoldCopperData baseData) throws ServiceException {
		Integer id=goldCopperDataDao.addReturnGeneratedKey(baseData);
		baseData.setID(id);
		return baseData;
	}
	
}
