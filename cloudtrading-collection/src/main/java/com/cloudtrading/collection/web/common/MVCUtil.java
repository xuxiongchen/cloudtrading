package com.cloudtrading.collection.web.common;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cloudtrading.collection.common.AppContext;
import com.cloudtrading.collection.utils.GsonObject;
import com.cloudtrading.collection.web.interceptor.MDCInterceptor;
import com.cloudtrading.collection.web.protocol.CommonRequest;
import com.cloudtrading.collection.web.protocol.RequestParam;
import com.cloudtrading.warehouse.common.ErrorCode;
import com.huisa.common.exception.Errorcode;
import com.huisa.common.exception.ServiceException;

/**
 * 请求数据和返回结果的管理工具类
 * 
 * @author moyuhui
 * 
 */
@Component
public class MVCUtil {
	private static final Logger logger = LoggerFactory.getLogger(MVCUtil.class);
	private static final ThreadLocal<HttpServletRequest> currentRequest = new ThreadLocal<HttpServletRequest>();
	private static final ThreadLocal<HttpServletResponse> currentResponse = new ThreadLocal<HttpServletResponse>();
	private static final ThreadLocal<CommonRequest> currentCommonRequest = new ThreadLocal<CommonRequest>();

	public static void init(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		currentRequest.set(request);
		currentResponse.set(response);
		readCommonFormData();
	}

	public static void destroy() {
		currentRequest.remove();
		currentResponse.remove();
		currentCommonRequest.remove();
	}

	private static void readCommonFormData() throws Exception {
		HttpServletRequest request = currentRequest.get();
		request.setCharacterEncoding("UTF-8");

		// 获取请求中的数据
		RequestParam requestParam = new RequestParam(request.getParameterMap());
		// setUserKeyForMDC
		if (StringUtils.isNotBlank(requestParam
				.getParam(MDCInterceptor.USER_KEY))) {
			MDCInterceptor.setUserKeyForMDC(requestParam
					.getParam(MDCInterceptor.USER_KEY));
		}
		// 获取post的json数据
		String data = IOUtils.toString(request.getInputStream(), "UTF-8");

		/* 解析requestdata为request对象 */
		CommonRequest commonRequest = new CommonRequest(request, data,
				requestParam);
		// 打印请求数据日志
//		if (AppContext.logswitch
//				&& !request.getRequestURI().contains("upload_image")) {
//			logger.info("==recvlog:" + commonRequest.toString());
//		}
		currentCommonRequest.set(commonRequest);

		if (!AppContext.isstarted) {
			throw new ServiceException(Errorcode.CODE_JAVA,
					"server is not ready!");
		}
	}

	public static CommonRequest getCommonRequest() {
		CommonRequest commonRequest = currentCommonRequest.get();
		return commonRequest;
	}

	public static GsonObject getRequestData() throws ServiceException {
		GsonObject gsonObject = null;
		CommonRequest commonRequest = getCommonRequest();
		if (commonRequest != null) {
			gsonObject = commonRequest.getRequestData();
		}
		if (gsonObject == null) {
			throw new ServiceException(ErrorCode.CODE_PARAM,
					"post的数据不是json格式的字符串!");
		}
		return gsonObject;
	}

	public static void returnData(String reponseData) {
		if (reponseData == null) {
			reponseData = "";
		}

		PrintWriter printWriter = null;
		try {
			HttpServletResponse response = currentResponse.get();
			response.setHeader("Pragma", "No-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("application/json;charset=UTF-8");
			printWriter = response.getWriter();
			printWriter.write(reponseData);
		} catch (Exception e) {
			logger.error("MVCUtil.returnJsonData faild!", e);
		} finally {
			// if (printWriter != null) {
			// printWriter.flush();
			// printWriter.close();
			// }
		}

		// 打印返回数据日志
		if (AppContext.logswitch) {
			HttpServletRequest request = currentRequest.get();
			if (!request.getRequestURI().contains("upload_image")) {
				logger.info("==submitlog:" + getCommonRequest().toString()
						+ "==response:" + reponseData.toString());
			}
		}
	}

	public static String getParam(String name) {
		Map<String, String[]> params = currentRequest.get().getParameterMap();
		if ((params != null) && (params.get(name) != null)) {
			return params.get(name)[0];
		}
		return null;
	}

	public static String[] getParamArray(String name) {
		Map<String, String[]> params = currentRequest.get().getParameterMap();
		if ((params != null) && (params.get(name) != null)) {
			return params.get(name);
		}
		return null;
	}

	public static int getIntParam(String name) {
		Map<String, String[]> params = currentRequest.get().getParameterMap();
		if ((params != null) && (params.get(name) != null)) {
			if (StringUtils.isNumeric(params.get(name)[0])) {
				return Integer.parseInt(params.get(name)[0]);
			}
		}
		return 0;
	}

	public static int[] getIntParamArray(String name) {
		int[] intparams = new int[0];
		String[] params = getParamArray(name);
		if ((params != null) && (params.length > 0)) {
			intparams = new int[params.length];
			for (int i = 0; i < params.length; i++) {
				intparams[i] = Integer.parseInt(params[i]);
			}
		}
		return intparams;
	}

	public static long getLongParam(String name) {
		Map<String, String[]> params = currentRequest.get().getParameterMap();
		if ((params != null) && (params.get(name) != null)) {
			if (StringUtils.isNumeric(params.get(name)[0])) {
				return Long.parseLong(params.get(name)[0]);
			}
		}
		return 0;
	}

	public static long[] getLongParamArray(String name) {
		long[] longparams = new long[0];
		String[] params = getParamArray(name);
		if ((params != null) && (params.length > 0)) {
			longparams = new long[params.length];
			for (int i = 0; i < params.length; i++) {
				longparams[i] = Long.parseLong(params[i]);
			}
		}
		return longparams;
	}

	/**
	 * 获取请求头的特殊的参数值
	 * 
	 * @param name
	 * @return
	 */
	public static Long getRequestDataHead(String name) {
		Long result = currentRequest.get().getDateHeader(name);
		return result;
	}


	public static void setCurrentSession(HttpServletRequest request,
			HttpServletResponse response) {
		currentRequest.set(request);
		currentResponse.set(response);
	}

	public static void removeCurrentSession() {
		currentRequest.remove();
		currentResponse.remove();
	}

}
