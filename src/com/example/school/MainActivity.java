package com.example.school;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.adapter.NoticeListAdapter;
import com.example.adapter.TopicListAdapter;
import com.example.adapter.ViewPagerAdapter;
import com.example.application.ActivityManager;
import com.example.application.SchoolApplication;
import com.example.dao.NoticeDao;
import com.example.dao.TopicDao;
import com.example.data.DataManager;
import com.example.data.ImageLoaderUtils;
import com.example.entity.Notice;
import com.example.entity.SlidingMenus;
import com.example.entity.Student;
import com.example.entity.Topic;
import com.example.service.UpdateService;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

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
	private PullToRefreshListView notice;
	private List<SlidingMenus> menus;
	private ImageView stuImg;
	private Student student;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_show);
		notice = (PullToRefreshListView) findViewById(R.id.notice_list);
		menus = new ArrayList<SlidingMenus>();
		notice.setMode(Mode.BOTH);
		View v2 = initViewPager();
		initSlidingMenu(v2);
		initView();
		initListener(v2);
		initTabMenus();
		// 修改状态栏
		getActionBar().setDisplayShowHomeEnabled(false);
		// 压入栈
		ActivityManager.getInstance().pushActivity(this);
		// 创建alertdialog
		alertDialog();
	}

	/**
	 * 联网检查数据
	 */
	public void alertDialog() {
		StringRequest request = new StringRequest(DataManager.ROOT_URL + "apk",
				new Listener<String>() {
					@Override
					public void onResponse(String arg0) {
						try {
							JSONObject jsonO = new JSONObject(arg0);
							String url = jsonO.getString("url");
							int vid = jsonO.getInt("vid");
							PackageManager pManager = getPackageManager();
							PackageInfo info = pManager.getPackageInfo(
									"com.example.school", 0);
							if (info.versionCode < vid) {
								createDialog(url);
							}
						} catch (JSONException e) {
							e.printStackTrace();
						} catch (NameNotFoundException e) {
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
					}
				});
		SchoolApplication.getInstance().getRequestQueue().add(request);
	}

	/**
	 * 弹出dialog
	 * 
	 * @param url
	 */
	public void createDialog(final String url) {
		final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setCancelable(false);
		dialog.setTitle("友情提示");
		View views = LayoutInflater.from(this).inflate(
				R.layout.layout_alertdialog, null);
		final AlertDialog alert = dialog.setView(views).create();
		views.findViewById(R.id.ok).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, UpdateService.class);
				i.putExtra("url", DataManager.ROOT_URL + url);
				startService(i);
				alert.dismiss();
			}
		});
		views.findViewById(R.id.no).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				alert.dismiss();
			}
		});
		alert.show();
	}

	private void initTabMenus() {
		SlidingMenus menu = new SlidingMenus(1, "校园活动", 0);
		menu.getMenus().add(new SlidingMenus(5, "社会实践", 1));
		menu.getMenus().add(new SlidingMenus(6, "素质拓展", 1));
		menu.getMenus().add(new SlidingMenus(7, "志愿服务", 1));
		menu.getMenus().add(new SlidingMenus(8, "科技创新", 1));
		menus.add(menu);
		menu = new SlidingMenus(2, "思想引领", 0);
		menu.getMenus().add(new SlidingMenus(9, "党课培训", 2));
		menu.getMenus().add(new SlidingMenus(10, "共青团教育", 2));
		menu.getMenus().add(new SlidingMenus(11, "心灵分享", 2));
		menus.add(menu);
		menu = new SlidingMenus(3, "就业招聘", 0);
		menu.getMenus().add(new SlidingMenus(12, "招聘信息", 3));
		menu.getMenus().add(new SlidingMenus(13, "通知文件", 3));
		menu.getMenus().add(new SlidingMenus(14, "就业政策", 3));
		menu.getMenus().add(new SlidingMenus(15, "就业情况", 3));
		menu.getMenus().add(new SlidingMenus(17, "创业情况", 3));
		menu.getMenus().add(new SlidingMenus(21, "创业政策", 3));
		menus.add(menu);
		menu = new SlidingMenus(4, "出国留学", 0);
		menu.getMenus().add(new SlidingMenus(18, "考研分析", 4));
		menu.getMenus().add(new SlidingMenus(19, "考研动态", 4));
		menu.getMenus().add(new SlidingMenus(20, "出国情况", 4));
		menus.add(menu);
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
		student = SchoolApplication.getInstance().getStudent();
		stuImg = (ImageView) v.findViewById(R.id.stu_img);
		TextView stuName = (TextView) v.findViewById(R.id.stu_name);
		// 当用户未登陆时
		if (student != null) {
			// MyAsyncTask task = new MyAsyncTask(stuImg);
			// task.execute(student.getImg());
			ImageLoaderUtils.display(student.getImg(), stuImg);
			stuName.setText("学号：" + student.getStuName());
		}
	}

	private void initView() {
		// 专题列表 动态列表添加数据
		topics = new ArrayList<Topic>();
		notices = new ArrayList<Notice>();
		// 从本地数据库得到数据
		TopicDao tDao = new TopicDao(getApplicationContext());
		topics = tDao.allTopic();
		NoticeDao nDao = new NoticeDao(getApplicationContext());
		notices = nDao.allNotice();
		tAdapter = new TopicListAdapter(topics, MainActivity.this);
		adapter2 = new NoticeListAdapter(notices, MainActivity.this);
		// 如果没有得到数据，就联网查询
		if (topics.size() == 0 || topics == null) {
			initData();
		}
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
		// SchoolApplication.getInstance().getRequestQueue()
		// .add(data.getNoticeData(true));
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
			if (student != null) {
				startActivity(new Intent(this, SettingInfoActivity.class));
			} else {
				ActivityManager.getInstance().finishAllActivity();
				startActivity(new Intent(this, LoginActivity.class));
			}
			break;
		case R.id.l2:
			Intent intent = new Intent(this, MessageActivity.class);
			if (student != null) {
				startActivity(intent);
			} else {
				Toast.makeText(getApplicationContext(), "请先登录",
						Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.l3:
			intent = new Intent(this, PagerListActivity.class);
			intent.putExtra("tabSe", menus.get(0));
			startActivity(intent);
			break;
		case R.id.l4:
			intent = new Intent(this, PagerListActivity.class);
			intent.putExtra("tabSe", menus.get(1));
			startActivity(intent);
			break;
		case R.id.l5:
			intent = new Intent(this, PagerListActivity.class);
			intent.putExtra("tabSe", menus.get(2));
			startActivity(intent);
			break;
		case R.id.l6:
			intent = new Intent(this, PagerListActivity.class);
			intent.putExtra("tabSe", menus.get(3));
			startActivity(intent);
			break;
		case R.id.l7:
			intent = new Intent(this, ToolsActivity.class);
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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exitByTwoClick();
		}
		return false;
	}

	private static Boolean isExit = false;

	private void exitByTwoClick() {
		Timer tExit = null;
		if (isExit == false) {
			isExit = true; // 准备退出
			Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
			tExit = new Timer();
			tExit.schedule(new TimerTask() {
				@Override
				public void run() {
					isExit = false; // 取消退出
				}
			}, 1000); // 如果1秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

		} else {
			finish();
		}
	}
}
