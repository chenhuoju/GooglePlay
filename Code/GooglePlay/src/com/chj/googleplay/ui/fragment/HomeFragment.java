package com.chj.googleplay.ui.fragment;

import java.util.List;

import android.view.View;
import android.widget.AbsListView;

import com.chj.googleplay.bean.AppInfoBean;
import com.chj.googleplay.bean.HomeBean;
import com.chj.googleplay.http.HomeProtocol;
import com.chj.googleplay.ui.adapter.AppListAdapter;
import com.chj.googleplay.ui.fragment.LoadingPager.LoadedResult;
import com.chj.googleplay.ui.holder.HomePictureHolder;
import com.chj.googleplay.ui.widget.BaseListView;
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
	private List<AppInfoBean>	mListDatas; // listView对应的数据
	private List<String>		mPictures;	// 轮播图数据

	private HomeBean			mDataBean;
	private HomeProtocol		mProtocol;

	@Override
	protected View onSuccessView()
	{
		// TextView tv = new TextView(UIUtils.getContext());
		// tv.setText("首页");
		// tv.setGravity(Gravity.CENTER);
		// tv.setTextSize(24);
		// return tv;

		// ListView mListView = new ListView(UIUtils.getContext());
		// // 设置listView的样式
		// mListView.setCacheColorHint(Color.TRANSPARENT);
		// mListView.setSelector(android.R.color.transparent);
		// mListView.setFadingEdgeLength(0);// 设置边缘
		// mListView.setDividerHeight(0);// 设置分割线
		// mListView.setBackgroundColor(UIUtils.getCoclor(R.color.bg));
		// 抽取，优化之后
		BaseListView mListView = new BaseListView(UIUtils.getContext());

		// 加载轮播图
		HomePictureHolder pictureHolder = new HomePictureHolder();
		// 加载头
		mListView.addHeaderView(pictureHolder.getRootView());
		// 给holder数据
		pictureHolder.setData(mPictures);

		// adapter ---> list
		mListView.setAdapter(new AppListAdapter(mListView, mListDatas));

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
		//
		// mDatas = new LinkedList<String>();
		// for (int i = 0; i < 50; i++)
		// {
		// mDatas.add("" + i);
		// }

		// 去网络获取数据
		// HttpUtils utils = new HttpUtils();
		// String url = "http://49.122.47.187:8080/GooglePlayServer/home";
		//
		// RequestParams params = new RequestParams();
		// params.addQueryStringParameter("index", 0 + "");
		//
		// try
		// {
		// ResponseStream stream = utils.sendSync(HttpMethod.GET, url, params);
		//
		// int statusCode = stream.getStatusCode();
		// if (statusCode == 200)
		// {
		// // 正确返回
		// String json = stream.readString();
		// LogUtils.d(json);
		//
		// // 解析json
		// Gson gson = new Gson();
		// mDataBean = gson.fromJson(json, HomeBean.class);
		//
		// if (mDataBean == null) { return LoadedResult.EMPTY; }
		//
		// mListDatas = mDataBean.list;
		// mPictures = mDataBean.picture;
		//
		// if (mListDatas == null || mListDatas.size() == 0) { return
		// LoadedResult.EMPTY; }
		// }
		// else
		// {
		// return LoadedResult.ERROR;
		// }
		// }
		// catch (Exception e)
		// {
		// e.printStackTrace();
		// return LoadedResult.ERROR;
		// }
		//
		// return LoadedResult.SUCCESS;

		mProtocol = new HomeProtocol();
		try
		{
			mDataBean = mProtocol.loadData(0);

			if (mDataBean == null) { return LoadedResult.EMPTY; }

			LoadedResult state = checkState(mDataBean);
			if (state == LoadedResult.EMPTY) { return state; }

			mListDatas = mDataBean.list;
			mPictures = mDataBean.picture;

			return checkState(mListDatas);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return LoadedResult.ERROR;
		}

	}

	class HomeAdapter extends AppListAdapter
	{
		public HomeAdapter(AbsListView listView, List<AppInfoBean> datas) {
			super(listView, datas);
		}

		@Override
		protected List<AppInfoBean> onLoadMoreData() throws Exception
		{
			return loadMoreData(mDatas.size());
		}

	}

	/** 网络加载数据 */
	private List<AppInfoBean> loadMoreData(int index) throws Exception
	{
		// HttpUtils utils = new HttpUtils();
		// String url = "http://49.122.47.187:8080/GooglePlayServer/home";
		//
		// RequestParams params = new RequestParams();
		// params.addQueryStringParameter("index", index + "");
		//
		// ResponseStream stream = utils.sendSync(HttpMethod.GET, url, params);
		//
		// int statusCode = stream.getStatusCode();
		// if (statusCode == 200)
		// {
		// // 正确返回
		// String json = stream.readString();
		// LogUtils.d(json);
		//
		// // 解析json
		// Gson gson = new Gson();
		// HomeBean bean = gson.fromJson(json, HomeBean.class);
		//
		// if (bean == null) { return null; }
		//
		// return bean.list;
		// }
		// else
		// {
		// return null;
		// }

		// 抽取，优化之后的
		HomeBean bean = mProtocol.loadData(index);

		return bean.list;

	}

}
