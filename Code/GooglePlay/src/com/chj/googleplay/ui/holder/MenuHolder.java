package com.chj.googleplay.ui.holder;

import com.chj.googleplay.R;
import com.chj.googleplay.utils.UIUtils;

import android.view.View;

/**
 * @包名: com.chj.googleplay.ui.holder
 * @类名: MenuHolder
 * @作者: 陈火炬
 * @创建时间 : 2015-9-2 上午10:46:16
 * 
 * @描述: TODO
 * 
 * @SVN版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * 
 * @更新描述:
 */
public class MenuHolder extends BaseHolder<String>
{

	@Override
	protected View initView()
	{
		View view = View.inflate(UIUtils.getContext(), R.layout.menu_view, null);
		return view;
	}

	@Override
	protected void refreshUI(String data)
	{

	}

}
