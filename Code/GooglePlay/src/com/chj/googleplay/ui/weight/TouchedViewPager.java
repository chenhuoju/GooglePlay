package com.chj.googleplay.ui.weight;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * @包名: com.chj.googleplay.ui.weight
 * @类名: TouchedViewPager
 * @作者: 陈火炬
 * @创建时间 : 2015-8-27 下午8:20:40
 * 
 * @描述: ViewPager重写
 * 
 * @SVN版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * 
 * @更新描述:
 */
public class TouchedViewPager extends ViewPager
{
	private float	mDownX;
	private float	mDownY;

	public TouchedViewPager(Context context) {
		this(context, null);
	}

	public TouchedViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/** 处理分发事件 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev)
	{
		getParent().requestDisallowInterceptTouchEvent(true);

		int action = ev.getAction();
		switch (action)
		{
			case MotionEvent.ACTION_DOWN:// 按下
				mDownX = ev.getX();
				mDownY = ev.getY();
				break;
			case MotionEvent.ACTION_MOVE:// 滑动
				float moveX = ev.getX();
				float moveY = ev.getY();

				float diffX = moveX - mDownX;
				float diffY = moveY - mDownY;

				if (Math.abs(diffX) > Math.abs(diffY))
				{
					// 水平滑动(不分发,自己处理)
					getParent().requestDisallowInterceptTouchEvent(true);
				}
				else
				{
					// 垂直滑动(分发,传递给子控件)
					getParent().requestDisallowInterceptTouchEvent(false);
				}

				break;
			case MotionEvent.ACTION_UP:// 松开

				break;
			default:
				break;
		}

		return super.dispatchTouchEvent(ev);
	}
}
