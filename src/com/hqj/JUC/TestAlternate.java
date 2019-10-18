package com.hqj.JUC;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 1、Condition能创建多个，用于进程间的通信，可以实现交互打印
 * 2、这道题目是春招在凡科的题目，当初不会做，现在学了JUC包之后，觉得挺简单，还是需要多学习，机会不会等人，永远做好准备
 * @author hqj
 *
 */
public class TestAlternate {
	public static void main(String[] args) {
		AlternateDemo alternateDemo = new AlternateDemo();
		//A线程
		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 1; i <= 5; i++) {// 外循环次数
					alternateDemo.loopA(i); // 传入i，打印出外循环次数
				}
			}
		}, "A").start();
		//B线程
		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 1; i <= 5; i++) {// 外循环次数
					alternateDemo.loopB(i); // 传入i，打印出外循环次数
				}
			}
		}, "B").start();
		//C线程
		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 1; i <= 5; i++) {// 外循环次数
					alternateDemo.loopC(i); // 传入i，打印出外循环次数
					System.out.println("=============================");
				}

			}
		}, "C").start();
	}
}

/**
 * 逻辑类，loopA,loopB,loopC分别代表ABC各个字母的循环
 * @author hqj
 *
 */
class AlternateDemo {

	private int number = 1;// 判断该哪个字母循环的标记，1==>A 2==>B 3==>C
	private Lock lock = new ReentrantLock();  //可见锁
	private Condition condition1 = lock.newCondition();  //A
	private Condition condition2 = lock.newCondition();  //B
	private Condition condition3 = lock.newCondition();  //C

	// 循环A的打印
	public void loopA(int totalNumber) {
		lock.lock();
		try {
			if (number != 1) {  //不为A的number数，Condition1进入等待状态
				try {
					condition1.await();
				} catch (InterruptedException e) {

				}
			}
			for (int i = 0; i < 1; i++) {   //i值控制每次循环打印的字母次数
				System.out.println(Thread.currentThread().getName() + " : " + "循环次数" + totalNumber);
			}
			number = 2;   //A打印完  number置为2，唤醒condition2
			condition2.signal();
		} finally {
			lock.unlock();  //放到finally进行解锁，养成好习惯
		}
	}

	// 循环B的打印
	public void loopB(int totalNumber) {
		lock.lock();
		try {
			if (number != 2) {  //不为B的number数，Condition2进入等待状态
				try {
					condition2.await();
				} catch (InterruptedException e) {

				}
			}
			for (int i = 0; i < 1; i++) {  //i值控制每次循环打印的字母次数
				System.out.println(Thread.currentThread().getName() + " : " + "循环次数" + totalNumber);
			}
			number = 3;  //打印完B  number置为3，唤醒condition3
			condition3.signal();
		} finally {
			lock.unlock();
		}
	}

	// 循环C的打印
	public void loopC(int totalNumber) {
		lock.lock();
		try {
			if (number != 3) {   //不为C的number数，Condition2进入等待状态
				try {
					condition3.await();
				} catch (InterruptedException e) {

				}
			}
			for (int i = 0; i < 1; i++) {   //i值控制每次循环打印的字母次数
				System.out.println(Thread.currentThread().getName() + " : " + "循环次数" + totalNumber);
			}
			number = 1; //打印完C  number置为1，唤醒condition1
			condition1.signal();
		} finally {
			lock.unlock();
		}
	}
}
