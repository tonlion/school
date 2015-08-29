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
		// �õ������������Ĳ���
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

	// ����fragmentΪviewpager�������
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

	// �������ݣ��˴�����ɾ��
	private void initData() {
		titles = new ArrayList<SlidingMenus>();
		titles.add(new SlidingMenus(5, "���ʵ��", 1));
		titles.add(new SlidingMenus(6, "������չ", 1));
		titles.add(new SlidingMenus(7, "־Ը����", 1));
		titles.add(new SlidingMenus(8, "�Ƽ�����", 1));
		titles.add(new SlidingMenus(9, "������ѵ", 2));
		titles.add(new SlidingMenus(10, "�����Ž���", 2));
		titles.add(new SlidingMenus(11, "�������", 2));
		titles.add(new SlidingMenus(12, "��Ƹ��Ϣ", 3));
		titles.add(new SlidingMenus(13, "֪ͨ�ļ�", 3));
		titles.add(new SlidingMenus(14, "��ҵ����", 3));
		titles.add(new SlidingMenus(15, "��ҵ���", 3));
		titles.add(new SlidingMenus(17, "��ҵ���", 3));
		titles.add(new SlidingMenus(21, "��ҵ����", 3));
		titles.add(new SlidingMenus(18, "���з���", 4));
		titles.add(new SlidingMenus(19, "���ж�̬", 4));
		titles.add(new SlidingMenus(20, "�������", 4));
	}
}
