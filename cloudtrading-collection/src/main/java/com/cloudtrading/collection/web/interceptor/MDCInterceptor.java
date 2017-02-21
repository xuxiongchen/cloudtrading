package com.cloudtrading.collection.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import ch.qos.logback.classic.ClassicConstants;

public class MDCInterceptor implements HandlerInterceptor {
	public final static String USER_KEY = "user_id";

	public boolean preHandle(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Object arg2)
			throws Exception {
		MDC.put(ClassicConstants.REQUEST_REQUEST_URI,
				httpServletRequest.getRequestURI());
		return true;
	}

	public void postHandle(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Object arg2,
			ModelAndView modelAndView) throws Exception {
	}

	public void afterCompletion(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Object arg2,
			Exception exception) throws Exception {
		MDC.remove(USER_KEY);
		MDC.remove(ClassicConstants.REQUEST_REQUEST_URI);
	}

	public static void setUserKeyForMDC(String userId) {
		MDC.put(USER_KEY, userId);
	}
}
