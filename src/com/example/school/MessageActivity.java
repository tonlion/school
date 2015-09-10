package com.example.school;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MessageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);
		// 设置actionbar
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle("消息平台");
		getActionBar().setDisplayShowHomeEnabled(false);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		// 点击+号时
		case R.id.add_message:
			Intent intent = new Intent(getApplicationContext(),
					AddNewMessActivity.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.add_message, menu);
		return true;
	}
}
