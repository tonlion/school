package com.example.data;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader.ImageCache;

public class SchoolImageCache implements ImageCache {

	private int maxSize = 1024 * 1024 * 10;
	private LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(
			maxSize) {
		protected int sizeOf(String key, Bitmap value) {
			return value.getRowBytes() * value.getHeight();
		};
	};

	@Override
	public Bitmap getBitmap(String arg0) {
		return this.cache.get(arg0);
	}

	@Override
	public void putBitmap(String arg0, Bitmap arg1) {
		this.cache.put(arg0, arg1);
	}

}
