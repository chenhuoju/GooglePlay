package com.chj.googleplay.ui.holder;

import java.util.List;

import com.chj.googleplay.R;
import com.chj.googleplay.ui.widget.RatioLayout;
import com.chj.googleplay.utils.BitmapHelper;
import com.chj.googleplay.utils.Constants;
import com.chj.googleplay.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * @包名: com.chj.googleplay.ui.holder
 * @类名: AppDetailInfoHolder
 * @作者: 陈火炬
 * @创建时间 : 2015-8-29 上午9:51:04
 * 
 * @描述: 应用详情页面-图片部分
 * 
 * @SVN版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * 
 * @更新描述:
 */
public class AppDetailPicHolder extends BaseHolder<List<String>>
{
	@ViewInject(R.id.app_detail_pic_iv_container)
	private LinearLayout	mPicContainer;

	@Override
	protected View initView()
	{
		View view = View.inflate(UIUtils.getContext(), R.layout.app_detail_pic, null);

		// view注入
		ViewUtils.inject(this, view);

		return view;
	}

	@Override
	protected void refreshUI(List<String> data)
	{
		if (mPicContainer == null) { return; }

		// 清空容器
		mPicContainer.removeAllViews();

		// 动态添加图片
		for (int i = 0; i < data.size(); i++)
		{
			String uri = Constants.BASE_IMAGE_URL + data.get(i);

			ImageView iv = new ImageView(UIUtils.getContext());
			iv.setImageResource(R.drawable.ic_default);// 设置默认图片
			BitmapHelper.display(iv, uri);// 网络图片加载

			// 设置宽高比布局
			RatioLayout layout = new RatioLayout(UIUtils.getContext());
			layout.setRatio(0.6f);// 设置比例
			layout.setRelative(RatioLayout.RELATIVE_WIDTH);// 设置参照物，即相对谁
			layout.addView(iv);

			// 参数设置
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(UIUtils.dip2px(100), 0);
			if (i != 0)
			{
				params.leftMargin = UIUtils.dip2px(10);
			}

			// 添加到容器中
			mPicContainer.addView(layout, params);
		}

	}
}
