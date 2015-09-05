package com.example.school;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
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
		insert = (LinearLayout) findViewById(R.id.insert);
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		TextView view = createTextView("山商在线");
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(ToolsActivity.this, "山商在线", Toast.LENGTH_SHORT)
						.show();
			}
		});
		insert.addView(view, lp);
		view = createTextView("教务处");
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(ToolsActivity.this, "教务处", Toast.LENGTH_SHORT)
						.show();
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
}
