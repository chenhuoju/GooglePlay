package com.chj.googleplay.http;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.chj.googleplay.bean.CategoryBean;

/**
 * @包名: com.chj.googleplay.http
 * @类名: CategoryProtocol
 * @作者: 陈火炬
 * @创建时间 : 2015-8-28 下午2:57:48
 * 
 * @描述: 分类协议
 * 
 * @SVN版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * 
 * @更新描述:
 */
public class CategoryProtocol extends BaseProtocol<List<CategoryBean>>
{

	@Override
	protected String getKey()
	{
		return "category";
	}

	@Override
	protected List<CategoryBean> parseJson(String json)
	{
		List<CategoryBean> list = null;

		// json解析，用原生解析

		try
		{
			// 获取根节点的array
			JSONArray array = new JSONArray(json);

			// 遍历array
			for (int i = 0; i < array.length(); i++)
			{
				// 取出元素
				JSONObject jsonObj = array.getJSONObject(i);

				// 解析title
				String title = jsonObj.getString("title");

				// 创建title对象
				CategoryBean titelbean = new CategoryBean();
				titelbean.title = title;
				titelbean.isTitle = true;

				// 添加到list
				if (list == null)
				{
					list = new LinkedList<CategoryBean>();
				}
				list.add(titelbean);

				// 解析infos节点
				JSONArray infosArray = jsonObj.getJSONArray("infos");
				// 遍历
				for (int j = 0; j < infosArray.length(); j++)
				{
					// 取出元素
					JSONObject obj = infosArray.getJSONObject(j);

					// 封装对象
					CategoryBean infosbean = new CategoryBean();
					infosbean.name1 = obj.getString("name1");
					infosbean.name2 = obj.getString("name2");
					infosbean.name3 = obj.getString("name3");

					infosbean.url1 = obj.getString("url1");
					infosbean.url2 = obj.getString("url2");
					infosbean.url3 = obj.getString("url3");

					// 添加到list
					if (list == null)
					{
						list = new LinkedList<CategoryBean>();
					}
					list.add(infosbean);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return list;
	}

}
