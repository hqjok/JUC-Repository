package com.hqj.JUC;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
/**
 * 具有调度作用的线程池
 * @author hqj
 *
 */
public class TestScheduledThreadPool {
	public static void main(String[] args) {
		ScheduledExecutorService pool = Executors.newScheduledThreadPool(5);
		for (int i = 0; i < 10; i++) {
			Future<Integer> future = pool.schedule(new Callable<Integer>() {
				
				private int sum = 0;
				
				@Override
				public Integer call() throws Exception {
					for (int i = 1; i < 101; i++) {
						sum += i;
					}
					return sum;
				}
				
			}, 1, TimeUnit.SECONDS); //各个线程延迟一秒执行，  需要用到TimeUtil工具包，也是JUC下的包
			try {
				System.out.println(future.get());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
 		}
		pool.shutdown();  //关闭线程池
	}
}
