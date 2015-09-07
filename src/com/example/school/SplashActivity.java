package com.example.school;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Response.Listener;
import com.example.application.SchoolApplication;
import com.example.dao.StudentDao;
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
import android.widget.Toast;

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
			// ��һ��ʹ��
			intent = new Intent(this, FirstReadActivity.class);
		} else {
			// �жϱ��ر���������Ƿ����������ͬ
			final SharedPreferences sp1 = getSharedPreferences("stuInfo",
					MODE_PRIVATE);
			// ���ӷ��������ж��û��Ƿ�����޸ģ��ж��Ƿ�ѡ���Զ���¼
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
							// ��ӵ�application
							SchoolApplication.getInstance().setStudent(student);
							// ɾ���Ա���ĵ�ǰ�û���Ϣ��Ȼ������������µ���Ϣ
							StudentDao dao = new StudentDao(SplashActivity.this);
							dao.saveOrUpdate(student);
							// �û���������ͬ��ȷʱ
							intent = new Intent(SplashActivity.this,
									MainActivity.class);
						} else {
							// �û���������ͬ����ȷʱ
							intent = new Intent(SplashActivity.this,
									LoginActivity.class);
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError arg0) {
						// �޸�������ʱ��
						Toast.makeText(SplashActivity.this, "�������",
								Toast.LENGTH_SHORT).show();
						// û��������£�ֱ�ӽ��������棬��ȡ������Ϣ
						StudentDao dao = new StudentDao(SplashActivity.this);
						dao.findByUno(sp1.getString("userName", ""));
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
		}, 10000);
	}
}
