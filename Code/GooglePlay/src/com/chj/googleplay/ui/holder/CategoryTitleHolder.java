package com.chj.googleplay.ui.holder;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.chj.googleplay.bean.CategoryBean;
import com.chj.googleplay.utils.UIUtils;

/**
 * @包名: com.chj.googleplay.ui.holder
 * @类名: CategoryTitleHolder
 * @作者: 陈火炬
 * @创建时间 : 2015-8-28 下午3:39:24
 * 
 * @描述: 分类title的holder
 * 
 * @SVN版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * 
 * @更新描述:
 */
public class CategoryTitleHolder extends BaseHolder<CategoryBean>
{

	private TextView	mTv;

	@Override
	protected View initView()
	{
		mTv = new TextView(UIUtils.getContext());

		// 给textView设置属性
		mTv.setTextColor(Color.GRAY);
		mTv.setBackgroundColor(Color.WHITE);
		mTv.setPadding(UIUtils.dip2px(10), UIUtils.dip2px(5), UIUtils.dip2px(10), UIUtils.dip2px(5));

		return mTv;
	}

	@Override
	protected void refreshUI(CategoryBean data)
	{
		// 数据刷新
		mTv.setText(data.title);
	}

}
