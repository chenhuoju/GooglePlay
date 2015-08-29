package com.chj.googleplay.ui.holder;

import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.chj.googleplay.R;
import com.chj.googleplay.bean.AppInfoBean;
import com.chj.googleplay.utils.LogUtils;
import com.chj.googleplay.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;

/**
 * @包名: com.chj.googleplay.ui.holder
 * @类名: AppDetailDesHolder
 * @作者: 陈火炬
 * @创建时间 : 2015-8-29 下午7:32:58
 * 
 * @描述: 应用详情页面-描述部分
 * 
 * @SVN版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * 
 * @更新描述:
 */
public class AppDetailDesHolder extends BaseHolder<AppInfoBean> implements OnClickListener
{
	private static final long	DURATION	= 300;	// 动画持续时间

	@ViewInject(R.id.app_detail_des_tv_des)
	private TextView			mTvDes;

	@ViewInject(R.id.app_detail_des_tv_author)
	private TextView			mTvAuthor;

	@ViewInject(R.id.app_detail_des_iv_arrow)
	private ImageView			mIvArrow;

	private boolean				isOpened	= true; // 当前是否是打开的

	@Override
	protected View initView()
	{
		View view = View.inflate(UIUtils.getContext(), R.layout.app_detail_des, null);

		// view注入
		ViewUtils.inject(this, view);

		// 设置点击监听事件
		view.setOnClickListener(this);

		return view;
	}

	@Override
	protected void refreshUI(AppInfoBean data)
	{
		// 设置数据
		mTvAuthor.setText(UIUtils.getString(R.string.app_detail_des_author, data.author));// 设置作者
		mTvDes.setText(data.des);

		// 让文本收起来
		toggle(false);

	}

	/** 点击事件 */
	@Override
	public void onClick(View v)
	{
		toggle(true);
	}

	private void toggle(boolean isAnimated)
	{
		// 判断是否开启
		if (isOpened)
		{
			// 需要关闭
			// 让textView的高度改变

			int start = getOpenedHeight();
			int end = getClosedHeight();

			if (start < end)
			{
				int tmp = start;
				start = end;
				end = tmp;
			}

			if (isAnimated)
			{
				// start --> end 由大到小
				doDesTextAnimation(start, end); // 处理描述文本的动画
			}
			else
			{
				setDesTextHeight(end);
			}

		}
		else
		{
			// 需要打开

			int start = getClosedHeight();// 取小的值
			int end = getOpenedHeight();

			if (start > end)
			{
				int tmp = start;
				start = end;
				end = tmp;
			}

			if (isAnimated)
			{
				// start --> end 由小到大
				doDesTextAnimation(start, end); // 处理描述文本的动画
			}
			else
			{
				setDesTextHeight(end);
			}

		}

		// 更改状态
		isOpened = !isOpened;

		// 判断是否执行箭头旋转动画
		if (isAnimated)
		{
			if (isOpened)
			{
				// 执行箭头旋转动画
				ObjectAnimator.ofFloat(mIvArrow, "rotation", 0, 180).setDuration(DURATION).start();
			}
			else
			{
				// 执行箭头旋转动画
				ObjectAnimator.ofFloat(mIvArrow, "rotation", 180, 360).setDuration(DURATION).start();

			}

		}
		else
		{
			// 不执行箭头旋转动画
			mIvArrow.setImageResource(isOpened ? R.drawable.arrow_up : R.drawable.arrow_down);
		}

	}

	/** 完全显示的高度 ，即开启时的高度 */
	private int getOpenedHeight()
	{
		TextView tv = new TextView(UIUtils.getContext());
		tv.setText(mData.des);
		// tv.setLines(7);// 设置textView的行数

		int measuredWidth = mTvDes.getMeasuredWidth();
		int measuredHeight = mTvDes.getMeasuredHeight();

		LogUtils.d("textView的宽度:" + measuredWidth);
		LogUtils.d("textView的高度:" + measuredHeight);

		// 测量
		int widthMeasureSpec = MeasureSpec.makeMeasureSpec(measuredWidth, MeasureSpec.EXACTLY);
		int heightMeasureSpec = 0;
		tv.measure(widthMeasureSpec, heightMeasureSpec);

		return tv.getMeasuredHeight();
	}

	/** 不完全显示的高度，即关闭时的高度 */
	private int getClosedHeight()
	{
		// 获取行高
		// int lineHeight = mTvDes.getLineHeight();
		// return lineHeight * 7;
		// ---->不可取,无法实现想要的效果

		TextView tv = new TextView(UIUtils.getContext());
		tv.setText(mData.des);
		tv.setLines(7);// 设置textView的行数

		int measuredWidth = mTvDes.getMeasuredWidth();
		int measuredHeight = mTvDes.getMeasuredHeight();

		LogUtils.d("textView的宽度:" + measuredWidth);
		LogUtils.d("textView的高度:" + measuredHeight);

		// 测量
		int widthMeasureSpec = MeasureSpec.makeMeasureSpec(measuredWidth, MeasureSpec.EXACTLY);
		int heightMeasureSpec = 0;
		tv.measure(widthMeasureSpec, heightMeasureSpec);

		return tv.getMeasuredHeight();
	}

	/** 处理描述文本的动画 */
	private void doDesTextAnimation(int start, int end)
	{
		ValueAnimator animator = ValueAnimator.ofInt(start, end);
		animator.setDuration(DURATION);// 设置动画持续时间
		// 添加更新动画监听
		animator.addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animator)
			{
				int value = (Integer) animator.getAnimatedValue();

				// 设置描述文本的高度
				setDesTextHeight(value);
			}
		});

		animator.addListener(new AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animator)
			{
			}

			@Override
			public void onAnimationRepeat(Animator animator)
			{
			}

			/** 动画结束时，此方法调用 */
			@Override
			public void onAnimationEnd(Animator animator)
			{
				// 让scrollView滑动
				ScrollView scrollView = getScrollView();
				if (scrollView != null)
				{
					// 滑动到底部
					scrollView.fullScroll(View.FOCUS_DOWN);
				}

			}

			@Override
			public void onAnimationCancel(Animator animator)
			{
			}
		});

		animator.start();
	}

	/** 设置描述文本的高度 */
	private void setDesTextHeight(int value)
	{
		// 改变TextView的高度
		LayoutParams params = mTvDes.getLayoutParams();
		params.height = value;
		mTvDes.setLayoutParams(params);
	}

	/** 获取滚动view */
	private ScrollView getScrollView()
	{
		ViewParent parent = mRootView.getParent();

		while (parent != null)
		{
			if (parent instanceof ScrollView) { return (ScrollView) parent; }

			parent = parent.getParent();
		}

		return null;
	}
}
