package com.cloudtrading.warehouse.model;

/**
 * 银基合金基本数据
 * @author chenxu
 *
 */
public interface GoldCopper {

	//屏幕的位置（92，190）
		int x=330+Window.x;
		int y=170+Window.y;
		
		
		//图片长度宽度
		int width=50;
		int higth=20;
		
		//合约定金
		int contractDeposit1=10;
		int contractDeposit2=50;
		int contractDeposit3=100;
		
		//止盈/止损点
		int stopPoint1=4;
		int stopPoint2=6;
		int stopPoint3=10;
		
		//数据位数
		int digit=4;
		
		//本地替换数值图片位置
		String position="C://temp//number2.png";
}
