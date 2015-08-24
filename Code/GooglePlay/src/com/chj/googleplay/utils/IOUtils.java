package com.chj.googleplay.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * @包名: com.chj.googleplay.utils
 * @类名: IOUtils
 * @作者: 陈火炬
 * @创建时间 : 2015-8-24 下午4:21:10
 * 
 * @描述: IO工具类
 * 
 * @SVN版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * 
 * @更新描述:
 */
public class IOUtils
{
	/** 关闭流 */
	public static boolean close(Closeable io)
	{
		if (io != null)
		{
			try
			{
				io.close();
			}
			catch (IOException e)
			{
				LogUtils.e(e);
			}
		}
		return true;
	}
}
