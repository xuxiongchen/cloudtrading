package com.mintour.service.service.cache;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.huisa.common.exception.ServiceException;
import com.mintour.service.common.AppContext;
import com.mintour.service.common.CacheConstants;
import com.mintour.service.common.ErrorCode;
import com.mintour.service.dao.guide.GuideInfoDao;
import com.mintour.service.entity.po.guide.GuideInfo;

@Component
public class GuideCache {
	@Autowired
	private GuideInfoDao guideInfoDao;

	public void saveSessionid(int userId, String sessionid) {
		AppContext.cache.setex(CacheConstants.REDIS_CACHE_KEY_GUIDE_SESSIONID
				+ userId, CacheConstants.REDIS_CACHE_SECONDS_GUIDE_SESSIONID,
				sessionid);
	}

	public String getSessionidByUserId(int userId) throws ServiceException {
		String sessionid = AppContext.cache
				.get(CacheConstants.REDIS_CACHE_KEY_GUIDE_SESSIONID + userId);
		if (StringUtils.isBlank(sessionid)) {
			GuideInfo guideInfo = guideInfoDao.getSessionid(userId);
			if (guideInfo == null) {
				throw new ServiceException(ErrorCode.CODE_AUTH, "请重新登录!");
			}
			sessionid = guideInfo.getSessionid();
			if (StringUtils.isNotBlank(sessionid)) {
				saveSessionid(userId, sessionid);
			}
		}
		return sessionid;
	}

	public void deleteSessionid(int userId) {
		AppContext.cache.del(CacheConstants.REDIS_CACHE_KEY_GUIDE_SESSIONID
				+ userId);

	}

	public void checkSessionid(int userId, String sessionid)
			throws ServiceException {
		// 校验
		if (userId <= 0) {
			throw new ServiceException(ErrorCode.CODE_PARAM, "userId 不能为空!");
		}
		if (StringUtils.isBlank(sessionid)) {
			throw new ServiceException(ErrorCode.CODE_PARAM, "sessionid 不能为空!");
		}
		// 检验userid和sessionid是否匹配
		String sessionid_db = getSessionidByUserId(userId);
		if (StringUtils.isBlank(sessionid_db)
				|| !sessionid_db.equals(sessionid)) {
			throw new ServiceException(ErrorCode.CODE_AUTH,
					"session校验失败,请重新登录!");
		}
	}

	public void saveGuideLatitudeAndLongitude(int guideId, String latitude,
			String longitude) {
		String cacheKey = CacheConstants.REDIS_CACHE_KEY_GUIDE_LATITUDE_LONGITUDE
				+ guideId;
		AppContext.cache.setex(cacheKey,
				CacheConstants.REDIS_CACHE_SECONDS_GUIDE_LATITUDE_LONGITUDE,
				latitude + "#" + longitude);
	}

	public String getGuideLatitudeAndLongitude(int guideId) {
		return AppContext.cache
				.get(CacheConstants.REDIS_CACHE_KEY_GUIDE_LATITUDE_LONGITUDE
						+ guideId);
	}
}
