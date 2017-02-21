package com.cloudtrading.analysis.utils;

import java.awt.AWTException;
import java.io.IOException;

import com.cloudtrading.pub.Direction;
import com.cloudtrading.pub.Production;
import com.cloudtrading.pub.Production.Period;
import com.cloudtrading.warehouse.utils.ValidPasswordUtils;
import com.cloudtrading.warehouse.utils.WindowsController;
import com.huisa.common.exception.ServiceException;


//设定marginalValue=6,此算法为银基合金最优算法！！
public class GoldCopperAnalyse extends BaseAnalyse {
	
	private int destionValue = 30;				//中金铜短期止损止盈
	private int marginalValue;					//分析预测临界值

	public GoldCopperAnalyse(Production production, int marginalValue,int destionValue) {
		super(production);
		// TODO Auto-generated constructor stub
		this.destionValue=destionValue;
		this.marginalValue = marginalValue;
	}

	@Override
	protected void calculate(){
		// TODO Auto-generated method stub
		int gap = super.getCurrentPosition().getValue() - super.getCurrentObservePosition().getValue();
		if(Math.abs(gap) >= destionValue){
			if(gap > 0){
				super.makeObserver(Direction.INSCREASE);
			}else{
				super.makeObserver(Direction.DESCREASE);
			}
			if(super.getSameDirectionCount() == marginalValue-2){ //提前准备
//准备买 下注
//1.输入密码
//2.同意用户协议
//								try {
//									ValidPasswordUtils.inputPassword(); //验证并输入密码
//								} catch (ServiceException e1) {
//									e1.printStackTrace();
//								} catch (IOException e1) {
//									e1.printStackTrace();
//								} catch (AWTException e1) {
//									e1.printStackTrace();
//								} catch (InterruptedException e1) {
//									e1.printStackTrace();
//								}
								Direction readyDirection=super.getContratyDirection(super.getLastDirection());
//								try {
//					
//								if(readyDirection==Direction.INSCREASE){
////买涨
//										WindowsController.inscreseGoldCopperReady(0);
//									}else if(readyDirection==Direction.DESCREASE){
//										WindowsController.descreseGoldCopperReady(0);
//									}
//								} catch (AWTException e) {
//									e.printStackTrace();
//								}
			}else if(super.getSameDirectionCount() == marginalValue){
								super.makeCalculate(super.getContratyDirection(super.getLastDirection()), Period.SHORT_PERIOD);
// 开始买
//TODO 设置购买的大小
//								try {
//									WindowsController.submit();
//								} catch (AWTException e) {
//									e.printStackTrace();
//								}
				
			}else if(super.getSameDirectionCount()==1){
// 取消
//								try {
//									WindowsController.cancal();
//								} catch (AWTException e) {
//									e.printStackTrace();
//								}
			}
//			System.err.println("2:"+super.getCurrentPosition().getValue()+" , "
//			   +super.getCurrentObservePosition().getValue()+" "+super.getSameDirectionCount());
		}
	}

	public void reReadyForCreate() {
		// TODO Auto-generated method stub
		
	}

}
