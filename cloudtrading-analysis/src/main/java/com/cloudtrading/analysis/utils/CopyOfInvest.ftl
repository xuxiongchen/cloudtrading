package com.cloudtrading.analysis.utils;

import java.awt.AWTException;
import java.util.Date;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloudtrading.pub.Direction;
import com.cloudtrading.warehouse.entity.Result;
import com.cloudtrading.warehouse.entity.Warehouse;
import com.cloudtrading.warehouse.entity.WarehouseAndLastResult;
import com.cloudtrading.warehouse.utils.DateFormatUtil;
import com.cloudtrading.warehouse.utils.WindowsController;

public class CopyOfInvest {
	private static final Logger logger = LoggerFactory.getLogger(CopyOfInvest.class);
	private int createPosition;		//建仓时点数
	private int destination;		//止盈止损点
	private static volatile boolean isOneReadyForCreateWarehouse=false;   //商品1是否准备建仓
	private static volatile boolean isTwoReadyForCreateWarehouse=false;   //商品2是否准备建仓
	private static volatile boolean isThreeReadyForCreateWarehouse=false;   //商品3是否准备建仓
	private int existWarehouseNumber=0;        //已持仓数目

	private boolean existWarehouse;				//当前是否持仓
	private Direction createDirection;			//建仓的方向
	private Direction calculateDirection;		//预测的方向
	private Direction actualDirection;          //实际方向
	private int sameDirectioproductionnCount;				//同一个方向发展的次数
	private int createCount;					//建仓次数
	private int successCount;					//成功次数
	private int failureCount;					//失败次数 
	private int failureInscreseCount;		    //买涨失败累加
	private int failureDescreseCount;			//买跌失败累加
	private int production;                     //刚玉：1     银基合金：2	中金铜：3
	//实时数据
	private int maxPosition;		//当日最高点
	private int minPosition;		//当日最低点
	private int lastPosition;		//上一次点数
	private int currentPosition;	//当前点数

	private int marginalValue;  //零界值
	private int ifFailureMarginalValue;  //如果失败了的买几次零界值
	private int allowDataLoseTime=30;
	private long lastTime;
	
	private int tapeStartPoint=0;
	private long tapeStartTime=0;
	
	private boolean isCreate=true;//是否要建仓
	/**
	 * 处理上一次预测或建仓结果
	 *
	 * @param position
	 */
	private synchronized Result dealLastCalculate(int position,Date date,Long valueTime){
		Result result=null;
		if(this.createDirection==Direction.INSCREASE){  //买涨
			
			
			if(this.createPosition-position>=this.destination){ //涨 ：  买点-现在的大小>止盈止损点
				result=new Result();
				result.setCreatePosition(this.createPosition);
				result.setPosition(position);
				result.setDate(date);
				result.setIsSuccess(0);
				result.setDescription(date.toLocaleString()+"买涨失败，当前点数为"+position+"，买时点数为"+this.createPosition);
				failureInscreseCount++;  //失败次数加一
				
			}
			else
			if(position-this.createPosition>=this.destination){ //跌 ： 现在的大小-买点> 止损止盈点
				result=new Result();
				result.setCreatePosition(this.createPosition);
				result.setPosition(position);
				result.setDate(date);
				result.setIsSuccess(1);
				result.setDescription(date.toLocaleString()+"买涨成功，当前点数为"+position+"，买时点数为"+this.createPosition);
				failureInscreseCount=1; //成功次数重置为0;
				this.tapeStartPoint=position;
				this.tapeStartTime=valueTime;
			}
		}
		if(this.createDirection==Direction.DESCREASE){

			if(this.createPosition-position>=this.destination){ //涨 ：  买点-现在的大小>止盈止损点
				result=new Result();
				result.setCreatePosition(this.createPosition);
				result.setPosition(position);
				result.setDate(date);
				result.setIsSuccess(1);
				result.setDescription(date.toLocaleString()+"买跌成功，当前点数为"+position+"，买时点数为"+this.createPosition);
				failureDescreseCount=1;
				this.tapeStartPoint=position;
				this.tapeStartTime=valueTime;
			}
			else
			if(position-this.createPosition>=this.destination){ //跌 ： 现在的大小-买点> 止损止盈点
				result=new Result();
				result.setCreatePosition(this.createPosition);
				result.setPosition(position);
				result.setDate(date);
				result.setIsSuccess(0);
				result.setDescription(date.toLocaleString()+"买跌失败，当前点数为"+position+"，买时点数为"+this.createPosition);
				failureDescreseCount++; //失败次数加一
			}
		}
		return result;
		
	}
	public CopyOfInvest(int production,int marginalValue,int destination,int ifFailureMarginalValue){
		this.maxPosition=0;
		this.minPosition=0;
		this.lastPosition=0;
		this.currentPosition=0;
		this.marginalValue=marginalValue;
		this.destination=destination;
		this.ifFailureMarginalValue=ifFailureMarginalValue;
		this.existWarehouse=false;
		this.sameDirectionCount=0;
		this.createCount=0;
		this.successCount=0;
		this.production=production;
	}
	/**
	 * 实时传入股市当前点数
	 *
	 * @param position
	 * @return 
	 * @throws AWTException 
	 */
	public WarehouseAndLastResult readPosition(int position,long valueTime,Date date) throws AWTException{
		if(this.maxPosition==0){
			this.minPosition=position;
			this.maxPosition=position;
			this.lastPosition=position;
			this.createPosition=position;
			this.lastTime=valueTime;
		}else{
			if(minPosition>position){
				minPosition=position;
			}
			else if(maxPosition<position){
				maxPosition=position;
			}
			if(lastPosition!=position){
				lastPosition=position;
			}
		}
		WarehouseAndLastResult warehouseAndLastResult=new WarehouseAndLastResult();
		//处理上一次预测或建仓结果
		Result result=this.dealLastCalculate(position,date,valueTime);
		//处理建仓
		com.cloudtrading.warehouse.entity.Warehouse warehouse=this.createWarehouse(position, date,valueTime);
		warehouseAndLastResult.setLastResult(result);
		warehouseAndLastResult.setWarehouse(warehouse);
		if(warehouse!=null){		//建仓了则已经做了预测，无需预测
			//预测
			calculateFucture(position);
		}
		return warehouseAndLastResult;
	}
	/**
	 * 重置分析条件
	 * 
	 * @param isException true 数据丢失异常 false 数据日期变更
	 */
	private void resetAnalyse(boolean isException){
		if(existWarehouse){
			existWarehouse = false;
			createCount--;
		}
		if(!isException){	//日期变更
			maxPosition = 0;
			minPosition = 0;
			lastPosition = 0;
			currentPosition = 0;
		}
		sameDirectionCount = 0;
	}

	/**
	 * 预测未来趋势
	 *
	 * @param position
	 */
	private void calculateFucture(int position){
		//showBuyResult(i,s.getCREATE_TIME());
	}

	
	
	/**
	 * 建仓，添加策略
	 *
	 * @return 是否进行建仓
	 * @throws AWTException 
	 */
	private synchronized Warehouse createWarehouse(int position,Date date,long valueTime) throws AWTException{
		Warehouse  warehouse=null;
		
		//开始订单处理,假设已经下过一次单，且下单成功
		if(this.createPosition-position>=this.destination){ //涨 ：  买点-现在的大小>止盈止损点
			//处理买涨
			warehouse=create(position,Direction.INSCREASE,valueTime);
		}else if(position-this.createPosition>=this.destination){ //跌 ： 现在的大小-买点> 止损止盈点
			//处理买跌
			warehouse=create(position,Direction.DESCREASE,valueTime);
		}
		//判断是否可以建仓的条件
		return warehouse;
	}
	private int isPrepreForWhat=0;
	
	private  Warehouse create(int position,Direction direction,long valueTime) throws AWTException {
		this.createPosition=position;
		this.createDirection=direction;
		Warehouse  warehouse=null;
		if(this.failureInscreseCount==this.marginalValue-1){
			//准备买
			//WindowsController.inscreseGoldCopperReady(0);
			isPrepreForWhat=1;
		}else if(this.failureDescreseCount==this.marginalValue-1){
			//WindowsController.descreseGoldCopperReady(0);
			isPrepreForWhat=2;
		}
		if(this.failureInscreseCount==1&&isPrepreForWhat==1){
			//取消
			//WindowsController.cancal();
		}else if(this.failureDescreseCount==1&&isPrepreForWhat==2){
			//取消
			//WindowsController.cancal();
		}
		if(direction==Direction.INSCREASE){
			if(this.failureInscreseCount>marginalValue&&this.failureInscreseCount<=this.marginalValue+this.ifFailureMarginalValue){  //不贪，只买 6 7两次
				float k=0;
				k=(float) (((position-this.tapeStartPoint)*10000.00))/(valueTime-this.tapeStartTime);
				
				warehouse=new Warehouse();
				Date date=new Date();
				warehouse.setCREATEPOSITION(position);
				warehouse.setDATETIME(date);
				warehouse.setOLDTIME(valueTime);
				warehouse.setTIME(System.currentTimeMillis());
				warehouse.setDIRECTION(2);
				warehouse.setCREATE_TIME(date);
				warehouse.setDESCRIPTION("开始值"+this.tapeStartPoint+"变化斜率"+k+"     第一次亏了！！这次我需要买跌"+date.toLocaleString()+"在"+position+"点我买了跌"+DateFormatUtil.formatDate(new Date(valueTime)));
				createCount++;
				//TODO 调用机器买跌,做种类判断
				
				
			}else if(this.failureInscreseCount==this.marginalValue){
				float k=0;
				k=(float) (((position-this.tapeStartPoint)*10000.00))/(valueTime-this.tapeStartTime);
				warehouse=new Warehouse();
				Date date=new Date();
				warehouse.setCREATEPOSITION(position);
				warehouse.setDATETIME(date);
				warehouse.setOLDTIME(valueTime);
				warehouse.setTIME(System.currentTimeMillis());
				warehouse.setDIRECTION(1);
				warehouse.setCREATE_TIME(date);
				warehouse.setDESCRIPTION("开始值"+this.tapeStartPoint+"变化斜率"+k+"这次我真买："+date.toLocaleString()+"在"+position+"点我买了涨"+DateFormatUtil.formatDate(new Date(valueTime)));
				//TODO 调用机器买涨
				if(this.isCreate){
					//WindowsController.submit();
				}
			}else if(this.failureInscreseCount>this.marginalValue+this.ifFailureMarginalValue){ //连续失败8次以上 TODO
				float k=0;
				k=(float) (((position-this.tapeStartPoint)*10000.00))/(valueTime-this.tapeStartTime);
				warehouse=new Warehouse();
				Date date=new Date();
				warehouse.setCREATEPOSITION(position);
				warehouse.setDATETIME(date);
				warehouse.setOLDTIME(valueTime);
				warehouse.setTIME(System.currentTimeMillis());
				warehouse.setDIRECTION(null);
				warehouse.setCREATE_TIME(date);
				warehouse.setDESCRIPTION("开始值"+this.tapeStartPoint+"变化斜率"+k+"   "+date.toLocaleString()+"在"+position+"我不敢买"+DateFormatUtil.formatDate(new Date(valueTime)));
			}else{
				warehouse=new Warehouse();
				Date date=new Date();
				warehouse.setCREATEPOSITION(position);
				warehouse.setDATETIME(date);
				warehouse.setOLDTIME(valueTime);
				warehouse.setTIME(System.currentTimeMillis());
				warehouse.setDIRECTION(1);
				warehouse.setCREATE_TIME(date);
				warehouse.setDESCRIPTION(date.toLocaleString()+"在"+position+"点我买了涨"+DateFormatUtil.formatDate(new Date(valueTime)));
				
			}
			
		}else{
			if(this.failureDescreseCount>this.marginalValue&&this.failureDescreseCount<=this.marginalValue+this.ifFailureMarginalValue){  //不贪，只买 6 7两次
				float k=0;
				k=(float) (((position-this.tapeStartPoint)*10000.00))/(valueTime-this.tapeStartTime);
				
				warehouse=new Warehouse();
				Date date=new Date();
				warehouse.setCREATEPOSITION(position);
				warehouse.setDATETIME(date);
				warehouse.setOLDTIME(valueTime);
				warehouse.setTIME(System.currentTimeMillis());
				warehouse.setDIRECTION(1);
				warehouse.setCREATE_TIME(date);
				warehouse.setDESCRIPTION("开始值"+this.tapeStartPoint+"变化斜率"+k+"     第一次亏了！！这次需要买涨"+date.toLocaleString()+"在"+position+"点我买涨"+DateFormatUtil.formatDate(new Date(valueTime)));
				createCount++;
				//TODO 调用机器买跌
			}else if(this.failureDescreseCount==this.marginalValue){
				float k=0;
				k=(float) (((position-this.tapeStartPoint)*10000.00))/(valueTime-this.tapeStartTime);
				warehouse=new Warehouse();
				Date date=new Date();
				warehouse.setCREATEPOSITION(position);
				warehouse.setDATETIME(date);
				warehouse.setOLDTIME(valueTime);
				warehouse.setTIME(System.currentTimeMillis());
				warehouse.setDIRECTION(1);
				warehouse.setCREATE_TIME(date);
				warehouse.setDESCRIPTION("开始值"+this.tapeStartPoint+"变化斜率"+k+"这次我真买："+date.toLocaleString()+"在"+position+"点我买了跌"+DateFormatUtil.formatDate(new Date(valueTime)));
				createCount++;
				//TODO 调用机器买跌
				if(this.isCreate){
					WindowsController.submit();
				}
			}else if(this.failureDescreseCount>this.marginalValue+this.ifFailureMarginalValue){ //连续失败8次以上 TODO
				float k=0;
				k=(float) (((position-this.tapeStartPoint)*10000.00))/(valueTime-this.tapeStartTime);
				
				warehouse=new Warehouse();
				Date date=new Date();
				warehouse.setCREATEPOSITION(position);
				warehouse.setDATETIME(date);
				warehouse.setOLDTIME(valueTime);
				warehouse.setTIME(System.currentTimeMillis());
				warehouse.setDIRECTION(null);
				warehouse.setCREATE_TIME(date);
				warehouse.setDESCRIPTION("开始值"+this.tapeStartPoint+"变化斜率"+k+"   "+date.toLocaleString()+"在"+position+"我不敢买"+DateFormatUtil.formatDate(new Date(valueTime)));
				
			}else{
				warehouse=new Warehouse();
				Date date=new Date();
				warehouse.setCREATEPOSITION(position);
				warehouse.setDATETIME(date);
				warehouse.setOLDTIME(valueTime);
				warehouse.setTIME(System.currentTimeMillis());
				warehouse.setDIRECTION(2);
				warehouse.setCREATE_TIME(date);
				warehouse.setDESCRIPTION(date.toLocaleString()+"在"+position+"点我买了跌"+DateFormatUtil.formatDate(new Date(valueTime)));
				//此时为模拟买
			}
		}
		return warehouse;
	}

	/**
	 * 获取反方向
	 *
	 * @param direction
	 * @return
	 */
	private Direction getContratyDirection(Direction direction){
		if(direction == Direction.INSCREASE){
			return Direction.DESCREASE;
		}
		return Direction.INSCREASE;
	}

	public int getCreatePosition() {
		return createPosition;
	}

	public void setCreatePosition(int createPosition) {
		this.createPosition = createPosition;
	}

	public int getDestination() {
		return destination;
	}

	public void setDestination(int destination) {
		this.destination = destination;
	}

	public int getAllowDataLoseTime() {
		return allowDataLoseTime;
	}
	public void setAllowDataLoseTime(int allowDataLoseTime) {
		this.allowDataLoseTime = allowDataLoseTime;
	}
	public boolean isExistWarehouse() {
		return existWarehouse;
	}

	public void setExistWarehouse(boolean existWarehouse) {
		this.existWarehouse = existWarehouse;
	}

	public Direction getCreateDirection() {
		return createDirection;
	}

	public void setCreateDirection(Direction createDirection) {
		this.createDirection = createDirection;
	}

	public Direction getCalculateDirection() {
		return calculateDirection;
	}

	public void setCalculateDirection(Direction calculateDirection) {
		this.calculateDirection = calculateDirection;
	}

	public Direction getActualDirection() {
		return actualDirection;
	}

	public void setActualDirection(Direction actualDirection) {
		this.actualDirection = actualDirection;
	}

	public int getSameDirectionCount() {
		return sameDirectionCount;
	}

	public void setSameDirectionCount(int sameDirectionCount) {
		this.sameDirectionCount = sameDirectionCount;
	}

	public int getCreateCount() {
		return createCount;
	}

	public void setCreateCount(int createCount) {
		this.createCount = createCount;
	}

	public int getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}

	public int getMaxPosition() {
		return maxPosition;
	}

	public void setMaxPosition(int maxPosition) {
		this.maxPosition = maxPosition;
	}

	public int getMinPosition() {
		return minPosition;
	}

	public void setMinPosition(int minPosition) {
		this.minPosition = minPosition;
	}

	public int getLastPosition() {
		return lastPosition;
	}

	public void setLastPosition(int lastPosition) {
		this.lastPosition = lastPosition;
	}

	public int getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(int currentPosition) {
		this.currentPosition = currentPosition;
	}


	
	
	public boolean isCreate() {
		return isCreate;
	}
	public void setCreate(boolean isCreate) {
		this.isCreate = isCreate;
	}




	public int getFailureCount() {
		return failureCount;
	}
	public void setFailureCount(int failureCount) {
		this.failureCount = failureCount;
	}
	public int getFailureInscreseCount() {
		return failureInscreseCount;
	}
	public void setFailureInscreseCount(int failureInscreseCount) {
		this.failureInscreseCount = failureInscreseCount;
	}
	public int getFailureDescreseCount() {
		return failureDescreseCount;
	}
	public void setFailureDescreseCount(int failureDescreseCount) {
		this.failureDescreseCount = failureDescreseCount;
	}
	public int getProduction() {
		return production;
	}
	public void setProduction(int production) {
		this.production = production;
	}
	public int getMarginalValue() {
		return marginalValue;
	}
	public void setMarginalValue(int marginalValue) {
		this.marginalValue = marginalValue;
	}
	public int getIfFailureMarginalValue() {
		return ifFailureMarginalValue;
	}
	public void setIfFailureMarginalValue(int ifFailureMarginalValue) {
		this.ifFailureMarginalValue = ifFailureMarginalValue;
	}
	public long getLastTime() {
		return lastTime;
	}
	public void setLastTime(long lastTime) {
		this.lastTime = lastTime;
	}
	public int getTapeStartPoint() {
		return tapeStartPoint;
	}
	public void setTapeStartPoint(int tapeStartPoint) {
		this.tapeStartPoint = tapeStartPoint;
	}
	public long getTapeStartTime() {
		return tapeStartTime;
	}
	public void setTapeStartTime(long tapeStartTime) {
		this.tapeStartTime = tapeStartTime;
	}




	private static class TimeUtil {

		public static final int SECONDS_IN_DAY = 60 * 60 * 24;

		/**
		 * 判断是否是同一个交易日
		 *
		 * @param s1
		 * @param s2
		 * @param startTradeHour
		 * @param endTradeHour
		 * @return
		 */
		public static boolean isSameTradeDay(final long s1, final long s2, int startTradeHour, int endTradeHour){
			//默认所有开始交易时间大于停止交易时间 即停止交易时间发生在晚上12点过后
			//Assert(endTradeHour < startTradeHour);
			long bigTime;
			long smallTime;
			if(s1 > s2){
				bigTime = s1;
				smallTime = s2;
			}else{
				bigTime = s2;
				smallTime = s1;
			}

			long smallHour = TimeUtil.getHour(smallTime);
			long bigHour = TimeUtil.getHour(bigTime);

			if(TimeUtil.isSameDay(bigTime, smallTime)){	//同一天
				return (smallHour < endTradeHour && bigHour < endTradeHour)
						|| (smallHour > startTradeHour && bigHour > startTradeHour);
			}else if(TimeUtil.isNextMorning(bigTime, smallTime)){	//隔天但相距不超过24小时
				return bigHour < endTradeHour && smallHour > startTradeHour;
			}
			return true;
		}

		public static boolean isSameDay(final long s1, final long s2) {
			final long interval = s1 - s2;
			return interval < SECONDS_IN_DAY
					&& interval > -1L * SECONDS_IN_DAY
					&& toDay(s1) == toDay(s2);
		}

		private static long toDay(long s) {
			return (s + TimeZone.getDefault().getOffset(s)) / SECONDS_IN_DAY;
		}

		private static long getHour(long s){
			return (s / 3600) % 24;
		}

		//相隔不超过24小时
		private static boolean isNextMorning(long s1, long s2){
			return Math.abs(s1 - s2) < SECONDS_IN_DAY;
		}
	}



}
