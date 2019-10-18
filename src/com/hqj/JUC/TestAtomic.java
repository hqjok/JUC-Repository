package com.hqj.JUC;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 一、i++原子性的问题，j = i++实际上有三步操作 1、int temp = i; 2、i = i+1; 3、j = temp;
 * 二、jdk1.5之后JUC下的atomic包提供了常用的原子变量 
 * 1、操作的数使用了volatile修饰，保证了内存可见性
 * 2、CAS（compare-and-swap）算法保证了数据的原子性 CAS算法是硬件对于并发操作共享数据的支持 
 * CAS包含了三个操作①内存值V②预估值A（即第二次取内存值）③更新值B 原理：当且仅当V==A时，V=B，否则不做任何操作
 * 
 * @author hqj
 *
 */
public class TestAtomic {
	public static void main(String[] args) {
		AtomicDemo atomicDemo = new AtomicDemo();
		for (int i = 0; i < 10; i++) {
			new Thread(atomicDemo).start();
		}

	}
}

class AtomicDemo implements Runnable {
	//JUC下的atomic,
	AtomicInteger addNumber = new AtomicInteger();

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + " " + getIncrNumber());
	}

	public int getIncrNumber() {
		return addNumber.getAndIncrement();
	}
}
