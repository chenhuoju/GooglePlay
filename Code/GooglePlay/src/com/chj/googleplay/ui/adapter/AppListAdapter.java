package com.chj.googleplay.ui.adapter;

import java.util.List;

import android.content.Intent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;

import com.chj.googleplay.bean.AppInfoBean;
import com.chj.googleplay.ui.activity.AppDatailActivity;
import com.chj.googleplay.ui.holder.AppItemHolder;
import com.chj.googleplay.ui.holder.BaseHolder;
import com.chj.googleplay.utils.ToastUtils;
import com.chj.googleplay.utils.UIUtils;

/**
 * @包名: com.chj.googleplay.ui.adapter
 * @类名: AppListAdapter
 * @作者: 陈火炬
 * @创建时间 : 2015-8-28 下午8:39:23
 * 
 * @描述: 应用详情对应的adapter的抽取
 * 
 * @SVN版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * 
 * @更新描述:
 */
public class AppListAdapter extends SuperBaseAdapter<AppInfoBean>
{

	public AppListAdapter(AbsListView listView, List<AppInfoBean> datas) {
		super(listView, datas);
	}

	@Override
	protected BaseHolder<AppInfoBean> getHolder(int position)
	{
		return new AppItemHolder();
	}

	@Override
	protected void onInnerItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		AppInfoBean bean = mDatas.get(position);
		ToastUtils.showToast(UIUtils.getContext(), bean.name);

		Intent intent = new Intent(UIUtils.getContext(), AppDatailActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// 设置标记
		UIUtils.startActivity(intent);

	}

}
