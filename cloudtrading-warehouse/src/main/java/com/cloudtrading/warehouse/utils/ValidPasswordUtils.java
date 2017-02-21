package com.cloudtrading.warehouse.utils;

import java.awt.AWTException;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import com.cloudtrading.warehouse.model.Window;
import com.cloudtrading.warehouse.utils.WindowsController;
import com.huisa.common.exception.ServiceException;

public class ValidPasswordUtils {

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
			System.err.println("输入密码检测结果：无必要输入密码");
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
		WindowsController.cancal();
		WindowsController.inscreseGoldCopperReady(0);  //仅仅是测试
		Thread.sleep(400);
		WindowsController.inscreseGoldCopperReady(0);  //仅仅是测试
		Thread.sleep(400);
		boolean isNeedInputPassword=ValidPasswordUtils.isNeedToInputPassword();
		if(isNeedInputPassword){
			Thread.sleep(100);
			int[] ks = new int[] { KeyEvent.VK_3, KeyEvent.VK_8,
			KeyEvent.VK_7,KeyEvent.VK_6,KeyEvent.VK_1,KeyEvent.VK_3,
			KeyEvent.VK_ENTER };
			WindowsController.inputPassWord(ks);
			WindowsController.submitPassword();
			Thread.sleep(500);
			WindowsController.clickYes();
		}else{
			//不需要输入密码
			WindowsController.clickYes();
			Thread.sleep(500);
			WindowsController.cancal();
		}
	}
	public static void main(String[] args) throws ServiceException, IOException, AWTException, InterruptedException {
		ValidPasswordUtils.inputPassword();

	}
	
}
