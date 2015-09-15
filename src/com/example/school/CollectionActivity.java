package com.example.school;

import java.util.List;

import com.example.adapter.NoticeListAdapter;
import com.example.application.SchoolApplication;
import com.example.dao.CollectionDao;
import com.example.data.TableMessage.CollectionTable;
import com.example.entity.Notice;
import com.example.entity.Student;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

public class CollectionActivity extends Activity {

	ListView listView;
	private NoticeListAdapter adapter;
	private List<Notice> notices;
	private Student s;
	CollectionDao dao = new CollectionDao(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.c_list_view);
		listView = (ListView) findViewById(R.id.c_listview);
		listView.setPadding(0, 20, 0, 0);
		listView.setBackgroundColor(Color.WHITE);
		s = SchoolApplication.getInstance().getStudent();
		notices = dao.findNotice(new String[] { CollectionTable.COL_UNO },
				new String[] { s.getUno() });
		adapter = new NoticeListAdapter(notices, this);
		listView.setAdapter(adapter);
		// 设置actionbar
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setTitle("个人收藏");
	}

	// 本意，点击返回时重新加载listview中数据，但是并没有显示
	@Override
	protected void onStart() {
		super.onStart();
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
