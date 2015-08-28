package com.chj.googleplay.ui.fragment;

import java.util.List;
import java.util.Random;

import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.chj.googleplay.http.RecommendProtocol;
import com.chj.googleplay.ui.fragment.LoadingPager.LoadedResult;
import com.chj.googleplay.ui.widget.StellarMap;
import com.chj.googleplay.ui.widget.StellarMap.Adapter;
import com.chj.googleplay.utils.ToastUtils;
import com.chj.googleplay.utils.UIUtils;

/**
 * @包名: com.chj.googleplay.ui.fragment
 * @类名: RecommendFragment
 * @作者: 陈火炬
 * @创建时间 : 2015-8-28 下午5:18:13
 * 
 * @描述: 推荐页面 ---->setGroup: 设置默认选中页 ; setRegularity：设置区域划分
 * 
 * @SVN版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * 
 * @更新描述:
 */
public class RecommendFragment extends BaseFragment
{
	private RecommendProtocol	mPortocol;
	private List<String>		mDatas;

	@Override
	protected View onSuccessView()
	{
		// 新建view,用来管理随机摆放的view的
		StellarMap stellarMapView = new StellarMap(UIUtils.getContext());

		// 给数据给到StellarMap
		stellarMapView.setAdapter(new RecommendAdapter());

		// 设置内边距
		// stellarMapView.setInnerPadding(UIUtils.dip2px(10),
		// UIUtils.dip2px(10), UIUtils.dip2px(10), UIUtils.dip2px(10));
		stellarMapView.setInnerPadding(UIUtils.dip2px(15), UIUtils.dip2px(15), UIUtils.dip2px(15), UIUtils.dip2px(15));

		// 设置区域划分
		stellarMapView.setRegularity(20, 30);

		// 设置默认选中页
		stellarMapView.setGroup(0, true);

		return stellarMapView;
	}

	@Override
	protected LoadedResult onLoadData()
	{
		// 新建协议
		mPortocol = new RecommendProtocol();

		try
		{
			// 去网络获取数据
			mDatas = mPortocol.loadData(0);
			return checkState(mDatas);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return LoadedResult.ERROR;
		}

	}

	/** 推荐适配器 */
	class RecommendAdapter implements Adapter
	{
		private int	PAGE_SIZE	= 15;	// 每页面显示的条目

		/** 数据可以显示多少个页面 */
		@Override
		public int getGroupCount()
		{
			if (mDatas != null)
			{
				int size = mDatas.size();// 所有的条目
				int count = size / PAGE_SIZE;// 页面数量

				if (size % PAGE_SIZE > 0)
				{
					count++;
				}
				return count;
			}
			return 0;
		}

		/** 第Group个页面有几个数据 */
		@Override
		public int getCount(int group)
		{
			if (mDatas != null)
			{
				int size = mDatas.size();// 所有的条目
				int groupCount = getGroupCount();// 共有几页

				if (groupCount != group)
				{
					// 不是最后一页
					return PAGE_SIZE;
				}
				else
				{
					if (size % PAGE_SIZE == 0)
					{
						return PAGE_SIZE;
					}
					else
					{
						// 最后一页
						return size % PAGE_SIZE;
					}
				}

			}
			return 0;
		}

		/**
		 * 获取view
		 * 
		 * @grou: 当前是第几页
		 * @position: 当前页面的第几个
		 */
		@Override
		public View getView(int group, int position, View convertView)
		{
			TextView tv = new TextView(UIUtils.getContext());

			// 给TextView设置属性

			int index = position + group * PAGE_SIZE;
			final String text = mDatas.get(index);
			tv.setText(text);// 设置文本

			Random rdm = new Random();// 随机对象

			// 设置随机色
			int red = rdm.nextInt(210) + 20;
			int green = rdm.nextInt(210) + 20;
			int blue = rdm.nextInt(210) + 20;
			int colors = Color.argb(0xff, red, green, blue);
			tv.setTextColor(colors);// 设置文本颜色

			// 设置随机的大小 14 ---> 24 = 随机值(24 - 14) + 基本值(14)
			int size = rdm.nextInt(11) + 14;
			tv.setTextSize(UIUtils.dip2px(size));// 设置文本字体大小

			// 设置监听
			tv.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v)
				{
					ToastUtils.showToast(UIUtils.getContext(), text);
				}
			});

			return tv;
		}

		@Override
		public int getNextGroupOnPan(int group, float degree)
		{
			return 0;
		}

		@Override
		public int getNextGroupOnZoom(int group, boolean isZoomIn)
		{
			return 0;
		}
	}

}
