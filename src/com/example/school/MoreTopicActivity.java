package com.example.school;

import java.util.ArrayList;
import java.util.List;

import com.example.adapter.TopicListAdapter;
import com.example.application.SchoolApplication;
import com.example.data.DataManager;
import com.example.entity.Topic;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

public class MoreTopicActivity extends Activity {
	private ListView mView;
	private List<Topic> topics;
	private TopicListAdapter tAdapter;

	// 显示每个主题界面
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more_topic_list);
		mView = (ListView) findViewById(R.id.more_topic_list);
		topics = new ArrayList<Topic>();
		tAdapter = new TopicListAdapter(topics, this);
		initData();
		mView.setAdapter(tAdapter);
		// 修改actionbar
		getActionBar().setTitle("更多专题");
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowHomeEnabled(false);
	}

	public void initData() {
		DataManager manager = new DataManager(tAdapter, topics, this, null);
		SchoolApplication.getInstance().getRequestQueue()
				.add(manager.getTopicData(20));
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
