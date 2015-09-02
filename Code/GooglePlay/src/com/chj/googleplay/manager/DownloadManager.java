package com.chj.googleplay.manager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.chj.googleplay.bean.AppInfoBean;
import com.chj.googleplay.bean.DownloadBean;
import com.chj.googleplay.utils.Constants;
import com.chj.googleplay.utils.FileUtils;
import com.chj.googleplay.utils.IOUtils;
import com.chj.googleplay.utils.LogUtils;
import com.chj.googleplay.utils.UIUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseStream;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

/**
 * @包名: com.chj.googleplay.manager
 * @类名: DownloadManager
 * @作者: 陈火炬
 * @创建时间 : 2015-8-30 上午9:08:51
 * 
 * @描述: 负责下载管理 --->单例模式
 * 
 * @SVN版本: $Rev$
 * @更新人: $Author$
 * @更新时间: $Date$
 * 
 * @更新描述:
 */
public class DownloadManager
{
	// 1. 未下载 -----> 用户提示： 下载
	// 2. 等待状态----> 用户提示： 等待中...
	// 3. 下载中 -----> 用户提示： 显示进度
	// 4. 暂停 ------> 用户提示： 继续下载
	// 5. 下载成功----> 用户提示： 安装
	// 6. 下载失败----> 用户提示： 重试
	// 7. 已经安装 ---> 用户提示： 打开
	public static final int				STATE_NONE			= 0;											// 未下载
	public static final int				STATE_WAITING		= 1;											// 等待状态
	public static final int				STATE_DOWNLOADING	= 2;											// 下载中
	public static final int				STATE_PAUSE			= 3;											// 暂停
	public static final int				STATE_SUCCESS		= 4;											// 下载成功,未安装
	public static final int				STATE_FAILED		= 5;											// 下载失败
	public static final int				STATE_INSTALLED		= 6;											// 已经安装

	private static final String			DOWNLOAD_DIR		= "apk";
	private static DownloadManager		instance;															// 单例的实例

	private HttpUtils					mHttpUtils;														// http工具

	/** 用来存储下载信息 ,key是应用的包名 */
	private Map<String, DownloadBean>	mDownloadInfos		= new LinkedHashMap<String, DownloadBean>();

	/** 保存下载任务的引用 */
	private Map<String, DownloadTask>	mTasks				= new LinkedHashMap<String, DownloadTask>();

	/** 存储的是观察者 */
	private List<DownloadObserver>		mObservers			= new LinkedList<DownloadObserver>();

	private DownloadManager() {
		mHttpUtils = new HttpUtils();
	}

	/** 获取下载管理者 (单例) */
	public static DownloadManager getInstance()
	{
		if (instance == null)
		{
			synchronized (DownloadManager.class)
			{
				if (instance == null)
				{
					// 双重保证，保证只有一个实例
					instance = new DownloadManager();
				}

			}
		}
		return instance;
	}

	/** 获取当前下载状态 */
	public DownloadBean getCurrentState(AppInfoBean bean)
	{
		// 判断是否存在数据
		if (bean == null) { return null; }

		// 判断是否已经安装
		if (isInstalled(bean.packageName))
		{
			DownloadBean downloadBean = initDownloadBean(bean);
			downloadBean.downloadState = STATE_INSTALLED;
			return downloadBean;
		}

		// 判断是否下载完成
		File file = new File(getAppSavePath(bean.name));
		if (file.exists())
		{
			long length = file.length();
			if (length == bean.size)
			{
				// 下载完
				DownloadBean downloadBean = initDownloadBean(bean);
				downloadBean.downloadState = STATE_SUCCESS;
				return downloadBean;
			}
		}

		// 判断是否下载过
		DownloadBean downloadBean = mDownloadInfos.get(bean.packageName);
		if (downloadBean == null)
		{
			// 没有下载过
			return initDownloadBean(bean);
		}

		return downloadBean;
	}

	/** 判断是否已经安装 */
	private boolean isInstalled(String packageName)
	{
		PackageManager packageManager = UIUtils.getContext().getPackageManager();

		try
		{
			packageManager.getPackageInfo(packageName, 0);
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}

	}

	/** 获取app下载保存的路径 */
	private String getAppSavePath(String name)
	{
		return FileUtils.getDir(DOWNLOAD_DIR) + "/" + name + ".apk";
	}

	/*** 初始化downloadBean */
	private DownloadBean initDownloadBean(AppInfoBean bean)
	{
		DownloadBean downloadBean = new DownloadBean();
		downloadBean.downloadUrl = bean.downloadUrl;// 下载地址
		downloadBean.size = bean.size;// 应用大小
		downloadBean.name = bean.name;// 应用名称
		downloadBean.packageName = bean.packageName;// 应用包名
		downloadBean.savePath = getAppSavePath(bean.name);// 保存路径
		// 未下载 ####
		downloadBean.downloadState = STATE_NONE;// 下载状态,默认未下载
		notifyStateChange(downloadBean);// 通知观察者状态改变
		return downloadBean;
	}

	/** TODO:下载 */
	public void download(AppInfoBean bean)
	{
		DownloadBean downloadBean = initDownloadBean(bean);

		// 等待下载中 ####
		downloadBean.downloadState = STATE_WAITING;
		notifyStateChange(downloadBean);// 通知观察者状态改变

		// 保存下载的信息
		mDownloadInfos.put(bean.packageName, downloadBean);

		// 去网络下载
		DownloadTask task = new DownloadTask(downloadBean);
		// 保存引用
		mTasks.put(bean.packageName, task);

		ThreadManager.getDownloadPool().execute(task);
	}

	/** 通知观察者们状态改变 */
	private void notifyStateChange(DownloadBean data)
	{
		// 通知观察者们下载的状态改变了

		// for循环会存在线程不安全
		// for (int i = 0; i < mObservers.size(); i++)
		// {
		// DownloadObserver observer = mObservers.get(i);
		// observer.onDownloadStateChange(this, data);
		// }

		// 使用迭代器，保证线程安全的操作(进行优化之后)
		ListIterator<DownloadObserver> iterator = mObservers.listIterator();
		while (iterator.hasNext())
		{
			DownloadObserver observer = iterator.next();
			observer.onDownloadStateChange(this, data);
		}

	}

	/** 通知观察者们进度改变 */
	private void notifyProgressChange(DownloadBean data)
	{
		// 通知观察者们下载的进度改变了 ---> 使用迭代器，保证线程安全的操作
		ListIterator<DownloadObserver> iterator = mObservers.listIterator();
		while (iterator.hasNext())
		{
			DownloadObserver observer = iterator.next();
			observer.onDownloadStateChange(this, data);
		}

	}

	/** 下载任务 */
	class DownloadTask implements Runnable
	{
		private DownloadBean	mBean;

		public DownloadTask(DownloadBean bean) {
			this.mBean = bean;
		}

		@Override
		public void run()
		{
			// 正在下载中 ####
			mBean.downloadState = STATE_DOWNLOADING;
			notifyStateChange(mBean);// 通知观察者状态改变

			InputStream is = null;// 输入流
			FileOutputStream fos = null;// 文件输出流
			try
			{
				File file = new File(mBean.savePath);// 存放文件的地址

				long range;
				// 判断文件是否存在
				if (!file.exists())
				{
					// 文件不存在
					range = 0;

				}
				else
				{
					// 获取文件当前的长度
					range = file.length();

					// 判断是否下载完毕
					if (range == mBean.size)
					{
						// 说明已经全部下载了
						return;
					}
				}

				// 去网络下载数据
				RequestParams params = new RequestParams();
				params.addQueryStringParameter("name", mBean.downloadUrl);
				params.addQueryStringParameter("range", range + "");// TODO:实现断点下载

				ResponseStream responseStream = mHttpUtils.sendSync(HttpMethod.GET, Constants.BASE_DOWNLOAD_URL, params);
				is = responseStream.getBaseStream();// 获得输入流

				// 判断是否要追加
				if (range == 0)
				{
					fos = new FileOutputStream(file);
				}
				else
				{
					// 追加
					fos = new FileOutputStream(file, true);
				}

				boolean isPause = false;// 是否是暂停的,做标记用的
				long progress = range;// 进度

				byte[] buffer = new byte[1024];
				int len = -1;
				while ((len = is.read(buffer)) != -1)
				{
					LogUtils.d("下载中......");

					// 写到本地
					fos.write(buffer, 0, len);

					progress += len;// 进度变化

					// 将进度push出去
					mBean.currenDownloadLength = progress;
					notifyProgressChange(mBean);

					// 如果是暂停的话,break
					if (mBean.downloadState == STATE_PAUSE)
					{
						isPause = true;// 是暂停的
						break;
					}
				}

				if (isPause)
				{
					// 暂停的状态
					notifyStateChange(mBean);
				}
				else
				{
					// 下载成功 ####
					mBean.downloadState = STATE_SUCCESS;
					notifyStateChange(mBean);// 通知观察者状态改变
				}

			}
			catch (Exception e)
			{
				e.printStackTrace();

				// 下载失败 ####
				mBean.downloadState = STATE_FAILED;
				notifyStateChange(mBean);// 通知观察者状态改变
			}
			finally
			{
				IOUtils.close(is);
				IOUtils.close(fos);

				// 清除task
				mTasks.remove(this);
			}

		}
	}

	/** TODO:安装应用程序 */
	public void install(AppInfoBean bean)
	{
		File file = new File(getAppSavePath(bean.name));

		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
		UIUtils.startActivity(intent);

	}

	/** TODO:打开应用 */
	public void open(AppInfoBean bean)
	{
		Intent intent = UIUtils.getContext().getPackageManager()
								.getLaunchIntentForPackage(bean.packageName);
		UIUtils.startActivity(intent);

	}

	/** TODO:暂停下载 */
	public void pause(AppInfoBean bean)
	{
		// 找到下载的相关信息
		DownloadBean downloadBean = mDownloadInfos.get(bean.packageName);
		if (downloadBean == null) { return; }

		// 暂停下载 ####
		// 如果处于正在下载中，需要停掉下载的任务
		downloadBean.downloadState = STATE_PAUSE;// 设置状态为暂停的状态

		// 如果在排队中，从队列中移除
		ThreadManager.getDownloadPool().remove(mTasks.get(bean.packageName));

	}

	/** 添加观察者 */
	public void addObserver(DownloadObserver observer)
	{
		if (observer == null) { throw new NullPointerException("observer == null"); }
		synchronized (this)
		{
			if (!mObservers.contains(observer)) mObservers.add(observer);
		}
	}

	/** 删除观察者 */
	public synchronized void deleteObserver(DownloadObserver observer)
	{
		mObservers.remove(observer);
	}

	/** 自定义下载观察者接口 */
	public interface DownloadObserver
	{
		/**
		 * 当下载的状态改变时的回调
		 * 
		 * @param manager
		 *            : 下载的管理者
		 * @param data
		 *            : 状态对应的数据
		 */
		void onDownloadStateChange(DownloadManager manager, DownloadBean data);

		/** 当进度条改变时的回调 */
		void onDownloadProgressChange(DownloadManager manager, DownloadBean data);

	}

}
