package com.chj.googleplay.ui.holder;

import android.view.View;

/**
 * @param <T>
 * @包名: com.chj.googleplay.ui.holder
 * @类名: BaseHolder
 * @作者: 陈火炬
 * @创建时间 : 2015-8-25 下午8:43:52
 * 
 * @描述: MVC中C，用来控制视图和数据，对视图和数据进行合理的显示
 * 
 * @SVN版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * 
 * @更新描述:
 */
public abstract class BaseHolder<T>
{
	protected View	mRootView;
	protected T		mData;

	public BaseHolder() {
		mRootView = initView();

		// 打标记
		mRootView.setTag(this);
	}

	/** 初始化视图 */
	protected abstract View initView();

	/** UI刷新 */
	protected abstract void refreshUI(T data);

	public void setData(T data)
	{
		this.mData = data;

		// UI刷新
		refreshUI(mData);
	}

	/** 获取根视图 */
	public View getRootView()
	{
		return mRootView;
	}

}
