package com.example.school;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.example.application.SchoolApplication;
import com.example.dao.StudentDao;
import com.example.data.DataManager;
import com.example.entity.Student;
import com.example.volley.PostRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class LoginActivity extends Activity implements OnClickListener {
	private CheckBox loginSelf;
	private CheckBox rememberPass;
	private TextView stuName;
	private TextView stuPass;
	private Student student;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		findViewById(R.id.login_btn).setOnClickListener(LoginActivity.this);
		loginSelf = (CheckBox) findViewById(R.id.login_self);
		rememberPass = (CheckBox) findViewById(R.id.remember_pass);
		stuName = (TextView) findViewById(R.id.login_name);
		stuPass = (TextView) findViewById(R.id.login_pass);
		setCheckBox();
		SharedPreferences sp = getSharedPreferences("stuInfo", MODE_PRIVATE);
		if (sp.getBoolean("remember", false)) {
			stuName.setText(sp.getString("userName", ""));
			stuPass.setText(sp.getString("pwd", ""));
		}
	}

	private void setCheckBox() {
		// ����Զ���¼ʱͬʱѡ�м�ס����
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
		// ȡ����ס�����ͬʱȡ���Զ�ѡ��
		rememberPass.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				CheckBox box = (CheckBox) findViewById(R.id.login_self);
				if (!isChecked) {
					box.setChecked(false);
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_btn:
			PostRequest post = new PostRequest(DataManager.ROOT_URL
					+ "LoginServlet", new Listener<String>() {
				@Override
				public void onResponse(String arg0) {
					if (arg0.length() > 25) {
						Gson gson = new Gson();
						student = gson.fromJson(arg0, new TypeToken<Student>() {
						}.getType());
						// ��ӵ�application
						// ��ȷ��ת���û���������ȷ
						SchoolApplication.getInstance().setStudent(student);
						// �û���Ϣ���浽�ͱ������ݿ�
						StudentDao dao = new StudentDao(LoginActivity.this);
						dao.addUser(student);
						// �ŵ����߳���ִ��
						startActivity(new Intent(LoginActivity.this,
								MainActivity.class));
						new Handler().postDelayed(new Runnable() {
							@Override
							public void run() {
								finish();
							}
						}, 500);
					} else {
						Toast.makeText(LoginActivity.this, "�û������������",
								Toast.LENGTH_SHORT).show();
					}
				}
			}, new ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError arg0) {
					Toast.makeText(LoginActivity.this, "�������Ӵ���",
							Toast.LENGTH_SHORT).show();
				}
			});
			post.setParams("userName", stuName.getText().toString());
			post.setParams("pwd", stuPass.getText().toString());
			SchoolApplication.getInstance().getRequestQueue().add(post);
			// �ж�ѡ���Զ���¼�ͼ�ס����Ĳ�����
			// �����¼֮���ס����
			SharedPreferences sp = getSharedPreferences("stuInfo", MODE_PRIVATE);
			Editor editor = sp.edit();
			if (rememberPass.isChecked()) {
				editor.putString("userName", stuName.getText().toString());
				editor.putString("pwd", stuPass.getText().toString());
				editor.putBoolean("remember", true);
			} else {
				editor.putBoolean("remember", false);
			}
			if (loginSelf.isChecked()) {
				editor.putBoolean("loginSelf", true);
			} else {
				editor.putBoolean("loginSelf", false);
			}
			editor.commit();
			break;
		}
	}
}
