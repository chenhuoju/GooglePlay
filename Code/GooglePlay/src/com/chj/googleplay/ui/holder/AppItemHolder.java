package com.chj.googleplay.ui.holder;

import com.chj.googleplay.R;
import com.chj.googleplay.utils.UIUtils;

import android.view.View;
import android.widget.TextView;

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
public class AppItemHolder extends BaseHolder<String>
{
	private TextView	tv1;
	private TextView	tv2;

	@Override
	protected View initView()
	{
		View view = View.inflate(UIUtils.getContext(), R.layout.tmp_item, null);

		tv1 = (TextView) view.findViewById(R.id.tmp_tv_1);
		tv2 = (TextView) view.findViewById(R.id.tmp_tv_2);

		return view;
	}

	@Override
	protected void refreshUI(String data)
	{
		// 给View铺数据
		tv1.setText("title : " + data);
		tv2.setText("content : " + data);

	}

}
