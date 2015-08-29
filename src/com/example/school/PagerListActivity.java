package com.example.school;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import com.astuetz.PagerSlidingTabStrip;
import com.example.adapter.PagerListAdapter;
import com.example.entity.SlidingMenus;
import com.example.fragment.PagerListFragment;

public class PagerListActivity extends FragmentActivity {

	private ViewPager mPager;
	private PagerSlidingTabStrip mSliding;
	private List<SlidingMenus> titles;
	private List<SlidingMenus> menus;
	private Intent intent;

	@SuppressLint("CutPasteId")
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_main_list_show);
		mPager = (ViewPager) findViewById(R.id.main_pager_show);
		mSliding = (PagerSlidingTabStrip) findViewById(R.id.main_title_show);
		// 得到父级传过来的参数
		intent = getIntent();
		String a = intent.getStringExtra("id");
		initData();
		menus = new ArrayList<SlidingMenus>();
		for (int i = 0; i < titles.size(); i++) {
			if (titles.get(i).getMenuId() == Integer.parseInt(a)) {
				menus.add(titles.get(i));
			}
		}
		initViewPager();
	}

	// 利用fragment为viewpager添加数据
	private void initViewPager() {
		List<Fragment> fragments = new ArrayList<Fragment>();
		for (int i = 0; i < menus.size(); i++) {
			Fragment f = new PagerListFragment();
			Bundle b = new Bundle();
			b.putInt("id", menus.get(i).getItemId());
			f.setArguments(b);
			fragments.add(f);
		}
		PagerListAdapter pf = new PagerListAdapter(getSupportFragmentManager(),
				fragments, menus);
		mPager.setAdapter(pf);
		mSliding.setViewPager(mPager);
	}

	// 加载数据，此处可以删除
	private void initData() {
		titles = new ArrayList<SlidingMenus>();
		titles.add(new SlidingMenus(5, "社会实践", 1));
		titles.add(new SlidingMenus(6, "素质拓展", 1));
		titles.add(new SlidingMenus(7, "志愿服务", 1));
		titles.add(new SlidingMenus(8, "科技创新", 1));
		titles.add(new SlidingMenus(9, "党课培训", 2));
		titles.add(new SlidingMenus(10, "共青团教育", 2));
		titles.add(new SlidingMenus(11, "心灵分享", 2));
		titles.add(new SlidingMenus(12, "招聘信息", 3));
		titles.add(new SlidingMenus(13, "通知文件", 3));
		titles.add(new SlidingMenus(14, "就业政策", 3));
		titles.add(new SlidingMenus(15, "就业情况", 3));
		titles.add(new SlidingMenus(17, "创业情况", 3));
		titles.add(new SlidingMenus(21, "创业政策", 3));
		titles.add(new SlidingMenus(18, "考研分析", 4));
		titles.add(new SlidingMenus(19, "考研动态", 4));
		titles.add(new SlidingMenus(20, "出国情况", 4));
	}
}
