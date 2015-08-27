package com.chj.googleplay.manager;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @包名: com.chj.googleplay.manager
 * @类名: ThreadPoolProxy
 * @作者: 陈火炬
 * @创建时间 : 2015-8-25 下午9:43:37
 * 
 * @描述: 线程池
 * 
 * @SVN版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * 
 * @更新描述:
 */
public class ThreadPoolProxy
{
	private ThreadPoolExecutor	mExecutor;

	/** 池线程中保持(核心)的数量 */
	private int					corePoolSize	= 0;
	/** 线程池中允许的最大数量 */
	private int					maximumPoolSize	= 0;
	/** 线程池中线程存活的最长时间 */
	private long				keepAliveTime	= 0;

	public ThreadPoolProxy(int corePoolSize, int maximumPoolSize, long keepAliveTime) {
		this.corePoolSize = corePoolSize;
		this.maximumPoolSize = maximumPoolSize;
		this.keepAliveTime = keepAliveTime;
	}

	/** 执行线程池中的任务 */
	public Future<?> execute(Runnable task)
	{
		if (task == null) { return null; }

		if (mExecutor == null || mExecutor.isShutdown() || mExecutor.isTerminated())
		{
			TimeUnit unit = TimeUnit.SECONDS;
			// BlockingQueue<Runnable> workQueue = new
			// ArrayBlockingQueue<Runnable>(capacity);//固定大小的队列，FIFO
			BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>();// 不固定大小，FIFO
			// BlockingQueue<Runnable> workQueue = new
			// PriorityBlockingQueue<Runnable>();// 优先级的队列，FIFO
			// BlockingQueue<Runnable> workQueue = new
			// SynchronousQueue<Runnable>();// 同步队列，FIFO

			ThreadFactory threadFactory = Executors.defaultThreadFactory();
			// RejectedExecutionHandler handler = new
			// ThreadPoolExecutor.AbortPolicy();// 将异常抛出
			// RejectedExecutionHandler handler = new
			// ThreadPoolExecutor.CallerRunsPolicy();// 如果有异常，直接执行加入的任务
			// RejectedExecutionHandler handler = new
			// ThreadPoolExecutor.DiscardOldestPolicy();// 移除第一个任务，执行加入的任务
			RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardPolicy();// 不做处理

			mExecutor = new ThreadPoolExecutor(corePoolSize,// 工作线程的个数
												maximumPoolSize,// 最多有多少个工作线程
												keepAliveTime,//
												unit,// 时间参数，是keepAliveTime
												workQueue,// 任务队列
												threadFactory,// 线程工厂
												handler);
		}
		// mExecutor.execute(task);

		return mExecutor.submit(task);
	}

	/** 移除线程池中的任务 */
	public void remove(Runnable task)
	{
		mExecutor.getQueue().remove(task);
	}

}
