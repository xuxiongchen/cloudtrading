package com.cloudtrading.collection.utils;


import javax.swing.*;

import com.huisa.common.exception.ServiceException;

import sun.text.normalizer.IntTrie;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DataReceiver extends JPanel {
    private List<Integer> values;                      // 保存接收到的数据的容器.
    private static final int MAX_VALUE = 3100;          // 接收到的数据的最大值.
    private static final int MAX_COUNT_OF_VALUES = 60; // 最多保存数据的个数.

    public DataReceiver() {
        values = Collections.synchronizedList(new ArrayList<Integer>());

        // 使用一个线程模拟产生数据.
        new Thread(new Runnable() {
            public void run() {
                Random rand = new Random();
                try {
    			 	Thread.sleep(2000);
    			 	int count=0;
    	            while(true){
    	            	//Thread.sleep(333);
    	            	int num=11-count;
    	            	count=count+50;
    	            	addValue(num); // 模拟接收并放到容器里.
    	            	System.out.println(num+"   "+num/400);
    	            	repaint();
    	            	Thread.sleep(250);
    					
    				}
	    		}  catch (InterruptedException e) {
	    			e.printStackTrace();
	    		} }}
        ).start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();
        int xDelta = w / MAX_COUNT_OF_VALUES;
        int length = values.size();

        for (int i = 0; i < length - 1; ++i) {
            g2d.drawLine(xDelta * (MAX_COUNT_OF_VALUES - length + i), normalizeValueForYAxis(values.get(i), h),
                    xDelta * (MAX_COUNT_OF_VALUES - length + i + 1), normalizeValueForYAxis(values.get(i + 1), h));
        }
    }

    /**
     * 接收到的数据放入内存.
     * @param value
     */
    private void addValue(int value) {
        // 循环的使用一个接收数据的空间.
        // 最好是实现一个循环数组，而不是偷懒的使用ArrayList.
        if (values.size() > MAX_COUNT_OF_VALUES) {
            values.remove(0);
        }

        values.add(value);
    }

    /**
     * 规一化y轴方向的值. 使得value在y轴的值为[0, height]之间.
     *
     * @param value
     * @param height
     * @return
     */
    private int normalizeValueForYAxis(int value, int height) {
        return (int) ((double) height / MAX_VALUE * value);
    }

    private static void createGuiAndShow() {
        JFrame frame = new JFrame("");

        frame.getContentPane().add(new DataReceiver());

        // Set frame's close operation and location in the screen.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        createGuiAndShow();
    }
}
