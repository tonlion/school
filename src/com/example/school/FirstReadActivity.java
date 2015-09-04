package com.example.school;

import java.util.ArrayList;
import java.util.List;

import com.example.adapter.ViewPagerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class FirstReadActivity extends Activity {

	private ViewPager pager;
	private List<ImageView> pics;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_pager);
		pager = (ViewPager) findViewById(R.id.view_pager);
		SharedPreferences sp = getSharedPreferences("First", MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putBoolean("isRead", true);
		editor.commit();
		pics = new ArrayList<ImageView>();
		initData();
		ViewPagerAdapter adapter = new ViewPagerAdapter(pics);
		pager.setAdapter(adapter);
		pager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				if (arg0 == pics.size() - 1) {
					startActivity(new Intent(FirstReadActivity.this,
							LoginActivity.class));
					finish();
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	private void initData() {
		ImageView imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.splash_page1_body);
		imageView.setScaleType(ScaleType.FIT_XY);
		pics.add(imageView);
		imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.splash_page2_body);
		imageView.setScaleType(ScaleType.FIT_XY);
		pics.add(imageView);
		imageView = new ImageView(this);
		imageView.setImageResource(R.drawable.splash_page3_body);
		imageView.setScaleType(ScaleType.FIT_XY);
		pics.add(imageView);
	}
}
