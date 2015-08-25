package com.chj.googleplay.ui.fragment.adapter;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.chj.googleplay.ui.holder.BaseHolder;

/**
 * @param <T>
 * @包名: com.chj.googleplay.ui.fragment.adapter
 * @类名: SuperBaseAdapter
 * @作者: 陈火炬
 * @创建时间 : 2015-8-25 上午11:51:14
 * 
 * @描述: listView对应的adapter基类
 * 
 * @SVN版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * 
 * @更新描述:
 */
public abstract class SuperBaseAdapter<T> extends BaseAdapter
{
	protected List<T>	mDatas;

	public SuperBaseAdapter(List<T> datas) {
		this.mDatas = datas;
	}

	@Override
	public int getCount()
	{
		if (mDatas != null) { return mDatas.size(); }
		return 0;
	}

	@Override
	public Object getItem(int position)
	{
		if (mDatas != null) { return mDatas.get(position); }
		return null;
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		BaseHolder holder = null;
		if (convertView == null)
		{
			// 没有复用

			// 1.holder初始化
			holder = getHolder();

			// 2.加载view
			convertView = holder.getRootView();
		}
		else
		{
			// 有复用

			holder = (BaseHolder) convertView.getTag();
		}

		// 设置数据,给View铺数据
		holder.setData(mDatas.get(position));

		return convertView;
	}

	protected abstract BaseHolder<T> getHolder();

}
