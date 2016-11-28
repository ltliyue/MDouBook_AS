package com.doubook.thread;

import com.doubook.utiltools.LogsUtils;

import java.util.concurrent.ExecutorService;

/**
 * 线程处理类
 * 
 * @author liyue
 */
public class ExecutorProcessFixedPool {

	private ExecutorService executor_fixed;
	private static ExecutorProcessFixedPool pool_fixed = new ExecutorProcessFixedPool();
	private final int threadMax = 6;

	private ExecutorProcessFixedPool() {
		// System.out.println("threadMax>>>>>>>" + threadMax);
		executor_fixed = ExecutorServiceFactory.getInstance().createSingleThreadExecutor();
	}

	public static ExecutorProcessFixedPool getInstance() {
		return pool_fixed;
	}

	/**
	 * 关闭线程池，这里要说明的是：调用关闭线程池方法后，线程池会执行完队列中的所有任务才退出
	 * 
	 * @author SHANHY
	 * @date 2015年12月4日
	 */
	public void shutdown() {
		LogsUtils.e("shutdown!!!!!");
		// executor.shutdown();
		executor_fixed.shutdown();
	}

	/**
	 * 直接提交任务到线程池，无返回值
	 * 
	 * @param task
	 * @author SHANHY
	 * @date 2015年12月4日
	 */
	public void execute(Runnable task) {
		executor_fixed.execute(task);
	}

}