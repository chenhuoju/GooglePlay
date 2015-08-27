package com.chj.googleplay.http;

import java.util.List;

import com.chj.googleplay.bean.SubjectBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @包名: com.chj.googleplay.http
 * @类名: SubjectProtocol
 * @作者: 陈火炬
 * @创建时间 : 2015-8-27 下午8:43:43
 * 
 * @描述: 专题页面的协议
 * 
 * @SVN版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * 
 * @更新描述:
 */
public class SubjectProtocol extends BaseProtocol<List<SubjectBean>>
{

	@Override
	protected String getKey()
	{
		return "subject";
	}

	@Override
	protected List<SubjectBean> parseJson(String json)
	{
		// json解析: gson的泛型解析
		Gson gson = new Gson();
		return gson.fromJson(json, new TypeToken<List<SubjectBean>>() {
		}.getType());

	}
}
