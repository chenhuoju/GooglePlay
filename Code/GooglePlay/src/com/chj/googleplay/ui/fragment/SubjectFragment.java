package com.chj.googleplay.ui.fragment;

import java.util.List;

import android.view.View;
import android.widget.AbsListView;

import com.chj.googleplay.bean.SubjectBean;
import com.chj.googleplay.http.SubjectProtocol;
import com.chj.googleplay.ui.adapter.SuperBaseAdapter;
import com.chj.googleplay.ui.fragment.LoadingPager.LoadedResult;
import com.chj.googleplay.ui.holder.BaseHolder;
import com.chj.googleplay.ui.holder.SubjectHolder;
import com.chj.googleplay.ui.weight.BaseListView;
import com.chj.googleplay.utils.UIUtils;

/**
 * @包名: com.chj.googleplay.ui.fragment
 * @类名: SubjectFragment
 * @作者: 陈火炬
 * @创建时间 : 2015-8-27 下午8:42:03
 * 
 * @描述: 专题页面
 * 
 * @SVN版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * 
 * @更新描述:
 */
public class SubjectFragment extends BaseFragment
{

	private SubjectProtocol		mProtocol;
	private List<SubjectBean>	mListDatas;

	@Override
	protected View onSuccessView()
	{
		BaseListView listView = new BaseListView(UIUtils.getContext());

		// 设置adapter
		listView.setAdapter(new SubjectAdapter(listView, mListDatas));

		return listView;
	}

	@Override
	protected LoadedResult onLoadData()
	{
		mProtocol = new SubjectProtocol();

		try
		{
			mListDatas = mProtocol.loadData(0);

			return checkState(mListDatas);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return LoadedResult.ERROR;
		}

	}

	/** 专题适配器类 */
	class SubjectAdapter extends SuperBaseAdapter<SubjectBean>
	{

		public SubjectAdapter(AbsListView listView, List<SubjectBean> datas) {
			super(listView, datas);
		}

		@Override
		protected BaseHolder<SubjectBean> getHolder()
		{
			return new SubjectHolder();
		}

		@Override
		protected List<SubjectBean> onLoadMoreData() throws Exception
		{
			return loadMoreData(mDatas.size());
		}

	}

	private List<SubjectBean> loadMoreData(int index) throws Exception
	{
		return mProtocol.loadData(index);
	}

}
