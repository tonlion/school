package com.example.fragment;

import java.util.ArrayList;
import java.util.List;

import com.example.adapter.NoticeListAdapter;
import com.example.application.SchoolApplication;
import com.example.data.DataManager;
import com.example.entity.Notice;
import com.example.school.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

//setUserVisibleHint执行顺序比较高，在onCreateView之前调用
public class PagerListFragment extends LanLoadFragment {

	private List<Notice> notices;
	private int itemId;
	private boolean isOver = false;// 是否加载完成，表明fragment所有控件初始化完成
	private PullToRefreshListView lView;
	private NoticeListAdapter adapter;
	private DataManager manager;
	private long startTime;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle b = getArguments();
		itemId = b.getInt("id");
		notices = new ArrayList<Notice>();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.list_view, null);
		lView = (PullToRefreshListView) view.findViewById(R.id.listview);
		lView.setMode(Mode.BOTH);
		adapter = new NoticeListAdapter(notices, getActivity());
		lView.setAdapter(adapter);
		isOver = true;
		startTime = System.currentTimeMillis();
		// 为第一次加载做准备，第一次加载时会没有数据
		if (isVisible) {
			// 加载数据
			new Handler().postDelayed(new Runnable() {
				@Override
				public void run() {
					manager = new DataManager(adapter, notices, getActivity(),
							lView);
					SchoolApplication.getInstance().getRequestQueue()
							.add(manager.getNoticeTypeData(itemId));
				}
			}, 500);
		}
		return view;
	}

	@Override
	public void loadData() {
		long endTime = System.currentTimeMillis();
		if (isVisible && isOver) {
			// 加载数据，如果没有数据不会刷新
			if (endTime - startTime > 1000 * 60) {
				manager = new DataManager(adapter, notices, getActivity(),
						lView);
				SchoolApplication.getInstance().getRequestQueue()
						.add(manager.getNoticeTypeData(itemId));
				startTime = System.currentTimeMillis();
			}
		}
	}
}
