package com.example.school;

import com.example.entity.Notice;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

public class NoticeDetailsActivity extends Activity {
	// 显示每条数据的详细页面
	private TextView title;
	private TextView date;
	private WebView content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_notice_detail);
		// 修改菜单栏
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle("信息详情");
		getActionBar().setDisplayShowHomeEnabled(false);
		Intent intent = getIntent();
		Notice notice = (Notice) intent.getSerializableExtra("noticeSe");
		title = (TextView) findViewById(R.id.notice_details_title);
		date = (TextView) findViewById(R.id.notice_details_date);
		content = (WebView) findViewById(R.id.notice_details_content);
		title.setText(notice.getTitle());
		date.setText(notice.getTime());
		content.loadDataWithBaseURL(null, notice.getContent(), "text/html",
				"utf-8", null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			Toast.makeText(this, "收藏成功", Toast.LENGTH_SHORT).show();
			break;
		case android.R.id.home:
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
