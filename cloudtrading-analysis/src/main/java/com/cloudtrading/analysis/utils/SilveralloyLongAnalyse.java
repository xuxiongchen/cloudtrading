package com.cloudtrading.analysis.utils;

import com.cloudtrading.pub.Direction;
import com.cloudtrading.pub.Production;
import com.cloudtrading.pub.Production.Period;
import com.cloudtrading.warehouse.model.SilverAlloy;

public class SilveralloyLongAnalyse extends BaseAnalyse {
	
	private int destionValue = SilverAlloy.contractDeposit1; //中金铜短期止损止盈
	private int marginalValue;								 //分析预测临界值
	private boolean isReadyCalculate;
	private int sameChangeDirection;
	private int contratyChangeDirection;

	public SilveralloyLongAnalyse(Production production, int marginalValue) {
		super(production);
		this.marginalValue = marginalValue;
		isReadyCalculate = false;
		sameChangeDirection = 0;
		contratyChangeDirection = 0;
	}

	@Override
	protected void calculate() {
		int gap = super.getCurrentPosition().getValue() - super.getCurrentObservePosition().getValue();
		if(Math.abs(gap) >= destionValue){
			if(gap > 0){
				super.makeObserver(Direction.INSCREASE);
			}else{
				super.makeObserver(Direction.DESCREASE);
			}
		}
		if(super.getSameDirectionCount() >= marginalValue){
			if(isReadyCalculate){
					Direction changeDirection = (getCurrentPosition().getValue() > getLastPosition().getValue()) ? Direction.INSCREASE : Direction.DESCREASE;
					/*
					if(changeDirection == getLastDirection()){
						sameChangeDirection++;
					}else{
						contratyChangeDirection++;
					}
					*/
					if(changeDirection != getLastDirection()){
						contratyChangeDirection++;
					}else{
						contratyChangeDirection = 0;
					}
					if(sameChangeDirection-contratyChangeDirection>= 2)
					{
						super.makeCalculate(super.getContratyDirection(super.getLastDirection()), Period.SHORT_PERIOD);
					}
			}else{
				isReadyCalculate = true;
			}
		}else{
				if(isReadyCalculate){
					isReadyCalculate = false;
					sameChangeDirection = 0;
					contratyChangeDirection = 0;
				}
		}
	}

}
