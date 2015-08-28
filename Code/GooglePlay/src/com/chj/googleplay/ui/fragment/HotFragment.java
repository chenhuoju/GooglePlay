package com.chj.googleplay.ui.fragment;

import java.util.List;
import java.util.Random;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.chj.googleplay.http.HotProtocol;
import com.chj.googleplay.ui.fragment.LoadingPager.LoadedResult;
import com.chj.googleplay.ui.weight.FlowLayout;
import com.chj.googleplay.utils.DrawableUtils;
import com.chj.googleplay.utils.UIUtils;

/**
 * @包名: com.chj.googleplay.ui.fragment
 * @类名: HotFragment
 * @作者: 陈火炬
 * @创建时间 : 2015-8-28 上午10:47:43
 * 
 * @描述: 排行页面
 * 
 * @SVN版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * 
 * @更新描述:
 */
public class HotFragment extends BaseFragment
{
	private HotProtocol		mProtocol;	// 网络协议
	private List<String>	mDatas;	// 页面对应的数据

	@Override
	protected View onSuccessView()
	{
		// 可滑动的view
		ScrollView rootView = new ScrollView(UIUtils.getContext());

		// 添加流布局
		FlowLayout flowLayout = new FlowLayout(UIUtils.getContext());
		// 设置属性
		flowLayout.setSpace(UIUtils.dip2px(10), UIUtils.dip2px(8));
		flowLayout.setPadding(UIUtils.dip2px(10), UIUtils.dip2px(10), UIUtils.dip2px(10), UIUtils.dip2px(10));
		rootView.addView(flowLayout);

		// 给流布局添加数据
		for (int i = 0; i < mDatas.size(); i++)
		{
			final String text = mDatas.get(i);// 获取当前文本

			TextView tv = new TextView(UIUtils.getContext());
			// 给textView设置属性
			tv.setText(text);// 设置文本
			tv.setGravity(Gravity.CENTER);// 设置文本居中
			tv.setTextColor(Color.WHITE);// 设置文本颜色
			tv.setPadding(UIUtils.dip2px(5), UIUtils.dip2px(2), UIUtils.dip2px(5), UIUtils.dip2px(2));
			// tv.setBackgroundColor(Color.GRAY);// 设置背景
			// tv.setBackgroundResource(R.drawable.hot_tv_normal);//颜色固定，不提倡

			Random rdm = new Random();
			// 0-255
			int red = rdm.nextInt(210) + 20;
			int green = rdm.nextInt(210) + 20;
			int blue = rdm.nextInt(210) + 20;
			// 随机颜色
			int argb = Color.argb(0xff, red, green, blue);

			// 固定颜色
			int fixedColor = Color.argb(0xff, 0xcc, 0xcc, 0xcc);

			// GradientDrawable normalBg = new GradientDrawable();
			// normalBg.setShape(GradientDrawable.RECTANGLE);// 设置矩形
			// normalBg.setCornerRadius(UIUtils.dip2px(5));// 设置边角
			// normalBg.setColor(argb);
			// 优化之后
			GradientDrawable normalBg = DrawableUtils.getGradientDrawable(GradientDrawable.RECTANGLE,
																			UIUtils.dip2px(5),
																			argb);

			GradientDrawable pressBg = DrawableUtils.getGradientDrawable(GradientDrawable.RECTANGLE,
																			UIUtils.dip2px(5),
																			fixedColor);

			// StateListDrawable selectorBg = new StateListDrawable();
			// selectorBg.addState(new int[] {}, normalBg);
			// selectorBg.addState(new int[] {}, pressBg);
			// 优化之后
			StateListDrawable selectorBg = DrawableUtils.getStateListDrawable(normalBg, pressBg);

			// tv.setBackgroundDrawable(normalBg);//过时了
			tv.setBackground(selectorBg);

			flowLayout.addView(tv);

			// 设置文本监听--->注：必须设置监听，不然StateListDrawable就没效果
			tv.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v)
				{
					Toast.makeText(UIUtils.getContext(), text, 0).show();
				}
			});
		}

		return rootView;
	}

	@Override
	protected LoadedResult onLoadData()
	{
		// 新建协议
		mProtocol = new HotProtocol();

		// 网络访问
		try
		{
			mDatas = mProtocol.loadData(0);
			return checkState(mDatas);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return LoadedResult.ERROR;
		}
	}
}
