package com.cloudtrading.collection.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.cloudtrading.collection.entity.CorundumData;
import com.cloudtrading.collection.entity.GoldCopperData;
import com.cloudtrading.collection.entity.SilverAlloyData;
import com.huisa.common.database.BaseDao;
import com.huisa.common.exception.ServiceException;

@Repository
public class AnalysisDao extends BaseDao {
	public List<SilverAlloyData> countSilverAlloy(String sql,List<Object> params)
			throws ServiceException {
		return list(sql,SilverAlloyData.class,params);
	}
	public List<GoldCopperData> countGoldCopper(String sql,List<Object> params)
			throws ServiceException {
		return list(sql,GoldCopperData.class,params);
	}
	public List<CorundumData> countCorundumData(String sql,List<Object> params) throws ServiceException {
		return list(sql,CorundumData.class,params);
	}
}
