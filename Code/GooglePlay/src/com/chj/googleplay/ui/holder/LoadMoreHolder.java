package com.chj.googleplay.ui.holder;

import android.view.View;

import com.chj.googleplay.R;
import com.chj.googleplay.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @包名: com.chj.googleplay.ui.holder
 * @类名: LoadMOreHolder
 * @作者: 陈火炬
 * @创建时间 : 2015-8-26 下午9:00:15
 * 
 * @描述: 加载更多的holder
 * 
 * @SVN版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * 
 * @更新描述:
 */
public class LoadMoreHolder extends BaseHolder<Integer>
{
	public static final int	STATE_LOADING	= 0;	// 加载中的状态
	public static final int	STATE_ERROR		= 1;	// 加载失败的状态
	public static final int	STATE_EMPTY		= 2;	// 没有更多数据的状态

	@ViewInject(R.id.item_loadmore_container_retry)
	private View			mErrorView;			// 加载失败的布局

	@ViewInject(R.id.item_loadmore_container_loading)
	private View			mLoadingView;			// 加载中的布局

	private int				mCurrentSate;			// 当前状态

	@Override
	protected View initView()
	{
		View view = View.inflate(UIUtils.getContext(), R.layout.item_load_more, null);

		// view注入
		ViewUtils.inject(this, view);

		refreshUI(STATE_LOADING);

		return view;
	}

	@Override
	protected void refreshUI(Integer data)
	{
		this.mCurrentSate = data;

		switch (data)
		{
			case STATE_LOADING:// 加载中
				mErrorView.setVisibility(View.GONE);
				mLoadingView.setVisibility(View.VISIBLE);
				break;
			case STATE_ERROR:// 加载失败
				mErrorView.setVisibility(View.VISIBLE);
				mLoadingView.setVisibility(View.GONE);
				break;
			case STATE_EMPTY:// 没有加载
				mErrorView.setVisibility(View.GONE);
				mLoadingView.setVisibility(View.GONE);
				break;
			default:
				break;
		}
	}

	/** 获取当前状态 */
	public int getCurrentState()
	{
		return mCurrentSate;
	}

}
