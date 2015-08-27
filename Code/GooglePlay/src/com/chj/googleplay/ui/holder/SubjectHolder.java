package com.chj.googleplay.ui.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chj.googleplay.R;
import com.chj.googleplay.bean.SubjectBean;
import com.chj.googleplay.utils.BitmapHelper;
import com.chj.googleplay.utils.Constants;
import com.chj.googleplay.utils.UIUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @包名: com.chj.googleplay.ui.holder
 * @类名: SubjectHolder
 * @作者: 陈火炬
 * @创建时间 : 2015-8-27 下午9:03:59
 * 
 * @描述: 专题对应的item的holder
 * 
 * @SVN版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * 
 * @更新描述:
 */
public class SubjectHolder extends BaseHolder<SubjectBean>
{
	@ViewInject(R.id.item_subject_iv_icon)
	private ImageView	mIvIcon;

	@ViewInject(R.id.item_subject_tv_des)
	private TextView	mTvDes;

	@Override
	protected View initView()
	{
		View view = View.inflate(UIUtils.getContext(), R.layout.item_subject, null);

		// view注入
		ViewUtils.inject(this, view);

		return view;
	}

	@Override
	protected void refreshUI(SubjectBean data)
	{
		mTvDes.setText(data.des);

		mIvIcon.setImageResource(R.drawable.ic_default);// 默认值设置
		String uri = Constants.BASE_IMAGE_URL + data.url;
		BitmapHelper.display(mIvIcon, uri);
	}

}
