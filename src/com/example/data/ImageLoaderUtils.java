package com.example.data;

import android.widget.ImageView;

import com.example.school.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class ImageLoaderUtils {

	private static DisplayImageOptions options = new DisplayImageOptions.Builder()
			// ���ع�������ʾ��ͼƬ
			.showImageOnLoading(R.drawable.cc_default_news_img)
			// ����ʧ��ʱ��ʾ��ͼƬ
			.showImageOnFail(R.drawable.cc_default_news_img_fail)
			// uriΪ�յ�ʱ����ʾ��ͼƬ
			.showImageForEmptyUri(R.drawable.cc_default_news_img_fail)
			// �Ƿ�����ڴ滺��
			.cacheOnDisk(true).resetViewBeforeLoading(true)
			.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
			.displayer(new FadeInBitmapDisplayer(200)).build();

	public static void display(String url, ImageView imageView) {
		ImageLoader.getInstance().displayImage(url, imageView, options);
	}
}
