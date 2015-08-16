package com.example.school;

import java.util.ArrayList;
import java.util.List;

import com.example.adapter.TopicListAdapter;
import com.example.entity.Topic;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class MoreTopicActivity extends Activity {
	private ListView mView;
	private List<Topic> notices;

	// 显示每个主题界面
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more_topic_list);
		mView = (ListView) findViewById(R.id.more_topic_list);
		initData1();
		TopicListAdapter adapter = new TopicListAdapter(notices, this);
		mView.setAdapter(adapter);
	}

	public void initData1() {
		notices = new ArrayList<Topic>();
		notices.add(new Topic(R.drawable.ic_launcher, "你好", "2015-01-01"));
		notices.add(new Topic(R.drawable.ic_launcher, "你好", "2015-01-01"));
		notices.add(new Topic(R.drawable.ic_launcher, "你好", "2015-01-01"));
		notices.add(new Topic(R.drawable.ic_launcher, "你好", "2015-01-01"));
		notices.add(new Topic(R.drawable.ic_launcher, "你好", "2015-01-01"));
		notices.add(new Topic(R.drawable.ic_launcher, "你好", "2015-01-01"));
		notices.add(new Topic(R.drawable.ic_launcher, "你好", "2015-01-01"));
		notices.add(new Topic(R.drawable.ic_launcher, "你好", "2015-01-01"));
		notices.add(new Topic(R.drawable.ic_launcher, "你好", "2015-01-01"));
		notices.add(new Topic(R.drawable.ic_launcher, "你好", "2015-01-01"));
		notices.add(new Topic(R.drawable.ic_launcher, "你好", "2015-01-01"));
	}
}
