package com.chj.googleplay.ui.fragment;

import java.util.List;

import android.view.View;
import android.widget.AbsListView;

import com.chj.googleplay.bean.AppInfoBean;
import com.chj.googleplay.http.AppProtocol;
import com.chj.googleplay.ui.adapter.AppListAdapter;
import com.chj.googleplay.ui.fragment.LoadingPager.LoadedResult;
import com.chj.googleplay.ui.widget.BaseListView;
import com.chj.googleplay.utils.UIUtils;

/**
 * @包名: com.chj.googleplay.ui.fragment
 * @类名: AppFragment
 * @作者: 陈火炬
 * @创建时间 : 2015-8-27 下午3:59:00
 * 
 * @描述: 应用的界面
 * 
 * @SVN版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * 
 * @更新描述:
 */
public class AppFragment extends BaseFragment
{
	private AppProtocol			mProtocol;
	private List<AppInfoBean>	mListDatas; // listView的数据

	@Override
	protected View onSuccessView()
	{
		BaseListView mListView = new BaseListView(UIUtils.getContext());

		// adapter ---> list
		mListView.setAdapter(new AppListAdapter(mListView, mListDatas));

		return mListView;
	}

	@Override
	protected LoadedResult onLoadData()
	{
		mProtocol = new AppProtocol();

		try
		{
			// 去网络加载数据
			mListDatas = mProtocol.loadData(0);

			return checkState(mListDatas);
		}
		catch (Exception e)
		{
			e.printStackTrace();

			return LoadedResult.ERROR;
		}

	}

	class AppAdapter extends AppListAdapter
	{

		public AppAdapter(AbsListView listView, List<AppInfoBean> datas) {
			super(listView, datas);
		}

		@Override
		protected List<AppInfoBean> onLoadMoreData() throws Exception
		{
			return loadMoreData(mDatas.size());
		}

	}

	/** 加载更多网络数据 */
	private List<AppInfoBean> loadMoreData(int index) throws Exception
	{
		return mProtocol.loadData(index);
	}

}
