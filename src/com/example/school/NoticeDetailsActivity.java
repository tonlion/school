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
	// ��ʾÿ�����ݵ���ϸҳ��
	private TextView title;
	private TextView date;
	private WebView content;
	private Notice notice;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_notice_detail);
		// �޸Ĳ˵���
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle("��̬����");
		getActionBar().setDisplayShowHomeEnabled(false);
		Intent intent = getIntent();
		notice = (Notice) intent.getSerializableExtra("noticeSe");
		title = (TextView) findViewById(R.id.notice_details_title);
		date = (TextView) findViewById(R.id.notice_details_date);
		content = (WebView) findViewById(R.id.notice_details_content);
		title.setText(notice.getTitle());
		date.setText(notice.getTime());
		// �޸�webview��Ĭ������
		WebSettings settings = content.getSettings();
		settings.setDefaultTextEncodingName("utf-8");
		content.loadDataWithBaseURL(DataManager.IP_URL, notice.getContent(),
				"text/html", "utf-8", null);
		// ���ĵ������������򿪣��޸�Ĭ����Ϊ
		content.setWebViewClient(new WebViewClient());
		ActivityManager.getInstance().pushActivity(this);
		addMenu();
	}

	// ���⣬���½���ʱ����ʾ�ղػ�ȡ���ղ�
	@Override
	protected void onStart() {
		super.onStart();
	}

	private void addMenu() {
		// ����ղذ�ť
		final TextView tv = new TextView(this);
		if (notice.getFavorTag() == 1) {
			tv.setText("ȡ���ղ�");
		} else {
			tv.setText("�ղ�");
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
					tv.setText("ȡ���ղ�");
					Toast.makeText(getApplicationContext(), "�ղسɹ�",
							Toast.LENGTH_SHORT).show();
				} catch (Exception e) {
					tv.setText("�ղ�");
					dao2.updateMes(new String[] { NoticeTable.COL_F_TAG },
							new String[] { NoticeTable.COL_ID }, new Object[] {
									0, notice.getId() });
					dao2 = new GlobalDao(NoticeDetailsActivity.this,
							CollectionTable.TABLE_NAME);
					dao2.deleteUser(new String[] { CollectionTable.COL_ID,
							CollectionTable.COL_UNO },
							new Object[] { notice.getId(), s.getUno() });
					Toast.makeText(getApplicationContext(), "ȡ���ղ�",
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
		// ֮ǰ���ղص�ʵ��
		// getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			break;
		case android.R.id.home:
			// ��ǰҳ��������������������ʱ����һЩС����
			// ����δʵ�ֳ���
			// ��onstart��ʵ��
			ActivityManager.getInstance().popOneActivity(this);
			// finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
