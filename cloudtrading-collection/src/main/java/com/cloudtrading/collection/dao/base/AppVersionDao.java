package com.cloudtrading.collection.dao.base;

import org.springframework.stereotype.Repository;

import com.cloudtrading.collection.entity.base.AppVersion;
import com.huisa.common.database.BaseDao;
import com.huisa.common.exception.ServiceException;

@Repository
public class AppVersionDao extends BaseDao {

	public AppVersion getMaxAppVersion(int appType, int sysType)
			throws ServiceException {
		return get(
				"SELECT * FROM app_version WHERE app_type=? AND sys_type=? ORDER BY version_code DESC LIMIT 1",
				AppVersion.class, appType, sysType);
	}

}
