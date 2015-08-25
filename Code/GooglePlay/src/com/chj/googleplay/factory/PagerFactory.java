package com.chj.googleplay.factory;

import java.util.LinkedHashMap;
import java.util.Map;

import com.chj.googleplay.ui.fragment.BaseFragment;
import com.chj.googleplay.ui.fragment.HomeFragment;
import com.chj.googleplay.utils.LogUtils;

/**
 * @包名: com.chj.googleplay.factory
 * @类名: PagerFactory
 * @作者: 陈火炬
 * @创建时间 : 2015-8-24 下午7:00:39
 * 
 * @描述: 提供主页中对应的子页面的工厂类
 * 
 * @SVN版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * 
 * @更新描述:
 */
public class PagerFactory
{
	// 缓存集合
	private static Map<Integer, BaseFragment>	mCaches	= new LinkedHashMap<Integer, BaseFragment>();

	public static BaseFragment getFragment(int position)
	{
		BaseFragment fragment = mCaches.get(position);

		// 判断缓存中是否有
		if (fragment != null)
		{
			LogUtils.d("读取缓存 :" + position);// 打印日志
			return fragment;
		}

		switch (position)
		{
			case 0:// 首页
				fragment = new HomeFragment();
				break;
			case 1:// 应用
				fragment = new HomeFragment();
				break;
			case 2:// 游戏
				fragment = new HomeFragment();
				break;
			case 3:// 专题
				fragment = new HomeFragment();
				break;
			case 4:// 推荐
				fragment = new HomeFragment();
				break;
			case 5:// 分类
				fragment = new HomeFragment();
				break;
			case 6:// 排行
				fragment = new HomeFragment();
				break;
			default:
				break;
		}

		// 存储到缓存
		mCaches.put(position, fragment);
		LogUtils.d("新建缓存 :" + position);// 打印日志

		return fragment;
	}

}
