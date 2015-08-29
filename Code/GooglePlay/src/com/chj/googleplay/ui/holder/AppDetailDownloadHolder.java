package com.chj.googleplay.ui.holder;

import android.view.View;
import android.view.View.OnClickListener;

import com.chj.googleplay.R;
import com.chj.googleplay.bean.AppInfoBean;
import com.chj.googleplay.ui.widget.ProgressButton;
import com.chj.googleplay.utils.ToastUtils;
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
public class AppDetailDownloadHolder extends BaseHolder<AppInfoBean> implements OnClickListener
{

	@ViewInject(R.id.app_detail_download_btn_download)
	private ProgressButton	mPbDownload;	// 下载按钮

	@Override
	protected View initView()
	{
		View view = View.inflate(UIUtils.getContext(), R.layout.app_detail_download, null);

		// view注入
		ViewUtils.inject(this, view);

		mPbDownload.setOnClickListener(this);// 设置点击监听

		return view;
	}

	@Override
	protected void refreshUI(AppInfoBean data)
	{

	}

	/** 按钮点击事件 */
	@Override
	public void onClick(View v)
	{
		ToastUtils.showToast(UIUtils.getContext(), "正在下载");
	}

}
