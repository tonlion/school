package com.example.school;

import com.example.application.SchoolApplication;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class LoginActivity extends Activity implements OnClickListener {
	private CheckBox loginSelf;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		findViewById(R.id.login_btn).setOnClickListener(this);
		loginSelf = (CheckBox) findViewById(R.id.login_self);
		// 点击自动登录时同时选中记住密码
		loginSelf.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				CheckBox box = (CheckBox) findViewById(R.id.remember_pass);
				if (isChecked) {
					box.setChecked(true);
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_btn:
			SchoolApplication.getInstance().getRequestQueue();
			startActivity(new Intent(this, MainActivity.class));
			break;
		}
	}
}
