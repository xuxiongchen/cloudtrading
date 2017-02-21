package com.cloudtrading.collection.web.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cloudtrading.collection.service.AnalysisService;
import com.cloudtrading.collection.service.CatchDataService;
import com.cloudtrading.collection.utils.GsonObject;
import com.cloudtrading.collection.web.common.MVCUtil;
import com.cloudtrading.collection.web.protocol.parser.BasicResultPaser;
import com.cloudtrading.collection.web.protocol.parser.JsonUnitData;
import com.cloudtrading.warehouse.entity.Result;
import com.cloudtrading.warehouse.entity.Warehouse;
import com.cloudtrading.warehouse.entity.WarehouseAndLastResult;
import com.cloudtrading.warehouse.utils.DateFormatUtil;

@Controller
@RequestMapping(value = "/analysis")
public class AnalysisController {

	@Autowired
	AnalysisService analysisService;
	@Autowired
	CatchDataService catchDataService;
	
	@RequestMapping(value = "/testAddWarehouse")
	@ResponseBody
	public void addWarehouse(HttpServletRequest request) throws Exception {
		GsonObject requestData = MVCUtil.getRequestData();
		int DIRECTION =requestData.getInt("DIRECTION", 0); //1涨 2跌
		int CREATEPOSITION = requestData.getInt("CREATEPOSITION", 0);//建仓时的大小
		String DESCRIPTION=requestData.getString("DESCRIPTION");
		java.lang.Long OLDTIME=System.currentTimeMillis();
		Warehouse warehouse=new Warehouse();
		warehouse.setDIRECTION(DIRECTION);
		warehouse.setCREATEPOSITION(CREATEPOSITION);
		warehouse.setDESCRIPTION(DESCRIPTION);
		WarehouseAndLastResult warehouseAndLastResult=new WarehouseAndLastResult();
		warehouseAndLastResult.setWarehouse(warehouse);
		Result result=new Result();
		result.setCREATE_TIME(new Date());
		result.setCREATE_POSITION(1111);
		result.setDESCRIPTION("这是一个测试");
		warehouseAndLastResult.setLastResult(result);
		catchDataService.addWarehouseAndLastResult(warehouseAndLastResult);
		
		JsonUnitData JSONresult = new BasicResultPaser();
		MVCUtil.returnData(JSONresult.getResponseData());
	}
	
	@RequestMapping(value = "/test")
	@ResponseBody
	public void getTest(HttpServletRequest request) throws Exception {
		System.out.println(request.getParameterMap());
		GsonObject requestData = MVCUtil.getRequestData();
		int sysType = requestData.getInt("sys_type", 0);
		int pVersion = requestData.getInt("p_version", 0);
		int userId = requestData.getInt("user_id",0);
		String sessionid = requestData.getString("session_id");
		
		System.out.println(userId);
		JsonUnitData result = new BasicResultPaser();
		MVCUtil.returnData(result.getResponseData());
	}
	@RequestMapping(value = "/countSilverAlloy")
	@ResponseBody
	public void getCount(String sql) throws Exception {
		GsonObject requestData = MVCUtil.getRequestData();
		int month=requestData.getInt("month", 0);
		int day=requestData.getInt("day", 0);
		Long time9=DateFormatUtil.getTime(2016,month,day,9,0,0);
		Long time430=DateFormatUtil.getTime(2016,month,day+1,4,30,0);
		JsonUnitData response=analysisService.analysis(time9,time430);
		MVCUtil.returnData(response.getResponseData());
	}
	@RequestMapping(value = "/silverAlloy")
	@ResponseBody
	public void getCountSilverAlloy(String sql) throws Exception {
		GsonObject requestData = MVCUtil.getRequestData();
		int month=requestData.getInt("month", 0);
		int day=requestData.getInt("day", 0);
		Long time9=DateFormatUtil.getTime(2016,month,day,9,0,0);
		Long time430=DateFormatUtil.getTime(2016,month,day+1,4,30,0);
		JsonUnitData response=analysisService.analysisSilveralloy12_8(time9,time430);
		MVCUtil.returnData(response.getResponseData());
	}
	@RequestMapping(value = "/countGoldCopper")
	@ResponseBody
	public void getCountGoldCopper() throws Exception {
		GsonObject requestData = MVCUtil.getRequestData();
		int month=requestData.getInt("month", 0);
		int day=requestData.getInt("day", 0);
		Long time9=DateFormatUtil.getTime(2016,month,day,9,0,0);
		Long time430=DateFormatUtil.getTime(2016,month,day+1,4,30,0);
		JsonUnitData response=analysisService.analysisGoldCopper12_8(time9,time430);
		MVCUtil.returnData(response.getResponseData());
	}
	@RequestMapping(value = "/countCorundum")
	@ResponseBody
	public void getCountCorundum() throws Exception {
		GsonObject requestData = MVCUtil.getRequestData();
		int month=requestData.getInt("month", 0);
		int day=requestData.getInt("day", 0);
		Long time9=DateFormatUtil.getTime(2016,month,day,9,0,0);
		Long time430=DateFormatUtil.getTime(2016,month,day+1,4,30,0);
		JsonUnitData response=analysisService.analysisCorundum12_8(time9,time430);
		MVCUtil.returnData(response.getResponseData());
	}
}
