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
public class AppItemHolder extends BaseHolder<AppInfoBean>
{
	@ViewInject(R.id.item_appinfo_tv_title)
	private TextView	mTvTitle;

	@ViewInject(R.id.item_appinfo_tv_des)
	private TextView	mTvDes;

	@ViewInject(R.id.item_appinfo_tv_size)
	private TextView	mTvSize;

	@ViewInject(R.id.item_appinfo_rb_stars)
	private RatingBar	mRbStars;

	@ViewInject(R.id.item_appinfo_iv_icon)
	private ImageView	mIvIcon;

	@Override
	protected View initView()
	{
		View view = View.inflate(UIUtils.getContext(), R.layout.item_app_info, null);

		// View注入
		ViewUtils.inject(this, view);

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
	}

}
