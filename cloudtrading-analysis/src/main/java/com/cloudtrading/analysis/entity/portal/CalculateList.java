package com.cloudtrading.analysis.entity.portal;

import java.util.ArrayList;
import java.util.List;

import com.cloudtrading.pub.Direction;
import com.cloudtrading.pub.Position;
import com.cloudtrading.pub.Production;
import com.cloudtrading.pub.Production.Period;

public class CalculateList {
	private List<Calculate> calculates;
	private String listCode;
	private int activeCount;
	private int successCount;
	private int effectiveCount;
	private boolean isAbandon;
	
	private boolean isReady;
	private Direction readyDirection;
	private int serialExpectDirectionCount;
	private int serialDirectionMarginCount;
	public String toString() {
		StringBuffer sbBuffer=new StringBuffer();
		float re=(float)successCount/effectiveCount;
		sbBuffer.append("listCode："+listCode+"\n");
		sbBuffer.append("activeCount："+activeCount+"\n");
		sbBuffer.append("successCount："+successCount+"\n");
		sbBuffer.append("effectiveCount："+effectiveCount+"\n");
		sbBuffer.append("serialExpectDirectionCount："+serialExpectDirectionCount+"\n");
		sbBuffer.append("serialDirectionMarginCount："+serialDirectionMarginCount+"\n");
		sbBuffer.append(calculates.toString()+"\n");
		sbBuffer.append("成功率："+re+"\n");
		
		return sbBuffer.toString();
	}
	public CalculateList(String listCode, int serialDirectionMarginCount) {
		super();
		this.listCode = listCode;
		this.serialDirectionMarginCount = serialDirectionMarginCount;
		calculates = new ArrayList<Calculate>();
	}
	
	public void dealCalculate(Position currentPosition){
		if(!isAbandon && activeCount > 0){
			for(int i = calculates.size()-1; i >= 0; i--){
				Calculate calculate = calculates.get(i);
				if(!calculate.isEnd()){
					int destionValue = calculate.getProduction().getPeriod().get(calculate.getPeriod());
					int gap = currentPosition.getValue() - calculate.getPosition().getValue();
					if(Math.abs(gap) >= destionValue){
						Direction changeDirection = gap > 0 ? Direction.INSCREASE : Direction.DESCREASE;
						if(calculate.getDirection() == changeDirection){
							calculate.setSuccess(true);
							successCount++;
						}else{
							calculate.setSuccess(false);
						}
						effectiveCount++;
						calculate.setSuccess(calculate.getDirection() == changeDirection);
						activeCount--;
						if(activeCount == 0){
							break;
						}
					}
				}
			}
		}
	}
	
	public boolean makeCalculate(Position currentPosition, Direction direction, Period period, Production production){
		if(calculates.size()  > 0){
			Calculate lastCalculate = calculates.get(calculates.size() - 1);
			if(currentPosition.getValueTime() - lastCalculate.getPosition().getValueTime() < 60000){
				return false;
			}
		}
		Calculate calculate = new Calculate(direction, currentPosition, period, production);
		calculates.add(calculate);
		activeCount++;
		isReady = false;
		serialExpectDirectionCount  = 0;
		return true;
	}
	
	public void makeReadyCalculate(Direction readyDirection){
		isReady = true;
		serialExpectDirectionCount = 0;
		this.readyDirection = readyDirection;
	}
	
	public void cancelReadyCalculate(){
		isReady = false;
		serialExpectDirectionCount = 0;
	}
	
	public void  cancelCalculate() {
		if(activeCount > 0){
			for(int i = calculates.size()-1; i >= 0; i--){
				Calculate calculate = calculates.get(i);
				if(!calculate.isEnd()){
					calculate.setHaveResult(false);
					calculate.setRemark("数据丢失，重置");
					activeCount--;
					if(activeCount == 0){
						break;
					}
				}
				activeCount = 0;
			}
		}
	}


	public List<Calculate> getCalculates() {
		return calculates;
	}

	public void setCalculates(List<Calculate> calculates) {
		this.calculates = calculates;
	}

	public String getListCode() {
		return listCode;
	}

	public void setListCode(String listCode) {
		this.listCode = listCode;
	}

	public int getActiveCount() {
		return activeCount;
	}

	public void setActiveCount(int activeCount) {
		this.activeCount = activeCount;
	}

	public int getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}

	public int getEffectiveCount() {
		return effectiveCount;
	}

	public void setEffectiveCount(int effectiveCount) {
		this.effectiveCount = effectiveCount;
	}

	public boolean isAbandon() {
		return isAbandon;
	}

	public void setAbandon(boolean isAbandon) {
		this.isAbandon = isAbandon;
	}

	public boolean isReady() {
		return isReady;
	}

	public void setReady(boolean isReady) {
		this.isReady = isReady;
	}

	public Direction getReadyDirection() {
		return readyDirection;
	}

	public void setReadyDirection(Direction readyDirection) {
		this.readyDirection = readyDirection;
	}

	public int getSerialExpectDirectionCount() {
		return serialExpectDirectionCount;
	}

	public void setSerialExpectDirectionCount(int serialExpectDirectionCount) {
		this.serialExpectDirectionCount = serialExpectDirectionCount;
	}

	public int getSerialDirectionMarginCount() {
		return serialDirectionMarginCount;
	}

	public void setSerialDirectionMarginCount(int serialDirectionMarginCount) {
		this.serialDirectionMarginCount = serialDirectionMarginCount;
	}
	
	
}
