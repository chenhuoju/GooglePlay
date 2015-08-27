package com.chj.googleplay.http;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import com.chj.googleplay.utils.FileUtils;
import com.chj.googleplay.utils.IOUtils;
import com.chj.googleplay.utils.LogUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseStream;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

/**
 * @包名: com.chj.googleplay.http
 * @类名: BaseProtocol
 * @作者: 陈火炬
 * @创建时间 : 2015-8-27 上午11:31:48
 * 
 * @描述: 基类协议
 * 
 * @SVN版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * 
 * @更新描述:
 */
public abstract class BaseProtocol<T>
{
	private static final String	DIR			= "json";
	private static final long	DURATION	= 5 * 60 * 1000;
	private HttpUtils			utils;

	/** 获取模块节点 */
	protected abstract String getKey();

	/** 解析json */
	protected abstract T parseJson(String json);

	public T loadData(int index) throws Exception
	{
		// 1.到缓存中去取数据
		T data = getDataFromLocal(index);
		if (data != null)
		{
			LogUtils.d("从缓存中取数据");
			return data;
		}

		// 2.到网络中去取数据
		LogUtils.d("从网络中取数据");
		return getDataFromNet(index);

	}

	/** 从缓存中获取数据 */
	private T getDataFromLocal(int index) throws Exception
	{
		// 存储缓存
		// 1.存储为文件 --->文件名的命名规范
		// ------> getKey() + "." + index
		// 2.文件内容的 --->文件内容中的规范
		// ------> 时间戳 + "\r\n" +json

		String name = getKey() + "." + index;
		File file = new File(FileUtils.getDir(DIR), name);

		// 如果文件不存在，说明本地没有缓存
		if (!file.exists()) { return null; }

		// 文件存在
		BufferedReader reader = null;
		try
		{
			reader = new BufferedReader(new FileReader(file));

			String timeString = reader.readLine();// 时间戳
			long time = Long.valueOf(timeString);

			if (time + DURATION < System.currentTimeMillis())
			{
				// 过期了
				return null;
			}

			String json = reader.readLine();// 读json
			return parseJson(json);
		}
		finally
		{
			IOUtils.close(reader);
		}

	}

	/** 从网络中获取数据 */
	private T getDataFromNet(int index) throws Exception
	{
		if (utils == null)
		{
			utils = new HttpUtils();
		}
		String url = "http://49.122.47.187:8080/GooglePlayServer/" + getKey();

		RequestParams params = new RequestParams();
		params.addQueryStringParameter("index", index + "");

		ResponseStream stream = utils.sendSync(HttpMethod.GET, url, params);// 获取响应流

		int statusCode = stream.getStatusCode();// 获取响应码
		if (statusCode == 200)
		{
			// 正确返回
			String json = stream.readString();// 获取json串
			LogUtils.d(json);

			// 存储到缓存中
			write2Local(index, json);

			// 解析json
			return parseJson(json);

		}
		throw new RuntimeException("服务器连接异常");

	}

	/** 把数据写到缓存中 */
	private void write2Local(int index, String json) throws Exception
	{
		// 存储缓存
		// 1.存储为文件 --->文件名的命名规范
		// ------> getKey() + "." + index
		// 2.文件内容的 --->文件内容中的规范
		// ------> 时间戳 + "\r\n" +json

		String name = getKey() + "." + index;
		File file = new File(FileUtils.getDir(DIR), name);

		// 往文件中写数据
		BufferedWriter writer = null;
		try
		{
			writer = new BufferedWriter(new FileWriter(file));
			writer.write(System.currentTimeMillis() + "");// 写时间戳
			writer.write("\r\n");// 换行
			writer.write(json);

		}
		finally
		{
			IOUtils.close(writer);
		}
	}
}
