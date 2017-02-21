package com.cloudtrading.warehouse.model;


/**
 * 屏幕中的位置
 * @author chenxu
 *
 */
public interface Window {
	
	int x=10;                             //窗体初始位置
	int y=40;
	int width=640;
	int higth=850;
	
	int buttonOpenx=Window.x+80;
	int buttonOpeny=Window.y+875;                  //打开微信窗口位置
	
	int buttonClosex=Window.x+623;
	int buttonClosey=Window.y+24;	              //关闭云交易位置
	
	
/**********************    建仓位置开始         ********************/
	int inscreseButtonx=Window.x+160;  //追涨按钮
	int inscreseButtony=Window.y+780;
	int descreseButtonx=Window.x+470;  //追跌按钮
	int descreseButtony=Window.y+780;
	
	int firstContractDeposit10x=Window.x+190;        //合约定金
	int secondContractDeposit100x=Window.x+370;
	int thirdContractDeposit500x=Window.x+550;
	int contractDeposity=Window.y+615;
	
	int lowButtonx=Window.x+150;          //减
	int lowButtony=Window.y+663;
	
	int addButtonx=Window.x+338;          //加注按钮
	int addButtony=Window.y+663;
	
	int firstStopLossx=Window.x+190;               //止损止盈位置
	int secondStopLossx=Window.x+370;
	int thirdStopLossx=Window.x+550;
	int stopLossy=Window.y+710;
	

	
	
	int cancalButtonx=Window.x+150; 
	int cancalButtony=Window.y+750;    //取消按钮
	int submitButtonx=Window.x+450;
	int submitButtony=Window.y+750;     //确定提交订单按钮

/**********************    建仓位置结束         ********************/
	
	
	int yesButtonx=Window.x+450;
	int yesButtony=Window.y+825;        //同意规定按钮
	
	
	int separationLength=40;              //每次添加一个订单后出现的间距变化
	

	
//TODO 修改
	int inputPassWordx=Window.x+270;   //输入密码
	int inputPassWordy=Window.y+424;
	int inputPassWordWidth=110;
	int inputPassWordHight=25;
	int inpustPassWordSubmitx=Window.x+320;
	int inpustPassWordSubmity=Window.y+510;
	String inputPasswordPath="C://Temp//passwordvalid.png";
	
}
