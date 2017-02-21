package com.cloudtrading.collection.common;

public class CacheConstants {
	
	// validateCode(redis 30分钟)
	public static final String REDIS_CACHE_KEY_VALIDATECODE = "VALIDATECODE#";
	public static final int REDIS_CACHE_SECONDS_VALIDATECODE = 30 * 60;

	// teacher sessionid(redis2个小时)
	public static final String REDIS_CACHE_KEY_TEACHER_SESSIONID = "TEACHER_SESSIONID#";
	public static final int REDIS_CACHE_SECONDS_TEACHER_SESSIONID = 2 * 60 * 60;
	
	// student sessionid(redis2个小时)
	public static final String REDIS_CACHE_KEY_STUDENT_SESSIONID = "STUDENT_SESSIONID#";
	public static final int REDIS_CACHE_SECONDS_STUDENT_SESSIONID = 2 * 60 * 60;

}
