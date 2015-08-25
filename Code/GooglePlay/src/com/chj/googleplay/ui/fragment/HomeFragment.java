package com.chj.googleplay.ui.fragment;

import java.util.LinkedList;
import java.util.List;

import android.view.View;
import android.widget.ListView;

import com.chj.googleplay.ui.fragment.LoadingPager.LoadedResult;
import com.chj.googleplay.ui.fragment.adapter.SuperBaseAdapter;
import com.chj.googleplay.ui.holder.AppItemHolder;
import com.chj.googleplay.ui.holder.BaseHolder;
import com.chj.googleplay.utils.UIUtils;

/**
 * @包名: com.chj.googleplay.ui.fragment
 * @类名: HomeFragment
 * @作者: 陈火炬
 * @创建时间 : 2015-8-24 下午6:49:31
 * 
 * @描述: TODO
 * 
 * @SVN版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * 
 * @更新描述:
 */
public class HomeFragment extends BaseFragment
{
	private List<String>	mDatas;

	@Override
	protected View onSuccessView()
	{
		// TextView tv = new TextView(UIUtils.getContext());
		// tv.setText("首页");
		// tv.setGravity(Gravity.CENTER);
		// tv.setTextSize(24);
		// return tv;

		ListView mListView = new ListView(UIUtils.getContext());

		// adapter ---> list
		mListView.setAdapter(new AppListAdapter(mDatas));

		return mListView;

	}

	/** 此方法是在子线程中执行的 */
	@Override
	protected LoadedResult onLoadData()
	{
		// LoadedResult[] results = new LoadedResult[] {
		// LoadedResult.EMPTY,
		// LoadedResult.ERROR,
		// LoadedResult.SUCCESS
		// };
		//
		// try
		// {
		// Thread.sleep(1000);
		// }
		// catch (Exception e)
		// {
		// e.printStackTrace();
		// }
		//
		// Random rdm = new Random();
		// return results[rdm.nextInt(results.length)];

		// 模拟数据加载

		mDatas = new LinkedList<String>();
		for (int i = 0; i < 50; i++)
		{
			mDatas.add("" + i);
		}

		return LoadedResult.SUCCESS;
	}

	class AppListAdapter extends SuperBaseAdapter<String>
	{
		public AppListAdapter(List<String> datas) {
			super(datas);
		}

		@Override
		protected BaseHolder<String> getHolder()
		{
			return new AppItemHolder();
		}
	}

}
