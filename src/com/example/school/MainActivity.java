package com.example.school;

import java.util.ArrayList;
import java.util.List;
import com.example.adapter.NoticeListAdapter;
import com.example.adapter.TopicListAdapter;
import com.example.adapter.ViewPagerAdapter;
import com.example.application.ActivityManager;
import com.example.application.SchoolApplication;
import com.example.async.MyAsyncTask;
import com.example.dao.NoticeDao;
import com.example.dao.TopicDao;
import com.example.data.DataManager;
import com.example.entity.Notice;
import com.example.entity.SlidingMenus;
import com.example.entity.Student;
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
import android.widget.TextView;

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
		// �޸�״̬��
		getActionBar().setDisplayShowHomeEnabled(false);
		// ѹ��ջ
		ActivityManager.getInstance().pushActivity(this);
	}

	private void initTabMenus() {
		SlidingMenus menu = new SlidingMenus(1, "У԰�", 0);
		menu.getMenus().add(new SlidingMenus(5, "���ʵ��", 1));
		menu.getMenus().add(new SlidingMenus(6, "������չ", 1));
		menu.getMenus().add(new SlidingMenus(7, "־Ը����", 1));
		menu.getMenus().add(new SlidingMenus(8, "�Ƽ�����", 1));
		menus.add(menu);
		menu = new SlidingMenus(2, "˼������", 0);
		menu.getMenus().add(new SlidingMenus(9, "������ѵ", 2));
		menu.getMenus().add(new SlidingMenus(10, "�����Ž���", 2));
		menu.getMenus().add(new SlidingMenus(11, "�������", 2));
		menus.add(menu);
		menu = new SlidingMenus(3, "��ҵ��Ƹ", 0);
		menu.getMenus().add(new SlidingMenus(12, "��Ƹ��Ϣ", 3));
		menu.getMenus().add(new SlidingMenus(13, "֪ͨ�ļ�", 3));
		menu.getMenus().add(new SlidingMenus(14, "��ҵ����", 3));
		menu.getMenus().add(new SlidingMenus(15, "��ҵ���", 3));
		menu.getMenus().add(new SlidingMenus(17, "��ҵ���", 3));
		menu.getMenus().add(new SlidingMenus(21, "��ҵ����", 3));
		menus.add(menu);
		menu = new SlidingMenus(4, "������ѧ", 0);
		menu.getMenus().add(new SlidingMenus(18, "���з���", 4));
		menu.getMenus().add(new SlidingMenus(19, "���ж�̬", 4));
		menu.getMenus().add(new SlidingMenus(20, "�������", 4));
		menus.add(menu);
	}

	private View initViewPager() {
		// ����viewpager
		LayoutInflater inflater = LayoutInflater.from(this);
		View v2 = inflater.inflate(R.layout.layout_viewpager, null);
		// �����Ƭ
		mPager = (ViewPager) v2.findViewById(R.id.title_pager);
		// СԲ��
		lyLayout = (LinearLayout) v2.findViewById(R.id.container);
		// �õ�ͷ���ļ��е�listview
		topic = (ListView) v2.findViewById(R.id.topic_list);
		// Ϊ�����������
		for (int i = 0; i < lyLayout.getChildCount(); i++) {
			if (i == 0) {
				lyLayout.getChildAt(i).setBackgroundColor(Color.BLUE);
			} else {
				lyLayout.getChildAt(i).setBackgroundColor(Color.GREEN);
			}
		}
		// �����¼�
		mPager.setOnPageChangeListener(this);
		initImage();
		ViewPagerAdapter vAdapter = new ViewPagerAdapter(images);
		mPager.setAdapter(vAdapter);
		return v2;
	}

	private void initSlidingMenu(View v2) {
		// �������ʽ
		menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT);// ���û�������
		View v = LayoutInflater.from(this).inflate(R.layout.layout_slide_menu,
				null);
		menu.setMenu(v);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		menu.setBehindOffset(300);
		notice.getRefreshableView().addHeaderView(v2);
		student = SchoolApplication.getInstance().getStudent();
		stuImg = (ImageView) v.findViewById(R.id.stu_img);
		TextView stuName = (TextView) v.findViewById(R.id.stu_name);
		// ���û�δ��½ʱ
		if (student != null) {
			MyAsyncTask task = new MyAsyncTask(stuImg);
			task.execute(student.getImg());
			stuName.setText("ѧ�ţ�" + student.getStuName());
		}
	}

	private void initView() {
		// ר���б� ��̬�б��������
		topics = new ArrayList<Topic>();
		notices = new ArrayList<Notice>();
		// �ӱ������ݿ�õ�����
		TopicDao tDao = new TopicDao(getApplicationContext());
		topics = tDao.allTopic();
		NoticeDao nDao = new NoticeDao(getApplicationContext());
		notices = nDao.allNotice();
		tAdapter = new TopicListAdapter(topics, MainActivity.this);
		adapter2 = new NoticeListAdapter(notices, MainActivity.this);
		// ���û�еõ����ݣ���������ѯ
		if (topics.size() == 0 || topics == null) {
			initData();
		}
		topic.setAdapter(tAdapter);
		notice.setAdapter(adapter2);
	}

	private void initListener(View v2) {
		// Ϊ������ӵ���¼�
		v2.findViewById(R.id.more_notice).setOnClickListener(this);
		v2.findViewById(R.id.more_topic).setOnClickListener(this);
		notice.setOnRefreshListener(this);
		// Ϊ���������Ŀ��ӵ���¼�
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
			// ��ת����ҳ
			break;
		case R.id.more_topic:
			startActivity(new Intent(this, MoreTopicActivity.class));
			// ��ת����ҳ
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
			startActivity(intent);
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
	
}
