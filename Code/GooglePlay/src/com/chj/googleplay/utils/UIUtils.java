package com.chj.googleplay.utils;

import com.chj.googleplay.BaseApplication;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.util.DisplayMetrics;

/**
 * @包名: com.chj.googleplay.utils
 * @类名: UIUtils
 * @作者: 陈火炬
 * @创建时间 : 2015-8-24 下午4:01:34
 * 
 * @描述: 和UI操作相关的类
 * 
 * @SVN版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * 
 * @更新描述:
 */
public class UIUtils
{
	/** 获取上下文 */
	public static Context getContext()
	{
		return BaseApplication.getContext();
	}

	/** 获取资源 */
	public static Resources getResources()
	{
		return getContext().getResources();
	}

	/** 获取主线程id */
	public static long getMainThreadId()
	{
		return BaseApplication.getMainThreadId();
	}

	/** 获取主线程handler */
	public static Handler getMainThreadHandler()
	{
		return BaseApplication.getMainThreadHandler();
	}

	/** 主线程中执行 任务 */
	public static void runOnUiThread(Runnable task)
	{
		long currentThreadId = android.os.Process.myTid();
		long mainThreadId = getMainThreadId();

		if (currentThreadId == mainThreadId)
		{
			// 如果在主线程中执行
			task.run();
		}
		else
		{
			// 需要转的主线程执行
			getMainThreadHandler().post(task);
		}
	}

	/** dip转换成px */
	public static int dip2px(int dip)
	{
		// 公式 1: px = dp * (dpi / 160)
		// 公式 2: dp = px / denistity;
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		float density = metrics.density;
		// int dpi = metrics.densityDpi;
		return (int) (dip * density + 0.5f);
	}

	/** px转换成dip */
	public static int px2dip(int px)
	{
		// 公式 1: px = dp * (dpi / 160)
		// 公式 2: dp = px / denistity;
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		float density = metrics.density;
		// int dpi = metrics.densityDpi;
		return (int) (px / density + 0.5f);
	}

	/** 获取字符串 */
	public static String getString(int resId)
	{
		return getResources().getString(resId);
	}

	/** 获取包名 */
	public static String getPackageName()
	{
		return getContext().getPackageName();
	}

	/** 获取字符串数组 */
	public static String[] getStringArray(int resId)
	{
		return getResources().getStringArray(resId);
	}

	/** 获取颜色值 */
	public static int getCoclor(int resId)
	{
		return getResources().getColor(resId);
	}

	/** 执行延时操作 */
	public static void postDelayed(Runnable task, long delay)
	{
		getMainThreadHandler().postDelayed(task, delay);
	}

	/** 从消息队列中移除任务 */
	public static void removeCallbacks(Runnable task)
	{
		getMainThreadHandler().removeCallbacks(task);
	}

}
