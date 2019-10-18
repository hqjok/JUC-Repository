package com.hqj.JUC;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/*
 * 1、创建线程的第三种方式：实现Callable接口
 * 2、Callable需要搭配FutureTask类工具进行实现，该累实现了Future接口，也是JUC包下的类
 */
public class TestCallable {
	
	public static void main(String[] args) {
		CallableDemo callableDemo = new CallableDemo();
		FutureTask<Integer> futureTask = new FutureTask<>(callableDemo);
		new Thread(futureTask).start();
		try {
			//接受返回的值
			int result = futureTask.get();
			//直到线程结束返回值才进行下面的操作，利用这个特性可以进行闭锁的操作
			System.out.println("===============================");
			System.out.println(result);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
	}
	
}
class CallableDemo implements Callable<Integer>{
	//统计1~100000的总数
	@Override
	public Integer call() throws Exception {
		int sum = 0;
		for(int i=1;i<=100000;i++){
			sum += i;
			System.out.println(Thread.currentThread().getName() + i);
		}
		return sum;
	}
	
}
