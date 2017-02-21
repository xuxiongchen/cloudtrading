package com.cloudtrading.collection.service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CatchDataScheduledService {
	private static final Logger logger = LoggerFactory.getLogger(CatchDataScheduledService.class);
	private static ScheduledExecutorService corundumScheduler = null;
	private static ScheduledExecutorService silverAlloyScheduler = null;
	private static ScheduledExecutorService goldCopperScheduler = null;
	
	@Autowired
	private CatchDataService catchDataService;
	
	public static volatile boolean isWork=false;
	
	public void stopWork(){
		isWork=false;
	}
	public void startWork(){
		isWork=true;
	}
	public void startCatchData(){
		runCorundumScheduler();
		//runSilverAlloyScheduler();
		//runGoldCopperScheduler();  无法并发操作
	}
	public void stopCatchData(){
		corundumScheduler.shutdown();
		silverAlloyScheduler.shutdown();
		goldCopperScheduler.shutdown();
	}
	
	
	public void runCorundumScheduler(){
		// 定时器
		silverAlloyScheduler = Executors.newScheduledThreadPool(6);
		silverAlloyScheduler.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				try {
					if(isWork){
						catchDataService.catchCorundumData();
						catchDataService.catchSilverAlloyData();
						catchDataService.catchGoldCopperData();
					}
				} catch (Throwable e) {
					logger.error("抓取数据失败!",e);
				}
			}
		}, 500, 500, TimeUnit.MICROSECONDS);
	}
	public void runSilverAlloyScheduler(){
		// 定时器
		goldCopperScheduler = Executors.newScheduledThreadPool(2);
		goldCopperScheduler.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				try {
					if(isWork){
						
					}
				} catch (Throwable e) {
					logger.error("抓取中金铜数据失败!",e);
				}
			}
		}, 500, 500, TimeUnit.MICROSECONDS);
	}
	public void runGoldCopperScheduler(){
		// 定时器
		corundumScheduler = Executors.newScheduledThreadPool(2);
		corundumScheduler.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				try {
					if(isWork){
						
					}
				} catch (Throwable e) {
					logger.error("抓取银基合金数据失败!",e);
				}
			}
		}, 500, 500, TimeUnit.MICROSECONDS);
	}
}
