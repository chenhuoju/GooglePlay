package com.chj.googleplay.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chj.googleplay.R;
import com.chj.googleplay.utils.UIUtils;

/**
 * @包名: com.chj.googleplay.ui.widget
 * @类名: ProgressCircleView
 * @作者: 陈火炬
 * @创建时间 : 2015-9-1 下午4:42:56
 * 
 * @描述: 环形进度条的view
 * 
 * @SVN版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * 
 * @更新描述:
 */
public class ProgressCircleView extends LinearLayout
{
	private TextView	mTvText;
	private ImageView	mIvIcon;
	private int			mProgress;				// 进度
	private Paint		mPaint	= new Paint();	// 画笔
	private RectF		mOval;					// 扇形的包裹矩形
	private boolean		mProgressEnable;

	public ProgressCircleView(Context context) {
		this(context, null);
	}

	public ProgressCircleView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ProgressCircleView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		init();
	}

	/** 初始化 */
	private void init()
	{
		// view挂载xml
		View.inflate(UIUtils.getContext(), R.layout.download_view, this);

		this.mIvIcon = (ImageView) findViewById(R.id.progress_circle_iv_icon);
		this.mTvText = (TextView) findViewById(R.id.progress_circle_tv_text);

	}

	/** 设置提示的文本 */
	public void setTipText(String text)
	{
		mTvText.setText(text);
	}

	/** 设置提示的图片 */
	public void setTipIcon(int resId)
	{
		mIvIcon.setImageResource(resId);
	}

	/***
	 * 设置当前的进度
	 * 
	 * @param progress
	 *            :0 - 100
	 */
	public void setProgress(int progress)
	{
		this.mProgress = progress;

		// UI刷新
		invalidate();
	}

	/** 设置进度是否显示 */
	public void setProgressEnable(boolean enable)
	{
		this.mProgressEnable = enable;
	}

	/** 绘制 */
	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);

		// 画圆形进度条 ---> 画扇形 ---> canvas,paint

		if (!mProgressEnable) { return; }

		if (mOval == null)
		{
			float left = mIvIcon.getLeft() + UIUtils.dip2px(1);
			float top = mIvIcon.getTop() + UIUtils.dip2px(1);
			float right = mIvIcon.getRight() - UIUtils.dip2px(1);
			float bottom = mIvIcon.getBottom() - UIUtils.dip2px(1);
			mOval = new RectF(left, top, right, bottom);
		}

		float startAngle = -90;// 开始的角度
		float sweepAngle = mProgress * 360f / 100;// 扫过的角度
		boolean useCenter = false;// 不画中间

		// 设置画笔
		mPaint.reset();// 重置画笔
		mPaint.setColor(Color.BLUE);// 设置颜色
		mPaint.setAntiAlias(true);// 设置抗锯齿
		mPaint.setStyle(Style.STROKE);// 设置空心样式
		mPaint.setStrokeWidth(UIUtils.dip2px(3));

		canvas.drawArc(mOval, startAngle, sweepAngle, useCenter, mPaint);

	}

}
