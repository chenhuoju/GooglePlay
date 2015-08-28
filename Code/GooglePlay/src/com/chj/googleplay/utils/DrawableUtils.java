package com.chj.googleplay.utils;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

/**
 * @包名: com.chj.googleplay.utils
 * @类名: DrawableUtils
 * @作者: 陈火炬
 * @创建时间 : 2015-8-28 下午12:55:28
 * 
 * @描述: drawable xml 对应的类的工具类
 * 
 * @SVN版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * 
 * @更新描述:
 */
public class DrawableUtils
{
	/** 获取梯度资源 */
	public static GradientDrawable getGradientDrawable(int shape, float radius, int argb)
	{
		GradientDrawable bg = new GradientDrawable();
		bg.setShape(shape);// 设置形状
		bg.setCornerRadius(radius);// 设置边角
		bg.setColor(argb);

		return bg;
	}

	/** 获取状态集合的资源 */
	public static StateListDrawable getStateListDrawable(Drawable normalBg, Drawable pressBg)
	{
		StateListDrawable selectorBg = new StateListDrawable();
		selectorBg.addState(new int[] { android.R.attr.state_pressed }, pressBg);
		selectorBg.addState(new int[] {}, normalBg);
		return selectorBg;
	}
}
