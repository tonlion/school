package com.example.school;

import java.util.ArrayList;
import java.util.List;
import com.example.adapter.NoticeListAdapter;
import com.example.adapter.TopicListAdapter;
import com.example.adapter.ViewPagerAdapter;
import com.example.application.SchoolApplication;
import com.example.data.DataManager;
import com.example.entity.Notice;
import com.example.entity.Topic;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
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
		OnPageChangeListener, OnRefreshListener2<ListView> {
	private ListView topic;
	private List<Notice> notices;
	private List<ImageView> images;
	private List<Topic> topics;
	private SlidingMenu menu;
	private ViewPager mPager;
	private LinearLayout lyLayout;
	private TopicListAdapter tAdapter;
	private NoticeListAdapter adapter2;
	PullToRefreshListView notice;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_show);
		notice = (PullToRefreshListView) findViewById(R.id.notice_list);
		notice.setMode(Mode.BOTH);
		View v2 = initViewPager();
		initSlidingMenu(v2);
		initView();
		initListener(v2);

	}

	private View initViewPager() {
		// 加载viewpager
		LayoutInflater inflater = LayoutInflater.from(this);
		View v2 = inflater.inflate(R.layout.layout_viewpager, null);
		// 添加照片
		mPager = (ViewPager) v2.findViewById(R.id.title_pager);
		// 小圆点
		lyLayout = (LinearLayout) v2.findViewById(R.id.container);
		// 得到头部文件中的listview
		topic = (ListView) v2.findViewById(R.id.topic_list);
		// 为主题添加数据
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
		return v2;
	}

	private void initSlidingMenu(View v2) {
		// 侧边栏样式
		menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT);// 设置滑动方向
		View v = LayoutInflater.from(this).inflate(R.layout.layout_slide_menu,
				null);
		menu.setMenu(v);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		menu.setBehindOffset(300);
		notice.getRefreshableView().addHeaderView(v2);
	}

	private void initView() {
		// 专题列表 动态列表添加数据
		topics = new ArrayList<Topic>();
		notices = new ArrayList<Notice>();
		tAdapter = new TopicListAdapter(topics, MainActivity.this);
		adapter2 = new NoticeListAdapter(notices, MainActivity.this);
		initData();
		topic.setAdapter(tAdapter);
		notice.setAdapter(adapter2);
	}

	private void initListener(View v2) {
		// 为更多添加点击事件
		v2.findViewById(R.id.more_notice).setOnClickListener(this);
		v2.findViewById(R.id.more_topic).setOnClickListener(this);
		notice.setOnRefreshListener(this);
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

	public void initData() {
		DataManager data2 = new DataManager(tAdapter, topics, this, notice);
		SchoolApplication.getInstance().getRequestQueue()
				.add(data2.getTopicData(1));
		DataManager data = new DataManager(adapter2, notices, this, notice);
		SchoolApplication.getInstance().getRequestQueue()
				.add(data.getNoticeData(true));
		SchoolApplication.getInstance().getRequestQueue()
				.add(data.getNoticeData(false));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.more_notice:
			startActivity(new Intent(this, NoticeListActivity.class));
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
			// intent.putExtra("id", "0");
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
			// intent.putExtra("id", "5");
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

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		initData();
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		DataManager allData = new DataManager(adapter2, notices, this, notice);
		SchoolApplication.getInstance().getRequestQueue()
				.add(allData.getClearNoticeData());
	}
}
