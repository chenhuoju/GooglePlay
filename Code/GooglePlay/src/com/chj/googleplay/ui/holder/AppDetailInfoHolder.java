package com.chj.googleplay.ui.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.chj.googleplay.R;
import com.chj.googleplay.bean.AppInfoBean;
import com.chj.googleplay.utils.BitmapHelper;
import com.chj.googleplay.utils.Constants;
import com.chj.googleplay.utils.StringUtils;
import com.chj.googleplay.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @包名: com.chj.googleplay.ui.holder
 * @类名: AppDetailInfoHolder
 * @作者: 陈火炬
 * @创建时间 : 2015-8-29 上午9:51:04
 * 
 * @描述: 应用详情页面-信息部分
 * 
 * @SVN版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * 
 * @更新描述:
 */
public class AppDetailInfoHolder extends BaseHolder<AppInfoBean>
{
	@ViewInject(R.id.app_detail_info_iv_icon)
	private ImageView	mIvIcon;		// 图标

	@ViewInject(R.id.app_detail_info_rb_star)
	private RatingBar	mRbStar;		// 评分

	@ViewInject(R.id.app_detail_info_tv_downloadnum)
	private TextView	mTvDownloadnum; // 下载量

	@ViewInject(R.id.app_detail_info_tv_size)
	private TextView	mTvSize;		// 大小

	@ViewInject(R.id.app_detail_info_tv_name)
	private TextView	mTvName;		// 应用名称

	@ViewInject(R.id.app_detail_info_tv_time)
	private TextView	mTvTime;		// 时间

	@ViewInject(R.id.app_detail_info_tv_version)
	private TextView	mTvVersion;	// 版本

	@Override
	protected View initView()
	{
		View view = View.inflate(UIUtils.getContext(), R.layout.app_detail_info, null);

		// View注入
		ViewUtils.inject(this, view);

		return view;
	}

	@Override
	protected void refreshUI(AppInfoBean data)
	{

		// 设置文本
		mTvDownloadnum.setText(UIUtils.getString(R.string.app_detail_info_downloadnum, data.downloadNum));
		mTvName.setText(data.name);
		mTvSize.setText(UIUtils.getString(R.string.app_detail_info_size, StringUtils.formatFileSize(data.size)));
		mTvTime.setText(UIUtils.getString(R.string.app_detail_info_date, data.date));
		mTvVersion.setText(UIUtils.getString(R.string.app_detail_info_version, data.version));

		// 设置star
		mRbStar.setRating(data.stars);

		// 设置默认图片
		mIvIcon.setImageResource(R.drawable.ic_default);
		// 加载网络图片
		BitmapHelper.display(mIvIcon, Constants.BASE_IMAGE_URL + data.iconUrl);
	}

}
