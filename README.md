# 对于JUC包的学习以及写的一些小Demo
1、volatile的可见性  
2、基于CAS原则的atomic包  
3、除开继承Thread类和实现Runable接口的另外两种创建线程方式：Callable和线程池  
4、Callable需要Future接口来接口返回值  
5、线程池的两个案例：newFixedThreadPool(5)和具有调度作用的newScheduledThreadPool(5)  
6、隐式锁synchronized和显示锁Lock 两种锁的消费者和提供者案例  
7、读写锁  
8、同步辅助工具：CountDownLatch  常用于线程统计时间  
9、另外还有一个面试题：三个线程按序打印ABCABCABC......使用Lock和Condition  
