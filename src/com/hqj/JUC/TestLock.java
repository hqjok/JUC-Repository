package com.hqj.JUC;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 解决多线程安全问题的三种方式 synchronized：隐式锁 1、synchronized代码块 2、synchronized同步方法
 * 
 * 3、jdk1.5以后提供了一种显示锁机制Lock 需要通过lock()方法进行上锁，unlock()进行解锁，解锁最好写在finally里面
 * 
 * @author hqj
 *
 */
public class TestLock {
	public static void main(String[] args) {
		Ticket ticket = new Ticket();
		for (int i = 1; i < 4; i++) {
			new Thread(ticket, i + "号窗口").start();
		}
	}
}

//购票
class Ticket implements Runnable {

	//初始化ticket为20
	private int ticket = 20;
	private Lock lock = new ReentrantLock(true);

	@Override
	public void run() {
		while (true) {
			//上鎖
			lock.lock();
			try {
				if (ticket > 0) {

					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
					}
					System.out.println(Thread.currentThread().getName() + "=====购票成功，余数为" + --ticket);

				} else
					break;
			} finally {
				//解锁，放在finally代码块里面
				lock.unlock();
			}
		}
	}

}
