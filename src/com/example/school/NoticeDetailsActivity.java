package com.example.school;

import java.util.ArrayList;

import com.example.adapter.NoticeListAdapter;
import com.example.entity.Notice;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class NoticeDetailsActivity extends Activity {
	private ListView item;
	private ArrayList<Notice> notices;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_topic_list);
		item = (ListView) findViewById(R.id.topic_details);
		initData();
		NoticeListAdapter adapter = new NoticeListAdapter(notices, this);
		item.setAdapter(adapter);
	}

	private void initData() {
		notices = new ArrayList<Notice>();
		notices.add(new Notice(R.drawable.cc_school_sxyl, "你好", "2015-01-01"));
		notices.add(new Notice(R.drawable.cc_school_sxyl, "你好", "2015-01-01"));
		notices.add(new Notice(R.drawable.cc_school_sxyl, "你好", "2015-01-01"));
		notices.add(new Notice(R.drawable.cc_school_sxyl, "你好", "2015-01-01"));
		notices.add(new Notice(R.drawable.cc_school_sxyl, "你好", "2015-01-01"));
		notices.add(new Notice(R.drawable.cc_school_sxyl, "你好", "2015-01-01"));
		notices.add(new Notice(R.drawable.cc_school_sxyl, "你好", "2015-01-01"));
		notices.add(new Notice(R.drawable.cc_school_sxyl, "你好", "2015-01-01"));
		notices.add(new Notice(R.drawable.cc_school_sxyl, "你好", "2015-01-01"));
		notices.add(new Notice(R.drawable.cc_school_sxyl, "你好", "2015-01-01"));
	}
}
