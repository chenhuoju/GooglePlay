package com.chj.googleplay.ui.adapter;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import android.content.Intent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.RecyclerListener;
import android.widget.AdapterView;

import com.chj.googleplay.bean.AppInfoBean;
import com.chj.googleplay.bean.DownloadBean;
import com.chj.googleplay.manager.DownloadManager;
import com.chj.googleplay.manager.DownloadManager.DownloadObserver;
import com.chj.googleplay.ui.activity.AppDetailActivity;
import com.chj.googleplay.ui.holder.AppItemHolder;
import com.chj.googleplay.ui.holder.BaseHolder;
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
public class AppListAdapter extends SuperBaseAdapter<AppInfoBean> implements DownloadObserver, RecyclerListener
{
	/** 当前listView对应的holder */
	private List<AppItemHolder>	mHolders	= new LinkedList<AppItemHolder>();

	public AppListAdapter(AbsListView listView, List<AppInfoBean> datas) {
		super(listView, datas);

		// 设置listView的item回收时的监听
		listView.setRecyclerListener(this);
	}

	@Override
	protected BaseHolder<AppInfoBean> getHolder(int position)
	{
		AppItemHolder holder = new AppItemHolder();
		// 同步,处理安全问题
		synchronized (mHolders)
		{
			mHolders.add(holder);
		}

		return holder;
	}

	@Override
	protected void onInnerItemClick(AdapterView<?> parent, View view, int position, long id)
	{
		AppInfoBean bean = mDatas.get(position);
		// ToastUtils.showToast(UIUtils.getContext(), bean.name);

		Intent intent = new Intent(UIUtils.getContext(), AppDetailActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// 设置标记
		intent.putExtra(AppDetailActivity.KEY_PACKAGE_NAME, bean.packageName);// 传递额外值
		UIUtils.startActivity(intent);

	}

	/** 开启观察者 */
	public void startObserver()
	{
		DownloadManager.getInstance().addObserver(this);

		// UI先刷新
		ListIterator<AppItemHolder> iterator = mHolders.listIterator();
		while (iterator.hasNext())
		{
			AppItemHolder holder = iterator.next();

			holder.checkDownloadState();
		}
	}

	/** 停止观察者 */
	public void stopObserver()
	{
		DownloadManager.getInstance().deleteObserver(this);
	}

	// 当下载的状态改变时的回调
	@Override
	public void onDownloadStateChange(DownloadManager manager, DownloadBean data)
	{
		// 子线程中
		// 让holder去根据状态改变UI
		safeRefreshHolders(data);
	}

	// 当下载的状态改变时的回调
	@Override
	public void onDownloadProgressChange(DownloadManager manager, DownloadBean data)
	{
		// 子线程中
		// 让holder去根据状态改变UI
		safeRefreshHolders(data);
	}

	/** 安全刷新holder */
	private void safeRefreshHolders(DownloadBean data)
	{
		Iterator<AppItemHolder> iterator = mHolders.listIterator();
		while (iterator.hasNext())
		{
			AppItemHolder holder = iterator.next();

			holder.safeRefreshView(data);
		}
	}

	/** 当移除view的时候，此方法调用 */
	@Override
	public void onMovedToScrapHeap(View view)
	{
		Object tag = view.getTag();
		if (tag != null && tag instanceof AppItemHolder)
		{
			// 移除这个holder
			synchronized (mHolders)
			{
				mHolders.remove((AppItemHolder) tag);
			}
		}
	}

}
