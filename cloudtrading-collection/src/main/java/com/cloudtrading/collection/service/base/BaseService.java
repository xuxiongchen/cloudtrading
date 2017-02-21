package com.cloudtrading.collection.service.base;

import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudtrading.collection.service.cache.AppVersionCache;
import com.cloudtrading.collection.service.cache.CommonCache;

@Service
public class BaseService {
	@Autowired
	private AppVersionCache appVersionCache;
	@Autowired
	private CommonCache commonCache;
	
	
}
