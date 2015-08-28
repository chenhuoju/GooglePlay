package com.chj.googleplay.ui.widget;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/***
 * 
 * @包名: com.chj.googleplay.ui.weight
 * @类名: FlowLayout
 * @作者: 陈火炬
 * @创建时间 : 2015-8-28 上午11:02:48
 * 
 * @描述: 流式布局
 * 
 * @SVN版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * 
 * @更新描述:
 */
public class FlowLayout extends ViewGroup
{
	private List<Line>	mLines				= new LinkedList<Line>();	// 用来记录页面中的行
	private Line		mCurrentLine;									// 当前行
	private int			mHorizontalSpace	= 2;						// 水平间距
	private int			mVerticalSpace		= 2;						// 垂直方向的间距

	public FlowLayout(Context context) {
		this(context, null);
	}

	public FlowLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public FlowLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	/** 设置距离 */
	public void setSpace(int horizontal, int vertical)
	{
		this.mHorizontalSpace = horizontal;
		this.mVerticalSpace = vertical;
	}

	/** 测量方法 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		// 孩子个数记录的清空
		mLines.clear();
		mCurrentLine = null;

		System.out.println("call : onMeasure");

		int widthSize = MeasureSpec.getSize(widthMeasureSpec);

		// 行的最大宽度
		int lineMaxWidth = widthSize - getPaddingLeft() - getPaddingRight();

		// 测量完孩子时，就记录到 行里面去
		int count = getChildCount();
		for (int i = 0; i < count; i++)
		{
			View child = getChildAt(i);

			// 孩子不可见时，不添加
			if (child.getVisibility() == View.GONE)
			{
				continue;
			}

			// 测量孩子，给孩子的宽高赋值
			measureChild(child, widthMeasureSpec, heightMeasureSpec);

			// 将孩子添加到记录中
			if (mCurrentLine == null)
			{
				// 新建行
				mCurrentLine = new Line(lineMaxWidth, mHorizontalSpace);
				// 添加到布局中
				mLines.add(mCurrentLine);
			}

			// 给行添加孩子
			if (mCurrentLine.canAddChild(child))
			{
				// 可以加孩子
				mCurrentLine.addChild(child);
			}
			else
			{
				// 加不了
				// 换行
				mCurrentLine = new Line(lineMaxWidth, mHorizontalSpace);
				// 添加到布局中
				mLines.add(mCurrentLine);
				// 再加孩子
				mCurrentLine.addChild(child);
			}
		}

		// 设置自己的宽高
		int measuredWidth = widthSize;
		int measuredHeight = getPaddingTop() + getPaddingBottom();
		// 通过line的高度来计算自己的高度
		for (int i = 0; i < mLines.size(); i++)
		{
			Line line = mLines.get(i);
			measuredHeight += line.lineHeight;

			if (i != 0)
			{
				measuredHeight += mVerticalSpace;
			}
		}
		setMeasuredDimension(measuredWidth, measuredHeight);

	}

	/** 布局方法 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b)
	{

		int left = getPaddingLeft();
		int top = getPaddingTop();

		// 让行进行布局
		for (int i = 0; i < mLines.size(); i++)
		{
			Line line = mLines.get(i);
			// 给行布局
			line.layout(left, top);

			// 添加top的记录
			top += line.lineHeight;
			if (i != mLines.size() - 1)
			{
				top += mVerticalSpace;
			}
		}

	}

	/** 行类 */
	class Line
	{
		// 行里的孩子在
		private List<View>	mChildViews	= new LinkedList<View>();

		private int			usedWith;								// 已经使用过的宽度，动态值
		private int			lineHeight;							// 行的高度
		private int			maxWith;								// 行的最大的宽度，是由父类给
		private int			horizontalSpace;						// 中间的间隔

		public Line(int maxWith, int horizontalSpace) {
			this.maxWith = maxWith;
			this.horizontalSpace = horizontalSpace;
		}

		public void layout(int left, int top)
		{
			// 给孩子布局
			int size = mChildViews.size();

			int tmpLeft = 0;

			int extraWidth = (int) ((maxWith - usedWith) * 1f / size + 0.5f);

			for (int i = 0; i < size; i++)
			{
				View child = mChildViews.get(i);
				int childWidth = child.getMeasuredWidth();
				int childHeight = child.getMeasuredHeight();

				if (extraWidth > 0)
				{
					// 希望孩子再宽点
					int widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidth + extraWidth, MeasureSpec.EXACTLY);
					int heightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);
					child.measure(widthMeasureSpec, heightMeasureSpec);

					childWidth = child.getMeasuredWidth();
					childHeight = child.getMeasuredHeight();
				}

				int extraHeight = (int) ((lineHeight - childHeight) / 2f + 0.5f);

				int l = left + tmpLeft;
				int t = top + extraHeight;
				int r = l + childWidth;
				int b = t + childHeight;

				child.layout(l, t, r, b);

				// 添加记录
				tmpLeft += childWidth + horizontalSpace;
			}

		}

		public boolean canAddChild(View view)
		{
			// 如果已经使用的宽度 + 准备加的View的宽度 + 中间间隔 > 最大的宽度：加不进去

			// 准备加的View的宽度
			int childWidth = view.getMeasuredWidth();

			int size = mChildViews.size();

			if (size == 0)
			{
				// 一个都没有加
				return true;
			}
			else if (usedWith + childWidth + horizontalSpace > maxWith) { return false; }

			return true;
		}

		public void addChild(View view)
		{
			int childWidth = view.getMeasuredWidth();
			int childHeight = view.getMeasuredHeight();
			int size = mChildViews.size();

			if (size == 0)
			{
				// 没有孩子
				// 已经使用的宽度
				if (childWidth > maxWith)
				{
					usedWith = maxWith;
				}
				else
				{
					usedWith = childWidth;
				}

				// 高度
				lineHeight = childHeight;
			}
			else
			{
				// 已经使用的宽度
				usedWith += childWidth + horizontalSpace;

				// 高度
				lineHeight = lineHeight > childHeight ? lineHeight : childHeight;
			}

			// 加孩子
			mChildViews.add(view);// 记录
		}

	}

}
