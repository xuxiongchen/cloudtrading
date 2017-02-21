package com.cloudtrading.collection.service;

import java.awt.AWTException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudtrading.analysis.utils.GoldCopperAnalyse;
import com.cloudtrading.analysis.utils.Invest;
import com.cloudtrading.collection.dao.GoldCopperDataDao;
import com.cloudtrading.collection.dao.WarehouseAndResultDao;
import com.cloudtrading.collection.entity.CorundumData;
import com.cloudtrading.collection.entity.GoldCopperData;
import com.cloudtrading.collection.entity.SilverAlloyData;
import com.cloudtrading.pub.Position;
import com.cloudtrading.pub.Production;
import com.cloudtrading.pub.Production.Period;
import com.cloudtrading.warehouse.entity.Result;
import com.cloudtrading.warehouse.entity.Warehouse;
import com.cloudtrading.warehouse.entity.WarehouseAndLastResult;
import com.cloudtrading.warehouse.model.Corundum;
import com.cloudtrading.warehouse.model.GoldCopper;
import com.cloudtrading.warehouse.model.SilverAlloy;
import com.cloudtrading.warehouse.utils.TessreactUtil;
import com.cloudtrading.warehouse.utils.WindowsController;
import com.huisa.common.exception.ServiceException;


/**
 * 抓取数据处理SERVICE
 * @author chenxu
 */
@Service
public class CatchDataService {
	private static final Logger logger = LoggerFactory.getLogger(CatchDataService.class);
	@Autowired
	private CorundumDataService corundumDataService;
	@Autowired
	private GoldCopperDataService goldCopperDataService;
	@Autowired
	private SilverAlloyDataService silverAlloyDataService;
	@Autowired
	private WarehouseAndResultDao warehouseAndResultDao;
//	private static int silverAlloy_failureToCreateTimes=5;
//	private static int silverAlloy_destination=30;
//	private static int silverAlloy_ifFailureToCreateTimes=2;
//	private static final Invest silverAlloy_invest=new Invest(3,silverAlloy_failureToCreateTimes, silverAlloy_destination,silverAlloy_ifFailureToCreateTimes);
	public SilverAlloyData catchSilverAlloyData() throws IOException, ServiceException, AWTException{
		long start = System.currentTimeMillis();
		int value = TessreactUtil.catchNumber(SilverAlloy.position, SilverAlloy.digit,SilverAlloy.x, SilverAlloy.y, SilverAlloy.width, SilverAlloy.higth);
		long end=System.currentTimeMillis();
		Date date = new Date();
		SilverAlloyData baseData=new SilverAlloyData();
		baseData.setVALUE(value);
		baseData.setCREATE_TIME(date);
		baseData.setTIME(start);
		baseData.setTIME_ERROR(end-start);
		baseData=silverAlloyDataService.saveBaseData(baseData);
//		silverAlloy_invest.setAllowDataLoseTime(40);
//		WarehouseAndLastResult warehouseAndLastResult=silverAlloy_invest.readPosition(value,start, date);
//		addWarehouseAndLastResult(warehouseAndLastResult);
		//System.out.println(baseData);
		return baseData;
	}
	
	
//	private static int corundum_failureToCreateTimes=4;
//	private static int corundum_destination=5;
//	private static int corundum_ifFailureToCreateTimes=2;
//	private static final Invest corundum_invest=new Invest(1,corundum_failureToCreateTimes, corundum_destination,corundum_ifFailureToCreateTimes);
	public CorundumData catchCorundumData() throws IOException, ServiceException, AWTException{
		long start = System.currentTimeMillis();
		int value = TessreactUtil.catchNumber(Corundum.position, Corundum.digit,Corundum.x, Corundum.y, Corundum.width, Corundum.higth);
		long end=System.currentTimeMillis();
		CorundumData baseData=new CorundumData();
		baseData.setVALUE(value);
		Date date=new Date();
		baseData.setCREATE_TIME(date);
		baseData.setTIME(start);
		baseData.setTIME_ERROR(end-start);
		baseData=corundumDataService.saveBaseData(baseData);
//		corundum_invest.setAllowDataLoseTime(40);
//		WarehouseAndLastResult warehouseAndLastResult=corundum_invest.readPosition(value,start, date);
//		addWarehouseAndLastResult(warehouseAndLastResult);
		//System.out.println(baseData);
		return baseData;
	}
	
	private static GoldCopperAnalyse goldcopperAnalyse=null;
	static{
			Map<Period, Integer> period=new HashMap<Production.Period, Integer>();
			period.put(Period.SHORT_PERIOD, GoldCopper.stopPoint1);
			period.put(Period.MIDDLE_PERIOD, GoldCopper.stopPoint2);
			period.put(Period.LONG_PERIOD, GoldCopper.stopPoint3);
			int marginalValue=6;  
			Production production=new Production("中金云", "银基合金", 2, period);
			goldcopperAnalyse=new GoldCopperAnalyse(production,marginalValue,GoldCopper.stopPoint1);
	}
//	private static int goldCopper_failureToCreateTimes=4;
//	private static int goldCopper_destination=4;
//	private static int goldCopper_ifFailureToCreateTimes=2;
//	private static final Invest goldCopper_invest=new Invest(2,goldCopper_failureToCreateTimes, goldCopper_destination,goldCopper_ifFailureToCreateTimes);
//	
	
	public GoldCopperData catchGoldCopperData() throws IOException, ServiceException, AWTException{
		long start = System.currentTimeMillis();
		int value = TessreactUtil.catchNumber(GoldCopper.position, GoldCopper.digit,GoldCopper.x, GoldCopper.y, GoldCopper.width, GoldCopper.higth);
		long end=System.currentTimeMillis();
		GoldCopperData baseData=new GoldCopperData();
		Date date=new Date();
		baseData.setVALUE(value);
		baseData.setCREATE_TIME(date);
		baseData.setTIME(start);
		baseData.setTIME_ERROR(end-start);
		baseData=goldCopperDataService.saveBaseData(baseData);
		goldcopperAnalyse.readPosition(new Position(value, start));
		
		//System.out.println(baseData);
		return baseData;
	}
	public void addWarehouseAndLastResult(WarehouseAndLastResult warehouseAndLastResult) throws ServiceException{
		Result result=warehouseAndLastResult.getLastResult();
		Warehouse warehouse=warehouseAndLastResult.getWarehouse();
		if(result!=null){
			warehouseAndResultDao.addReturnGeneratedKey(result);
		}
		if(warehouse!=null){
			logger.info(warehouse.getDESCRIPTION());
			warehouseAndResultDao.addReturnGeneratedKey(warehouse);
		}
	}
	
	public void resetCCWindow(){
		try {
			WindowsController.reOpenCCWindow();
			new Thread(new Runnable() {
				@Override
				public void run() {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						//goldcopperAnalyse.reReadyForCreate();
				}
			});
		} catch (AWTException e) {
			logger.error("重新打开窗口失败");
		}
	}
	
public static void main(String[] args) throws IOException, ServiceException {
	long start = System.currentTimeMillis();
	int value = TessreactUtil.catchNumber(Corundum.position, Corundum.digit,Corundum.x, Corundum.y, Corundum.width, Corundum.higth);
	long end=System.currentTimeMillis();
	CorundumData baseData=new CorundumData();
	baseData.setVALUE(value);
	baseData.setCREATE_TIME(new Date());
	baseData.setTIME(start);
	baseData.setTIME_ERROR(end-start);
	System.out.println(baseData);
}
}
