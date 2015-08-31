package com.example.school;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.example.adapter.PagerListAdapter;
import com.example.entity.SlidingMenus;
import com.example.fragment.PagerListFragment;

public class PagerListActivity extends FragmentActivity {

	private ViewPager mPager;
	private PagerSlidingTabStrip mSliding;
	private Intent intent;
	SlidingMenus tabs;

	@SuppressLint("CutPasteId")
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_main_list_show);
		mPager = (ViewPager) findViewById(R.id.main_pager_show);
		mSliding = (PagerSlidingTabStrip) findViewById(R.id.main_title_show);
		// 得到父级传过来的参数
		intent = getIntent();
		tabs = (SlidingMenus) intent.getSerializableExtra("tabSe");
		initViewPager();
		// 修改菜单栏
		getActionBar().setTitle(tabs.getItemName());
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowHomeEnabled(false);
	}

	// 利用fragment为viewpager添加数据
	private void initViewPager() {
		List<Fragment> fragments = new ArrayList<Fragment>();
		for (int i = 0; i < tabs.getMenus().size(); i++) {
			Fragment f = new PagerListFragment();
			// 传过去的是子类新闻的类型
			Bundle b = new Bundle();
			b.putInt("id", tabs.getMenus().get(i).getItemId());
			f.setArguments(b);
			fragments.add(f);
		}
		PagerListAdapter pf = new PagerListAdapter(getSupportFragmentManager(),
				fragments, tabs.getMenus());
		mPager.setAdapter(pf);
		mSliding.setViewPager(mPager);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
