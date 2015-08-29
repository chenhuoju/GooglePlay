package com.chj.googleplay.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * @包名: com.chj.googleplay.utils
 * @类名: ToastUtils
 * @作者: 陈火炬
 * @创建时间 : 2015-8-28 下午5:09:42
 * 
 * @描述: 吐司工具类
 * 
 * @SVN版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * 
 * @更新描述:
 */
public class ToastUtils
{
	public static Toast	mToast;

	/** 弹吐司 */
	public static void showToast(Context mContext, String msg)
	{
		if (mToast == null)
		{
			mToast = Toast.makeText(mContext, "", Toast.LENGTH_SHORT);
		}
		mToast.setText(msg);
		mToast.show();
	}
}
