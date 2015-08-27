package com.chj.googleplay.utils;

/**
 * @包名: com.chj.googleplay.utils
 * @类名: Constants
 * @作者: 陈火炬
 * @创建时间 : 2015-8-26 下午5:11:49
 * 
 * @描述: 全局常量
 * 
 * @SVN版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * 
 * @更新描述:
 */
public interface Constants
{
	/** 手机服务器地址 --->存在问题 */
	// String SERVER_URL = "http://localhost:8090/GooglePlayServer/";

	/** 电脑服务器地址(192.168.89.1) */
	String	SERVER_URL		= "http://49.122.47.187:8080/GooglePlayServer/";

	/** 图片基本访问地址 */
	String	BASE_IMAGE_URL	= SERVER_URL + "image?name=";

	/** 页面数量 */
	int		PAGE_SIZE		= 20;

}
