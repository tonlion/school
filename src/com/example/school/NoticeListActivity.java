package com.example.school;

import java.util.ArrayList;
import java.util.List;

import com.example.adapter.NoticeListAdapter;
import com.example.application.SchoolApplication;
import com.example.data.DataManager;
import com.example.entity.Notice;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class NoticeListActivity extends Activity {

	private List<Notice> notices;
	private ListView notice;
	private NoticeListAdapter nAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_view);
		notice = (ListView) findViewById(R.id.listview);
		notices = new ArrayList<Notice>();
		nAdapter = new NoticeListAdapter(notices, this);
		initData();
		notice.setAdapter(nAdapter);
	}

	private void initData() {
		DataManager manager = new DataManager(nAdapter, notices, this, null);
		SchoolApplication.getInstance().getRequestQueue()
				.add(manager.getNoticeData(false));
	}
}
