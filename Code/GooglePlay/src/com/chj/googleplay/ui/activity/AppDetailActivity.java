package com.chj.googleplay.ui.activity;

import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.chj.googleplay.R;
import com.chj.googleplay.bean.AppInfoBean;
import com.chj.googleplay.http.AppDetailProtocol;
import com.chj.googleplay.ui.fragment.LoadingPager;
import com.chj.googleplay.ui.fragment.LoadingPager.LoadedResult;
import com.chj.googleplay.ui.holder.AppDetailDesHolder;
import com.chj.googleplay.ui.holder.AppDetailDownloadHolder;
import com.chj.googleplay.ui.holder.AppDetailInfoHolder;
import com.chj.googleplay.ui.holder.AppDetailPicHolder;
import com.chj.googleplay.ui.holder.AppDetailSafeHolder;
import com.chj.googleplay.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @包名: com.chj.googleplay.ui.activity
 * @类名: AppDetailActivity
 * @作者: 陈火炬
 * @创建时间 : 2015-8-28 下午8:00:15
 * 
 * @描述: 应用详情页面
 * 
 * @SVN版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * 
 * @更新描述:
 */
public class AppDetailActivity extends BaseActivity
{
	public static final String		KEY_PACKAGE_NAME	= "package_name";

	@ViewInject(R.id.app_detail_info_container)
	private FrameLayout				mContainerInfo;						// app信息容器

	@ViewInject(R.id.app_detail_safe_container)
	private FrameLayout				mContainerSafe;						// app安全容器

	@ViewInject(R.id.app_detail_pic_container)
	private FrameLayout				mContainerPic;							// app图片容器

	@ViewInject(R.id.app_detail_des_container)
	private FrameLayout				mContainerDes;							// app简介容器

	@ViewInject(R.id.app_detail_bottom_container)
	private FrameLayout				mContainerBottom;						// 底部容器

	private ActionBar				mActionBar;

	private LoadingPager			mPager;

	private AppDetailProtocol		mProtocol;

	private AppInfoBean				mData;

	private AppDetailDownloadHolder	mDownloadHolder;						// 应用详情页面下载持有者

	@Override
	protected void initView()
	{
		super.initView();

		mPager = new LoadingPager(this) {

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

		setContentView(mPager);

	}

	@Override
	protected void initActionBar()
	{

		mActionBar = getSupportActionBar();
		mActionBar.setTitle("应用详情");// 设置title
		mActionBar.setDisplayHomeAsUpEnabled(true);// 设置back按钮的显示

	}

	@Override
	protected void initData()
	{
		if (mPager != null)
		{
			mPager.loadData();
		}
	}

	/** 加载数据时，此方法调用 */
	private LoadedResult onLoadData()
	{
		// 取数据
		String packageName = getIntent().getStringExtra(KEY_PACKAGE_NAME);

		// 非空判断
		if (TextUtils.isEmpty(packageName)) { return LoadedResult.EMPTY; }

		// 新建协议
		mProtocol = new AppDetailProtocol(packageName);

		// 去网络加载数据
		try
		{
			mData = mProtocol.loadData(0);

			Thread.sleep(1000);

			if (mData == null) { return LoadedResult.EMPTY; }
			return LoadedResult.SUCCESS;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return LoadedResult.ERROR;
		}

	}

	/** 成功之后，此方法才调用 */
	private View onSuccessView()
	{
		View view = View.inflate(UIUtils.getContext(), R.layout.act_app_detail, null);

		// View的注入
		ViewUtils.inject(this, view);

		// TODO:应用详情

		// 加载应用的信息部分
		AppDetailInfoHolder infoHolder = new AppDetailInfoHolder();
		mContainerInfo.addView(infoHolder.getRootView());// 加载视图
		infoHolder.setData(mData);// 设置数据

		// 加载应用的安全部分
		AppDetailSafeHolder safeHolder = new AppDetailSafeHolder();
		mContainerSafe.addView(safeHolder.getRootView());// 加载视图
		safeHolder.setData(mData.safe);// 设置数据

		// 加载应用的图片部分
		AppDetailPicHolder picHolder = new AppDetailPicHolder();
		mContainerPic.addView(picHolder.getRootView());// 加载视图
		picHolder.setData(mData.screen);// 设置数据

		// 加载应用的描述部分
		AppDetailDesHolder desHolder = new AppDetailDesHolder();
		mContainerDes.addView(desHolder.getRootView()); // 加载视图
		desHolder.setData(mData);// 设置数据

		// 加载应用的下载部分
		mDownloadHolder = new AppDetailDownloadHolder();
		mContainerBottom.addView(mDownloadHolder.getRootView());// 加载视图
		mDownloadHolder.setData(mData);// 设置数据

		// 开始观察下载状态的变化
		mDownloadHolder.startObserver();

		return view;
	}

	/** 获取焦点 */
	@Override
	protected void onResume()
	{
		super.onResume();

		// 开始下载的观察
		if (mDownloadHolder != null)
		{
			mDownloadHolder.startObserver();
		}
	}

	/** 失去焦点 */
	@Override
	protected void onPause()
	{
		super.onPause();

		// 停止下载的观察
		if (mDownloadHolder != null)
		{
			mDownloadHolder.stopObserver();
		}
	}

	/**
	 * 被选中的菜单项
	 * 
	 * @false:传递给菜单处理
	 * @true:自己消费
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int itemId = item.getItemId();

		switch (itemId)
		{
			case android.R.id.home:
				finish();
				break;
			default:
				break;
		}

		return super.onOptionsItemSelected(item);
	}

}
