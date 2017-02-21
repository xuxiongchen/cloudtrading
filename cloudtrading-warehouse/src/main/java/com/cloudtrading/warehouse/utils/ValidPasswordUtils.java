package com.cloudtrading.warehouse.utils;

import java.awt.AWTException;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloudtrading.warehouse.model.Window;
import com.cloudtrading.warehouse.utils.WindowsController;
import com.huisa.common.exception.ServiceException;

public class ValidPasswordUtils {
	private static final Logger logger = LoggerFactory.getLogger(ValidPasswordUtils.class);
	/**
	 *  返回true则需要输入密码
	 * @return
	 */
	public static boolean isNeedToInputPassword() {
		String string="iﬁﬁ";
		String filepath=Window.inputPasswordPath;
		String password="";
		try {
					password = TessreactUtil.catchString(filepath, 3, 
					Window.inputPassWordx, Window.inputPassWordy,
					Window.inputPassWordWidth, Window.inputPassWordHight);
		} catch (Throwable e) {
			logger.info("输入密码检测结果：无必要输入密码");
		} 
		if(password.equals(string)){
			logger.info("……需要输入密码……");
		}else{
			logger.info("……不需要输入密码……");
		}
		return password.equals(string);
	}
	/**
	 * 需要新建一个线程
	 * @throws ServiceException
	 * @throws IOException
	 * @throws AWTException
	 * @throws InterruptedException
	 */
	public static void inputPassword()throws ServiceException, IOException, AWTException, InterruptedException{
		logger.info("……开始验证是否需要输入密码……");
		WindowsController.cancal();
		WindowsController.inscreseGoldCopperReady(0);  //仅仅是测试
		logger.info("……仅仅是测试……");
		Thread.sleep(1200);
		WindowsController.inscreseGoldCopperReady(0);  //仅仅是测试
		Thread.sleep(1200);
		boolean isNeedInputPassword=ValidPasswordUtils.isNeedToInputPassword();
		if(isNeedInputPassword){
			Thread.sleep(100);
			int[] ks = new int[] { KeyEvent.VK_3, KeyEvent.VK_8,
			KeyEvent.VK_7,KeyEvent.VK_6,KeyEvent.VK_1,KeyEvent.VK_3,
			KeyEvent.VK_ENTER };
			WindowsController.inputPassWord(ks);
			logger.info("输入密码……");
			Thread.sleep(500);
			WindowsController.submitPassword();
			Thread.sleep(1000);
			WindowsController.clickYes();
		}else{
			//不需要输入密码
			WindowsController.clickYes();
			Thread.sleep(1000);
			WindowsController.cancal();
		}
		logger.info("…………验证结束…………");
	}
	public static void main(String[] args) throws ServiceException, IOException, AWTException, InterruptedException {
		ValidPasswordUtils.inputPassword();

	}
	
}
