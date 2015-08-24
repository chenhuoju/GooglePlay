package com.chj.googleplay;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

/**
 * @包名: com.chj.googleplay
 * @类名: BaseApplication
 * @作者: 陈火炬
 * @创建时间 : 2015-8-24 下午3:38:46
 * 
 * @描述: 应用程序的入口
 * 
 * @SVN版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * 
 * @更新描述:
 */
public class BaseApplication extends Application
{
	private static Context	mContext;			// 上下文
	private static Thread	mMainThread;		// 主线程
	private static long		mMainThreadId;		// 主线程id
	private static Handler	mMainThreadHandler; // 主线程handler
	private static Looper	mMainThreadLooper;	// 主线程looper

	@Override
	public void onCreate()
	{
		super.onCreate();

		// 在应用程序入口提供全局的工具

		// ①上下文
		mContext = this;

		// ②主线程和子线程
		mMainThread = Thread.currentThread();

		// ③主线程id
		// mMainThreadId = mMainThread.getId();//其中的一种获取主线程id的方法
		// android.os.Process.myPid();// 进程id
		mMainThreadId = android.os.Process.myTid();// 当前线程id
		// android.os.Process.myUid();// 用户id

		// ④主线程handler
		mMainThreadHandler = new Handler();

		// ⑤主线程looper
		mMainThreadLooper = getMainLooper();
	}

	/**
	 * 获取上下文的方法
	 * 
	 * @return
	 */
	public static Context getContext()
	{
		return mContext;
	}

	/**
	 * 获取主线程的方法
	 * 
	 * @return
	 */
	public static Thread getMainThread()
	{
		return mMainThread;
	}

	/**
	 * 获取主线程id的方法
	 * 
	 * @return
	 */
	public static long getMainThreadId()
	{
		return mMainThreadId;
	}

	/**
	 * 获取主线程handler的方法
	 */
	public static Handler getMainThreadHandler()
	{
		return mMainThreadHandler;
	}

	/**
	 * 获取主线程looper的方法
	 */
	public static Looper getMainThreadLooper()
	{
		return mMainThreadLooper;
	}
}
