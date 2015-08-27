package com.chj.googleplay.utils;

import com.lidroid.xutils.BitmapUtils;

import android.view.View;

/**
 * @包名: com.chj.googleplay.utils
 * @类名: BitmapHelper
 * @作者: 陈火炬
 * @创建时间 : 2015-8-26 下午5:06:50
 * 
 * @描述: 图片加载的工具类
 * 
 * @SVN版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * 
 * @更新描述:
 */
public class BitmapHelper
{
	private static BitmapUtils	utils;

	public static void display(View view, String uri)
	{
		if (utils == null)
		{
			utils = new BitmapUtils(UIUtils.getContext());
		}
		utils.display(view, uri);
	}
}
