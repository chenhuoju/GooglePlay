package com.chj.googleplay.ui.holder;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chj.googleplay.R;
import com.chj.googleplay.bean.CategoryBean;
import com.chj.googleplay.utils.BitmapHelper;
import com.chj.googleplay.utils.Constants;
import com.chj.googleplay.utils.ToastUtils;
import com.chj.googleplay.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * @包名: com.chj.googleplay.ui.holder
 * @类名: CategoryItemHolder
 * @作者: 陈火炬
 * @创建时间 : 2015-8-28 下午3:40:22
 * 
 * @描述: 分类item的holder
 * 
 * @SVN版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * 
 * @更新描述:
 */
public class CategoryItemHolder extends BaseHolder<CategoryBean>
{
	@ViewInject(R.id.item_category_item_1)
	private View			mItem1;
	@ViewInject(R.id.item_category_item_2)
	private View			mItem2;
	@ViewInject(R.id.item_category_item_3)
	private View			mItem3;

	@ViewInject(R.id.item_category_icon_1)
	private ImageView		mIv1;
	@ViewInject(R.id.item_category_icon_2)
	private ImageView		mIv2;
	@ViewInject(R.id.item_category_icon_3)
	private ImageView		mIv3;

	@ViewInject(R.id.item_category_name_1)
	private TextView		mTv1;
	@ViewInject(R.id.item_category_name_2)
	private TextView		mTv2;
	@ViewInject(R.id.item_category_name_3)
	private TextView		mTv3;

	private CategoryBean	mDatas;

	@Override
	protected View initView()
	{
		View view = View.inflate(UIUtils.getContext(), R.layout.item_category, null);

		// View注入
		ViewUtils.inject(this, view);

		return view;
	}

	@Override
	protected void refreshUI(CategoryBean data)
	{
		this.mDatas = data;

		// 给视图铺数据

		// 给textView设置数据
		mTv1.setText(data.name1);
		mTv2.setText(data.name2);
		mTv3.setText(data.name3);

		// 给imageView设置默认图片
		mIv1.setImageResource(R.drawable.ic_default);
		mIv2.setImageResource(R.drawable.ic_default);
		mIv3.setImageResource(R.drawable.ic_default);

		// 网络加载图片
		display(mItem1, mIv1, data.url1);
		display(mItem2, mIv2, data.url2);
		display(mItem3, mIv3, data.url3);

	}

	private void display(View itemView, ImageView iv, String uri)
	{
		if (!TextUtils.isEmpty(uri))
		{
			itemView.setVisibility(View.VISIBLE);
			BitmapHelper.display(iv, Constants.BASE_IMAGE_URL + uri);
		}
		else
		{
			itemView.setVisibility(View.INVISIBLE);
		}
	}

	// 点击事件

	@OnClick(R.id.item_category_item_1)
	public void clickItem1(View view)
	{
		// Toast.makeText(UIUtils.getContext(), mDatas.name1, 0).show();
		ToastUtils.showToast(UIUtils.getContext(), mDatas.name1);
	}

	@OnClick(R.id.item_category_item_2)
	public void clickItem2(View view)
	{
		// Toast.makeText(UIUtils.getContext(), mDatas.name2, 0).show();
		ToastUtils.showToast(UIUtils.getContext(), mDatas.name2);
	}

	@OnClick(R.id.item_category_item_3)
	public void clickItem3(View view)
	{
		// Toast.makeText(UIUtils.getContext(), mDatas.name3, 0).show();
		ToastUtils.showToast(UIUtils.getContext(), mDatas.name3);
	}

}
