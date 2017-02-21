package com.cloudtrading.collection.service.common;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huisa.common.http.HttpClientUtil;
import com.huisa.common.http.model.FormData;
import com.huisa.common.http.model.HttpResult;
import com.huisa.common.http.model.PostData;

public class SmsService {
	private static Logger LOGGER = LoggerFactory.getLogger(SmsService.class);
	private static String url = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";
	private static String format = "您的验证码是：%s。请不要把验证码泄露给其他人。";

	public static void sendValidateCode(String phoneNum, String validateCode) {
		FormData formData = new FormData();
		formData.addParam("account", "cf_mintour");
		formData.addParam("password", "invoker0519");
		formData.addParam("mobile", phoneNum);
		formData.addParam("content", String.format(format, validateCode));
		PostData postData = new PostData(formData);
		try {
			HttpResult httpResult = HttpClientUtil.post(url, postData);
			// LOGGER.info(httpResult.getStringData());
		} catch (IOException e) {
		} catch (Exception e) {
		}
	}

	public static void main(String[] args) {
		sendValidateCode("18665587613", "222233");
	}
}
