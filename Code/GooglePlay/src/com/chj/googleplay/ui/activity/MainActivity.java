package com.chj.googleplay.ui.activity;

import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.chj.googleplay.R;
import com.chj.googleplay.factory.PagerFactory;
import com.chj.googleplay.ui.fragment.BaseFragment;
import com.chj.googleplay.ui.holder.MenuHolder;
import com.chj.googleplay.utils.UIUtils;
import com.chj.indicator.lib.TabSlidingIndicator;

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
public class MainActivity extends BaseActivity implements DrawerListener, OnPageChangeListener
{

	private ActionBar				mActionBar;	// actionbar实例

	private DrawerLayout			mDrawerLayout;
	private ActionBarDrawerToggle	mDrawerToggle;	// actionbar菜单的开关

	private TabSlidingIndicator		mIndicator;	// indicator
	private ViewPager				mPager;		// viewpager

	private String[]				mTitles;

	private FrameLayout				mMenuContainer; // 菜单容器

	/** 初始化视图 */
	protected void initView()
	{
		super.initView();
		setContentView(R.layout.act_main);

		mIndicator = (TabSlidingIndicator) findViewById(R.id.main_indicator);
		mPager = (ViewPager) findViewById(R.id.main_viewpager);
		mMenuContainer = (FrameLayout) findViewById(R.id.main_menu);
	}

	/** 初始化ActionBar */
	protected void initActionBar()
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

		// 设置ViewPager监听
		mIndicator.setOnPageChangeListener(this);

	}

	/** 加载数据 */
	protected void initData()
	{
		// 加载list数据
		mTitles = UIUtils.getStringArray(R.array.main_titles);

		// 给viewpager加载数据---> Adapter ---> list
		mPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));

		// 设置indicator的颜色
		mIndicator.setTextColor(UIUtils.getCoclor(R.color.tab_text_normal), UIUtils.getCoclor(R.color.tab_text_selected));

		// 给indicator配置Viewpager
		mIndicator.setViewPager(mPager);

		// 加载menu
		MenuHolder menuHolder = new MenuHolder();
		mMenuContainer.addView(menuHolder.getRootView());
		// TODO: 设置数据
	}

	/** 创建选项菜单(ActionBar)时调用 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/** item点击时的操作 */
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

	/** 当抽屉的位置变化时，此方法调用 */
	@Override
	public void onDrawerSlide(View drawerView, float slideOffset)
	{
		mDrawerToggle.onDrawerSlide(drawerView, slideOffset);
	}

	/** 当抽屉开启时，此方法调用 */
	@Override
	public void onDrawerOpened(View drawerView)
	{
		mDrawerToggle.onDrawerOpened(drawerView);
	}

	/** 当抽屉关闭时，此方法调用 */
	@Override
	public void onDrawerClosed(View drawerView)
	{
		mDrawerToggle.onDrawerClosed(drawerView);
	}

	/** 当抽屉的状态发生变化时，此方法调用 */
	@Override
	public void onDrawerStateChanged(int newState)
	{
		mDrawerToggle.onDrawerStateChanged(newState);
	}

	/**
	 * 自定义MainPagerAdapter
	 * 
	 * @FragmentStatePagerAdapter : 在多页面显示时会销毁fragment
	 * @FragmentPagerAdapter : 不会销毁fragment
	 */
	class MainPagerAdapter extends FragmentStatePagerAdapter
	{

		public MainPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		/** 获取Fragment指定的位置 */
		@Override
		public Fragment getItem(int position)
		{
			// 通过position去找到对应的fragment
			Fragment fragment = PagerFactory.getFragment(position);
			return fragment;
		}

		/** 获取views的数量 */
		@Override
		public int getCount()
		{
			if (mTitles != null) { return mTitles.length; }
			return 0;
		}

		/** 获取请求页面的标题 */
		@Override
		public CharSequence getPageTitle(int position)
		{
			if (mTitles != null) { return mTitles[position]; }
			return super.getPageTitle(position);
		}

	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
	{

	}

	/** 页面选中时才去加载数据 */
	@Override
	public void onPageSelected(int position)
	{
		BaseFragment fragment = PagerFactory.getFragment(position);
		fragment.loadData();
	}

	@Override
	public void onPageScrollStateChanged(int state)
	{

	}

}
