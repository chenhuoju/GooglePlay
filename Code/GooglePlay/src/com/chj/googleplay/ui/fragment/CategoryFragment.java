package com.chj.googleplay.ui.fragment;

import java.util.List;

import android.view.View;
import android.widget.AbsListView;

import com.chj.googleplay.bean.CategoryBean;
import com.chj.googleplay.http.CategoryProtocol;
import com.chj.googleplay.ui.adapter.SuperBaseAdapter;
import com.chj.googleplay.ui.fragment.LoadingPager.LoadedResult;
import com.chj.googleplay.ui.holder.BaseHolder;
import com.chj.googleplay.ui.holder.CategoryItemHolder;
import com.chj.googleplay.ui.holder.CategoryTitleHolder;
import com.chj.googleplay.ui.widget.BaseListView;
import com.chj.googleplay.utils.UIUtils;

/**
 * @包名: com.chj.googleplay.ui.fragment
 * @类名: CategoryFragment
 * @作者: 陈火炬
 * @创建时间 : 2015-8-28 下午2:34:39
 * 
 * @描述: 分类页面
 * 
 * @SVN版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * 
 * @更新描述:
 */
public class CategoryFragment extends BaseFragment
{
	private CategoryProtocol	mProtocol;
	private List<CategoryBean>	mListDatas;

	@Override
	protected View onSuccessView()
	{
		// 新建listView
		BaseListView listView = new BaseListView(UIUtils.getContext());

		// 设置Adpater --> list
		listView.setAdapter(new CategoryAdapter(listView, mListDatas));

		return listView;
	}

	@Override
	protected LoadedResult onLoadData()
	{
		// 新建协议
		mProtocol = new CategoryProtocol();

		try
		{
			// 加载数据
			mListDatas = mProtocol.loadData(0);

			// 数据检查
			return checkState(mListDatas);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return LoadedResult.ERROR;
		}
	}

	class CategoryAdapter extends SuperBaseAdapter<CategoryBean>
	{

		public CategoryAdapter(AbsListView listView, List<CategoryBean> datas) {
			super(listView, datas);
		}

		@Override
		protected BaseHolder<CategoryBean> getHolder(int position)
		{
			// 根据数据判断 该显示什么样的view
			CategoryBean bean = mDatas.get(position);

			if (bean.isTitle)
			{
				// 分类title的持有者
				return new CategoryTitleHolder();
			}
			else
			{
				// 分类item的持有者
				return new CategoryItemHolder();
			}
		}

		/** 取消加载更多 */
		@Override
		protected boolean hasLoadMore()
		{
			return false;
		}

		@Override
		public int getViewTypeCount()
		{
			return super.getViewTypeCount() + 1;
		}

		@Override
		protected int getInnerItemViewType(int position)
		{
			CategoryBean bean = mDatas.get(position);
			if (bean.isTitle)
			{
				// titleholder
				return super.getInnerItemViewType(position);
			}
			else
			{
				// itemholder
				return super.getInnerItemViewType(position) + 1;
			}
		}

	}

}
