package com.example.school;

import java.util.ArrayList;
import java.util.List;

import com.example.adapter.NoticeListAdapter;
import com.example.application.SchoolApplication;
import com.example.data.DataManager;
import com.example.entity.Notice;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

public class NoticeListActivity extends Activity {

	private List<Notice> notices;
	private PullToRefreshListView notice;
	private NoticeListAdapter nAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_view);
		// 添加监听事件，即上划和下划事件
		notice = (PullToRefreshListView) findViewById(R.id.listview);
		notice.setMode(Mode.BOTH);
		notices = new ArrayList<Notice>();
		nAdapter = new NoticeListAdapter(notices, this);
		initData();
		notice.setAdapter(nAdapter);
		// 修改actionbar
		getActionBar().setTitle("动态列表");
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowHomeEnabled(false);
	}

	private void initData() {
		DataManager manager = new DataManager(nAdapter, notices, this, null);
		SchoolApplication.getInstance().getRequestQueue()
				.add(manager.getNoticeData(false));
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
