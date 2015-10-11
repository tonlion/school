package com.example.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.example.data.FileUitlity;
import com.example.entity.ApplicationInfo;
import com.example.school.R;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;

public class UpdateService extends Service {

	private NotificationManager manager;
	private String url;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
	}

	@Override
	public int onStartCommand(final Intent intent, int flags, int startId) {
		url = intent.getStringExtra("url");
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				downLoad(url);
				stopSelf();
			}
		});
		thread.start();
		return super.onStartCommand(intent, flags, startId);
	}

	public void notification(int progress) {
		// 创建notification
		NotificationCompat.Builder builder = new Builder(
				getApplicationContext());
		builder.setContentTitle("校园通");
		builder.setProgress(100, progress, false);
		builder.setSmallIcon(R.drawable.ic_launcher);
		manager.notify(0, builder.build());

	}

	public void notificationFinish() {
		// 创建notification
		NotificationCompat.Builder builder = new Builder(
				getApplicationContext());
		builder.setContentTitle("校园通");
		builder.setContentText("下载完成，点击安装");
		Intent i = new Intent();
		// 调用android安装程序，安装文件
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		i.setAction(Intent.ACTION_VIEW);
		File file = new File(FileUitlity.getInstance(getApplicationContext())
				.makeDir("apk"), "cc.apk");
		i.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive");
		PendingIntent intent = PendingIntent.getActivity(
				getApplicationContext(), 0, i,
				PendingIntent.FLAG_UPDATE_CURRENT);
		builder.setSmallIcon(R.drawable.ic_launcher);
		builder.setContentIntent(intent);
		builder.setAutoCancel(true);
		manager.notify(0, builder.build());
	}

	public void downLoad(String uriStr) {
		HttpURLConnection connection = null;
		URL url = null;
		OutputStream out = null;
		int currentLen = 0;
		int contentLen = 0;
		InputStream in = null;
		try {
			url = new URL(uriStr);
			File file = new File(FileUitlity.getInstance(
					getApplicationContext()).makeDir("apk"), "cc.apk");
			out = new FileOutputStream(file);
			connection = (HttpURLConnection) url.openConnection();
			connection.connect();
			if (connection.getResponseCode() == 200) {
				contentLen = connection.getContentLength();
				in = connection.getInputStream();
				byte[] b = new byte[512];
				int len = 0;
				while ((len = in.read(b)) != -1) {
					out.write(b, 0, len);
					currentLen += len;
					int progress = (int) (((float) currentLen / contentLen) * 100);
					notification(progress);
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
				in.close();
				connection.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		notificationFinish();
	}

	public ApplicationInfo getApkVersion(String urlStr) {
		ApplicationInfo info = new ApplicationInfo();
		info.setVersionCode(2);
		info.setVersionName("2.0");
		info.setVersionUrl("");
		return info;
	}

	public boolean checkUpdate() {
		ApplicationInfo info = getApkVersion("");
		PackageManager manager = getPackageManager();
		try {
			PackageInfo info2 = manager.getPackageInfo("", 0);
			if (info2.versionCode != info.getVersionCode()) {
				return true;
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
}
