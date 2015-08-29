package com.chj.googleplay.ui.widget;

import com.chj.googleplay.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * @包名: com.chj.googleplay.ui.weight
 * @类名: RatioLayout
 * @作者: 陈火炬
 * @创建时间 : 2015-8-28 上午9:25:59
 * 
 * @描述: 控制宽高比的布局：
 * @1. 已知 宽度准确值，通过宽高比例， 要计算出 高度的值
 * @2. 已知 高度准确值，通过宽高比例， 要计算出 宽度的值
 * 
 * @SVN版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * 
 * @更新描述:
 */
public class RatioLayout extends FrameLayout
{
	public static final int	RELATIVE_WIDTH	= 0;				// 相对宽
	public static final int	RELATIVE_HEIGHT	= 1;				// 相对高

	private float			mRatio;							// 宽高比例
	private int				mRelative		= RELATIVE_WIDTH;	// 相对谁来计算

	public RatioLayout(Context context) {
		this(context, null);
	}

	public RatioLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public RatioLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RatioLayout);

		// 取值
		mRatio = typedArray.getFloat(R.styleable.RatioLayout_ratio, 0f);
		mRelative = typedArray.getInt(R.styleable.RatioLayout_relative, RELATIVE_WIDTH);

		typedArray.recycle();// 回收typedArray(是调用底层的应用)
	}

	/** 设置比例 */
	public void setRatio(float ratio)
	{
		this.mRatio = ratio;
	}

	/** 设置参照物，即相对谁 */
	public void setRelative(int relative)
	{
		if (relative > 1 || relative < 0) { return; }
		this.mRelative = relative;
	}

	/** 测量方法 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		// MeasureSpec
		// 1. widthMeasureSpec : 32位的0101......
		// (头两位表示 模式：UNSPECIFIED 未定义，EXACTLY 精确，AT_MOST 最大)
		// (后30位表示的是大小值：010101---> 30dp)

		// 获取宽度的大小和模式
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);

		// 获取高度的大小和模式
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);

		if (widthMode == MeasureSpec.EXACTLY && mRatio != 0f && mRelative == RELATIVE_WIDTH)
		{
			// 1. 已知 宽度准确值，通过宽高比例， 要计算出 高度的值

			// 计算出孩子的宽度和高度
			int width = widthSize - getPaddingLeft() - getPaddingRight();
			int height = (int) ((width / mRatio) + 0.5f);

			// child.measure ： 测量孩子
			int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
			int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
			measureChildren(childWidthMeasureSpec, childHeightMeasureSpec);

			// 设置自己的宽高
			int measuredWidth = widthSize;
			int measuredHeight = height + getPaddingTop() + getPaddingBottom();
			setMeasuredDimension(measuredWidth, measuredHeight);
		}
		else if (heightMode == MeasureSpec.EXACTLY && mRatio != 0f && mRelative == RELATIVE_HEIGHT)
		{
			// 2. 已知 高度准确值，通过宽高比例， 要计算出 宽度的值

			// 计算出孩子的宽度和高度
			int height = heightSize - getPaddingTop() - getPaddingBottom();
			int width = (int) ((height * mRatio) + 0.5f);

			// child.measure ： 测量孩子
			int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
			int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
			measureChildren(childWidthMeasureSpec, childHeightMeasureSpec);

			// 设置自己的宽高
			int measuredWidth = width + getPaddingLeft() + getPaddingRight();
			int measuredHeight = heightSize;
			setMeasuredDimension(measuredWidth, measuredHeight);
		}
		else
		{
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}

	}

}
