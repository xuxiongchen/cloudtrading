package com.cloudtrading.analysis.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.cloudtrading.analysis.entity.portal.CalculateList;
import com.cloudtrading.analysis.entity.portal.Observe;
import com.cloudtrading.pub.Direction;
import com.cloudtrading.pub.Production;
import com.cloudtrading.pub.Production.Period;
import com.google.common.collect.Lists;

public class AgAnalyse extends BaseAnalyse2 {
	
	private int preConditionObserveCountMin;			//条件1，要求的观察数量3~20
	private int preConditionObserveCountMax;	
	private int BackContinuousObserveCountMin;		//条件2成立，要求反个方向连续出现的次数，0~3
	private int BackContinuousObserveCountMax;	
	private int expectDirectionAppearCountMin;			//等待时机，期望的方向，连续出现的次数， -3~3
	private int expectDirectionAppearCountMax;	
	   	
	public AgAnalyse(Production production){
		super(production);
		preConditionObserveCountMin = 3;				//必须大于0
		preConditionObserveCountMax = 4;
		BackContinuousObserveCountMin = 1;		//必须大于0
		BackContinuousObserveCountMax = 2;
		expectDirectionAppearCountMin = -1;
		expectDirectionAppearCountMax = 1;
		for(int preObserveCount = preConditionObserveCountMin;
				preObserveCount <= preConditionObserveCountMax;
				preObserveCount++)
		{
			for(int singleDirectionAppearCount = 0; 
					singleDirectionAppearCount <= preObserveCount;
					singleDirectionAppearCount++)
			{
				for(int BackObserveCount = BackContinuousObserveCountMin;
						BackObserveCount <= BackContinuousObserveCountMax;
						BackObserveCount++)
				{
					for(int expectDirectionAppearCount = expectDirectionAppearCountMin;
							expectDirectionAppearCount <= expectDirectionAppearCountMax;
							expectDirectionAppearCount++)
					{
						String listCode = preObserveCount + "-"+singleDirectionAppearCount+"-"+BackObserveCount+"-"+expectDirectionAppearCount;
						CalculateList calculateList = new CalculateList(listCode, expectDirectionAppearCount);
						calculateLists.add(calculateList);
					}
				}
			}
		}
	}
	
	@Override 
	protected void calculate(boolean endObserve) {
		dealReadyStatusCalculate();
		long observeCount = 0;
		if(endObserve){
			observeCount++;
			if(observeCount % 10000 == 0){
				filterBadAlgorithm();
			}
			dealNewObserveAppear();
		}
	} 
	
	private void filterBadAlgorithm(){
		List<Integer> filter=Lists.newArrayList(8,12,20);
		for(int i = 0; i < calculateLists.size(); i++){
			CalculateList calculateList = calculateLists.get(i);
			if(filter.contains(calculateList.getEffectiveCount())){
				if(calculateList.getSuccessCount() / calculateList.getEffectiveCount()< 0.5){
					calculateList.setAbandon(true);
				}
			}
		}
	}

	/**
	 * 出现新的观察的时候 去做处理
	 */
	private void dealNewObserveAppear(){
		int index = 0;
		for(int preObserveCount = preConditionObserveCountMin;
				preObserveCount <= preConditionObserveCountMax;
				preObserveCount++)
		{
			for(int singleDirectionAppearCount = 0; 
					singleDirectionAppearCount <= preObserveCount;
					singleDirectionAppearCount++)
			{
				for(int BackObserveCount = BackContinuousObserveCountMin;
						BackObserveCount <= BackContinuousObserveCountMax;
						BackObserveCount++)
				{
					List<Observe> recentObserves = getRecentObserve(preObserveCount + BackObserveCount);
					for(int expectDirectionAppearCount = expectDirectionAppearCountMin;
							expectDirectionAppearCount <= expectDirectionAppearCountMax;
							expectDirectionAppearCount++)
					{
						CalculateList calculateList = calculateLists.get(index);
						calculateList.cancelReadyCalculate();		//新的观察到来，取消之前的预测准备
						if(recentObserves.size() == preObserveCount+BackObserveCount){
							Direction calDirection = checkDirection(recentObserves, preObserveCount, singleDirectionAppearCount, BackObserveCount);
							if(calDirection != null){
								if(expectDirectionAppearCount == 0){
									calculateList.makeCalculate(currentPosition, calDirection, Period.LONG_PERIOD, production);
								}else{
									calculateList.makeReadyCalculate(calDirection);
								}
							}
						}
						index++;
					}
				}
			}
		}
	}
	
	/**
	 * 判断预测的方向
	 * 
	 * @param recentObserves	所需要的观察总数
	 * @param preObserveCount	第一条件所需要的观察条件
	 * @param singleDirectionAppearCount	第一条件所需要的单边方向观察数量
	 * @param BackObserveCount	第二条件需要的反方向观察数量
	 * @return null 不能作出预测 
	 */
	private Direction checkDirection(List<Observe> recentObserves,  int preObserveCount, int singleDirectionAppearCount, int BackObserveCount){
		boolean accordSecondCondition = true;		//第二个条件成立：连续出现BackObserveCount同个方向
		Direction calDirection = Direction.INSCREASE;		//准备预测的方向
		for(int i = recentObserves.size()-1; i >= recentObserves.size()-1-BackObserveCount; i--){
			if(i == recentObserves.size()-1){
				calDirection = recentObserves.get(i).getDirection();
			}else{
				if(calDirection != recentObserves.get(i).getDirection()){
					accordSecondCondition = false;
				}
			}
		}
		
		if(accordSecondCondition){	//第二个条件成立
			Direction firstConfditionDirection = getContratyDirection(calDirection);
			int appearCount = 0;
			for(int i = 0; i <= recentObserves.size()-BackObserveCount-1; i++){
				if(recentObserves.get(i).getDirection() == firstConfditionDirection){
					appearCount++;
				}
			}
			if(appearCount == singleDirectionAppearCount){
				return calDirection;
			}
		}
		return null;
	}

	/**
	 * 处理正在等待时机的预测
	 */
	private void dealReadyStatusCalculate(){
		int changeValue = currentPosition.getValue() - lastPosition.getValue();
		for(int i = 0; i < calculateLists.size(); i++){
			CalculateList calculateList = calculateLists.get(i);
			if(!calculateList.isAbandon() && calculateList.isReady()){	
				if(isSameSymbol(changeValue, calculateList.getSerialDirectionMarginCount())){
					calculateList.setSerialExpectDirectionCount(calculateList.getSerialExpectDirectionCount() + changeValue);
				}else{
					calculateList.setSerialExpectDirectionCount(0);
				}
				if(Math.abs(calculateList.getSerialExpectDirectionCount()) == calculateList.getSerialDirectionMarginCount()){
					makeCalculate(calculateList,Period.LONG_PERIOD);
				}
			}
		}
	}
	
	private boolean isSameSymbol(int num1, int num2){
		return (num1 > 0 && num2 > 0) || (num1 < 0 && num2 < 0);
	}
}
