package com.chj.googleplay.manager;

/**
 * @包名: com.chj.googleplay.manager
 * @类名: ThreadManager
 * @作者: 陈火炬
 * @创建时间 : 2015-8-26 上午9:28:01
 * 
 * @描述: 线程管理
 * 
 * @SVN版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * 
 * @更新描述:
 */
public class ThreadManager
{
	/** 长时间运行的线程池 */
	private static ThreadPoolProxy	mLongPool;
	/** 长时间运行的锁 */
	private static Object			mLongLock	= new Object();

	/** 获取长时间运行的线程池 */
	public static ThreadPoolProxy getLongRunPool()
	{
		if (mLongPool == null)
		{
			synchronized (mLongLock)
			{
				if (mLongPool == null)
				{
					mLongPool = new ThreadPoolProxy(3, 3, 5L);
				}
			}

		}
		return mLongPool;
	}

}
