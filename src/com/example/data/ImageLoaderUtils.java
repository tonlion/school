package com.example.data;

import android.widget.ImageView;

import com.example.school.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class ImageLoaderUtils {

	private static DisplayImageOptions options = new DisplayImageOptions.Builder()
			// 下载过程中显示的图片
			.showImageOnLoading(R.drawable.cc_default_news_img)
			// 下载失败时显示的图片
			.showImageOnFail(R.drawable.cc_default_news_img_fail)
			// uri为空的时候显示的图片
			.showImageForEmptyUri(R.drawable.cc_default_news_img_fail)
			// 是否进行内存缓存
			.cacheOnDisk(true).resetViewBeforeLoading(true)
			.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
			.displayer(new FadeInBitmapDisplayer(200)).build();

	public static void display(String url, ImageView imageView) {
		ImageLoader.getInstance().displayImage(url, imageView, options);
	}
}
