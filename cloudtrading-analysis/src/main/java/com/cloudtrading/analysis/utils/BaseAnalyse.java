package com.cloudtrading.analysis.utils;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.cloudtrading.analysis.entity.portal.Calculate;
import com.cloudtrading.pub.Direction;
import com.cloudtrading.pub.Position;
import com.cloudtrading.pub.Production;
import com.cloudtrading.pub.Production.Period;


public abstract class BaseAnalyse {
	protected static final Logger logger = LoggerFactory.getLogger(BaseAnalyse.class);
	private Production production;       					//产品类型
	
	private boolean existCalculate;						//是否做了预测
	private List<Calculate> calculates;					//分析预测
	
	private Direction lastDirection;          				//上一次变化的方向
	private Position currentObservePosition;			//本次观察（预测）的起点
	
	private int sameDirectionCount;						//同一个方向发展的次数
	private Position maxPosition;							//当日最高点
	private Position minPosition;							//当日最低点
	private Position lastPosition;							//上一次传入的点数
	private Position currentPosition;						//当前点数

	private int allowDataLoseTime=30*1000;					//最大允许数据缺失时间
	
	/**
	 * 构造
	 * 
	 * @param production 产品类型，重写请先调用父类方法
	 */
	public BaseAnalyse(Production production){
		this.maxPosition=new Position(0, 0);
		this.minPosition=new Position(0, 0);
		this.lastPosition=new Position(0, 0);
		this.currentPosition=new Position(0, 0);
		this.currentObservePosition = new Position(0, 0);
		this.sameDirectionCount=0;
		this.existCalculate = false;
		calculates = new ArrayList<Calculate>();
		
		this.production=production;	
	}
	
	/**
	 * 分析预测
	 */
	protected abstract void calculate();
	
	/**
	 * 实时传入股点
	 *
	 * @param position
	 */
	public final void readPosition(Position position){
		if(position.getValueTime() - lastPosition.getValueTime() > allowDataLoseTime){		//数据丢失时间过长，重置
			resetAnalyse();
		}else if(position.getValue() != lastPosition.getValue()){		//股点有变化
			currentPosition = position;	
			dealLastCalculate();
			calculate();
		}	
		lastPosition = position;
		setLimitValue(position);
	}
	
	/**
	 * 输出所有预测结果
	 * 
	 */
	public String printCalculateResult() {
		int successCount = 0;
		int failCount = 0;
		float successPrecent = 0;
		int calculateCount = calculates.size();
		StringBuffer sBuffer=new StringBuffer();
		for(int i = 0; i < calculates.size(); i++){
			Calculate calculate = calculates.get(i);
			if(calculate.isHaveResult()){
				if(calculate.isSuccess()){
					successCount++;
				}else {
					failCount++;
				}
			}

			//logger.info(calculate.toString());
			sBuffer.append(calculate.toString());
			sBuffer.append("\n");
		}
		
		int effectiveCount = failCount + successCount;
		if(effectiveCount > 0){
			successPrecent = (float) (successCount*1.0000 / effectiveCount);
		}
		sBuffer.append("总预测次数：" + calculateCount + ", 有效预测次数：" + effectiveCount +
						", 成功次数：" + successCount + ", 失败次数：" + failCount + ", 成功率：" + successPrecent+"\n");
		return sBuffer.toString();
		//logger.info("总预测次数：" + calculateCount + ", 有效预测次数：" + effectiveCount +
		//		", 成功次数：" + successCount + ", 失败次数：" + failCount + ", 成功率：" + successPrecent);
	}
	
	/**
	 * 创建一次分析预测
	 * 
	 * @param direction
	 * @param period
	 * @return 
	 */
	protected synchronized  final boolean makeCalculate(Direction direction, Period period){
		if(calculates.size()  > 0){
			Calculate lastCalculate = calculates.get(calculates.size() - 1);
			if(currentPosition.getValueTime() - lastCalculate.getPosition().getValueTime() < 60*1000){
				return false;
			}
		}
		if(existCalculate){
			return false;
		}
		Calculate calculate = new Calculate(direction, this.currentPosition,this.lastPosition, period, this.production);
		calculates.add(calculate);
		existCalculate = true;
		return true;
	}
	
	/**
	 * 创建一次新的观察，同时更新lastDirection、sameDirectionCount、预测结果
	 * 
	 * @param lastDirection 上一次变化的方向
	 */
	int makeObservercount=0;
	protected final void makeObserver(Direction lastDirection){
		if(lastDirection == this.lastDirection){
			sameDirectionCount++;
		}else{
			sameDirectionCount = 1;
			this.lastDirection = lastDirection;
		}
		makeObservercount++;
		currentObservePosition = currentPosition;
		
	}
	
	int resetAnalyseCount=0;
	/**
	 * 重置分析条件
	 */
	protected void resetAnalyse(){
		sameDirectionCount = 0;
		if(existCalculate){
			existCalculate = false;
			Calculate lastCalculate = calculates.get(calculates.size()-1);
			lastCalculate.setHaveResult(false);
			lastCalculate.setRemark("数据丢失，无法确定预测结果");
		}
		currentObservePosition = currentPosition;
	}
	
	/**
	 * 获取反方向
	 *
	 * @param direction
	 * @return
	 */
	protected final Direction getContratyDirection(Direction direction){
		if(direction == Direction.INSCREASE){
			return Direction.DESCREASE;
		}
		return Direction.INSCREASE;
	}
	
	/**
	 * 处理上一次预测
	 */
	private void dealLastCalculate() {
		if(existCalculate){
			Calculate lastCalculate = calculates.get(calculates.size()-1);
			int destionValue = lastCalculate.getProduction().getPeriod().get(lastCalculate.getPeriod());
			int gap = currentPosition.getValue() - lastCalculate.getPosition().getValue();
			if(Math.abs(gap) >= destionValue){
				existCalculate = false;
				Direction changeDirection = gap > 0 ? Direction.INSCREASE : Direction.DESCREASE;
				lastCalculate.setSuccess(lastCalculate.getDirection() == changeDirection);
			}
		}
	}
	
	/**
	 * 设置极限值
	 * 
	 * @param position
	 */
	private void setLimitValue(Position position){
		if(maxPosition.getValue() == 0){
			maxPosition = position;
			minPosition = position;
		}else {
			if(maxPosition.getValue() <= position.getValue()){
				maxPosition = position;
			}else if(minPosition.getValue() >= position.getValue()){
				minPosition = position;
			}
		}
	}

	public Production getProduction() {
		return production;
	}

	public boolean isExistCalculate() {
		return existCalculate;
	}

	public List<Calculate> getCalculates() {
		return calculates;
	}
	
	public Direction getLastDirection() {
		return lastDirection;
	}

	public int getSameDirectionCount() {
		return sameDirectionCount;
	}

	public Position getMaxPosition() {
		return maxPosition;
	}

	public Position getMinPosition() {
		return minPosition;
	}

	public Position getCurrentObservePosition() {
		return currentObservePosition;
	}

	public Position getLastPosition() {
		return lastPosition;
	}

	public Position getCurrentPosition() {
		return currentPosition;
	}

	public int getAllowDataLoseTime() {
		return allowDataLoseTime;
	}

	public void setAllowDataLoseTime(int allowDataLoseTime) {
		Assert.isTrue(allowDataLoseTime > 3);
		this.allowDataLoseTime = allowDataLoseTime;
	}
}
