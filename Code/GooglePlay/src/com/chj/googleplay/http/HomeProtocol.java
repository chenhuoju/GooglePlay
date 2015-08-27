package com.chj.googleplay.http;

import com.chj.googleplay.bean.HomeBean;
import com.google.gson.Gson;

/**
 * @包名: com.chj.googleplay.http
 * @类名: HomeProtocol
 * @作者: 陈火炬
 * @创建时间 : 2015-8-27 上午10:51:13
 * 
 * @描述: Home协议
 * 
 * @SVN版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * 
 * @更新描述:
 */
public class HomeProtocol extends BaseProtocol<HomeBean>
{
	// public HomeBean loadData(int index) throws Exception
	// {
	// HttpUtils utils = new HttpUtils();
	// String url = "http://49.122.47.187:8080/GooglePlayServer/home";
	//
	// RequestParams params = new RequestParams();
	// params.addQueryStringParameter("index", index + "");
	//
	// ResponseStream stream = utils.sendSync(HttpMethod.GET, url, params);//
	// 获取响应流
	//
	// int statusCode = stream.getStatusCode();// 获取响应码
	// if (statusCode == 200)
	// {
	// // 正确返回
	// String json = stream.readString();// 获取json串
	// LogUtils.d(json);
	//
	// // 解析json
	// Gson gson = new Gson();
	// return gson.fromJson(json, HomeBean.class);
	//
	// }
	// throw new RuntimeException("服务器连接异常");
	// }

	// 抽取，优化之后

	@Override
	protected String getKey()
	{
		// 返回模块节点
		return "home";
	}

	@Override
	protected HomeBean parseJson(String json)
	{
		// 解析json串
		Gson gson = new Gson();
		return gson.fromJson(json, HomeBean.class);
	}
}
