package com.cloudtrading.collection.web.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.cloudtrading.warehouse.common.ErrorCode;
import com.google.gson.JsonObject;
import com.huisa.common.exception.ServiceException;

public class ExceptionHandler implements HandlerExceptionResolver {
	private final Logger logger = LoggerFactory
			.getLogger(ExceptionHandler.class);

	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		// 异常处理
		if (ex != null) {
			logger.error(ex.getLocalizedMessage(), ex);

			JsonObject reponseData = new JsonObject();
			if (ex instanceof ServiceException) {
				reponseData.addProperty("code",
						((ServiceException) ex).getErrorcode());
				reponseData.addProperty("msg", ex.getMessage());
			} else {
				reponseData.addProperty("code", ErrorCode.CODE_JAVA);
				reponseData.addProperty("msg", "Server Exception");
			}

			MVCUtil.returnData(reponseData.toString());
		}
		return new ModelAndView();
	}

}
