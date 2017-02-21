package com.cloudtrading.collection.service.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huisa.common.http.HttpClientUtil;
import com.huisa.common.http.model.FormData;
import com.huisa.common.http.model.HttpResult;
import com.huisa.common.http.model.PostData;

@Service
public class MailService  implements InitializingBean{
	
	@Autowired
	private SysconfigService sysconfigService;
	
	
	private static Logger LOGGER = LoggerFactory.getLogger(MailService.class);
	private static String url = "https://api.submail.cn/mail/send.json";
	private static String appid = "10710";
	private static String signature = "bd40c38743e1b8a4f0406b24f79998d9";
	private static String from = "notice@mintour.com";
	private static String validateCodeMailSubject = "Mintour验证码";
	private static String validateCodeMailFormat = "欢迎使用Mintour，您的验证码是：%s。请不要把验证码泄露给其他人。";
	
	private static String comfirmMailValidateCodeSubject = "Mintour导购账号激活";
	
	private static String emailValidatehtml;
	private static String INTERFACE_DOMAIN;
	public void afterPropertiesSet() throws Exception {
		INTERFACE_DOMAIN=sysconfigService.getValue("INTERFACE_DOMAIN");
		emailValidatehtml=sysconfigService.getValue("EMAIL_VALIDATE_HTML");
		
	}
	public  void sendValidateCode(String email, String validateCode) {
		FormData formData = new FormData();
		formData.addParam("appid", appid);
		formData.addParam("to", email);
		formData.addParam("from", from);
		formData.addParam("from_name", "Mintour");
		formData.addParam("subject", validateCodeMailSubject);
		formData.addParam("text",
				String.format(validateCodeMailFormat, validateCode));
		formData.addParam("signature", signature);
		PostData postData = new PostData(formData);
		try {
			HttpResult httpResult = HttpClientUtil.post(url, postData);
			LOGGER.info(httpResult.getStringData());
		} catch (Exception e) {
		}
	}
	public String formHTML(long t,String k){

		
		String appUrl=INTERFACE_DOMAIN+"web/user/confirm_email?t="+t+"&k="+k;
        return String.format(emailValidatehtml, appUrl,appUrl);
	}
	
    public  void sendHtmlValidateCode(long t, String email, String emailValidateCode ){
    	String html=formHTML(t,emailValidateCode);
		FormData formData = new FormData();
		formData.addParam("appid", appid);
		formData.addParam("to", email);
		formData.addParam("from", from);
		formData.addParam("from_name", "Mintour");
		formData.addParam("subject", comfirmMailValidateCodeSubject);
		formData.addParam("html",html);
		formData.addParam("signature", signature);
		PostData postData = new PostData(formData);
		try {
			HttpResult httpResult = HttpClientUtil.post(url, postData);
			LOGGER.info(httpResult.getStringData());
		} catch (Exception e) {
		}
    }

}
