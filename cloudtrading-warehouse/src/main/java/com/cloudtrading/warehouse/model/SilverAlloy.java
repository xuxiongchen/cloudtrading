package com.cloudtrading.warehouse.model;

/**
 * 中金铜基本数据
 * @author chenxu
 *
 */
public interface SilverAlloy {

	//屏幕的位置（92，190）
		int x=434+Window.x;
		int y=170+Window.y;
		
		
		//图片长度宽度
		int width=58;
		int higth=20;
		
		//合约定金
		int contractDeposit1=10;
		int contractDeposit2=50;
		int contractDeposit3=100;
		
		//止盈/止损点
		int stopPoint1=40;
		int stopPoint2=50;
		int stopPoint3=100;
		
		//数据位数
		int digit=5;
		
		//本地替换数值图片位置
		String position="C://temp//number3.png";
}
