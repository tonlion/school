package com.example.school;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.astuetz.PagerSlidingTabStrip;
import com.example.adapter.PagerListAdapter;
import com.example.fragment.PagerListFragment;

public class PagerListActivity extends FragmentActivity {

	/**
	 * 注释内容为修改之前版本
	 */
	private RadioGroup mGroup;
	private ViewPager mPager;
	private PagerSlidingTabStrip mSliding;
	private List<Map<String, String>> titles;
	private Intent intent;

	@SuppressLint("CutPasteId")
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_main_list_show);
		// mGroup = (RadioGroup) findViewById(R.id.main_title_show);
		mPager = (ViewPager) findViewById(R.id.main_pager_show);
		mSliding = (PagerSlidingTabStrip) findViewById(R.id.main_title_show);
		// 得到父级传过来的参数
		intent = getIntent();
		String a = intent.getStringExtra("id");
		initData();
		// initButtonText();
		initViewPager();
		// 当点击相应的按钮时，设置点击的按钮为名字当前页
		mPager.setCurrentItem(Integer.parseInt(a));
		// mGroup.check(Integer.parseInt(a));
		// initEvent();
	}

	// 为button添加文字
	private void initButtonText() {
		for (int i = 0; i < titles.size(); i++) {
			RadioButton rbtn = (RadioButton) LayoutInflater.from(this).inflate(
					R.layout.radio_button, null);
			if (i == 0) {
				rbtn.setChecked(true);
			}
			rbtn.setText(titles.get(i).get("name"));
			rbtn.setId(Integer.parseInt(titles.get(i).get("id")));
			mGroup.addView(rbtn);
		}
	}

	// 利用fragment为viewpager添加数据
	private void initViewPager() {
		List<Fragment> fragments = new ArrayList<Fragment>();
		for (int i = 0; i < titles.size(); i++) {
			Fragment f = new PagerListFragment();
			Bundle b = new Bundle();
			b.putInt("id", i);
			f.setArguments(b);
			fragments.add(f);
		}
		PagerListAdapter pf = new PagerListAdapter(getSupportFragmentManager(),
				fragments, titles);
		mPager.setAdapter(pf);
		mSliding.setViewPager(mPager);
	}

	// 设置监听事件
	private void initEvent() {
		mGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				mPager.setCurrentItem(checkedId);
			}
		});
		mPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				mGroup.check(arg0);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}

	// 加载数据，此处可以删除
	private void initData() {
		titles = new ArrayList<Map<String, String>>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", 0 + "");
		map.put("name", "消息平台");
		titles.add(map);
		map = new HashMap<String, String>();
		map.put("id", 1 + "");
		map.put("name", "校园活动");
		titles.add(map);
		map = new HashMap<String, String>();
		map.put("id", 2 + "");
		map.put("name", "思想引领");
		titles.add(map);
		map = new HashMap<String, String>();
		map.put("id", 3 + "");
		map.put("name", "就业招聘");
		titles.add(map);
		map = new HashMap<String, String>();
		map.put("id", 4 + "");
		map.put("name", "考研出国");
		titles.add(map);
		map = new HashMap<String, String>();
		map.put("id", 5 + "");
		map.put("name", "便利工具");
		titles.add(map);

	}
}
