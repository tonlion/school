package com.example.application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.data.SchoolImageCache;
import com.example.entity.Student;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import android.app.Application;

public class SchoolApplication extends Application {

	private RequestQueue queue;
	private static SchoolApplication school;
	private SchoolImageCache cache;
	private Student student;

	@Override
	public void onCreate() {
		super.onCreate();
		school = this;
		queue = Volley.newRequestQueue(this);
		cache = new SchoolImageCache();
		// 初始化UIL
		@SuppressWarnings("deprecation")
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				this)
		// 不要缓存不同尺寸
				.denyCacheImageMultipleSizesInMemory()
				// 下载图片线程的优先级
				.threadPriority(Thread.NORM_PRIORITY - 2)
				// 设置下载线程的执行器（线程池）
				// .taskExecutor(SchoolImageCache.getInstance().getExecutors())
				// 设置内存缓存的大小
				.memoryCacheSize((int) Runtime.getRuntime().maxMemory() / 8)
				// 设置磁盘缓存大小
				.diskCacheSize(50 * 1024 * 1024)
				// 设置磁盘缓存文件的命名生成器
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				// 设置磁盘缓存的路径
				.discCache(
						new UnlimitedDiskCache(com.example.data.FileUitlity
								.getInstance(this).makeDir("imagCache")))
				//
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				// 设置具体的图片加载器：
				.imageDownloader(
						new BaseImageDownloader(this, 60 * 1000, 60 * 1000))
				// 生成配置信息
				.build();
		ImageLoader.getInstance().init(config);
	}

	public static SchoolApplication getInstance() {
		return school;
	}

	public RequestQueue getRequestQueue() {
		return this.queue;
	}

	public SchoolImageCache getImageCache() {
		return this.cache;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
}
