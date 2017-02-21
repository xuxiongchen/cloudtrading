package com.cloudtrading.collection.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloudtrading.collection.common.AppContext;
import com.huisa.common.http.HttpClientUtil;

public class ContextListener implements ServletContextListener {
	private final Logger logger = LoggerFactory
			.getLogger(ContextListener.class);

	public void contextDestroyed(ServletContextEvent sce) {
		AppContext.destroy();
	}

	public void contextInitialized(ServletContextEvent se) {
		// 初始化资源
		try {
			HttpClientUtil.createHttpClient("ssl");
			//HttpClientUtil.openSSLSupport("ssl");
			AppContext.init();
			logger.info("server is started!");
		} catch (Exception e) {
			logger.error("ContextListener.contextInitialized faild!", e);
			System.exit(1);
		}

	}

}
