package com.hqj.JUC;

/**
 * synchronized的生产者消费者问题
 * @author hqj
 *
 */
public class TestProAndCon {
	public static void main(String[] args) {
		Clerk clerk = new Clerk();
		//创建两个消费者线程两个生产者线程
		new Thread(new Productor(clerk), "生产者A").start();
		new Thread(new Consumer(clerk), "消费者B").start();
		new Thread(new Productor(clerk), "生产者C").start();
		new Thread(new Consumer(clerk), "消费者D").start();
	}
}

//店员：负责进货售货
class Clerk {
	//初始化为未有产品，之后设置循环次数允许每次只生产一次
	private int product = 0;

	// 获取产品
	public synchronized void get() {
		while (product >= 1) {  //为了避免虚假唤醒，即多个消费者先后抢到资源，notifyall后一起减一，导致product成了负数，根据官方文档，判断条件应该始终在while循环中去不断的判断
			System.out.println("产品已满！");
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(Thread.currentThread().getName() + " : " + ++product);
		this.notifyAll();
	}

	// 卖出产品
	public synchronized void sale() {
		while (product <= 0) {
			System.out.println("产品缺货");
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(Thread.currentThread().getName() + " : " + --product);
		this.notifyAll();
	}
}

//提供者
class Productor implements Runnable {

	private Clerk clerk;

	public Productor(Clerk clerk) {
		this.clerk = clerk;
	}

	@Override
	public void run() {
		for (int i = 0; i < 20; i++) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			clerk.get();
		}
	}
}

//消费者
class Consumer implements Runnable {
	private Clerk clerk;

	public Consumer(Clerk clerk) {
		this.clerk = clerk;
	}

	@Override
	public void run() {
		for (int i = 0; i < 20; i++) {
			clerk.sale();
		}
	}
}