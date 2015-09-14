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
		// ��ʼ��UIL
		@SuppressWarnings("deprecation")
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				this)
		// ��Ҫ���治ͬ�ߴ�
				.denyCacheImageMultipleSizesInMemory()
				// ����ͼƬ�̵߳����ȼ�
				.threadPriority(Thread.NORM_PRIORITY - 2)
				// ���������̵߳�ִ�������̳߳أ�
				// .taskExecutor(SchoolImageCache.getInstance().getExecutors())
				// �����ڴ滺��Ĵ�С
				.memoryCacheSize((int) Runtime.getRuntime().maxMemory() / 8)
				// ���ô��̻����С
				.diskCacheSize(50 * 1024 * 1024)
				// ���ô��̻����ļ�������������
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				// ���ô��̻����·��
				.discCache(
						new UnlimitedDiskCache(com.example.data.FileUitlity
								.getInstance(this).makeDir("imagCache")))
				//
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				// ���þ����ͼƬ��������
				.imageDownloader(
						new BaseImageDownloader(this, 60 * 1000, 60 * 1000))
				// ����������Ϣ
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
