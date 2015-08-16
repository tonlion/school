package com.example.school;

import android.app.Activity;
import android.os.Bundle;

public class NoticeListActivity extends Activity {
	// 显示每条数据的详细页面
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_notice_detail);
	}
}
