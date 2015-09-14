package com.example.school;

import java.util.List;

import com.example.adapter.NoticeListAdapter;
import com.example.dao.CollectionDao;
import com.example.entity.Notice;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

public class CollectionActivity extends Activity {

	ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.c_list_view);
		listView = (ListView) findViewById(R.id.c_listview);
		listView.setPadding(0, 20, 0, 0);
		listView.setBackgroundColor(Color.WHITE);
		CollectionDao dao = new CollectionDao(this);
		List<Notice> notices = dao.allNotice();
		NoticeListAdapter adapter = new NoticeListAdapter(notices, this);
		listView.setAdapter(adapter);
		// 设置actionbar
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setTitle("个人收藏");
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
