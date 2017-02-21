package com.cloudtrading.collection.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cloudtrading.collection.service.CatchDataScheduledService;
import com.cloudtrading.collection.service.CatchDataService;
import com.cloudtrading.collection.service.common.SysconfigService;
import com.cloudtrading.collection.utils.SpringContextHolder;
import com.cloudtrading.warehouse.utils.TessreactUtil;
import com.huisa.common.cache.redis.JedisCache;
import com.huisa.common.cache.redis.assist.Config_JedisCache;

/**
 * 系统上下文配置
 * 
 * @author moyuhui
 * 
 */
public class AppContext {
	private static final Logger logger = LoggerFactory.getLogger(AppContext.class);
	public static boolean isstarted = false;
	public static boolean logswitch = false;// 请求和响应debug日志开关 （默认关闭）
	public static JedisCache cache;
	private static SysconfigService sysconfigService;
	private static ScheduledExecutorService scheduler = null;
	private static String filedomain;
	private static String filepath = "D:/otherproject/workspace/matheasy/src/main/webapp/upload";
	
	public static void init()
			throws Exception {
		//TessreactUtil.createSreen();
		AppContext.sysconfigService = SpringContextHolder.getBean(SysconfigService.class);
		final CatchDataScheduledService catchDataScheduledService = SpringContextHolder.getBean(CatchDataScheduledService.class);
		// 初始化
		refreshConfig();
		String redisServers = sysconfigService.getValue("REDIS_SERVERS");
		//需要开启redis 服务
		Config_JedisCache jedisCacheConfig = new Config_JedisCache( redisServers, null);

		cache = new JedisCache(jedisCacheConfig);
		//CatchDataScheduledService.isWork=isNotIn4To9();
		catchDataScheduledService.startCatchData();  //开始抓取数据
		// 定时器
		scheduler = Executors.newScheduledThreadPool(2);
		scheduler.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				try {
					sysconfigService.updateConfig();
					AppContext.refreshConfig();
				} catch (Throwable e) {
					logger.error("AppContext.scheduler faild!", e);
				}
			}
		}, 10, 10, TimeUnit.MINUTES);
		isstarted = true;
	}

	public static void destroy() {
		isstarted = false;
	}

	public static boolean isNotIn4To9(){
		
		Date date=new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//格式化当前时间
	    String now=dateFormat.format(date);    //获取当前时间
	    String timeString=now.substring(11, 13);
	    int hour=Integer.parseInt(timeString);
	    if(hour>4&&hour<9){
	    	return false;
	    }
	    return true;
	}
	public static void refreshConfig() {
		// 请求和响应debug日志开关
		if (sysconfigService.getInt("LOG_SWITCH") != null) {
			if (sysconfigService.getInt("LOG_SWITCH") == 1) {
				logswitch = true;
			} else {
				logswitch = false;
			}
		}
		
		if (StringUtils.isNotBlank(sysconfigService.getValue("FILE_DOMAIN"))) {
			filedomain = sysconfigService.getValue("FILE_DOMAIN");
		}

		if (StringUtils.isNotBlank(sysconfigService.getValue("FILE_PATH"))) {
			filepath = sysconfigService.getValue("FILE_PATH");
		}
		
	}

	public static String getValueFromSysConfig(String pkey) {
		return sysconfigService.getValue(pkey);
	}

	public static Integer getIntegerFromSysConfig(String pkey) {
		return sysconfigService.getInt(pkey);
	}



	public static String getFileUrl(String fileUri) {
		if (StringUtils.isBlank(fileUri)) {
			return null;
		} else if (!fileUri.startsWith("http")
				&& StringUtils.isNotBlank(AppContext.filedomain)) {
			return AppContext.filedomain + fileUri;
		}
		return fileUri;
	}

	
	public static String getFileUri(String fileUrl) {
		if (StringUtils.isBlank(fileUrl)) {
			return null;
		} else if (fileUrl.startsWith("http")
				&& StringUtils.isNotBlank(AppContext.filedomain)) {
			return fileUrl.replace(AppContext.filedomain, "");
		}
		return fileUrl;
	}

	public static String getFiledomain() {
		return filedomain;
	}

	public static String getFilepath() {
		return filepath;
	}

}
