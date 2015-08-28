package com.chj.googleplay.ui.widget;

import com.chj.googleplay.R;
import com.chj.googleplay.utils.UIUtils;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * @包名: com.chj.googleplay.ui.weight
 * @类名: BaseListView
 * @作者: 陈火炬
 * @创建时间 : 2015-8-27 下午4:10:38
 * 
 * @描述: listView基类，设置listView的一些样式
 * 
 * @SVN版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * 
 * @更新描述:
 */
public class BaseListView extends ListView
{

	public BaseListView(Context context) {
		this(context, null);
	}

	public BaseListView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public BaseListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		initView();
	}

	/** 初始化view */
	private void initView()
	{
		this.setCacheColorHint(Color.TRANSPARENT);
		this.setSelector(android.R.color.transparent);
		this.setFadingEdgeLength(0);// 设置边缘
		this.setDividerHeight(0);// 设置分割线
		this.setBackgroundColor(UIUtils.getCoclor(R.color.bg));
	}
}
