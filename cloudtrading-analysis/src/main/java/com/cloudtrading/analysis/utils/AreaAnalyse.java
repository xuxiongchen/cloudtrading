package com.cloudtrading.analysis.utils;

import java.awt.AWTException;
import java.io.IOException;
import java.util.LinkedList;

import com.cloudtrading.pub.Direction;
import com.cloudtrading.pub.Position;
import com.cloudtrading.pub.Production;
import com.cloudtrading.pub.Production.Period;
import com.cloudtrading.warehouse.utils.ValidPasswordUtils;
import com.cloudtrading.warehouse.utils.WindowsController;
import com.huisa.common.exception.ServiceException;


/**
 * 面积计算分析法
 * @author chenxu
 *
 */
public class AreaAnalyse extends AreaBaseAnalyse {
	


	public AreaAnalyse(Production production, int positionListSize,int areaListSize) {
		super(production,positionListSize,areaListSize);
	}

	@Override
	protected void calculate(){
		LinkedList<Long> areaList=super.getAreaList();
		
		System.out.println(areaList);
		for(int i=0;i<areaList.size()-1;i++){
			Long area=areaList.get(i)/10000;
			Long lastArea=areaList.getLast()/10000;
			if(area.equals(lastArea)){
				int midIndex=(i+areaList.size()-1)>>>2;
				if(areaList.get(midIndex)/10000>area){
					super.makeCalculate(Direction.INSCREASE, Period.LONG_PERIOD);
				}else{
					super.makeCalculate(Direction.DESCREASE, Period.LONG_PERIOD);
				}
				System.out.println("找到对称点");
			}
		}
	}

}
