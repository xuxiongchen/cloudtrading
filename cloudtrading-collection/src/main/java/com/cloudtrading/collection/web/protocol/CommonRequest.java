package com.cloudtrading.collection.web.protocol;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.cloudtrading.collection.utils.GsonObject;
import com.cloudtrading.collection.web.interceptor.MDCInterceptor;
import com.cloudtrading.warehouse.common.ErrorCode;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.huisa.common.exception.ServiceException;

public class CommonRequest {
	private String cip;
	private GsonObject requestData;
	private RequestParam requestParam;

	public CommonRequest(HttpServletRequest httpServletRequest,
			String requestJsonData, RequestParam requestParam)
			throws ServiceException {
		this.requestParam = requestParam;
		if (StringUtils.isNotBlank(requestJsonData)) {
			JsonElement jsonElement = null;
			try {
				jsonElement = new JsonParser().parse(requestJsonData);
			} catch (Exception e) {
				throw new ServiceException(ErrorCode.CODE_PARAM,
						"post的数据不是json格式的字符串!");
			}
			this.requestData = new GsonObject(jsonElement.getAsJsonObject());
			// setUserKeyForMDC
			if (!requestData.isnull(MDCInterceptor.USER_KEY)) {
				MDCInterceptor.setUserKeyForMDC(requestData
						.getString(MDCInterceptor.USER_KEY));
			}
		}
		this.cip = getIpAddr(httpServletRequest);
	}

	public String getCip() {
		if (StringUtils.isBlank(cip)) {
			cip = "127.0.0.1";
		}
		return cip;
	}

	public GsonObject getRequestData() {
		return requestData;
	}

	public RequestParam getRequestParam() {
		return requestParam;
	}

	/**
	 * 获取客户端的ip
	 */
	private String getIpAddr(HttpServletRequest httpServletRequest) {
		// 针对　nginx获取ip
		String ip = httpServletRequest.getHeader("X-Real-IP");
		// 针对　一般tomcat获取ip
		if ((ip == null) || (ip.length() == 0)
				|| "unknown".equalsIgnoreCase(ip)) {
			ip = httpServletRequest.getRemoteAddr();
		}
		if ((ip == null) || (ip.length() == 0)
				|| "unknown".equalsIgnoreCase(ip)) {
			ip = httpServletRequest.getRemoteHost();
		}
		return ip;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("requestParam:");
		builder.append(requestParam);
		if (requestData != null) {
			builder.append("\nrequestData:");
			builder.append(requestData);
		}
		return builder.toString();
	}
}