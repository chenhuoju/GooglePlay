package com.chj.googleplay.ui.holder;

import java.util.List;

import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.chj.googleplay.R;
import com.chj.googleplay.bean.AppInfoBean.AppSafeBean;
import com.chj.googleplay.utils.BitmapHelper;
import com.chj.googleplay.utils.Constants;
import com.chj.googleplay.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;

/**
 * @包名: com.chj.googleplay.ui.holder
 * @类名: AppDetailSafeHolder
 * @作者: 陈火炬
 * @创建时间 : 2015-8-29 上午11:46:11
 * 
 * @描述: 应用详情- 安全部分
 * 
 * @SVN版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * 
 * @更新描述:
 */
public class AppDetailSafeHolder extends BaseHolder<List<AppSafeBean>> implements OnClickListener
{
	private static final long	DURATION	= 300;	// 持续时间

	@ViewInject(R.id.app_detail_safe_pic_container)
	private LinearLayout		mPicContainer;		// 图片容器

	@ViewInject(R.id.app_detail_safe_des_container)
	private LinearLayout		mDesContainer;		// 描述容器

	@ViewInject(R.id.app_detail_safe_iv_arrow)
	private ImageView			mIvArrow;			// 箭头

	private List<AppSafeBean>	mDatas;

	private boolean				isOpened	= true; // 当前是打开的

	@Override
	protected View initView()
	{
		View view = View.inflate(UIUtils.getContext(), R.layout.app_detail_safe, null);

		// view注入
		ViewUtils.inject(this, view);

		// 设置点击事件
		view.setOnClickListener(this);

		return view;
	}

	@Override
	protected void refreshUI(List<AppSafeBean> data)
	{
		// 数据接收
		this.mDatas = data;

		// 动态的加载图片
		loadPicData();

		// 动态的加载描述
		loadDesData();

		// 关闭
		toggle(false);
	}

	/** 动态加载图片 */
	private void loadPicData()
	{
		if (mDatas == null) { return; }

		// 清空容器
		mPicContainer.removeAllViews();

		// 循环添加图片
		for (int i = 0; i < mDatas.size(); i++)
		{
			AppSafeBean bean = mDatas.get(i);
			String uri = Constants.BASE_IMAGE_URL + bean.safeUrl;

			// 添加图片
			ImageView iv = new ImageView(UIUtils.getContext());
			BitmapHelper.display(iv, uri);// 加载网络图片

			// 参数设置
			LayoutParams params = new LayoutParams(UIUtils.dip2px(60), UIUtils.dip2px(30));
			if (i != 0)
			{
				params.leftMargin = UIUtils.dip2px(6);
			}

			// 添加到容器中
			mPicContainer.addView(iv, params);
		}

	}

	/** 动态加载描述 */
	private void loadDesData()
	{
		if (mDatas == null) { return; }

		// 清空容器
		mDesContainer.removeAllViews();

		// 循环添加图片
		for (int i = 0; i < mDatas.size(); i++)
		{
			AppSafeBean bean = mDatas.get(i);
			String uri = Constants.BASE_IMAGE_URL + bean.safeDesUrl;

			LinearLayout ll = new LinearLayout(UIUtils.getContext());
			ll.setOrientation(LinearLayout.HORIZONTAL);// 设置水平摆放控件
			ll.setPadding(UIUtils.dip2px(10), UIUtils.dip2px(2), UIUtils.dip2px(10), UIUtils.dip2px(2));// 设置padding
			ll.setGravity(Gravity.CENTER_VERTICAL);// 设置孩子垂直摆放

			// 添加图标
			ImageView icon = new ImageView(UIUtils.getContext());
			BitmapHelper.display(icon, uri);// 加载网络图片
			ll.addView(icon);

			// 添加文本
			TextView text = new TextView(UIUtils.getContext());
			text.setText(bean.safeDes);// 设置文本
			// 设置颜色
			// if (bean.safeDesColor == 0)
			// {
			// text.setTextColor(UIUtils.getCoclor(R.color.app_detail_safe_normal));
			// }
			// else
			// {
			// text.setTextColor(UIUtils.getCoclor(R.color.app_detail_safe_warning));
			// }
			// 优化之后
			text.setTextColor(UIUtils.getCoclor(bean.safeDesColor == 0 ? R.color.app_detail_safe_normal : R.color.app_detail_safe_warning));
			ll.addView(text);

			// 添加View
			mDesContainer.addView(ll);

		}

	}

	/** 点击事件 */
	@Override
	public void onClick(View v)
	{
		// 打开或者关闭详情布局
		toggle(true);
	}

	/** 开关方法 */
	private void toggle(boolean isAnimated)
	{
		// 给描述的容器高度进行重新赋值
		// ViewGroup.LayoutParams params = mDesContainer.getLayoutParams();//
		// 向下转型(父类下的子类)
		// params.height = xxx;// 设置高度
		// mDesContainer.setLayoutParams(params);

		mDesContainer.measure(0, 0);// 主动去测量
		int measuredHeight = mDesContainer.getMeasuredHeight();// 获取测量高度

		if (isOpened)
		{
			if (isAnimated)
			{
				// 需要关闭
				// 高度是由大变小

				int start = measuredHeight;// 描述的容器高度
				int end = 0;// 0

				// 动画操作
				doDesAnimation(start, end);
			}
			else
			{
				setDesContainerHeiget(0);
			}

		}
		else
		{
			if (isAnimated)
			{
				// 需要打开
				// 高度是由小变大

				int start = 0;// 0
				int end = measuredHeight;// 描述的容器高度

				// 动画操作
				doDesAnimation(start, end);
			}
			else
			{
				setDesContainerHeiget(measuredHeight);
			}

		}
		isOpened = !isOpened;

		if (isAnimated)
		{
			// 处理箭头动画

			if (isOpened)
			{
				// open --->提示可以关闭，箭头由下向上
				float start = 0;
				float end = 180;
				ObjectAnimator.ofFloat(mIvArrow, "rotation", start, end)
								.setDuration(DURATION)
								.start();
			}
			else
			{
				// close --->提示可以开启，箭头由上向下
				float start = 180;
				float end = 360;
				ObjectAnimator.ofFloat(mIvArrow, "rotation", start, end)
								.setDuration(DURATION)
								.start();
			}

		}
		else
		{
			mIvArrow.setImageResource(isOpened ? R.drawable.arrow_up : R.drawable.arrow_down);
		}

	}

	/** 实现描述动画 */
	private void doDesAnimation(int start, int end)
	{
		ValueAnimator animator = ValueAnimator.ofInt(start, end);
		animator.setDuration(DURATION);// 设置持续时间
		// 设置更新监听
		animator.addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator animator)
			{
				int value = (Integer) animator.getAnimatedValue();
				// 设置高度
				setDesContainerHeiget(value);
			}
		});
		animator.start();
	}

	/** 设置描述容器的高度 */
	private void setDesContainerHeiget(int height)
	{
		ViewGroup.LayoutParams params = mDesContainer.getLayoutParams();// 向下转型(父类下的子类)
		params.height = height;// 设置高度
		mDesContainer.setLayoutParams(params);
	}

}
