package com.example.adapter;

import java.util.List;
import com.example.entity.SlidingMenus;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PagerListAdapter extends FragmentPagerAdapter {

	private List<Fragment> notices;
	private List<SlidingMenus> maps;

	public PagerListAdapter(FragmentManager fm, List<Fragment> notices,
			List<SlidingMenus> maps) {
		super(fm);
		this.notices = notices;
		this.maps = maps;
	}

	@Override
	public Fragment getItem(int arg0) {
		return notices.get(arg0);
	}

	@Override
	public int getCount() {
		return notices.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return maps.get(position).getItemName();
	}

}
