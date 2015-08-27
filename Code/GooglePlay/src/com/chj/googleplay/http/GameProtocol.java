package com.chj.googleplay.http;

import java.util.List;

import com.chj.googleplay.bean.AppInfoBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @包名: com.chj.googleplay.http
 * @类名: GameProtocol
 * @作者: 陈火炬
 * @创建时间 : 2015-8-27 下午5:02:20
 * 
 * @描述: 游戏页面的网络访问协议--->gson的泛型解析
 * 
 * @SVN版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * 
 * @更新描述:
 */
public class GameProtocol extends BaseProtocol<List<AppInfoBean>>
{

	@Override
	protected String getKey()
	{
		return "game";
	}

	@Override
	protected List<AppInfoBean> parseJson(String json)
	{
		// json解析: gson的泛型解析
		Gson gson = new Gson();

		List<AppInfoBean> list = gson.fromJson(json, new TypeToken<List<AppInfoBean>>() {
		}.getType());

		return list;
	}
}
