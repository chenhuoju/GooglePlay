package com.chj.googleplay.http;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @包名: com.chj.googleplay.http
 * @类名: RecommendProtocol
 * @作者: 陈火炬
 * @创建时间 : 2015-8-28 下午5:18:55
 * 
 * @描述: 推荐页面的网络访问协议
 * 
 * @SVN版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * 
 * @更新描述:
 */
public class RecommendProtocol extends BaseProtocol<List<String>>
{

	@Override
	protected String getKey()
	{
		return "recommend";
	}

	@Override
	protected List<String> parseJson(String json)
	{
		// Json的泛型解析
		return new Gson().fromJson(json, new TypeToken<List<String>>() {
		}.getType());
	}

}
