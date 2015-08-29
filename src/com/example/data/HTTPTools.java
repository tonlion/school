package com.example.data;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Set;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class HTTPTools {

	// 定义connection
	private HttpURLConnection connection;
	private StringBuilder builder;

	/**
	 * get和post方法时调用
	 */
	public HTTPTools() {
		super();
		connection = null;
		builder = null;
	}

	/**
	 * 
	 * @param urlStr
	 *            URL地址
	 * @return
	 */
	// POST方法得到数据
	public String doPost(String urlStr) {
		return doPost(urlStr, null);
	}

	/**
	 * 
	 * @param urlStr
	 *            URL地址
	 * @param params
	 *            参数列表
	 * @return
	 */
	public String doPost(String urlStr, Map<String, String> params) {
		try {
			URL url = new URL(urlStr);
			connection = (HttpURLConnection) url.openConnection();
			// 设置参数
			connection.setConnectTimeout(5 * 1000);
			// 是否使用缓存
			connection.setUseCaches(false);
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setDoInput(true);
			// 建立链接
			connection.connect();
			// 向后台传递数据
			String useInfo = "";
			OutputStream oStream = connection.getOutputStream();
			if (params != null) {
				Set<String> keys = params.keySet();
				for (String obj : keys) {
					useInfo += obj + "=" + params.get(obj) + "&";
				}
				useInfo.substring(1, useInfo.length() - 1);
			}
			oStream.write(useInfo.getBytes());
			oStream.flush();
			oStream.close();
			// 接收从后台返回的数据
			InputStream iStream = connection.getInputStream();
			byte[] buffer = new byte[1024];
			builder = new StringBuilder();
			int len = 0;
			if ((len = iStream.read(buffer)) != -1) {
				builder.append(new String(buffer, 0, len));
			}
			iStream.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		return builder.toString();

	}

	/**
	 * 
	 * @param urlStr
	 *            URL地址
	 * @return
	 */
	// GET方法得到数据
	public String doGet(String urlStr) {
		try {
			URL url = new URL(urlStr);
			connection = (HttpURLConnection) url.openConnection();
			// 设置参数
			connection.setConnectTimeout(5 * 1000);
			if (connection.getResponseCode() != 200) {
				return null;
			}
			// 接收从后台返回的数据
			InputStream iStream = connection.getInputStream();
			byte[] buffer = new byte[1024];
			builder = new StringBuilder();
			int len = 0;
			if ((len = iStream.read(buffer)) != -1) {
				builder.append(new String(buffer, 0, len));
			}
			iStream.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}

	/**
	 * 
	 * @param bitmapUrlString
	 *            图片的下载路径
	 * @return
	 */
	// 加载图片
	public Bitmap loadBitmap(String bitmapUrlString) {
		try {
			URL url = new URL(bitmapUrlString);
			connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(5 * 1000);
			connection.setReadTimeout(5 * 1000);
			if (connection.getResponseCode() == 200) {
				InputStream iStream = connection.getInputStream();
				Bitmap b = BitmapFactory.decodeStream(iStream);
				return b;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
