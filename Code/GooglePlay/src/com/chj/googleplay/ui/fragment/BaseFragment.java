package com.chj.googleplay.ui.fragment;

import com.chj.googleplay.ui.fragment.LoadingPager.LoadedResult;
import com.chj.googleplay.utils.UIUtils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

/**
 * @包名: com.chj.googleplay.ui.fragment
 * @类名: BaseFragment
 * @作者: 陈火炬
 * @创建时间 : 2015-8-24 下午7:42:09
 * 
 * @描述: 页面的基类
 * 
 * @SVN版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * 
 * @更新描述:
 */
public abstract class BaseFragment extends Fragment
{
	// 1. 加载数据
	// 2. 加载数据为空
	// 3. 加载失败
	// 4. 加载成功显示

	private LoadingPager	mPager; // 是一个页面容器

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// 共通点：
		// 1. 加载数据
		// 2. 加载数据为空
		// 3. 加载失败

		// 不同点：
		// 4. 加载成功显示

		// 只有一个能显示
		// 操作方式：通过一个容器，来装载所有的View；获取到数据,通过数据判断来选中显示哪一个

		// 做逻辑处理，判断是否改显示哪一个View

		if (mPager == null)
		{
			mPager = new LoadingPager(UIUtils.getContext()) {

				@Override
				protected View onCreateSuccessView()
				{
					return onSuccessView();
				}

				@Override
				protected LoadedResult onStartLoadData()
				{
					return onLoadData();
				}
			};
		}
		else
		{
			ViewParent parent = mPager.getParent();
			if (parent != null && parent instanceof ViewGroup)
			{
				((ViewGroup) parent).removeView(mPager);
			}
		}

		return mPager;

	}

	/** 获取数据的行为 */
	public void loadData()
	{
		if (mPager != null)
		{
			mPager.loadData();
		}
	}

	/** view成功显示时此方法调用 */
	protected abstract View onSuccessView();

	/** 当加载数据时此方法调用 */
	protected abstract LoadedResult onLoadData();

}
