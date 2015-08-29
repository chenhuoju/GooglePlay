package com.chj.googleplay.bean;

import java.util.List;

/**
 * @包名: com.chj.googleplay.bean
 * @类名: AppInfoBean
 * @作者: 陈火炬
 * @创建时间 : 2015-8-26 上午11:28:58
 * 
 * @描述: 应用详情的bean,是一个并集
 * 
 * @SVN版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * 
 * @更新描述:
 */
public class AppInfoBean
{
	public String				des;
	public String				downloadUrl;
	public String				iconUrl;
	public long					id;
	public String				name;
	public String				packageName;
	public long					size;
	public float				stars;

	public String				author;
	public String				date;

	public String				downloadNum;

	public List<AppSafeBean>	safe;
	public List<String>			screen;

	public String				version;

	class AppSafeBean
	{
		public String	safeDes;
		public int		safeDesColor;
		public String	safeDesUrl;
		public String	safeUrl;
	}

}
