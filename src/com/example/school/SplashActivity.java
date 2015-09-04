package com.example.school;

import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.example.application.SchoolApplication;
import com.example.data.DataManager;
import com.example.entity.Student;
import com.example.volley.PostRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class SplashActivity extends Activity {

	private Student student;
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		SharedPreferences sp = getSharedPreferences("First", MODE_PRIVATE);
		boolean flag = sp.getBoolean("isRead", false);

		if (!flag) {
			// 第一次使用
			intent = new Intent(this, FirstReadActivity.class);
		} else {
			// 判断本地保存的数据是否与服务器相同
			SharedPreferences sp1 = getSharedPreferences("stuInfo",
					MODE_PRIVATE);
			// 连接服务器，判断用户是否进行修改，判断是否选中自动登录
			if (sp1.getBoolean("loginSelf", false)) {
				PostRequest post = new PostRequest(DataManager.ROOT_URL
						+ "LoginServlet", new Listener<String>() {
					@Override
					public void onResponse(String arg0) {
						if (arg0.length() > 25) {
							Gson gson = new Gson();
							student = gson.fromJson(arg0,
									new TypeToken<Student>() {
									}.getType());
							// 添加到application
							SchoolApplication.getInstance().setStudent(student);
							// 用户名密码相同正确时
							intent = new Intent(SplashActivity.this,
									MainActivity.class);
						} else {
							// 用户名密码相同不正确时
							intent = new Intent(SplashActivity.this,
									LoginActivity.class);
						}
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError arg0) {
						intent = new Intent(SplashActivity.this,
								LoginActivity.class);
					}
				});
				post.setParams("userName", sp1.getString("userName", ""));
				post.setParams("pwd", sp1.getString("pwd", ""));
				SchoolApplication.getInstance().getRequestQueue().add(post);
			} else {
				intent = new Intent(SplashActivity.this, LoginActivity.class);
			}
		}
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				startActivity(intent);
				finish();
			}
		}, 2000);
	}
}
