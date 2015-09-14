package com.example.school;

import com.example.data.DataManager;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ToolsActivity extends Activity {

	private LinearLayout insert;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tools);
		ActionBar bar = getActionBar();
		bar.setDisplayHomeAsUpEnabled(true);
		bar.setTitle("��������");
		bar.setDisplayShowHomeEnabled(false);
		insert = (LinearLayout) findViewById(R.id.insert);
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		TextView view = createTextView("ɽ������");
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(ToolsActivity.this, "ɽ������", Toast.LENGTH_SHORT)
						.show();
			}
		});
		insert.addView(view, lp);
		view = createTextView("����");
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(ToolsActivity.this,
						WebContenterActivity.class);
				i.putExtra("title", "����");
				i.putExtra("url", DataManager.ROOT_URL + "login.html");
				startActivity(i);
			}
		});
		insert.addView(view, lp);
	}

	private TextView createTextView(String textStr) {
		TextView text = new TextView(this);
		text.setText(textStr);
		text.setPadding(25, 20, 25, 20);
		text.setGravity(Gravity.CENTER_VERTICAL);
		text.setBackgroundResource(R.drawable.backgroud);
		text.setTextSize(16);
		return text;
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
