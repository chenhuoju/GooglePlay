package com.chj.googleplay.ui.activity;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

/**
 * @包名: com.chj.googleplay.ui.activity
 * @类名: BaseActivity
 * @作者: 陈火炬
 * @创建时间 : 2015-8-28 下午8:03:29
 * 
 * @描述: 基类Activity
 * 
 * @SVN版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * 
 * @更新描述:
 */
public class BaseActivity extends ActionBarActivity
{
	/** 存储全局的activity */
	private static List<Activity>	mActivities	= new LinkedList<Activity>();

	/** 当前前台的activity */
	private static Activity			mForegroundActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		// 同步
		synchronized (mActivities)
		{
			mActivities.add(this);
		}

		initView();
		initActionBar();
		initData();
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();

		synchronized (mActivities)
		{
			mActivities.remove(this);
		}
	}

	/** 获取焦点时，此方法调用 */
	@Override
	protected void onResume()
	{
		super.onResume();

		mForegroundActivity = this;
	}

	/** 失去焦点时，此方法调用 */
	@Override
	protected void onPause()
	{
		super.onPause();

		mForegroundActivity = null;
	}

	/** 退出App详情页面 */
	public void exitApp()
	{
		// 方法一:线程安全的操作-->利用for循环
		// synchronized (mActivities)
		// {
		// for (Activity activity : mActivities)
		// {
		// activity.finish();
		// }
		// }

		// 方法二:线程安全的操作-->利用迭代器
		ListIterator<Activity> iterator = mActivities.listIterator();
		while (iterator.hasNext())
		{
			Activity activity = iterator.next();// 获取下一个对象
			activity.finish();

		}

	}

	/** 加载数据的方法，自己覆盖实现 */
	protected void initData()
	{

	}

	/** 初始化ActionBar的方法，子类如果有ActionBar的初始化，自己覆盖实现 */
	protected void initActionBar()
	{

	}

	/** 初始化View的方法，子类如果有View的初始化，自己覆盖实现 */
	protected void initView()
	{

	}

	/** 获取前台activity */
	public Activity getForegroundActivity()
	{
		return mForegroundActivity;
	}

}
