package com.matheasy.service.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huisa.common.database.BaseDao;
import com.huisa.common.exception.ServiceException;

public class importModelClass {
	
	private static Logger logger = LoggerFactory.getLogger(importModelClass.class);

	public static void main(String[] args) throws Exception {
		// 生成javabean
		try {
			new BaseDao()
					.generateJavaBean(
							"class_info",
							"J:/class",
							"com.mintour.service.entity.po");
		} catch (ServiceException e) {
		}

	}
}
