package com.hqj.JUC;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/**
 * 1、线程池：提供了一个线程队列，队列中保存着所有等待态的线程。降低了创建和销毁线程的资源损耗，提高相应速度 
 * 2、Excutor:负责线程调度和使用的总接口
 * |-- ExcutorService : Excutor的子接口，线程池的主要接口 ******* 
 * |-- ThreadPoolExcutor：线程池的实现类 
 * |-- ScheduledExcutorService: ExcutorService的子接口，负责线程调度 
 * |-- ScheduledThreadPoolExcutor: 继承了ThreadPoolExcutor，实现了ScheduledExcutorService接口，具有使用和调度的功能 
 * 
 * 3、Excutors工具类：
 * |-- ExecutorService newFixedThreadpool()：固定大小线程池 
 * |-- ExecutorService newCachedThreadpool()：缓存线程池，线程池线程数量不固定，根据需求改大小 
 * |-- ExecutorService newSingleThreadpool()：线程池中只有一个线程
 * 
 * |-- 调度 
 * |-- ScheduledExcutorService newScheduledThreadPool()：具有调度功能的线程池
 * 
 * @author hqj
 *
 */
public class TestThreadPool {
	public static void main(String[] args) {
		ExecutorService pool = Executors.newFixedThreadPool(5);  //五个固定线程的线程池
		ArrayList<Future> futures = new ArrayList<>();           //存放返回的future
		for (int i = 0; i < 10; i++) {
			Future<Integer> future = pool.submit(new Callable<Integer>() {   //计算1～100的总数
				private int sum = 0;
				
				@Override
				public Integer call() throws Exception {
					for (int j = 1; j < 101; j++) {
						sum += j;
					}
					return sum;
				}
				
			});
			futures.add(future);
		}
		pool.shutdown();  //线程池关闭
		for (Future future : futures) {
			try {
				System.out.println(future.get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
	}
}

//线程实现
class ThreadPoolDemo implements Runnable {
	@Override
	public void run() {
		for (int i = 0; i < 20; i++) {
			System.out.println(Thread.currentThread().getName() + " " + i);
		}
	}
}
