package com.chj.googleplay.ui.fragment;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;

import com.chj.googleplay.R;
import com.chj.googleplay.utils.UIUtils;

/**
 * @包名: com.chj.googleplay.ui.fragment
 * @类名: LoadingPager
 * @作者: 陈火炬
 * @创建时间 : 2015-8-24 下午7:57:01
 * 
 * @描述: 加载页面
 * 
 * @SVN版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * 
 * @更新描述:
 */
public abstract class LoadingPager extends FrameLayout implements OnClickListener
{
	// 共通点：
	// 1. 加载数据
	// 2. 加载数据为空
	// 3. 加载失败

	// 不同点：
	// 4. 加载成功显示

	private static final int	STATE_NONE		= -1;			// 无状态
	private static final int	STATE_LOADING	= 0;			// 加载中的状态
	private static final int	STATE_EMPTY		= 1;			// 空的状态
	private static final int	STATE_ERROR		= 2;			// 错误的状态
	private static final int	STATE_SUCCESS	= 3;			// 成功的状态

	private int					mCurrentState	= STATE_NONE;	// 用来标记当前属于什么状态，就显示什么View

	private View				mLoadingView;					// 正在加载中的View
	private View				mEmptyView;					// 数据为空的View
	private View				mErrorView;					// 错误页面的View
	private View				mSuccessView;					// 成功的view

	private View				mBtnRetry;

	public LoadingPager(Context context) {
		this(context, null);
	}

	public LoadingPager(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public LoadingPager(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		initView();
	}

	/** 初始化视图 */
	private void initView()
	{
		// 添加相同的View

		// 1. 加载数据
		if (mLoadingView == null)
		{
			// 初始化
			mLoadingView = View.inflate(getContext(), R.layout.pager_loading, null);
			addView(mLoadingView);
		}

		// 2. 加载数据为空
		if (mEmptyView == null)
		{
			// 初始化
			mEmptyView = View.inflate(getContext(), R.layout.pager_empty, null);
			addView(mEmptyView);
		}

		// 3. 加载失败
		if (mErrorView == null)
		{
			// 初始化
			mErrorView = View.inflate(getContext(), R.layout.pager_error, null);
			addView(mErrorView);

			// 设置监听
			mBtnRetry = mErrorView.findViewById(R.id.error_btn_retry);
			mBtnRetry.setOnClickListener(this);
		}

		// 根据状态显示view
		safeUpdateUIStyle();

	}

	/** 安全的UI更新，即区分是在主线程或者子线程中调用 */
	private void safeUpdateUIStyle()
	{
		UIUtils.runOnUiThread(new Runnable() {

			@Override
			public void run()
			{
				updateUIStyle();
			}
		});

	}

	/** 更新UI状态 */
	private void updateUIStyle()
	{
		// 1.loading
		if (mLoadingView != null)
		{
			// 状态为加载中或无状态时
			// if (mCurrentState == STATE_LOADING || mCurrentState ==
			// STATE_NONE)
			// {
			// mLoadingView.setVisibility(View.VISIBLE);
			// }
			// else
			// {
			// mLoadingView.setVisibility(View.GONE);
			// }
			// 优化之后
			mLoadingView.setVisibility(mCurrentState == STATE_LOADING || mCurrentState == STATE_NONE ? View.VISIBLE : View.GONE);
		}
		// 2. empty
		if (mEmptyView != null)
		{
			mEmptyView.setVisibility(mCurrentState == STATE_EMPTY ? View.VISIBLE : View.GONE);
		}

		// 3. error
		if (mErrorView != null)
		{
			mErrorView.setVisibility(mCurrentState == STATE_ERROR ? View.VISIBLE : View.GONE);
		}

		// 4. success
		if (mSuccessView == null && mCurrentState == STATE_SUCCESS)
		{
			// 初始化View
			mSuccessView = onCreateSuccessView();
			LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			// add
			addView(mSuccessView, params);
		}

		if (mSuccessView != null)
		{
			mSuccessView.setVisibility(mCurrentState == STATE_SUCCESS ? View.VISIBLE : View.GONE);
		}

	}

	/** 加载数据的方法 */
	public void loadData()
	{
		if (mCurrentState == STATE_EMPTY || mCurrentState == STATE_ERROR || mCurrentState == STATE_NONE)
		{
			mCurrentState = STATE_LOADING;

			new Thread(new LoadDataTask()).start();
		}

		safeUpdateUIStyle();
	}

	protected abstract View onCreateSuccessView();

	protected abstract LoadedResult onStartLoadData();

	class LoadDataTask implements Runnable
	{

		@Override
		public void run()
		{
			// 获取数据
			LoadedResult result = onStartLoadData();

			// 根据结果得到状态值
			mCurrentState = result.getState();

			// UI改变
			safeUpdateUIStyle();
		}
	}

	public enum LoadedResult
	{
		EMPTY(STATE_EMPTY), ERROR(STATE_ERROR), SUCCESS(STATE_SUCCESS);

		int	state;

		private LoadedResult(int state) {
			this.state = state;
		}

		public int getState()
		{
			return state;
		}

	}

	/** 当view点击时此方法调用 */
	@Override
	public void onClick(View v)
	{
		if (v == mBtnRetry)
		{
			loadData();
		}
	}

}
