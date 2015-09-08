package com.example.async;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadManager {

	private static ThreadManager manager;

	private ThreadManager() {
		init();
	}

	/**
	 * 单利模式 多线程时，会创建多个对象
	 * 
	 * @return
	 */
	public static ThreadManager getInetance() {
		if (manager == null) {
			// 同步块，防止多线程
			synchronized (ThreadManager.class) {
				if (manager == null) {
					manager = new ThreadManager();
				}
			}
		}
		return manager;
	}

	private ExecutorService sersvice;

	/**
	 * 线程池的初始化
	 */
	private void init() {
		int threadNO = 2 * Runtime.getRuntime().availableProcessors() + 1;
		int max = threadNO >= 8 ? 8 : threadNO;
		sersvice = Executors.newFixedThreadPool(max);
	}

	/**
	 * 执行线程
	 * @param runnable
	 */
	public void execute(Runnable runnable) {
		this.sersvice.execute(runnable);
	}
}
