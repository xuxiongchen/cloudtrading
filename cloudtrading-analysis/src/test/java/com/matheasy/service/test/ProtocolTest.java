package com.matheasy.service.test;

import java.io.File;
import java.io.IOException;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.huisa.common.http.HttpClientUtil;
import com.huisa.common.http.model.FormData;
import com.huisa.common.http.model.HttpResult;
import com.huisa.common.http.model.PostData;
import com.sun.jna.Native;

public class ProtocolTest {
	private Logger logger = LoggerFactory.getLogger(ProtocolTest.class);

	String url = "http://localhost:8080/cloudtrading";

	JsonObject  test_Manager(String url,String[] key,Object[] value) throws IOException, Exception{
		String requestUrl = url;
		JsonObject requestjson = new JsonObject();
		for(int i=0;i<key.length;i++){
			if(value[i] instanceof String)
				requestjson.addProperty(key[i], (String)value[i]);
			else if(value[i] instanceof Integer)
			requestjson.addProperty(key[i], (Integer)value[i]);
		}
		PostData postData = new PostData(requestjson.toString());
		HttpResult httpResult = HttpClientUtil.post(requestUrl, postData);
		String jsonStr = httpResult.getStringData();
		JsonObject responsedata = new JsonParser().parse(jsonStr)
				.getAsJsonObject();
		return responsedata;
	}
	
	@Test
	public void test_test() throws IOException, Exception {
		String requestUrl = url + "/analysis/test";
		String[] key={"user_id",
				"sql",
				"class_id",
				"start_date",
				"count_limit",
				"homework_state"};
		Object[] value={52, 
				"3ff1f8f4e2fd4d748969c586ccedfa3c",
				185,
				"2016-04-27",
				10,
				0};
		logger.info("testt:" + test_Manager(requestUrl,key,value));
	}

	@Test
	public void test_count() throws IOException, Exception {
		String requestUrl = url + "/count";
		JsonObject requestjson = new JsonObject();
		requestjson.addProperty("sql", "select * from ct_silveralloy_data where ID>335443 AND ID<361679");
		PostData postData = new PostData(requestjson.toString());
		HttpResult httpResult = HttpClientUtil.post(requestUrl, postData);
		String jsonStr = httpResult.getStringData();
		JsonObject responsedata = new JsonParser().parse(jsonStr)
				.getAsJsonObject();
		logger.info("test_count:" + responsedata);
		
	}

	@Test
	public void test_count2() throws IOException, Exception {
		String requestUrl = url + "/countGoldCopper";
		JsonObject requestjson = new JsonObject();
		PostData postData = new PostData(requestjson.toString());
		HttpResult httpResult = HttpClientUtil.post(requestUrl, postData);
		String jsonStr = httpResult.getStringData();
		JsonObject responsedata = new JsonParser().parse(jsonStr)
				.getAsJsonObject();
		logger.info("test_count:" + responsedata);
		
	}
	@Test
	public void test_count3() throws IOException, Exception {
		String requestUrl = url + "/countCorundum";
		JsonObject requestjson = new JsonObject();
		PostData postData = new PostData(requestjson.toString());
		HttpResult httpResult = HttpClientUtil.post(requestUrl, postData);
		String jsonStr = httpResult.getStringData();
		JsonObject responsedata = new JsonParser().parse(jsonStr)
				.getAsJsonObject();
		logger.info("test_count:" + responsedata);
		
	}
}
