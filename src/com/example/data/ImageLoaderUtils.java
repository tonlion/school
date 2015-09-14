package com.example.data;

import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

public class ImageLoaderUtils {

	public static void display(String url, ImageView imageView) {
		ImageLoader.getInstance().displayImage(url, imageView);
	}
}
