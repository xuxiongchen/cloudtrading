package com.cloudtrading.collection.service.cache;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cloudtrading.collection.dao.base.AppVersionDao;
import com.cloudtrading.collection.entity.base.AppVersion;
import com.huisa.common.cache.jvmcache.JVMTimeLimitLRUCache;
import com.huisa.common.exception.ServiceException;
import com.huisa.common.util.DateCoverd;

@Component
public class AppVersionCache {
	@Autowired
	private AppVersionDao appVersionDao;
	private static final JVMTimeLimitLRUCache<String, AppVersion> CACHE_MAXAPPVERSION = new JVMTimeLimitLRUCache<String, AppVersion>(
			2);

	public AppVersion getMaxAppVersion(int appType, int sysType)
			throws ServiceException {
		String cacheKey = appType + "#" + sysType;
		AppVersion maxAppVersion = CACHE_MAXAPPVERSION.get(cacheKey);
		if (maxAppVersion == null) {
			maxAppVersion = appVersionDao.getMaxAppVersion(appType, sysType);
			if (maxAppVersion == null) {
				maxAppVersion = new AppVersion();
			}
			CACHE_MAXAPPVERSION.put(cacheKey, maxAppVersion,
					DateCoverd.addMinute(new Date(), 2));
		}
		return maxAppVersion;
	}

}
