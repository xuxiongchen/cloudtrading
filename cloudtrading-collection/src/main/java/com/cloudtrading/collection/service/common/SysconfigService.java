package com.cloudtrading.collection.service.common;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cloudtrading.collection.dao.common.SysconfigDao;
import com.cloudtrading.collection.entity.common.Sysconfig;


@Service
public class SysconfigService {
	private final Logger logger = LoggerFactory
			.getLogger(SysconfigService.class);
	private static Map<String, String> sysparms = new ConcurrentHashMap<String, String>();
	private static Map<String, Integer> sysparms_Integer = new ConcurrentHashMap<String, Integer>();
	@Autowired
	private SysconfigDao sysconfigDao;
	@Value("${servercode:}")
	private String servercode;

	@PostConstruct
	private void init() {
		try {
			List<Sysconfig> sysConfigs = sysconfigDao.listSysconfig(servercode);
			for (Sysconfig sysConfig : sysConfigs) {
				if (sysConfig.getCkey() != null
						&& sysConfig.getCvalue() != null) {
					sysparms.put(sysConfig.getCkey(), sysConfig.getCvalue());
				}
			}
		} catch (Exception e) {
			logger.error("SysconfigService.init faild", e);
		}
	}

	/**
	 * 刷新配置信息
	 */
	public void updateConfig() {
		init();
		// 更新sysparms_Integer
		Set<String> intkeys = sysparms_Integer.keySet();
		for (String intkey : intkeys) {
			String value = getValue(intkey);
			if (StringUtils.isNumeric(value)) {
				Integer intValue = Integer.parseInt(value);
				sysparms_Integer.put(intkey, intValue);
			} else {
				logger.error("SysConfig key:" + intkey + " is not a int value!");
			}
		}
	}

	/**
	 * 获取String配置
	 * 
	 * @param key
	 * @return
	 */
	public String getValue(String key) {
		return sysparms.get(key);
	}

	/**
	 * 获取Int配置
	 * 
	 * @param key
	 * @return
	 * @throws AppException
	 */
	public Integer getInt(String key) {
		Integer intValue = sysparms_Integer.get(key);
		if (intValue == null) {
			String value = getValue(key);
			if (StringUtils.isNumeric(value)) {
				intValue = Integer.parseInt(value);
				sysparms_Integer.put(key, intValue);
			}
		}
		return intValue;
	}

}
