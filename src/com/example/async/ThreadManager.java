package com.example.async;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadManager {

	private static ThreadManager manager;

	private ThreadManager() {
		init();
	}

	/**
	 * ����ģʽ ���߳�ʱ���ᴴ���������
	 * 
	 * @return
	 */
	public static ThreadManager getInetance() {
		if (manager == null) {
			// ͬ���飬��ֹ���߳�
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
	 * �̳߳صĳ�ʼ��
	 */
	private void init() {
		int threadNO = 2 * Runtime.getRuntime().availableProcessors() + 1;
		int max = threadNO >= 8 ? 8 : threadNO;
		sersvice = Executors.newFixedThreadPool(max);
	}

	/**
	 * ִ���߳�
	 * @param runnable
	 */
	public void execute(Runnable runnable) {
		this.sersvice.execute(runnable);
	}
}
