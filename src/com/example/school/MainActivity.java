package com.example.school;

import java.util.ArrayList;
import java.util.List;

import com.example.adapter.NoticeListAdapter;
import com.example.adapter.TopicListAdapter;
import com.example.adapter.ViewPagerAdapter;
import com.example.entity.Notice;
import com.example.entity.Topic;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;

public class MainActivity extends Activity implements OnClickListener,
		OnPageChangeListener {
	private ListView topic;
	private ListView notice;
	private List<Notice> notices;
	private List<ImageView> images;
	private List<Topic> topics;
	private SlidingMenu menu;
	private ViewPager mPager;
	private LinearLayout lyLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_show);
		notice = (ListView) findViewById(R.id.notice_list);
		// 加载viewpager
		LayoutInflater inflater = LayoutInflater.from(this);
		View v2 = inflater.inflate(R.layout.layout_viewpager, null);
		// 添加照片
		mPager = (ViewPager) v2.findViewById(R.id.title_pager);
		// 小圆点
		lyLayout = (LinearLayout) v2.findViewById(R.id.container);
		// 得到头部文件中的listview
		initData();
		topic = (ListView) v2.findViewById(R.id.topic_list);
		// 为主题添加数据
		TopicListAdapter nAdapter = new TopicListAdapter(topics, this);
		topic.setAdapter(nAdapter);
		for (int i = 0; i < lyLayout.getChildCount(); i++) {
			if (i == 0) {
				lyLayout.getChildAt(i).setBackgroundColor(Color.BLUE);
			} else {
				lyLayout.getChildAt(i).setBackgroundColor(Color.GREEN);
			}
		}
		// 监听事件
		mPager.setOnPageChangeListener(this);
		initImage();
		ViewPagerAdapter vAdapter = new ViewPagerAdapter(images);
		mPager.setAdapter(vAdapter);
		// 侧边栏样式
		menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT);// 设置滑动方向
		View v = LayoutInflater.from(this).inflate(R.layout.layout_slide_menu,
				null);
		menu.setMenu(v);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		menu.setBehindOffset(300);
		// 专题列表
		initData();
		notice.addHeaderView(v2);
		// 动态列表
		initData1();
		NoticeListAdapter adapter2 = new NoticeListAdapter(notices, this);
		notice.setAdapter(adapter2);
		// 为更多添加点击事件
		findViewById(R.id.more_notice).setOnClickListener(this);
		v2.findViewById(R.id.more_topic).setOnClickListener(this);

		// 为侧边栏的条目添加点击事件
		findViewById(R.id.l1).setOnClickListener(this);
		findViewById(R.id.l2).setOnClickListener(this);
		findViewById(R.id.l3).setOnClickListener(this);
		findViewById(R.id.l4).setOnClickListener(this);
		findViewById(R.id.l5).setOnClickListener(this);
		findViewById(R.id.l6).setOnClickListener(this);
		findViewById(R.id.l7).setOnClickListener(this);

	}

	private void initImage() {
		images = new ArrayList<ImageView>();
		ImageView mImage = new ImageView(this);
		mImage.setScaleType(ScaleType.FIT_XY);
		mImage.setImageResource(R.drawable.head1);
		images.add(mImage);
		mImage = new ImageView(this);
		mImage.setScaleType(ScaleType.FIT_XY);
		mImage.setImageResource(R.drawable.head2);
		images.add(mImage);
	}

	private void initData() {
		topics = new ArrayList<Topic>();
		topics.add(new Topic(R.drawable.logo, "你好", "2015-01-01"));
	}

	public void initData1() {
		notices = new ArrayList<Notice>();
		notices.add(new Notice(R.drawable.cc_school_sxyl, "你好", "2015-01-01"));
		notices.add(new Notice(R.drawable.cc_school_sxyl, "你好", "2015-01-01"));
		notices.add(new Notice(R.drawable.cc_school_sxyl, "你好", "2015-01-01"));
		notices.add(new Notice(R.drawable.cc_school_sxyl, "你好", "2015-01-01"));
		notices.add(new Notice(R.drawable.cc_school_sxyl, "你好", "2015-01-01"));
		notices.add(new Notice(R.drawable.cc_school_sxyl, "你好", "2015-01-01"));
		notices.add(new Notice(R.drawable.cc_school_sxyl, "你好", "2015-01-01"));
		notices.add(new Notice(R.drawable.cc_school_sxyl, "你好", "2015-01-01"));
		notices.add(new Notice(R.drawable.cc_school_sxyl, "你好", "2015-01-01"));
		notices.add(new Notice(R.drawable.cc_school_sxyl, "你好", "2015-01-01"));
		notices.add(new Notice(R.drawable.cc_school_sxyl, "你好", "2015-01-01"));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.more_notice:
			startActivity(new Intent(this, MoreTopicActivity.class));
			// 跳转到新页
			break;
		case R.id.more_topic:
			startActivity(new Intent(this, MoreTopicActivity.class));
			// 跳转到新页
			break;
		case R.id.l1:
			startActivity(new Intent(this, SettingInfoActivity.class));
			break;
		case R.id.l2:
			Intent intent = new Intent(this, PagerListActivity.class);
			intent.putExtra("id", "0");
			startActivity(intent);
			break;
		case R.id.l3:
			intent = new Intent(this, PagerListActivity.class);
			intent.putExtra("id", "1");
			startActivity(intent);
			break;
		case R.id.l4:
			intent = new Intent(this, PagerListActivity.class);
			intent.putExtra("id", "2");
			startActivity(intent);
			break;
		case R.id.l5:
			intent = new Intent(this, PagerListActivity.class);
			intent.putExtra("id", "3");
			startActivity(intent);
			break;
		case R.id.l6:
			intent = new Intent(this, PagerListActivity.class);
			intent.putExtra("id", "4");
			startActivity(intent);
			break;
		case R.id.l7:
			intent = new Intent(this, PagerListActivity.class);
			intent.putExtra("id", "5");
			startActivity(intent);
			break;
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int arg0) {
		for (int i = 0; i < lyLayout.getChildCount(); i++) {
			ImageView iv = (ImageView) lyLayout.getChildAt(i);
			if (i == arg0) {
				iv.setBackgroundColor(Color.BLUE);
			} else {
				iv.setBackgroundColor(Color.GREEN);
			}
		}
	}
}
