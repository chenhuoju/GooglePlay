package com.chj.googleplay.http;

import java.util.HashMap;
import java.util.Map;

import com.chj.googleplay.bean.AppInfoBean;
import com.google.gson.Gson;

/**
 * @包名: com.chj.googleplay.http
 * @类名: AppDetailProtocol
 * @作者: 陈火炬
 * @创建时间 : 2015-8-28 下午10:01:01
 * 
 * @描述: 应用详情页面的网络访问协议
 * 
 * @SVN版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * 
 * @更新描述:
 */
public class AppDetailProtocol extends BaseProtocol<AppInfoBean>
{
	private String	mPackageName;

	public AppDetailProtocol(String packageName) {
		this.mPackageName = packageName;
	}

	@Override
	protected String getKey()
	{
		return "detail";
	}

	@Override
	protected Map<String, String> getParameters()
	{
		Map<String, String> map = new HashMap<String, String>();

		map.put("packageName", mPackageName);

		return map;
	}

	@Override
	protected AppInfoBean parseJson(String json)
	{
		// Gson 解析
		return new Gson().fromJson(json, AppInfoBean.class);
	}

}
