package com.hqj.JUC;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 1、写写，读写   ===>  互斥
 * 2、读读        ===>  不互斥
 * @author hqj
 *
 */
public class TestReadWriteLock {
	public static void main(String[] args) {
		ReadWriteLockDemo readWriteLockDemo = new ReadWriteLockDemo();
		
		//读锁线程之间不受限制，但是与写入操作互斥
		for (int i = 0; i < 20; i++) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					readWriteLockDemo.get();
				}
			}, "读线程" + i).start();
		}
		
		//写线程
		new Thread(new Runnable() {

			@Override
			public void run() {
				readWriteLockDemo.set((int) (Math.random() * 100));
			}
		}, "写线程").start();
		//更好区别读写锁的区别设置的20个循环
		for (int i = 0; i < 20; i++) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					readWriteLockDemo.get();
				}
			}, "读线程" + i).start();
		}

	}
}

class ReadWriteLockDemo {

	private int number = 0;
	//读写锁
	private ReadWriteLock readWriteLock = new ReentrantReadWriteLock(true);
	//读取数据
	public void get() {
		// 读锁上锁
		readWriteLock.readLock().lock();
		try {
			System.out.println(Thread.currentThread().getName() + " : " + this.number);
		} finally {
			//读锁解锁
			readWriteLock.readLock().unlock();
		}
	}
	//写入数据
	public void set(int newNumber) {
		// 写锁上锁
		readWriteLock.writeLock().lock();
		try {
			this.number = newNumber;
			System.out.println(Thread.currentThread().getName() + "写入:");
		} finally {
			//写锁解锁
			readWriteLock.writeLock().unlock();
		}
	}

}