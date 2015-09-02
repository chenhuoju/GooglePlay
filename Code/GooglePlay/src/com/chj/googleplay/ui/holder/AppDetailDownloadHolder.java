package com.chj.googleplay.ui.holder;

import android.view.View;
import android.view.View.OnClickListener;

import com.chj.googleplay.R;
import com.chj.googleplay.bean.AppInfoBean;
import com.chj.googleplay.bean.DownloadBean;
import com.chj.googleplay.manager.DownloadManager;
import com.chj.googleplay.manager.DownloadManager.DownloadObserver;
import com.chj.googleplay.ui.widget.ProgressButton;
import com.chj.googleplay.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @包名: com.chj.googleplay.ui.holder
 * @类名: AppDetailDownloadHolder
 * @作者: 陈火炬
 * @创建时间 : 2015-8-29 下午9:09:10
 * 
 * @描述: 应用详情页面 - 下载部分
 * 
 * @SVN版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * 
 * @更新描述:
 */
public class AppDetailDownloadHolder extends BaseHolder<AppInfoBean> implements OnClickListener, DownloadObserver
{

	/** 下载按钮 */
	@ViewInject(R.id.app_detail_download_btn_download)
	private ProgressButton	mPbDownload;

	/** 信息bean */
	private AppInfoBean		mInfoBean;

	/** 下载管理者 */
	private DownloadManager	mDownloadManager;

	/** 下载信息 */
	private DownloadBean	mDownloadInfo;

	@Override
	protected View initView()
	{
		View view = View.inflate(UIUtils.getContext(), R.layout.app_detail_download, null);

		// view注入
		ViewUtils.inject(this, view);

		mPbDownload.setOnClickListener(this);// 设置点击监听

		// 下载的管理者
		mDownloadManager = DownloadManager.getInstance();

		return view;
	}

	@Override
	protected void refreshUI(AppInfoBean data)
	{
		// 数据接收
		this.mInfoBean = data;

		// 获取下载的信息(包含状态)
		mDownloadInfo = mDownloadManager.getCurrentState(data);

		// 根据状态更新UI
		safeRefreshView(mDownloadInfo);
	}

	/** 开始观察 */
	public void startObserver()
	{
		mDownloadManager.addObserver(this);

		// 获取下载的状态
		mDownloadInfo = mDownloadManager.getCurrentState(mInfoBean);

		// 根据状态更新UI
		safeRefreshView(mDownloadInfo);
	}

	/** 停止观察 */
	public void stopObserver()
	{
		mDownloadManager.deleteObserver(this);
	}

	/** 当下载状态改变时，此方法调用 */
	@Override
	public void onDownloadStateChange(DownloadManager manager, DownloadBean data)
	{
		// 进行过滤
		if (data.packageName.equals(mInfoBean.packageName))
		{
			// 子线程中执行的 ---> UI更新
			safeRefreshView(data);
		}
	}

	/** 当下载状态进度时，此方法调用 */
	@Override
	public void onDownloadProgressChange(DownloadManager manager, DownloadBean data)
	{
		// 进行过滤
		if (data.packageName.equals(mInfoBean.packageName))
		{
			// 子线程中执行的 ---> UI更新
			safeRefreshView(data);
		}
	}

	/** 安全的UI刷新,因为有可能在子线程执行 */
	private void safeRefreshView(final DownloadBean data)
	{
		UIUtils.runOnUiThread(new Runnable() {

			@Override
			public void run()
			{
				mDownloadInfo = data;
				refreshView();
			}
		});
	}

	/** 更新UI界面 */
	private void refreshView()
	{
		// 根据状态来显示
		// 1. 未下载 -----> 用户提示： 下载
		// 2. 等待状态----> 用户提示： 等待中...
		// 3. 下载中 -----> 用户提示： 显示进度
		// 4. 暂停 ------> 用户提示： 继续下载
		// 5. 下载成功----> 用户提示： 安装
		// 6. 下载失败----> 用户提示： 重试
		// 7. 已经安装 ---> 用户提示： 打开
		int downloadState = mDownloadInfo.downloadState;

		// 设置下载按钮的默认背景
		mPbDownload.setBackgroundResource(R.drawable.progress_btn_normal_bg);

		switch (downloadState)
		{
			case DownloadManager.STATE_DOWNLOADING:// 下载中 -----> 用户提示： 显示进度
				// 0 - 100
				int progress = (int) (mDownloadInfo.currenDownloadLength * 100f / mDownloadInfo.size);
				mPbDownload.setText(progress + "%");
				mPbDownload.setProgress(progress);
				mPbDownload.setBackgroundResource(R.drawable.progress_btn_loading_bg);
				break;
			case DownloadManager.STATE_FAILED:// 下载失败----> 用户提示： 重试
				mPbDownload.setText("重试");
				break;
			case DownloadManager.STATE_INSTALLED:// 已经安装 ---> 用户提示： 打开
				mPbDownload.setText("打开");
				break;
			case DownloadManager.STATE_NONE:// 未下载 -----> 用户提示： 下载
				mPbDownload.setText("下载");
				break;
			case DownloadManager.STATE_PAUSE:// 暂停 ------> 用户提示： 继续下载
				mPbDownload.setText("继续下载");
				break;
			case DownloadManager.STATE_SUCCESS:// 下载成功----> 用户提示： 安装
				mPbDownload.setText("安装");
				break;
			case DownloadManager.STATE_WAITING:// 等待状态----> 用户提示： 等待中...
				mPbDownload.setText("等待中...");
				break;
			default:
				break;
		}

	}

	/** 按钮点击事件 */
	@Override
	public void onClick(View v)
	{
		if (v == mPbDownload)
		{
			clickDownloadButton();
		}
	}

	/** 点击下载按钮 */
	private void clickDownloadButton()
	{
		// 根据状态来处理事情
		int downloadState = mDownloadInfo.downloadState;

		switch (downloadState)
		{
			case DownloadManager.STATE_DOWNLOADING:// 下载中 --> 点击时,需要暂停
				doPause();
				break;
			case DownloadManager.STATE_INSTALLED:// 已经安装 --> 点击时,需要打开
				doOpen();
				break;
			case DownloadManager.STATE_NONE:
			case DownloadManager.STATE_PAUSE:
			case DownloadManager.STATE_FAILED:// 未下载,暂停,下载失败 --> 去下载
				doDownload();
				break;
			case DownloadManager.STATE_SUCCESS:// 下载成功 --> 等待安装
				doInstall();
				break;
			case DownloadManager.STATE_WAITING:// 等待时 --> 不做任何处理
				break;
			default:
				break;
		}

	}

	/** 执行下载操作 */
	private void doDownload()
	{
		mDownloadManager.download(mInfoBean);
	}

	/** 执行安装操作 */
	private void doInstall()
	{
		mDownloadManager.install(mInfoBean);
	}

	/** 执行打开操作 */
	private void doOpen()
	{
		mDownloadManager.open(mInfoBean);
	}

	/** 执行暂停操作 */
	private void doPause()
	{
		mDownloadManager.pause(mInfoBean);
	}

}
