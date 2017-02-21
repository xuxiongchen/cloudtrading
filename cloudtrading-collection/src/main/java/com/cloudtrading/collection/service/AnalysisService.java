package com.cloudtrading.collection.service;

import java.awt.AWTException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudtrading.analysis.utils.Invest;
import com.cloudtrading.analysis.utils.SilveralloyAnalyse;
import com.cloudtrading.collection.dao.AnalysisDao;
import com.cloudtrading.collection.entity.CorundumData;
import com.cloudtrading.collection.entity.GoldCopperData;
import com.cloudtrading.collection.entity.SilverAlloyData;
import com.cloudtrading.collection.utils.FileUtil;
import com.cloudtrading.collection.web.protocol.parser.BasicResultPaser;
import com.cloudtrading.collection.web.protocol.parser.JsonUnitData;
import com.cloudtrading.pub.Position;
import com.cloudtrading.pub.Production;
import com.cloudtrading.pub.Production.Period;
import com.cloudtrading.warehouse.entity.Result;
import com.cloudtrading.warehouse.entity.Warehouse;
import com.cloudtrading.warehouse.entity.WarehouseAndLastResult;
import com.cloudtrading.warehouse.model.Corundum;
import com.cloudtrading.warehouse.model.GoldCopper;
import com.cloudtrading.warehouse.model.SilverAlloy;
import com.cloudtrading.warehouse.utils.DateFormatUtil;
import com.huisa.common.exception.ServiceException;

@Service
public class AnalysisService {

	@Autowired
	private AnalysisDao analysisDao;
	public JsonUnitData analysisSilveralloy12_8(Long time9,Long time430) throws ServiceException, IOException, AWTException{
		StringBuffer sql=new StringBuffer();
		List<Object> queryParams = new ArrayList<Object>();
		
		sql.append("select * from ct_silveralloy_data where 1=1 AND ");
		sql.append(" TIME>? ");
		queryParams.add(time9);
		sql.append(" AND TIME<? ");
		queryParams.add(time430);
		List<SilverAlloyData> list=analysisDao.countSilverAlloy(sql.toString(),queryParams);
		Map<Period, Integer> period=new HashMap<Production.Period, Integer>();
		period.put(Period.SHORT_PERIOD, SilverAlloy.stopPoint1);
		period.put(Period.MIDDLE_PERIOD, SilverAlloy.stopPoint2);
		period.put(Period.LONG_PERIOD, SilverAlloy.stopPoint3);
		Production production=new Production("中金云", "中金铜", 3, period);
		int marginalValue=9;
		SilveralloyAnalyse silveralloyAnalyse=
				new SilveralloyAnalyse(production,marginalValue,SilverAlloy.stopPoint1);
		//alllist.addAll(list);
		for(SilverAlloyData value:list){
			silveralloyAnalyse.readPosition(new Position(value.getVALUE(),value.getTIME()));
		}
		String result=silveralloyAnalyse.printCalculateResult();
		System.out.println(result);
		String dateString=DateFormatUtil.formatDate(new Date(time9));
		String timeString=DateFormatUtil.formatDate(new Date()).substring(0,10);
		FileUtil.TextToFile("F:/invest/中金铜"+timeString+"/"+dateString+".txt",result);
		//FileUtil.TextToFile("F:/invest/中金铜建仓"+dateString+"-"+failureToCreateTimes+"-"+destination+"-"+ifFailureToCreateTimes+".txt",sbBuffer2.toString());
		//Count.count(list);
		return new BasicResultPaser();
	}
	
	public JsonUnitData analysisGoldCopper12_8(Long time9,Long time430) throws ServiceException, IOException, AWTException{
		StringBuffer sql=new StringBuffer();
		List<Object> queryParams = new ArrayList<Object>();
		
		sql.append("select * from ct_goldcopper_data where 1=1 AND ");
		sql.append(" TIME>? ");
		queryParams.add(time9);
		sql.append(" AND TIME<? ");
		queryParams.add(time430);
		List<GoldCopperData> list=analysisDao.countGoldCopper(sql.toString(),queryParams);
		Map<Period, Integer> period=new HashMap<Production.Period, Integer>();
		period.put(Period.SHORT_PERIOD, GoldCopper.stopPoint1);
		period.put(Period.MIDDLE_PERIOD, GoldCopper.stopPoint2);
		period.put(Period.LONG_PERIOD, GoldCopper.stopPoint3);
		Production production=new Production("中金云", "银基合金", 2, period);
		int marginalValue=6;  //3时 概率大于0.6 有0.8 1.0 情况，但一日成交量小于4
		SilveralloyAnalyse goldcopperAnalyse=
				new SilveralloyAnalyse(production,marginalValue,GoldCopper.stopPoint1);
		//alllist.addAll(list);
		for(GoldCopperData value:list){
			goldcopperAnalyse.readPosition(new Position(value.getVALUE(),value.getTIME()));
		}
		String result=goldcopperAnalyse.printCalculateResult();
		String dateString=DateFormatUtil.formatDate(new Date(time9));
		String timeString=DateFormatUtil.formatDate(new Date());
		FileUtil.TextToFile("F:/invest/银基合金"+timeString.substring(0,10)+"/"+dateString+".txt",result);
		//FileUtil.TextToFile("F:/invest/中金铜建仓"+dateString+"-"+failureToCreateTimes+"-"+destination+"-"+ifFailureToCreateTimes+".txt",sbBuffer2.toString());
		//Count.count(list);
		return new BasicResultPaser();
	}
	public JsonUnitData analysisCorundum12_8(Long time9,Long time430) throws ServiceException, IOException, AWTException{
		StringBuffer sql=new StringBuffer();
		List<Object> queryParams = new ArrayList<Object>();
		
		sql.append("select * from ct_corundum_data where 1=1 AND ");
		sql.append(" TIME>? ");
		queryParams.add(time9);
		sql.append(" AND TIME<? ");
		queryParams.add(time430);
		List<CorundumData> list=analysisDao.countCorundumData(sql.toString(),queryParams);
		Map<Period, Integer> period=new HashMap<Production.Period, Integer>();
		period.put(Period.SHORT_PERIOD, Corundum.stopPoint1);
		period.put(Period.MIDDLE_PERIOD, Corundum.stopPoint2);
		period.put(Period.LONG_PERIOD, Corundum.stopPoint3);
		Production production=new Production("中金云", "刚玉", 1, period);
		int marginalValue=8;
		SilveralloyAnalyse corundumAnalyse=
				new SilveralloyAnalyse(production,marginalValue,Corundum.stopPoint1);
		//alllist.addAll(list);
		for(CorundumData value:list){
			corundumAnalyse.readPosition(new Position(value.getVALUE(),value.getTIME()));
		}
		String result=corundumAnalyse.printCalculateResult();
		String dateString=DateFormatUtil.formatDate(new Date(time9));
		String timeString=DateFormatUtil.formatDate(new Date());
		FileUtil.TextToFile("F:/invest/刚玉"+timeString.substring(0,10)+"/"+dateString+".txt",result);
		//FileUtil.TextToFile("F:/invest/中金铜建仓"+dateString+"-"+failureToCreateTimes+"-"+destination+"-"+ifFailureToCreateTimes+".txt",sbBuffer2.toString());
		//Count.count(list);
		return new BasicResultPaser();
	}
	
	
	
	
	
	public JsonUnitData analysis(Long time9,Long time430) throws ServiceException, IOException, AWTException{
		StringBuffer sql=new StringBuffer();
		List<Object> queryParams = new ArrayList<Object>();
		
		sql.append("select * from ct_silveralloy_data where 1=1 AND ");
		sql.append(" TIME>? ");
		queryParams.add(time9);
		sql.append(" AND TIME<? ");
		queryParams.add(time430);
		List<SilverAlloyData> list=analysisDao.countSilverAlloy(sql.toString(),queryParams);
		
		StringBuffer sbBuffer1=new StringBuffer();
		StringBuffer sbBuffer2=new StringBuffer();
		
		int failureToCreateTimes=5;
		int destination=30;
		int ifFailureToCreateTimes=2;
		final Invest invest=new Invest(3,failureToCreateTimes, destination,ifFailureToCreateTimes);
		invest.setAllowDataLoseTime(40);
		for(SilverAlloyData value:list){
			WarehouseAndLastResult warehouseAndLastResult=invest.readPosition(value.getVALUE(),value.getTIME(), value.getCREATE_TIME());
			Result result=warehouseAndLastResult.getLastResult();
			Warehouse warehouse=warehouseAndLastResult.getWarehouse();
			if(result!=null){
				sbBuffer1.append(result.toString()+"\n");
			}
			if(warehouse!=null){
				sbBuffer2.append(warehouse.getDESCRIPTION().toString()+"\n");
			}
		}
		String dateString=DateFormatUtil.formatDate(new Date(time9));
		String timeString=DateFormatUtil.formatDate(new Date()).substring(0,10);
		//FileUtil.TextToFile("F:/invest/中金铜结果"+dateString+"-"+failureToCreateTimes+"-"+destination+"-"+ifFailureToCreateTimes+".txt",sbBuffer1.toString());
		FileUtil.TextToFile("F:/invest/中金铜"+timeString+"/"+dateString+"-"+failureToCreateTimes+"-"+destination+"-"+ifFailureToCreateTimes+".txt",sbBuffer2.toString());
		//Count.count(list);
		return new BasicResultPaser();
	}
	public JsonUnitData analysisGoldCopper(Long time9,Long time430) throws ServiceException, IOException, AWTException{
		StringBuffer sql=new StringBuffer();
		List<Object> queryParams = new ArrayList<Object>();
		
		sql.append("select * from ct_goldcopper_data where 1=1 AND ");
		sql.append(" TIME>? ");
		queryParams.add(time9);
		sql.append(" AND TIME<? ");
		queryParams.add(time430);
		List<GoldCopperData> list=analysisDao.countGoldCopper(sql.toString(),queryParams);
		
		
		StringBuffer sbBuffer1=new StringBuffer();
		StringBuffer sbBuffer2=new StringBuffer();
		
		int failureToCreateTimes=4;
		int destination=4;
		int ifFailureToCreateTimes=2;
		final Invest invest=new Invest(2,failureToCreateTimes, destination,ifFailureToCreateTimes);
		invest.setAllowDataLoseTime(40);
		for(GoldCopperData value:list){
			WarehouseAndLastResult warehouseAndLastResult=invest.readPosition(value.getVALUE(),value.getTIME(), value.getCREATE_TIME());
			Result result=warehouseAndLastResult.getLastResult();
			Warehouse warehouse=warehouseAndLastResult.getWarehouse();
			if(result!=null){
				sbBuffer1.append(result.toString()+"\n");
			}
			if(warehouse!=null){
				sbBuffer2.append(warehouse.getDESCRIPTION().toString()+"\n");
			}
		}
		String dateString=DateFormatUtil.formatDate(new Date(time9));
		String timeString=DateFormatUtil.formatDate(new Date()).substring(0,10);
		FileUtil.TextToFile("F:/invest/银基合金"+timeString+"/"+dateString+"-"+failureToCreateTimes+"-"+destination+"-"+ifFailureToCreateTimes+".txt",sbBuffer1.toString());
		//FileUtil.TextToFile("F:/invest/银基合金建仓"+dateString+"-"+failureToCreateTimes+"-"+destination+"-"+ifFailureToCreateTimes+".txt",sbBuffer2.toString());
		//GoldCopperCount.countGoldCopper(list);
		return new BasicResultPaser();
	}
	public JsonUnitData analysisCorundum(Long time9,Long time430) throws ServiceException, IOException, AWTException{
		StringBuffer sql=new StringBuffer();
		List<Object> queryParams = new ArrayList<Object>();
		
		sql.append("select * from ct_corundum_data where 1=1 AND ");
		sql.append(" TIME>? ");
		queryParams.add(time9);
		sql.append(" AND TIME<? ");
		queryParams.add(time430);
		List<CorundumData> list=analysisDao.countCorundumData(sql.toString(),queryParams);
		
		StringBuffer sbBuffer1=new StringBuffer();
		StringBuffer sbBuffer2=new StringBuffer();
		
		int failureToCreateTimes=5;
		int destination=4;
		int ifFailureToCreateTimes=2;
		final Invest invest=new Invest(1,failureToCreateTimes, destination,ifFailureToCreateTimes);
		invest.setAllowDataLoseTime(40);
		for(CorundumData value:list){
			WarehouseAndLastResult warehouseAndLastResult=invest.readPosition(value.getVALUE(),value.getTIME(), value.getCREATE_TIME());
			
			Result result=warehouseAndLastResult.getLastResult();
			Warehouse warehouse=warehouseAndLastResult.getWarehouse();
			if(result!=null){
				sbBuffer1.append(result.toString()+"\n");
			}
			if(warehouse!=null){
				sbBuffer2.append(warehouse.getDESCRIPTION().toString()+"\n");
			}
		}
		String dateString=DateFormatUtil.formatDate(new Date(time9));
		String timeString=DateFormatUtil.formatDate(new Date()).substring(0,10);
		FileUtil.TextToFile("F:/invest/刚玉"+timeString+"/"+dateString+"-"+failureToCreateTimes+"-"+destination+"-"+ifFailureToCreateTimes+".txt",sbBuffer2.toString());
		//FileUtil.TextToFile("F:/invest/刚玉建仓"+dateString+"-"+failureToCreateTimes+"-"+destination+"-"+ifFailureToCreateTimes+".txt",sbBuffer2.toString());
		return new BasicResultPaser();
	}
}
