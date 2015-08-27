package com.chj.googleplay.http;

import java.util.ArrayList;
import java.util.List;

import com.chj.googleplay.bean.AppInfoBean;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

/**
 * @包名: com.chj.googleplay.http
 * @类名: AppProtocol
 * @作者: 陈火炬
 * @创建时间 : 2015-8-27 下午4:04:18
 * 
 * @描述: 应用页面的网络访问协议--->Json节点解析
 * 
 * @SVN版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * 
 * @更新描述:
 */
public class AppProtocol extends BaseProtocol<List<AppInfoBean>>
{

	@Override
	protected String getKey()
	{
		return "app";
	}

	@Override
	protected List<AppInfoBean> parseJson(String json)
	{
		// json解析 : Gson节点解析

		// JsonElement : 所有的节点都是JsonElement对象.
		//
		// JsonPrimitive : 基本的数据类型的节点对象,JsonElement的子类.
		//
		// JsonNull : 代表空节点对象,即有key,value为空,JsonElement的子类.
		//
		// JsonObject : 对象数据类型的节点对象,JsonElement的子类.
		//
		// JsonArray : 数组数据类型的节点对象,JsonElement的子类.

		List<AppInfoBean> list = null;

		// 1.新建json解析器
		JsonParser parser = new JsonParser();

		// 2.解析
		JsonElement root = parser.parse(json);

		// 3.获得详细类型
		JsonArray jsonArray = root.getAsJsonArray();

		for (int i = 0; i < jsonArray.size(); i++)
		{
			JsonElement element = jsonArray.get(i);

			// 详细类型
			JsonObject object = element.getAsJsonObject();

			JsonPrimitive desJson = object.getAsJsonPrimitive("des");
			JsonPrimitive downloadUrlJson = object.getAsJsonPrimitive("downloadUrl");
			JsonPrimitive iconUrlJson = object.getAsJsonPrimitive("iconUrl");
			JsonPrimitive idJson = object.getAsJsonPrimitive("id");
			JsonPrimitive nameJson = object.getAsJsonPrimitive("name");
			JsonPrimitive packageNameJson = object.getAsJsonPrimitive("packageName");
			JsonPrimitive sizeJson = object.getAsJsonPrimitive("size");
			JsonPrimitive starsJson = object.getAsJsonPrimitive("stars");

			AppInfoBean bean = new AppInfoBean();

			bean.des = desJson.getAsString();
			bean.downloadUrl = downloadUrlJson.getAsString();
			bean.iconUrl = iconUrlJson.getAsString();
			bean.id = idJson.getAsLong();
			bean.name = nameJson.getAsString();
			bean.packageName = packageNameJson.getAsString();
			bean.size = sizeJson.getAsLong();
			bean.stars = starsJson.getAsFloat();

			if (list == null)
			{
				list = new ArrayList<AppInfoBean>();
			}
			list.add(bean);
		}

		return list;
	}

}
