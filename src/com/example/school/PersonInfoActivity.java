package com.example.school;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupWindow;

public class PersonInfoActivity extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person_info);
		findViewById(R.id.person_image).setOnClickListener(this);
		// �޸Ĳ˵���
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle("������Ϣ");
		getActionBar().setDisplayShowHomeEnabled(false);
		// �������ݵõ������Լ����������Ϣ
	}

	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.person_image:
			View view = LayoutInflater.from(this).inflate(
					R.layout.toast_change_image, null);
			final PopupWindow window = new PopupWindow(view,
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			// �˷����Ժ�Ҫ��ȡ������Ϊ����������¼�
			view.findViewById(R.id.exit).setOnClickListener(
					new OnClickListener() {
						@Override
						public void onClick(View v) {
							window.dismiss();
						}
					});
			window.setFocusable(true);
			window.setOutsideTouchable(true);
			window.setBackgroundDrawable(new ColorDrawable());
			window.setAnimationStyle(R.style.popupstyle);
			window.showAtLocation(view, Gravity.BOTTOM, 0, 0);
			break;
		}
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
