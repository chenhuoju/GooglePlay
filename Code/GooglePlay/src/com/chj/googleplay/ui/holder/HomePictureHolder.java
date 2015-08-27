package com.chj.googleplay.ui.holder;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.chj.googleplay.R;
import com.chj.googleplay.ui.weight.TouchedViewPager;
import com.chj.googleplay.utils.BitmapHelper;
import com.chj.googleplay.utils.Constants;
import com.chj.googleplay.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @包名: com.chj.googleplay.ui.holder
 * @类名: HomePictureHolder
 * @作者: 陈火炬
 * @创建时间 : 2015-8-27 下午5:44:38
 * 
 * @描述: 首页的轮播图
 * 
 * @SVN版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * 
 * @更新描述:
 */
public class HomePictureHolder extends BaseHolder<List<String>> implements OnPageChangeListener
{
	@ViewInject(R.id.item_home_picture_pager)
	private TouchedViewPager	mPager;			// viewPager

	@ViewInject(R.id.item_home_picture_container_indicator)
	private LinearLayout		mPointContainer;	// 指示点容器

	private List<String>		mPictureDatas;		// 图片地址数据
	private AutoSwitchTask		mSwitchTask;		// 图片轮播任务

	@Override
	protected View initView()
	{
		View view = View.inflate(UIUtils.getContext(), R.layout.item_home_picture, null);

		// View注入
		ViewUtils.inject(this, view);

		// 新建轮播任务
		mSwitchTask = new AutoSwitchTask();

		return view;
	}

	@Override
	protected void refreshUI(List<String> data)
	{
		// 铺数据
		this.mPictureDatas = data;

		// 加载点
		mPointContainer.removeAllViews();// 清空
		if (mPictureDatas != null)
		{
			for (int i = 0; i < mPictureDatas.size(); i++)
			{
				View point = new View(UIUtils.getContext());
				point.setBackgroundResource(R.drawable.indicator_normal);// 设置点的背景

				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(UIUtils.dip2px(8), UIUtils.dip2px(8));
				params.leftMargin = UIUtils.dip2px(8);
				params.bottomMargin = UIUtils.dip2px(12);
				mPointContainer.addView(point, params);

				if (i == 0)
				{
					point.setBackgroundResource(R.drawable.indicator_selected);
				}
			}
		}

		// 给viewpager设置数据 Adapter ---> list
		mPager.setAdapter(new HomePictureAdapter());

		// 设置ViewPager的监听
		mPager.setOnPageChangeListener(this);

		// 设置当前选中的item
		mPager.setCurrentItem(mPictureDatas.size() * 1000);

		// 开启轮播任务
		mSwitchTask.start();

		/** 当手指触摸mPager时，此方法调用 */
		mPager.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				int action = event.getAction();
				switch (action)
				{
					case MotionEvent.ACTION_DOWN:// 按下
						mSwitchTask.stop();
						break;
					case MotionEvent.ACTION_UP:// 松开
					case MotionEvent.ACTION_CANCEL:// 取消
						mSwitchTask.start();
						break;
					default:
						break;
				}
				return false;
			}
		});

	}

	/** 自动轮播图片的任务（是一个线程） */
	class AutoSwitchTask implements Runnable
	{
		private static final long	DELAY	= 2000; // 延时时间

		/** 开启 */
		public void start()
		{
			stop();
			// 执行延时操作
			UIUtils.postDelayed(this, DELAY);
		}

		/** 关闭 */
		public void stop()
		{
			UIUtils.removeCallbacks(this);
		}

		@Override
		public void run()
		{

			// 选中下一个
			int item = mPager.getCurrentItem();
			mPager.setCurrentItem(++item);

			UIUtils.postDelayed(this, DELAY);

		}
	}

	/** 轮播图片的适配器类 */
	class HomePictureAdapter extends PagerAdapter
	{

		/** 获取页面数量 */
		@Override
		public int getCount()
		{
			if (mPictureDatas != null)
			{
				// 无限轮播条件
				// return mPictureDatas.size();
				return Integer.MAX_VALUE;
			}
			return 0;
		}

		/** view判断 */
		@Override
		public boolean isViewFromObject(View view, Object object)
		{
			return view == object;
		}

		/** 实例化item */
		@Override
		public Object instantiateItem(ViewGroup container, int position)
		{
			ImageView iv = new ImageView(UIUtils.getContext());

			iv.setScaleType(ScaleType.FIT_XY);// 设置规模样式，填充整个布局
			// 设置默认图标
			iv.setImageResource(R.drawable.ic_default);

			// 加载图片
			position = position % mPictureDatas.size();// 图片的无限轮播条件
			String uri = Constants.BASE_IMAGE_URL + mPictureDatas.get(position);
			BitmapHelper.display(iv, uri);

			container.addView(iv);

			return iv;
		}

		/** 销毁item */
		@Override
		public void destroyItem(ViewGroup container, int position, Object object)
		{
			container.removeView((View) object);
		}

	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
	{

	}

	/** 选中时切换点 */
	@Override
	public void onPageSelected(int position)
	{
		int count = mPointContainer.getChildCount();
		for (int i = 0; i < count; i++)
		{
			// 设置默认
			// if (i == position)
			// {
			// mPointContainer.getChildAt(i).setBackgroundResource(R.drawable.indicator_selected);
			// }
			// else
			// {
			// mPointContainer.getChildAt(i).setBackgroundResource(R.drawable.indicator_normal);
			// }
			// 优化之后，点的无限轮播条件
			mPointContainer.getChildAt(i).setBackgroundResource((i == position % count) ? R.drawable.indicator_selected : R.drawable.indicator_normal);

		}

	}

	@Override
	public void onPageScrollStateChanged(int state)
	{

	}

}
