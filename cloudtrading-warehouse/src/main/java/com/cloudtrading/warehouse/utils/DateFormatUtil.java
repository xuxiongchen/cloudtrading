package com.cloudtrading.warehouse.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateFormatUtil {

	/**
	 * @return
	 */
	public static String getFormatDate(int year,int month,int day,int hour,int min,int second){
		Calendar cal = Calendar.getInstance(); 
		cal = new GregorianCalendar(year, month-1, day,hour,min,second);
		Date date=cal.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
		return format.format(date);
		
	}
	/**
	 * @return
	 */
	public static Date getDate(int year,int month,int day,int hour,int min,int second){
		Calendar cal = Calendar.getInstance(); 
		cal = new GregorianCalendar(year, month-1, day,hour,min,second);
		Date date=cal.getTime();
		return date;
		
	}
	
	/**
	 * @return
	 */
	public static Long getTime(int year,int month,int day,int hour,int min,int second){
		Calendar cal = Calendar.getInstance(); 
		cal = new GregorianCalendar(year, month-1, day,hour,min,second);
		Date date=cal.getTime();
		return date.getTime();
	}
	
	public static String formatDate(Date date){
		
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
		return format.format(date);
		
	}
	public static String formatSimpleDate(Date date){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return format.format(date);
	}
	public static void main(String[] args) {
		
		System.out.println(formatDate(new Date()));
		System.out.println(getFormatDate(2016,11,30,9,0,0));
		int month=11;
		int day=30;
		Long time9=DateFormatUtil.getTime(2016,month,day,9,0,0);
		Long time430=DateFormatUtil.getTime(2016,month,day+1,4,30,0);
		System.out.println(formatDate(new Date(time9)));
		System.out.println(formatDate(new Date(time430)));
	}
}
