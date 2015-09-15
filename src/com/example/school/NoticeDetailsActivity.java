package com.example.school;

import com.example.application.ActivityManager;
import com.example.application.SchoolApplication;
import com.example.dao.CollectionDao;
import com.example.dao.GlobalDao;
import com.example.data.DataManager;
import com.example.data.TableMessage.CollectionTable;
import com.example.data.TableMessage.NoticeTable;
import com.example.entity.Notice;
import com.example.entity.Student;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
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
		ActivityManager.getInstance().pushActivity(this);
		addMenu();
	}

	// 本意，重新进入时，显示收藏或取消收藏
	@Override
	protected void onStart() {
		super.onStart();
	}

	private void addMenu() {
		// 添加收藏按钮
		final TextView tv = new TextView(this);
		if (notice.getFavorTag() == 1) {
			tv.setText("取消收藏");
		} else {
			tv.setText("收藏");
		}
		tv.setTextSize(18);
		tv.setGravity(Gravity.CENTER_VERTICAL);
		tv.setPadding(0, 0, 10, 0);
		tv.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Student s = SchoolApplication.getInstance().getStudent();
				CollectionDao dao = new CollectionDao(getApplicationContext());
				GlobalDao dao2 = new GlobalDao(NoticeDetailsActivity.this,
						"notice");
				;
				try {
					notice.setFavorTag(1);
					dao2.updateMes(new String[] { NoticeTable.COL_F_TAG },
							new String[] { NoticeTable.COL_ID }, new Object[] {
									1, notice.getId() });
					dao.addNotice(notice, s);
					tv.setText("取消收藏");
					Toast.makeText(getApplicationContext(), "收藏成功",
							Toast.LENGTH_SHORT).show();
				} catch (Exception e) {
					tv.setText("收藏");
					dao2.updateMes(new String[] { NoticeTable.COL_F_TAG },
							new String[] { NoticeTable.COL_ID }, new Object[] {
									0, notice.getId() });
					dao2 = new GlobalDao(NoticeDetailsActivity.this,
							CollectionTable.TABLE_NAME);
					dao2.deleteUser(new String[] { CollectionTable.COL_ID,
							CollectionTable.COL_UNO },
							new Object[] { notice.getId(), s.getUno() });
					Toast.makeText(getApplicationContext(), "取消收藏",
							Toast.LENGTH_SHORT).show();
				}

			}
		});
		LayoutParams l = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.MATCH_PARENT);
		l.gravity = Gravity.RIGHT;
		getActionBar().setDisplayShowCustomEnabled(true);
		getActionBar().setCustomView(tv, l);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// 之前对收藏的实现
		// getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			break;
		case android.R.id.home:
			// 当前页面如果不彻底清除，加载时会有一些小问题
			// 功能未实现彻底
			// 在onstart中实现
			ActivityManager.getInstance().popOneActivity(this);
			// finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
