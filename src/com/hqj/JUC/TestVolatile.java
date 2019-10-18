package com.hqj.JUC;

/**
 * volatile关键字保证数据的可见性，相较于synchronized轻量级
 * 1.不具备互斥性，即不保证不同线程互不影响
 * 2.不能保证数据的原子性
 * @author hqj
 *
 */
public class TestVolatile {

	public static void main(String[] args) {

		TestDemo td = new TestDemo();
		new Thread(td).start();
		while (true) {
			if (td.isFlag()) {
				System.out.println("============");
				break;
			}
		}

	}

}

class TestDemo implements Runnable {
	//volatile保证了线程间的可见性
	private volatile boolean flag = false;

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	@Override
	public void run() {

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//显示可见
		flag = true;

		System.out.println("flag == " + isFlag());

	}

}