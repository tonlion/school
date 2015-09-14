package com.example.school;

import com.example.application.SchoolApplication;
import com.example.dao.CollectionDao;
import com.example.data.DataManager;
import com.example.entity.Notice;
import com.example.entity.Student;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

public class NoticeDetailsActivity extends Activity {
	// 显示每条数据的详细页面
	private TextView title;
	private TextView date;
	private WebView content;
	private Notice notice;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_notice_detail);
		// 修改菜单栏
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle("动态详情");
		getActionBar().setDisplayShowHomeEnabled(false);
		Intent intent = getIntent();
		notice = (Notice) intent.getSerializableExtra("noticeSe");
		title = (TextView) findViewById(R.id.notice_details_title);
		date = (TextView) findViewById(R.id.notice_details_date);
		content = (WebView) findViewById(R.id.notice_details_content);
		title.setText(notice.getTitle());
		date.setText(notice.getTime());
		// 修改webview的默认属性
		WebSettings settings = content.getSettings();
		settings.setDefaultTextEncodingName("utf-8");
		content.loadDataWithBaseURL(DataManager.IP_URL, notice.getContent(),
				"text/html", "utf-8", null);
		// 更改点击链接浏览器打开，修改默认行为
		content.setWebViewClient(new WebViewClient());
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
			Student s = SchoolApplication.getInstance().getStudent();
			CollectionDao dao = new CollectionDao(getApplicationContext());
			try {
				dao.addNotice(notice, s);
				Toast.makeText(getApplicationContext(), "收藏成功",
						Toast.LENGTH_SHORT).show();
			} catch (Exception e) {
				Toast.makeText(getApplicationContext(), "已经收藏了",
						Toast.LENGTH_SHORT).show();
			}

			break;
		case android.R.id.home:
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
