package com.chj.googleplay.bean;

/**
 * @包名: com.chj.googleplay.bean
 * @类名: DownloadBean
 * @作者: 陈火炬
 * @创建时间 : 2015-8-30 上午9:57:57
 * 
 * @描述: 记录下载相关的数据
 * 
 * @SVN版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * 
 * @更新描述:
 */
public class DownloadBean
{
	public String	downloadUrl;			// 网络下载地址
	public String	savePath;				// 存储的路径
	public long		size;					// 应用的总长度
	public String	name;					// 应用的名称
	public int		downloadState;			// 下载状态
	public String	packageName;			// 应用的包名
	public long		currenDownloadLength;	// 当前下载的长度
}
