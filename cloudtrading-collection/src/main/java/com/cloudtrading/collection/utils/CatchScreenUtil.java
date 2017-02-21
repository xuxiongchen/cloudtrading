package com.cloudtrading.collection.utils;

import java.awt.AWTException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class CatchScreenUtil {
	/**
     * 指定屏幕区域截图，返回截图的BufferedImage对象
     * @param x
     * @param y
     * @param width
     * @param height
     * @return 
     */
    public static BufferedImage getScreenShot(int x, int y, int width, int height) {
        BufferedImage bfImage = null;
        try {
            Robot robot = new Robot();
            bfImage = robot.createScreenCapture(new Rectangle(x, y, width, height));
        } catch (AWTException e) {
            e.printStackTrace();
        }
        return bfImage;
    }
    
    /**
     * 指定屏幕区域截图，保存到指定目录
     * @param x
     * @param y
     * @param width
     * @param height
     * @param savePath - 文件保存路径
     * @param fileName - 文件保存名称
     * @param format - 文件格式
     */
    public  static void screenShotAsFile(int x, int y, int width, int height, String savePath, String fileName, String format) {
        try {
            Robot robot = new Robot();
            BufferedImage bfImage = robot.createScreenCapture(new Rectangle(x, y, width, height));
            File path = new File(savePath);
            File file = new File(path, fileName+ "." + format);
            ImageIO.write(bfImage, format, file);
        } catch (AWTException e) {
            e.printStackTrace();    
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * BufferedImage图片剪裁
     * @param srcBfImg - 被剪裁的BufferedImage
     * @param x - 左上角剪裁点X坐标
     * @param y - 左上角剪裁点Y坐标
     * @param width - 剪裁出的图片的宽度
     * @param height - 剪裁出的图片的高度
     * @return 剪裁得到的BufferedImage
     */
    public static BufferedImage cutBufferedImage(BufferedImage srcBfImg, int x, int y, int width, int height) {
        BufferedImage cutedImage = null;
        CropImageFilter cropFilter = new CropImageFilter(x, y, width, height);  
        Image img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(srcBfImg.getSource(), cropFilter));  
        cutedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);  
        Graphics g = cutedImage.getGraphics();  
        g.drawImage(img, 0, 0, null);  
        g.dispose(); 
        return cutedImage;
    }
}
