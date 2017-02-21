package com.cloudtrading.analysis.utils;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.cloudtrading.analysis.entity.portal.Calculate;
import com.cloudtrading.analysis.entity.portal.CalculateList;
import com.cloudtrading.analysis.entity.portal.Observe;
import com.cloudtrading.pub.Direction;
import com.cloudtrading.pub.Position;
import com.cloudtrading.pub.Production;
import com.cloudtrading.pub.Production.Period;


public abstract class BaseAnalyse2 {
	protected static final Logger logger = LoggerFactory.getLogger(BaseAnalyse2.class);
	protected Production production;       			
	
	protected List<CalculateList> calculateLists;		    //分析预测
	protected List<Observe> observes;						//观察
	
	protected Position maxPosition;							
	protected Position minPosition;							
	protected Position lastPosition;							
	protected Position currentPosition;						

	private int allowDataLoseTime=30*1000;							
	
	/**
	 * 构造
	 * 
	 * @param production 产品类型，重写请先调用父类方法
	 */
	public BaseAnalyse2(Production production){
		this.maxPosition=new Position(0, 0);
		this.minPosition=new Position(0, 0);
		this.lastPosition=new Position(0, 0);
		this.currentPosition=new Position(0, 0);
		calculateLists = new ArrayList<CalculateList>();
		observes = new ArrayList<Observe>();
		
		this.production=production;	
	}
	
	/**
	 * 分析预测
	 */
	protected abstract void calculate(boolean endObserve);
	
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
			boolean endObserve = dealLastObserve();
			calculate(endObserve);
			if(endObserve){
				makeObserver();
			}
		}	
		lastPosition = position;
		setLimitValue(position);
	}
	
	/**
	 * 输出所有预测结果
	 * 
	 */
	public String printCalculateResult() {
		String result = "";
		for(int i = 0; i < calculateLists.size(); i++){
			if(!(calculateLists.get(i)).isAbandon()){
				result += (calculateLists.get(i)).toString() + "\n";
			}
		}
		return result;
	}
	
	/**
	 * 创建一次分析预测
	 */
	protected synchronized  final boolean makeCalculate(CalculateList calculateList, Period period){
		return calculateList.makeCalculate(currentPosition, calculateList.getReadyDirection(), period, production);
	}
	
	/**
	 * 创建一次新的观察
	 */
	protected final void makeObserver(){
		Observe observe = new Observe(this.production, Period.SHORT_PERIOD, currentPosition);
		observes.add(observe);
	}
	
	/**
	 * 重置分析条件
	 */
	protected void resetAnalyse(){
		for(int i = 0; i < calculateLists.size(); i++){
			(calculateLists.get(i)).cancelCalculate();
			(calculateLists.get(i)).cancelReadyCalculate();
		}
		if(observes.size() > 0){
			Observe lastObserve = observes.get(observes.size()-1);
			if(!lastObserve.isEnd()){
				lastObserve.setHaveResult(false);
			}
		}
		makeObserver();
	}
	
	/**
	 * 获取最近的一串观察
	 * @return
	 */
	protected List<Observe> getRecentObserve(int maxCount) {
		List<Observe> recentObserves = new ArrayList<Observe>();
		int count = 0;
		if(observes.size() > 0 && maxCount > 0){
			for(int i = observes.size()-1; i >= 0; i--){
				Observe observe = observes.get(i);
				if(!observe.isHaveResult()){
					recentObserves.add(0, observe);
					count++;
					if(count == maxCount){
						break;
					}
				}
			}
		}
		return recentObserves;
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
		for(int i = 0; i < calculateLists.size(); i++){
			(calculateLists.get(i)).dealCalculate(currentPosition);
		}
	}
	
	/**
	 * 处理上一次观察
	 */
	private boolean dealLastObserve(){
		Observe lastObserve = observes.get(observes.size()-1);
		return lastObserve.observerPosition(currentPosition);
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
	
	public Direction getLastDirection() {
		for (int i = observes.size(); i >= 1; i--) {
			Observe observe = observes.get(i-1);
			if(observe.isHaveResult() && observe.isEnd()){
				return observe.getDirection();
			}
		}
		return null;
	}
}
