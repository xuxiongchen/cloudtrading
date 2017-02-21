package com.cloudtrading.analysis.entity.portal;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.cloudtrading.pub.Direction;
import com.cloudtrading.pub.Position;
import com.cloudtrading.pub.Production;
import com.cloudtrading.pub.Production.Period;
import com.cloudtrading.warehouse.utils.DateFormatUtil;

public class Observe {
	private Position observePosition;		//观察的起点
	private boolean haveResult;			//是否有结果
	private boolean isEnd;					//观察是否结束
	private Direction direction;				//涨跌的结果
	private Period period;						//观察的时期长短
	private Production production;		//产品类型
	private String remark;
	private LinkedList<Position> recentPositions;		//缓存的最近股点
	private int recordCount;					//缓存的股点的数目
	private int calKCount;						//用于计算K值得股点数目
	
	public Observe(Production production, Period period, Position observerPosition){
		super();
		this.production = production;
		this.period = period;
		this.observePosition = observerPosition;
		this.recordCount = 30;
		this.calKCount = 5;
		recentPositions = new LinkedList<Position>();
	}
	
	/**
	 * 股点变化，处理观察过程
	 * 
	 * @param currentPosition
	 * @return TRUE 观察结束 false 观察未结束
	 */
	public boolean observerPosition(Position currentPosition){
		if(recentPositions.size() >= recordCount){
			recentPositions.pop();
		}
		recentPositions.push(currentPosition);
		int gap = currentPosition.getValue() - currentPosition.getValue();
		if(Math.abs(gap) >= production.getPeriod().get(period)){
			if(gap > 0){
				direction = Direction.INSCREASE;
			}else{
				direction = Direction.DESCREASE;
			}
			isEnd = true;
			haveResult = true;
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		String str = "观察点:" + observePosition. getValue()+ ","+DateFormatUtil.formatSimpleDate(new Date(observePosition.getValueTime() ))+
				", 是否有结果:"+ (haveResult?"是":"否") + ", 是否结束:" + (isEnd?"是":"否") + ", 方向:" + direction.getName()
				+",观察时长:"+getTime()/60 +"分钟"+ ", 周期:" + period.getName() + ", 产品:" + production.getName()
				+ ", 备注:" + remark;
		str += "\n最近点变化平均值:" + getAverageValue();
		str += ",上涨点数占百分比:" + getIncreasePercent();
		str += ",下跌点数占百分比:" + getDecreasePercent();
		str += "\n最近点之间K值:";
		List<Float> recentK = getRecentK();
		for(Float f : recentK){
			int f2 = (int) (f*1000);
			str += (float)(f2/1000) + ",";
		}
		return str;
	}

	/**
	 * 获取最近几个点的K值
	 * 
	 * @return
	 */
	public List<Float> getRecentK() {
		List<Float> kList = new ArrayList<Float>();
		if(recentPositions.size() > 1){
			for(int i = 1; ( i < recentPositions.size() && i < calKCount); i++){
				kList.add((float) ((recentPositions.get(i).getValue() - recentPositions.get(i-1).getValue()) / (recentPositions.get(i).getValueTime() - recentPositions.get(i-1).getValueTime()) * 10000));
			}
		}
		return kList;
	}

	public Position getObservePosition() {
		return observePosition;
	}

	public boolean isHaveResult() {
		return haveResult;
	}

	public void setHaveResult(boolean haveResult) {
		this.isEnd = true;
		this.haveResult = haveResult;
	}
	
	public boolean isEnd() {
		return isEnd;
	}

	public Direction getDirection() {
		return direction;
	}
	
	/**
	 * 获取变化平均值(实际平均值减去观察起点值，除以止损止盈点)
	 * 
	 * @return
	 */
	public float getAverageValue() {
		if(recentPositions.size() != 0){
			float total = 0.0f;
			for(Position position : recentPositions){
				total = total + position.getValue() - observePosition.getValue();
			}
			return total / recentPositions.size() / production.getPeriod().get(period);
		}
		return 0;
	}

	/**
	 * 获取观察的时间
	 * 
	 * @return
	 */
	public long getTime() {
		if(recentPositions.size() > 1){
			return (recentPositions.getLast().getValueTime() -  observePosition.getValueTime()) / 1000;
		}
		return 0;
	}

	/**
	 * 上涨的点数 占 全部点数的百分比
	 * 
	 * @return
	 */
	public float getIncreasePercent() {
		if(recentPositions.size() > 1){
			float inscreaseCount = 0;
			for(int i = 1; i < recentPositions.size(); i++){
				if(recentPositions.get(i).getValue() - recentPositions.get(i-1).getValue() > 0){
					inscreaseCount++;
				}
			}
			return inscreaseCount / recentPositions.size();
		}
		return 0;
	}

	/**
	 * 下跌的点数 占 全部点数的百分比
	 * 
	 * @return
	 */
	public float getDecreasePercent() {
		if(recentPositions.size() > 1){
			float descreaseCount = 0;
			for(int i = 1; i < recentPositions.size(); i++){
				if(recentPositions.get(i).getValue() - recentPositions.get(i-1).getValue() < 0){
					descreaseCount++;
				} 
			}
			return descreaseCount / recentPositions.size();
		}
		return 0;
	}

	public Period getPeriod() {
		return period;
	}

	public Production getProduction() {
		return production;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Queue<Position> getRecentPositions() {
		return recentPositions;
	}
}
