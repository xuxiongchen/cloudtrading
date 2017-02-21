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

	String url = "http://localhost:8080/cloudtrading-collection";

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
	public void test_addWarehouse() throws IOException, Exception {
		String requestUrl = url + "/analysis/testAddWarehouse";
		String[] key={"user_id",
				"DIRECTION",
				"CREATEPOSITION",
				"DESCRIPTION"
				};
		Object[] value={52, 
				1,
				222,
				"这是一个测试"};
		logger.info("test_Addwarehouse:" + test_Manager(requestUrl,key,value));
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
	public void test_countSilverAlloy() throws IOException, Exception {
		
		String requestUrl = url + "/analysis/silverAlloy";
		String[] key={"month","day"};
//		for(int i=28;i<=30;i++){
//			Object[] value={11,i};
//			logger.info("test:"+"11月"+i+"日" + test_Manager(requestUrl,key,value));
//		}
//		for(int i=1;i<=8;i++){
			Object[] value={12,9};
			logger.info("test:"+"12月"+9+"日"  + test_Manager(requestUrl,key,value));
//		}
//		Object[] value={11,28};
//		logger.info("test:"+"12月"+7+"日" + test_Manager(requestUrl,key,value));
	}
	@Test
	public void test_countSilverAlloyInvest() throws IOException, Exception {
		String requestUrl = url + "/analysis/countSilverAlloy";
		String[] key={"month","day"};
		for(int i=28;i<=30;i++){
			Object[] value={11,i};
			logger.info("test:"+"11月"+i+"日" + test_Manager(requestUrl,key,value));
		}
		for(int i=1;i<=8;i++){
			Object[] value={12,i};
			logger.info("test:"+"12月"+i+"日"  + test_Manager(requestUrl,key,value));
		}
	}

	@Test
	public void test_countGoldCopper() throws IOException, Exception {
		String requestUrl = url + "/analysis/countGoldCopper";
		String[] key={"month","day"};
//		for(int i=28;i<=30;i++){
//			Object[] value={11,i};
//			logger.info("test银基合金:"+"11月"+i+"日" + test_Manager(requestUrl,key,value));
//		}
//		for(int i=11;i<=20;i++){
			Object[] value={12,15};
			logger.info("test银基合金:"+"12月"+15+"日"  + test_Manager(requestUrl,key,value));
//		}
//		Object[] value={11,28};
//		logger.info("testt:" + test_Manager(requestUrl,key,value));
	}
	@Test
	public void test_countCorundum() throws IOException, Exception {
		String requestUrl = url + "/analysis/countCorundum";
		String[] key={"month","day"};
//		for(int i=28;i<=30;i++){
//			Object[] value={11,i};
//			logger.info("test刚玉:"+"11月"+i+"日" + test_Manager(requestUrl,key,value));
//		}
//		for(int i=1;i<=8;i++){
			Object[] value={12,9};
			logger.info("test刚玉:"+"12月"+9+"日"  + test_Manager(requestUrl,key,value));
//		}
//		Object[] value={11,28};
//		logger.info("testt:" + test_Manager(requestUrl,key,value));
		//logger.info("testt:" + test_Manager(requestUrl,key,value));
	}
}
