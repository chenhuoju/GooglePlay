package com.chj.googleplay.ui.holder;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.chj.googleplay.R;
import com.chj.googleplay.bean.AppInfoBean;
import com.chj.googleplay.bean.DownloadBean;
import com.chj.googleplay.manager.DownloadManager;
import com.chj.googleplay.ui.widget.ProgressCircleView;
import com.chj.googleplay.utils.BitmapHelper;
import com.chj.googleplay.utils.Constants;
import com.chj.googleplay.utils.StringUtils;
import com.chj.googleplay.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @param <T>
 * @包名: com.chj.googleplay.ui.holder
 * @类名: AppItemHolder
 * @作者: 陈火炬
 * @创建时间 : 2015-8-25 下午9:03:24
 * 
 * @描述: 主页中listView的item对应的holder
 * 
 * @SVN版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * 
 * @更新描述:
 */
public class AppItemHolder extends BaseHolder<AppInfoBean> implements OnClickListener
{
	@ViewInject(R.id.item_appinfo_tv_title)
	private TextView			mTvTitle;

	@ViewInject(R.id.item_appinfo_tv_des)
	private TextView			mTvDes;

	@ViewInject(R.id.item_appinfo_tv_size)
	private TextView			mTvSize;

	@ViewInject(R.id.item_appinfo_rb_stars)
	private RatingBar			mRbStars;

	@ViewInject(R.id.item_appinfo_iv_icon)
	private ImageView			mIvIcon;

	@ViewInject(R.id.item_appinfo_pcv)
	private ProgressCircleView	mPcvProgress;		// 自定义控件

	private DownloadBean		mDownloadBean;

	private DownloadManager		mDownloadManager;

	@Override
	protected View initView()
	{
		View view = View.inflate(UIUtils.getContext(), R.layout.item_app_info, null);

		// View注入
		ViewUtils.inject(this, view);

		// 设置点击事件
		mPcvProgress.setOnClickListener(this);

		mDownloadManager = DownloadManager.getInstance();// 初始化

		return view;
	}

	@Override
	protected void refreshUI(AppInfoBean data)
	{
		// 给View铺数据
		mTvTitle.setText(data.name);
		mTvDes.setText(data.des);
		mTvSize.setText(StringUtils.formatFileSize(data.size));
		mRbStars.setRating(data.stars);

		// 设置默认图片
		mIvIcon.setImageResource(R.drawable.ic_default);
		// 图片设置
		String uri = Constants.BASE_IMAGE_URL + data.iconUrl;
		BitmapHelper.display(mIvIcon, uri);

		// 检查下载状态
		checkDownloadState();
	}

	/** 检查下载状态 */
	public void checkDownloadState()
	{
		mDownloadBean = DownloadManager.getInstance().getCurrentState(mData);

		safeRefreshView(mDownloadBean);
	}

	/** 安全的刷新UI */
	public void safeRefreshView(final DownloadBean data)
	{
		if (!data.packageName.equals(mData.packageName)) { return; }

		UIUtils.runOnUiThread(new Runnable() {

			@Override
			public void run()
			{
				refreshView(data);
			}
		});
	}

	/** 刷新UI */
	protected void refreshView(DownloadBean data)
	{
		// 根据状态来显示
		// 1. 未下载 -----> 用户提示： 下载
		// 2. 等待状态----> 用户提示： 等待中...
		// 3. 下载中 -----> 用户提示： 显示进度
		// 4. 暂停 ------> 用户提示： 继续下载
		// 5. 下载成功----> 用户提示： 安装
		// 6. 下载失败----> 用户提示： 重试
		// 7. 已经安装 ---> 用户提示： 打开
		int downloadState = data.downloadState;

		mPcvProgress.setProgressEnable(false);// 设置默认不可见
		switch (downloadState)
		{
			case DownloadManager.STATE_DOWNLOADING:
				// 下载中 -----> 用户提示： 显示进度--->0-100
				mPcvProgress.setProgressEnable(true);
				int progress = (int) (data.currenDownloadLength * 100f / data.size + 0.5f);
				mPcvProgress.setTipText(progress + "%");
				mPcvProgress.setProgress(progress);
				mPcvProgress.setTipIcon(R.drawable.ic_pause);
				break;
			case DownloadManager.STATE_INSTALLED:
				// 已经安装 ---> 用户提示： 打开
				mPcvProgress.setTipText("打开");
				mPcvProgress.setTipIcon(R.drawable.ic_install);
				break;
			case DownloadManager.STATE_NONE:
				// 未下载 -----> 用户提示： 下载
				mPcvProgress.setTipText("下载");
				mPcvProgress.setTipIcon(R.drawable.ic_download);
				break;
			case DownloadManager.STATE_FAILED:
				// 下载失败----> 用户提示： 重试
				mPcvProgress.setTipText("重试");
				mPcvProgress.setTipIcon(R.drawable.ic_redownload);
				break;
			case DownloadManager.STATE_PAUSE:
				// 暂停 ------> 用户提示： 继续下载
				mPcvProgress.setTipText("继续下载");
				mPcvProgress.setTipIcon(R.drawable.ic_resume);
				break;
			case DownloadManager.STATE_SUCCESS:
				// 下载成功----> 用户提示： 安装
				mPcvProgress.setTipText("安装");
				mPcvProgress.setTipIcon(R.drawable.ic_install);
				break;
			case DownloadManager.STATE_WAITING:
				// 等待状态----> 用户提示： 等待中...
				mPcvProgress.setTipText("等待中...");
				mPcvProgress.setTipIcon(R.drawable.ic_pause);
				break;
			default:
				break;
		}
	}

	/** 按钮点击事件 */
	@Override
	public void onClick(View v)
	{
		if (mPcvProgress == v)
		{
			doDownloadClick();
		}
	}

	/** 执行下载点击事件 */
	private void doDownloadClick()
	{
		// 根据状态来处理事情
		int downloadState = mDownloadBean.downloadState;

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

	/** 下载 */
	private void doDownload()
	{
		mDownloadManager.download(mData);
	}

	/** 安装 */
	private void doInstall()
	{
		mDownloadManager.install(mData);
	}

	/** 打开 */
	private void doOpen()
	{
		mDownloadManager.open(mData);
	}

	/** 暂停 */
	private void doPause()
	{
		mDownloadManager.pause(mData);
	}

}
