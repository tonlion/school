package com.example.school;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ToolsActivity extends Activity {

	private LinearLayout insert;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tools);
		insert = (LinearLayout) findViewById(R.id.insert);
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		TextView text = new TextView(this);
		text.setText("山商在线");
		insert.addView(text, lp);
	}
}
