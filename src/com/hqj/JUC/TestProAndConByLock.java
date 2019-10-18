package com.hqj.JUC;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 1、Lock的生产者消费者问题 2、Condition用于线程间通信，通过Lock的newCondition()方法获得
 * 3、Condition对应对象的wait、notify、notifyall方法有await、singal、singalall
 * 
 * @author hqj
 *
 */
public class TestProAndConByLock {
	public static void main(String[] args) {
		Clerk1 clerk = new Clerk1();
		//创建两个消费者线程两个生产者线程
		new Thread(new Productor1(clerk), "生产者A").start();
		new Thread(new Consumer1(clerk), "消费者B").start();
		new Thread(new Productor1(clerk), "生产者C").start();
		new Thread(new Consumer1(clerk), "消费者D").start();
	}
}

//店员：负责进货售货
class Clerk1 {
	//初始化为未有产品，之后设置循环次数允许每次只生产一次
	private int product = 0;
	//创建显示锁 lock
	private Lock lock = new ReentrantLock(true);
	//创建条件
	private Condition condition = lock.newCondition();

	// 获取产品
	public void get() {
		lock.lock();
		try {
			while (product >= 1) { // 为了避免虚假唤醒，即多个消费者先后抢到资源，notifyall后一起减一，导致product成了负数，根据官方文档，判断条件应该始终在while循环中去不断的判断
				System.out.println("产品已满！");
				try {
					condition.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println(Thread.currentThread().getName() + " : " + ++product);
			condition.signalAll();
		} finally {
			lock.unlock();
		}
	}

	// 卖出产品
	public void sale() {
		lock.lock();
		try {
			while (product <= 0) {
				System.out.println("产品缺货");
				try {
					condition.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println(Thread.currentThread().getName() + " : " + --product);
			condition.signalAll();
		} finally {
			lock.unlock();
		}
	}
}
//提供者
class Productor1 implements Runnable {

	private Clerk1 clerk;

	public Productor1(Clerk1 clerk) {
		this.clerk = clerk;
	}

	@Override
	public void run() {
		for (int i = 0; i < 20; i++) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			clerk.get();
		}
	}
}
//消费者
class Consumer1 implements Runnable {
	private Clerk1 clerk;

	public Consumer1(Clerk1 clerk) {
		this.clerk = clerk;
	}

	@Override
	public void run() {
		for (int i = 0; i < 20; i++) {
			clerk.sale();
		}
	}
}