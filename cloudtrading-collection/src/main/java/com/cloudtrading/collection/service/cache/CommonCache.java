package com.cloudtrading.collection.service.cache;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.huisa.common.cache.jvmcache.JVMTimeLimitLRUCache;
import com.huisa.common.exception.ServiceException;
import com.huisa.common.util.DateCoverd;

@Component
public class CommonCache {
/*	private static final JVMTimeLimitLRUCache<String, List<String>> CACHE_SUPPORTEDSERVICEAREAS = new JVMTimeLimitLRUCache<String, List<String>>(
			1);
	private static final JVMTimeLimitLRUCache<String, List<DictionaryServiceArea>> CACHE_SUPPORTEDSERVICEAREA_ENTITYS = new JVMTimeLimitLRUCache<String, List<DictionaryServiceArea>>(
			1);
	private static final JVMTimeLimitLRUCache<String, List<String>> CACHE_DICTIONARYCONSUMPTIONAMOUNTS = new JVMTimeLimitLRUCache<String, List<String>>(
			1);
	private static final JVMTimeLimitLRUCache<String, List<String>> CACHE_DICTIONARYGOODSTYPES = new JVMTimeLimitLRUCache<String, List<String>>(
			1);

	@Autowired
	private DictionaryServiceAreaDao dictionaryServiceAreaDao;
	@Autowired
	private DictionaryConsumptionAmountDao dictionaryConsumptionAmountDao;
	@Autowired
	private DictionaryGoodsTypeDao dictionaryGoodsTypeDao;

	public void saveValidateCode(String account, String validateCode) {
		AppContext.cache.setex(CacheConstants.REDIS_CACHE_KEY_VALIDATECODE
				+ account, CacheConstants.REDIS_CACHE_SECONDS_VALIDATECODE,
				validateCode);
	}

	public void checkValidateCode(String account, String validateCode)
			throws ServiceException {
		String validateCode_cache = getValidateCode(account);
		if (StringUtils.isBlank(validateCode)
				|| !validateCode.equals(validateCode_cache)) {
			throw new ServiceException(ErrorCode.CODE_VALIDATE_CODE, "验证码错误!");
		}
		destroyValidateCode(account);
	}

	public String getValidateCode(String account) {
		String validateCode = AppContext.cache
				.get(CacheConstants.REDIS_CACHE_KEY_VALIDATECODE + account);
		return validateCode;
	}

	private void destroyValidateCode(String account) {
		AppContext.cache.del(CacheConstants.REDIS_CACHE_KEY_VALIDATECODE
				+ account);
	}

	public List<String> getSupportedServiceArea() throws ServiceException {
		List<String> serviceAreas = CACHE_SUPPORTEDSERVICEAREAS
				.get(CacheConstants.JVM_CACHE_KEY_SUPPORTEDSERVICEAREAS);
		if (serviceAreas == null) {
			serviceAreas = new ArrayList<String>();
			List<DictionaryServiceArea> dictionaryServiceAreas = dictionaryServiceAreaDao
					.listAll();
			if (dictionaryServiceAreas != null
					&& dictionaryServiceAreas.size() > 0) {
				for (DictionaryServiceArea dictionaryServiceArea : dictionaryServiceAreas) {
					serviceAreas.add(dictionaryServiceArea.getServiceArea());
				}
			}
			CACHE_SUPPORTEDSERVICEAREAS
					.put(CacheConstants.JVM_CACHE_KEY_SUPPORTEDSERVICEAREAS,
							serviceAreas,
							DateCoverd
									.addMinute(
											new Date(),
											CacheConstants.JVM_CACHE_MINS_SUPPORTEDSERVICEAREAS));
		}
		return serviceAreas;
	}

	public List<DictionaryServiceArea> getServiceAreas()
			throws ServiceException {
		List<DictionaryServiceArea> serviceAreas = CACHE_SUPPORTEDSERVICEAREA_ENTITYS
				.get(CacheConstants.JVM_CACHE_KEY_SUPPORTEDSERVICEAREA_ENTITYS);
		if (serviceAreas == null) {
			serviceAreas = dictionaryServiceAreaDao.listAll();
			if (serviceAreas == null) {
				serviceAreas = new ArrayList<DictionaryServiceArea>();
			}
			CACHE_SUPPORTEDSERVICEAREA_ENTITYS
					.put(CacheConstants.JVM_CACHE_KEY_SUPPORTEDSERVICEAREA_ENTITYS,
							serviceAreas,
							DateCoverd
									.addMinute(
											new Date(),
											CacheConstants.JVM_CACHE_MINS_SUPPORTEDSERVICEAREA_ENTITYS));
		}
		return serviceAreas;
	}

	public List<String> getDictionaryConsumptionAmount()
			throws ServiceException {
		List<String> consumptionAmounts = CACHE_DICTIONARYCONSUMPTIONAMOUNTS
				.get(CacheConstants.JVM_CACHE_KEY_DICTIONARYCONSUMPTIONAMOUNTS);
		if (consumptionAmounts == null) {
			consumptionAmounts = new ArrayList<String>();
			List<DictionaryConsumptionAmount> dictionaryConsumptionAmounts = dictionaryConsumptionAmountDao
					.listAll();
			if (dictionaryConsumptionAmounts != null
					&& dictionaryConsumptionAmounts.size() > 0) {
				for (DictionaryConsumptionAmount dictionaryConsumptionAmount : dictionaryConsumptionAmounts) {
					consumptionAmounts.add(dictionaryConsumptionAmount
							.getConsumptionAmount());
				}
			}
			CACHE_DICTIONARYCONSUMPTIONAMOUNTS
					.put(CacheConstants.JVM_CACHE_KEY_DICTIONARYCONSUMPTIONAMOUNTS,
							consumptionAmounts,
							DateCoverd
									.addMinute(
											new Date(),
											CacheConstants.JVM_CACHE_MINS_DICTIONARYCONSUMPTIONAMOUNTS));
		}
		return consumptionAmounts;
	}

	public List<String> getDictionaryGoodsType() throws ServiceException {
		List<String> goodsTypes = CACHE_DICTIONARYGOODSTYPES
				.get(CacheConstants.JVM_CACHE_KEY_DICTIONARYGOODSTYPES);
		if (goodsTypes == null) {
			goodsTypes = new ArrayList<String>();
			List<DictionaryGoodsType> dictionaryGoodsTypes = dictionaryGoodsTypeDao
					.listAll();
			if (dictionaryGoodsTypes != null && dictionaryGoodsTypes.size() > 0) {
				for (DictionaryGoodsType dictionaryGoodsType : dictionaryGoodsTypes) {
					goodsTypes.add(dictionaryGoodsType.getGoodsType());
				}
			}
			CACHE_DICTIONARYGOODSTYPES
					.put(CacheConstants.JVM_CACHE_KEY_DICTIONARYGOODSTYPES,
							goodsTypes,
							DateCoverd
									.addMinute(
											new Date(),
											CacheConstants.JVM_CACHE_MINS_DICTIONARYGOODSTYPES));
		}
		return goodsTypes;
	}*/

}
