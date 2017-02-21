package com.cloudtrading.warehouse.utils;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.ImageHelper;

import com.cloudtrading.warehouse.common.ErrorCode;
import com.cloudtrading.warehouse.common.SystemConstants;
import com.cloudtrading.warehouse.model.Corundum;
import com.cloudtrading.warehouse.model.GoldCopper;
import com.cloudtrading.warehouse.model.SilverAlloy;
import com.cloudtrading.warehouse.model.Window;
import com.huisa.common.exception.ServiceException;

public class TessreactUtil {

	private TessreactUtil() {
	}

	private static final Tesseract tessreact = new Tesseract();
	public static class Holder{
		private static Robot robot = null;  //robot不能直接饿汉加载
		private static Robot getRobot() throws ServiceException{
			try {
				if(robot==null){
					synchronized (Holder.class) {
						if(robot==null){
							robot = new Robot();
							ImageIO.scanForPlugins();
						}
					}
				}
			} catch (AWTException e) {
				throw new ServiceException(ErrorCode.CODE_JAVA,"TessreactUtil.$Holder 获取Robot失败");
			}
			return robot;
		}
	}
		
	static {
		tessreact.setDatapath(SystemConstants.SYS_TESSREACT_DATAPATH);//"C:/tessdata"
	}

	/**
	 * 图片转换为数字
	 * @param imageFile
	 * @param length
	 * @return
	 */
	private static int tessreactToNumber(File imageFile, int length) {
		int numInteger = 0;
		try {
			String result = tessreact.doOCR(imageFile);
			numInteger = Integer.parseInt(result.replaceAll(" ", "").substring(0, length));
		} catch (TesseractException e) {
			System.err.println(e.getMessage());
		}
		return numInteger;
	}
	/**
	 * 图片转换为字符串
	 * @param imageFile
	 * @param length
	 * @return
	 */
	private static String tessreactToString(File imageFile,int length){
		String string="";
		try {
			string = tessreact.doOCR(imageFile);
			if(length>=string.length()){
				return string;
			}
			string=string.substring(0,length);
		} catch (TesseractException e) {
			System.err.println(e.getMessage());
		}
		return string;
	}
	/**
	 * 抓取对应位置内的图片，并转换为字符串
	 * @param filePath
	 * @param length
	 * @param x
	 * @param y
	 * @param width
	 * @param higth
	 * @return
	 * @throws ServiceException
	 * @throws IOException
	 */
	public static String catchString(String filePath,int length,int x,int y,int width,int higth) throws ServiceException, IOException{
		File imageFile = catchWindowScreen(filePath, x, y, width, higth);
		return tessreactToString(imageFile, length);
	}
	/**
	 * 抓取对应位置内的图片，并转换为数字
	 * @param filePath
	 * @param numberLength
	 * @param x
	 * @param y
	 * @param width
	 * @param higth
	 * @return
	 * @throws IOException
	 * @throws ServiceException
	 */
	public static int catchNumber(String filePath,int numberLength,int x,int y,int width,int higth) throws IOException, ServiceException {
		File imageFile = catchWindowScreen(filePath, x, y, width, higth);
		return tessreactToNumber(imageFile, numberLength);
	}

	/**
	 * 截图保存并放大
	 * @param filePath
	 * @param x
	 * @param y
	 * @param width
	 * @param higth
	 * @return
	 * @throws ServiceException
	 * @throws IOException
	 */
	public static File catchWindowScreen(String filePath, int x, int y,
			int width, int higth) throws ServiceException, IOException {
		BufferedImage bfImage = Holder.getRobot().createScreenCapture(new Rectangle(
				x, y, width, higth));
		bfImage = ImageHelper.getScaledInstance(bfImage, width * 5,
				higth * 5);
		File imageFile = new File(filePath);
		if(!imageFile.getParentFile().exists()){
			imageFile.getParentFile().mkdirs();
		}
		ImageIO.write(bfImage, "png", imageFile);
		return imageFile;
	}
	
	/**
	 * 划线
	 * @param filePath
	 * @return
	 * @throws ServiceException
	 * @throws IOException
	 */
	public static File catchScreen(String filePath) throws ServiceException, IOException {
		//Dimension定义图片的尺寸,Toolkit 定义的一些方法能直接查询本机操作系统。该句的意义就是获得系统屏幕尺寸，保存的Dimension类型的screenSize里面
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		//Rectangle 指定了坐标空间中的一个区域,根据宽度和高度可以定义一个区域
		Rectangle screenRectangle = new Rectangle(screenSize);
		
		BufferedImage bfImage = Holder.getRobot().createScreenCapture(new Rectangle(
				screenRectangle));
		File imageFile = new File(filePath);
		if(!imageFile.getParentFile().exists()){
			imageFile.getParentFile().mkdirs();
		}
		Graphics2D g2d = bfImage.createGraphics();
		//g2d.drawImage(imageFile,0,0,null);
		g2d.drawLine(Window.x,Window.y,Window.x,Window.y+Window.higth);
		g2d.drawLine(Window.x,Window.y,Window.x+Window.width,Window.y);
		
		g2d.drawLine(Window.x,Window.y+Window.higth,Window.x+Window.width,Window.y+Window.higth);
		g2d.drawLine(Window.x+Window.width,Window.y,Window.x+Window.width,Window.y+Window.higth);
		
		g2d.drawLine(Window.x+Window.width,550, Window.x+Window.width, Window.buttonOpeny+10);
		g2d.drawLine(Window.x+Window.width,550, Window.x+Window.width+100, 550);
		g2d.dispose();
		ImageIO.write(bfImage, "png", imageFile);
		return imageFile;
	}
	
	
	public static void main(String[] args) throws ServiceException, IOException {
		int value1 = TessreactUtil.catchNumber(SilverAlloy.position, SilverAlloy.digit,SilverAlloy.x, SilverAlloy.y, SilverAlloy.width, SilverAlloy.higth);
		int value2= TessreactUtil.catchNumber(Corundum.position, Corundum.digit,Corundum.x, Corundum.y, Corundum.width, Corundum.higth);
		int value3 = TessreactUtil.catchNumber(GoldCopper.position, GoldCopper.digit,GoldCopper.x, GoldCopper.y, GoldCopper.width, GoldCopper.higth);

		System.out.println(value1+" " +value2+ " "+value3);
	}


	public static void createSreen() throws ServiceException, IOException {
		int x=10;
		int y=20;
		int width=650;
		int higth=550;
		
		//TessreactUtil.catchWindowScreen("C:\\Temp\\window.png", x, y, width, higth);
		TessreactUtil.catchScreen("C:\\Temp\\window.png");
	}

}
