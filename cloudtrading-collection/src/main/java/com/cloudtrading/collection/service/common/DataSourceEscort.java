/**  
 * Project Name:test  
 * File Name:DBEscort.java  
 * Package Name:com.huisa.test  
 * Date:2014年12月23日下午2:25:15  
 * Copyright (c) 2014, vipshop.com All Rights Reserved.  
 *  
 */

package com.cloudtrading.collection.service.common;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ClassName: DataSourceEscort <br/>
 * Function: 数据源保护者(在缓存没有命中时，保障在多个相同查询条件的并发访问情况下，只有1个线程直接访问数据源，其他线程等待结果) <br/>
 * date: 2014年12月23日 下午2:25:15 <br/>
 * 
 * @author yuhui.mo
 * @version
 * @param <K> 获取数据的入参(多个参数时建议使用Map)
 * @param <V> 获取的数据结果
 * @since JDK 1.6
 */
public class DataSourceEscort<K, V> {
    // 存储每个key对应Lock的容器
    private final Map<String, LoadLock<V>> TASK_LOCK_CONTAINER = new ConcurrentHashMap<String, LoadLock<V>>();
    // 操作TASK_LOCK_CONTAINER容器的同步锁
    private final Object CONTAINER_LOCKER = new Object();
    // 当缓存没有数据时，其他等待线程等待load数据的最大时长
    private int waitTime;
    // 等待的时间单位
    private TimeUnit timeUnit;
    // 数据加载者
    DataLoader<K, V> dataLoader;
    // 缓存操作者
    CacheOperator<V> cacheOperator;

    /**
     * 创建数据源保护者(在缓存没有命中时，保障在多个相同查询条件的并发访问情况下，只有1个线程直接访问数据源，其他线程等待结果).
     * 
     * @param waitTime 当缓存没有数据时，其他等待线程等待load数据的最大时长
     * @param timeUnit 等待的时间单位
     * @param dataLoader 数据加载处理者
     * @param cacheOperator 缓存操作者
     * @throws Exception
     */
    public DataSourceEscort(int waitTime, TimeUnit timeUnit, DataLoader<K, V> dataLoader, CacheOperator<V> cacheOperator) {
        this.waitTime = waitTime;
        this.timeUnit = timeUnit;
        this.dataLoader = dataLoader;
        this.cacheOperator = cacheOperator;
    }

    /**
     * get:获取数据，先从缓存获取，如果缓存没有则从数据源获取(在多个相同查询条件的并发访问时，只有1个线程直接访问数据源，其他线程等待结果). <br/>
     * 
     * @author yuhui.mo
     * @param params 获取数据的入参(多个参数时建议使用Map)
     * @param uniqueKey 该数据的唯一标识(根据请求参数生成)
     * @return
     * @throws Exception
     * @since JDK 1.6
     */
    public V get(K params, String uniqueKey) throws Exception {
        if (uniqueKey == null) {
            throw new Exception("uniqueKey can't be null!");
        }
        // 先从缓存中获取
        V value = cacheOperator.get(uniqueKey);
        if (value == null) {
            // 代码说明：TASK_LOCK_CONTAINER为1个容器，存储每个uniqueKey对应load缓存的锁；保证每1个uniqueKey只有1个线程load缓存 其他线程等待load的结果
            boolean loadTaskExists = false;
            LoadLock<V> loadLock = null;
            synchronized (CONTAINER_LOCKER) {
                if (TASK_LOCK_CONTAINER.containsKey(uniqueKey)) {
                    loadTaskExists = true;
                    loadLock = TASK_LOCK_CONTAINER.get(uniqueKey);
                } else {
                    loadTaskExists = false;
                    loadLock = new LoadLock<V>();
                    loadLock.lock();
                    TASK_LOCK_CONTAINER.put(uniqueKey, loadLock);
                }
            }
            // 处理从数据源加载数据
            if (!loadTaskExists) {
                try {
                    // 再次从缓存中获取看看是否已经存在
                    value = cacheOperator.get(uniqueKey);
                    if (value == null) {
                        // 从数据源加载数据
                        value = dataLoader.load(params);
                    }
                    if (value != null) {
                        // 加载成功，将结果放入锁中供其他线程使用
                        loadLock.setValue(value);
                        cacheOperator.save(uniqueKey, value);
                    }
                } finally {
                    synchronized (CONTAINER_LOCKER) {
                        TASK_LOCK_CONTAINER.remove(uniqueKey);
                    }
                    loadLock.unlock();
                }
            }
            // 已经有其他线程在load缓存，等待它的结果
            else {
                try {
                    if (loadLock.tryLock(waitTime, timeUnit)) {
                        // 加载数据的线程已加载完成,从锁中获取加载的结果
                        value = loadLock.getValue();
                        loadLock.unlock();
                    }
                } catch (InterruptedException e) {
                }
            }
        }
        return value;
    }

    /**
     * 调试用. <br/>
     */
    @Deprecated
    public int getLockContainerSize() {
        return TASK_LOCK_CONTAINER.size();
    }

    class LoadLock<T> extends ReentrantLock {
        private static final long serialVersionUID = -4039629536174667756L;
        private T value;

        public LoadLock() {
            super(true);
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

    }

    /**
     * ClassName: DataLoader <br/>
     * Function: 数据加载处理者. <br/>
     * date: 2014年12月24日 下午3:37:24 <br/>
     * 
     * @author yuhui.mo
     * @version DataSourceEscort@param <K> 获取数据的入参(多个参数时建议使用Map)
     * @version DataSourceEscort@param <V> 获取的数据结果
     * @since JDK 1.6
     */
    public interface DataLoader<K, V> {
        public V load(K params) throws Exception;
    }

    /**
     * ClassName: CacheOperator <br/>
     * Function: 缓存操作者. <br/>
     * date: 2014年12月24日 下午3:38:44 <br/>
     * 
     * @author yuhui.mo
     * @version DataSourceEscort@param <V> 获取的数据结果
     * @since JDK 1.6
     */
    public interface CacheOperator<V> {
        public V get(String uniqueKey);

        public boolean save(String uniqueKey, V value);
    }

}
