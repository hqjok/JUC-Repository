package com.hqj.JUC;

import java.util.concurrent.CountDownLatch;

/**
 * 闭锁：CountDownLatch,JUC包下的一个同步辅助类工具 在完成一组其他线程操作之前，允许一个或多个线程进行等待 （多用于做并发操作的时间统计）
 * 
 * @author hqj
 *
 */
public class TestCountDownLatch {
	public static void main(String[] args) {
		// 需要传入一个int值，表示多少个线程在跑，做计算用，与下面的线程个数相同
		CountDownLatch countDownLatch = new CountDownLatch(5);
		LatchDemo latchDemo = new LatchDemo(countDownLatch);
		long start_time = System.currentTimeMillis();
		for (int i = 0; i < 5; i++) {
			new Thread(latchDemo).start();
		}
		// 等待5个线程完成操作
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long end_time = System.currentTimeMillis();
		System.out.println("===============耗费时间=================");
		System.out.println(end_time - start_time + "毫秒");
	}
}

class LatchDemo implements Runnable {

	private CountDownLatch countDownLatch;

	public LatchDemo(CountDownLatch countDownLatch) {
		this.countDownLatch = countDownLatch;
	}

	@Override
	public void run() {

		try {
			for (int i = 1; i <= 100000; i++) {
				if (i % 2 == 0) {
					System.out.println(Thread.currentThread().getName() + i);
				}
			}
		} finally {
			// 没结束一个线程操作就进行减一操作，直到为零，判断为可往下操作
			countDownLatch.countDown();
		}
	}
}