package com.chj.googleplay.http;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @包名: com.chj.googleplay.http
 * @类名: HotProtocol
 * @作者: 陈火炬
 * @创建时间 : 2015-8-28 上午10:50:55
 * 
 * @描述: 排行页面的网络协议
 * 
 * @SVN版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * 
 * @更新描述:
 */
public class HotProtocol extends BaseProtocol<List<String>>
{

	@Override
	protected String getKey()
	{
		return "hot";
	}

	@Override
	protected List<String> parseJson(String json)
	{
		// json解析:泛型解析
		return new Gson().fromJson(json, new TypeToken<List<String>>() {
		}.getType());
	}
}
