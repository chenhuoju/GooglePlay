package com.chj.googleplay;

import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * @包名: com.chj.googleplay
 * @类名: MainActivity
 * @作者: 陈火炬
 * @创建时间 : 2015-8-24 上午10:57:39
 * 
 * @描述: 主页抽屉的实现
 * 
 * @SVN版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * 
 * @更新描述:
 */
public class MainActivity extends ActionBarActivity implements DrawerListener
{

	private ActionBar				mActionBar;	// actionbar实例

	private DrawerLayout			mDrawerLayout;

	private ActionBarDrawerToggle	mDrawerToggle;	// actionbar菜单的开关

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_main);

		initActionBar();
	}

	/**
	 * 初始化ActionBar
	 */
	private void initActionBar()
	{
		mActionBar = getSupportActionBar();
		// 设置相关的参数
		// mActionBar.setTitle("GoogleMarket");// 设置title
		// mActionBar.setIcon(android.R.drawable.ic_delete);// 设置图标
		mActionBar.setDisplayHomeAsUpEnabled(true);// 设置back按钮的显示

		mDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawer_layout);

		mDrawerToggle = new ActionBarDrawerToggle(this,
													mDrawerLayout,// 需要和drawerlayou搭配使用
													R.drawable.ic_drawer_am,// 图标资源
													R.string.main_drawer_toggle_open,// drawer的开启描述
													R.string.main_drawer_toggle_open);// drawer的关闭描述
		// 开启同步--->必须执行的操作
		mDrawerToggle.syncState();

		// 设置mDrawLayout的监听
		mDrawerLayout.setDrawerListener(this);
	}

	/**
	 * 创建选项菜单(ActionBar)时调用
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * item点击时的操作
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int itemId = item.getItemId();
		switch (itemId)
		{
			case android.R.id.home:
				boolean selected = mDrawerToggle.onOptionsItemSelected(item);
				if (selected) { return true; }
				break;
			default:
				break;
		}

		return super.onOptionsItemSelected(item);
	}

	/**
	 * 当抽屉的位置变化时，此方法调用
	 */
	@Override
	public void onDrawerSlide(View drawerView, float slideOffset)
	{
		mDrawerToggle.onDrawerSlide(drawerView, slideOffset);
	}

	/**
	 * 当抽屉开启时，此方法调用
	 */
	@Override
	public void onDrawerOpened(View drawerView)
	{
		mDrawerToggle.onDrawerOpened(drawerView);
	}

	/**
	 * 当抽屉关闭时，此方法调用
	 */
	@Override
	public void onDrawerClosed(View drawerView)
	{
		mDrawerToggle.onDrawerClosed(drawerView);
	}

	/**
	 * 当抽屉的状态发生变化时，此方法调用
	 */
	@Override
	public void onDrawerStateChanged(int newState)
	{
		mDrawerToggle.onDrawerStateChanged(newState);
	}

}
