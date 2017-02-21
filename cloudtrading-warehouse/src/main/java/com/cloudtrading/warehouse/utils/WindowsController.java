package com.cloudtrading.warehouse.utils;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloudtrading.warehouse.model.Corundum;
import com.cloudtrading.warehouse.model.GoldCopper;
import com.cloudtrading.warehouse.model.SilverAlloy;
import com.cloudtrading.warehouse.model.Window;


public class WindowsController {
	public static void main(String[] args) throws Exception {
		//java.awt.Toolkit.getDefaultToolkit().beep();
		
		//inscreseGoldCopperReady();
		//submit();
		//descreseGoldCopperReady();
//		WindowsController.reOpenCCWindow();
//		int[] ks = new int[] { KeyEvent.VK_3, KeyEvent.VK_8,
//				KeyEvent.VK_7,KeyEvent.VK_6,KeyEvent.VK_1,KeyEvent.VK_3,
//				KeyEvent.VK_ENTER };
//		WindowsController.inputPassWord(ks);
		WindowsController.inscreseGoldCopperReady(0);
	}
	private static final Logger logger = LoggerFactory.getLogger(WindowsController.class);
	
	private static Robot rb=null;
	public static Robot getRobot() throws AWTException{
		if(rb==null){
			synchronized (WindowsController.class) {
				if(rb==null){
					rb=new Robot();
				}
			}
		}
		return rb;
	}
	/**
	 * 输入密码
	 * @param passWord
	 * @throws AWTException
	 */
	public static void inputPassWord(int[] passWord) throws AWTException{
		final Robot rb =getRobot();
		rb.mouseMove(Window.inputPassWordx,Window.inputPassWordy);
		pressMouse(rb, InputEvent.BUTTON1_MASK, 20);
		pressMouse(rb, InputEvent.BUTTON1_MASK, 20);
		pressKeys(rb,passWord,10);
	}
	
	/**
	 * 银基合金准备买涨
	 * @param separationLength 间距变化，位置下移，未产生订单时设定为0
	 * @throws AWTException
	 */
	public static void inscreseGoldCopperReady(int separationLength) throws AWTException{
		java.awt.Toolkit.getDefaultToolkit().beep();
		final Robot rb =getRobot();
		//找到买的位置
		rb.mouseMove(GoldCopper.x+10, GoldCopper.y+3);
		pressMouse(rb, InputEvent.BUTTON1_MASK, 20);
		rb.mouseMove(Window.inscreseButtonx, Window.inscreseButtony+separationLength);
		pressMouse(rb, InputEvent.BUTTON1_MASK, 20);
		logger.info("银基合金准备买涨");
	}
	
	/**
	 * 银基合金准备买跌
	 * @param separationLength 间距变化，位置下移，未产生订单时设定为0
	 * @throws AWTException
	 */
	public static void descreseGoldCopperReady(int separationLength) throws AWTException{
		java.awt.Toolkit.getDefaultToolkit().beep();
		final Robot rb =getRobot();
		rb.mouseMove(GoldCopper.x+10, GoldCopper.y+3);
		pressMouse(rb, InputEvent.BUTTON1_MASK, 20);
		rb.mouseMove(Window.descreseButtonx, Window.descreseButtony+separationLength);
		pressMouse(rb, InputEvent.BUTTON1_MASK, 20);
		logger.info("银基合金准备买跌");
	}
	
	public static void clickYes() throws AWTException{
		final Robot rb =getRobot();
		rb.mouseMove(Window.yesButtonx, Window.yesButtony);
		pressMouse(rb, InputEvent.BUTTON1_MASK, 20);
		pressMouse(rb, InputEvent.BUTTON1_MASK, 20);
		logger.info("点击同意规则");
	}
	
	/**
	 * 刚玉准备买涨
	 * @param separationLength 间距变化，位置下移，未产生订单时设定为0
	 * @throws AWTException
	 */
	public static void inscreseCorundumReady(int separationLength) throws AWTException{
		java.awt.Toolkit.getDefaultToolkit().beep();
		final Robot rb =getRobot();
		//找到买的位置
		rb.mouseMove(Corundum.x+10, Corundum.y+3);
		pressMouse(rb, InputEvent.BUTTON1_MASK, 20);
		rb.mouseMove(Window.inscreseButtonx, Window.inscreseButtony+separationLength);
		pressMouse(rb, InputEvent.BUTTON1_MASK, 20);
		logger.info("刚玉准备买涨");
	}
	
	/**
	 * 刚玉准备买跌
	 * @param separationLength 间距变化，位置下移，未产生订单时设定为0
	 * @throws AWTException
	 */
	public static void descreseCorundumReady(int separationLength) throws AWTException{
		java.awt.Toolkit.getDefaultToolkit().beep();
		final Robot rb =getRobot();
		rb.mouseMove(Corundum.x+10, Corundum.y+3);
		pressMouse(rb, InputEvent.BUTTON1_MASK, 20);
		rb.mouseMove(Window.descreseButtonx, Window.descreseButtony+separationLength);
		pressMouse(rb, InputEvent.BUTTON1_MASK, 20);
		logger.info("刚玉准备买跌");
	}
	
	/**
	 * 刚玉准备买涨
	 * @param separationLength 间距变化，位置下移，未产生订单时设定为0
	 * @throws AWTException
	 */
	public static void inscreseSilverAlloyReady(int separationLength) throws AWTException{
		java.awt.Toolkit.getDefaultToolkit().beep();
		final Robot rb =getRobot();
		//找到买的位置
		rb.mouseMove(SilverAlloy.x+10, SilverAlloy.y+3);
		pressMouse(rb, InputEvent.BUTTON1_MASK, 20);
		rb.mouseMove(Window.inscreseButtonx, Window.inscreseButtony+separationLength);
		pressMouse(rb, InputEvent.BUTTON1_MASK, 20);
		logger.info("刚玉准备买涨");
	}
	
	/**
	 * 刚玉准备买跌
	 * @param separationLength 间距变化，位置下移，未产生订单时设定为0
	 * @throws AWTException
	 */
	public static void descreseSilverAlloyReady(int separationLength) throws AWTException{
		java.awt.Toolkit.getDefaultToolkit().beep();
		final Robot rb =getRobot();
		rb.mouseMove(SilverAlloy.x+10, SilverAlloy.y+3);
		pressMouse(rb, InputEvent.BUTTON1_MASK, 20);
		rb.mouseMove(Window.descreseButtonx, Window.descreseButtony+separationLength);
		pressMouse(rb, InputEvent.BUTTON1_MASK, 20);
		logger.info("刚玉准备买跌");
	}
	/**
	 * 加注
	 * @param fillCount加注数目
	 * @param contractDepositNo 合约定金序号 1、2、3
	 * @throws AWTException
	 */
	public static void fill(int fillCount,int contractDeposit) throws AWTException{
		final Robot rb =getRobot();
		StringBuffer sbBuffer=new StringBuffer();
		if(contractDeposit==1){
			sbBuffer.append("加注短期");
			rb.mouseMove(Window.firstContractDeposit10x, Window.contractDeposity);
		}else if(contractDeposit==2){
			sbBuffer.append("加注中期");
			rb.mouseMove(Window.secondContractDeposit100x, Window.contractDeposity);
		}else if(contractDeposit==3){
			sbBuffer.append("加注长期");
			rb.mouseMove(Window.thirdContractDeposit500x, Window.contractDeposity);
		}
		pressMouse(rb, InputEvent.BUTTON1_MASK, 20);
		sbBuffer.append(fillCount);
		sbBuffer.append("注");
		rb.mouseMove(Window.addButtonx, Window.addButtony);
		for(int i=0;i<fillCount;i++){
			pressMouse(rb, InputEvent.BUTTON1_MASK, 20);
		}
		logger.info(sbBuffer.toString());
	}
	
	
	/**
	 * 点击确定
	 * 需要延时使用
	 */
	public static void submit() throws AWTException{
		final Robot rb =getRobot();
		rb.mouseMove(Window.submitButtonx, Window.submitButtony);
		pressMouse(rb, InputEvent.BUTTON1_MASK, 20);
		java.awt.Toolkit.getDefaultToolkit().beep();
		logger.info("确定");
	}
	public static void submitPassword() throws AWTException{
		final Robot rb =getRobot();
		rb.mouseMove(Window.inpustPassWordSubmitx, Window.inpustPassWordSubmity);
		pressMouse(rb, InputEvent.BUTTON1_MASK, 20);
		java.awt.Toolkit.getDefaultToolkit().beep();
		logger.info("确定输入密码");
	}
	/**
	 * 点击取消
	 * 需要延时使用
	 */
	public static void cancal() throws AWTException{
		final Robot rb =getRobot();
		rb.mouseMove(Window.cancalButtonx, Window.cancalButtony);
		pressMouse(rb, InputEvent.BUTTON1_MASK, 20);
		logger.info("取消");
	}
	
	/**
	 * 重新打开微信窗口
	 * @throws AWTException
	 */
	public static void reOpenCCWindow() throws AWTException{
		final Robot rb =getRobot();
		rb.mouseMove(Window.buttonClosex, Window.buttonClosey);
		pressMouse(rb, InputEvent.BUTTON1_MASK, 20);
		rb.delay(100);
		rb.mouseMove(Window.buttonOpenx,Window.buttonOpeny);
		pressMouse(rb, InputEvent.BUTTON1_MASK, 20);
	}
	
	// 鼠标单击,要双击就连续调用
	private static void pressMouse(Robot r, int m, int delay) {
		r.mousePress(m);
		r.delay(10);
		r.mouseRelease(m);
		r.delay(delay);
	}
	// 键盘输入
	private static void pressKeys(Robot r, int[] ks, int delay) {
		for (int i = 0; i < ks.length; i++) {
			r.keyPress(ks[i]);
			r.delay(10);
			r.keyRelease(ks[i]);
			r.delay(delay);
		}
	}
	//demo
	private static void demo() throws AWTException {
		final Robot rb =getRobot();
		new Thread() {
			public void run() {
				rb.delay(2000);
				// 回车
				rb.keyPress(KeyEvent.VK_ENTER);
				rb.keyRelease(KeyEvent.VK_ENTER);
			}
		}.start();
		JOptionPane.showMessageDialog(null,
				"以下程序自动执行,包括本对话框,请不必进行人为干预.\n如果不能正常执行程序,请先关闭输入法");

		// 设置开始菜单的大概位置
		int x = 40;
		int y = Toolkit.getDefaultToolkit().getScreenSize().height - 10;

		// 鼠标移动到开始菜单,
		rb.mouseMove(x, y);
		rb.delay(500);

		// 单击三次开始菜单
		for (int i = 0; i < 3; i++)
			pressMouse(rb, InputEvent.BUTTON1_MASK, 500);
		rb.delay(1000);

		// 运行CMD命令 r cmd enter
		int[] ks = { KeyEvent.VK_R, KeyEvent.VK_C, KeyEvent.VK_M,
				KeyEvent.VK_D, KeyEvent.VK_ENTER, };
		pressKeys(rb, ks, 500);
		rb.mouseMove(400, 400);
		rb.delay(500);
		// 运行DIR命令 dir enter
		ks = new int[] { KeyEvent.VK_D, KeyEvent.VK_I, KeyEvent.VK_R,
				KeyEvent.VK_ENTER };
		pressKeys(rb, ks, 500);
		rb.delay(1000);
		// 运行CLS命令 cls enter
		ks = new int[] { KeyEvent.VK_C, KeyEvent.VK_L, KeyEvent.VK_S,
				KeyEvent.VK_ENTER };
		pressKeys(rb, ks, 500);
		rb.delay(1000);
		// 运行EXIT命令 exit enter
		ks = new int[] { KeyEvent.VK_E, KeyEvent.VK_X, KeyEvent.VK_I,
				KeyEvent.VK_T, KeyEvent.VK_ENTER };
		pressKeys(rb, ks, 500);
		rb.delay(1000);

		// 右键测试
		x = Toolkit.getDefaultToolkit().getScreenSize().width - 10;
		rb.mouseMove(x, y);
		// 如果是双键鼠标,请改用InputEvent.BUTTON2_MASK试试,我没有这种鼠标
		pressMouse(rb, InputEvent.BUTTON3_MASK, 500);
		// 显示日期调整对话框 a
		pressKeys(rb, new int[] { KeyEvent.VK_A }, 1000);
		rb.delay(2000);
		pressKeys(rb, new int[] { KeyEvent.VK_ESCAPE }, 0);
		rb.delay(1000);
		new Thread() {
			public void run() {
				rb.delay(1000);
				// 回车
				rb.keyPress(KeyEvent.VK_ENTER);
				rb.keyRelease(KeyEvent.VK_ENTER);
			}
		}.start();
		JOptionPane.showMessageDialog(null, "演示完毕!");
	}

}
