package com.example.school;

import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.example.application.ActivityManager;
import com.example.application.SchoolApplication;
import com.example.data.DataManager;
import com.example.volley.PostRequest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class ModifyPasswordActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modify_password);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setTitle("修改密码");
		final EditText oldPass = (EditText) findViewById(R.id.old_pass);
		final EditText newpass = (EditText) findViewById(R.id.new_pass);
		final EditText rePass = (EditText) findViewById(R.id.affirm_pass);
		// 压入栈
		ActivityManager.getInstance().pushActivity(this);
		modifyPass(oldPass, newpass, rePass);
	}

	private void modifyPass(final EditText oldPass, final EditText newpass,
			final EditText rePass) {
		findViewById(R.id.motify).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 判断新密码与确认密码
				if ((newpass.getText().toString()).equals(rePass.getText()
						.toString())) {
					// 判断旧密码是否正确
					if (SchoolApplication.getInstance().getStudent().getPwd()
							.equals(oldPass.getText().toString())) {
						// 判断原密码与新密码是否相同
						if (!newpass.getText().toString()
								.equals(oldPass.getText().toString())) {
							// 数据提交进行修改
							PostRequest post = new PostRequest(
									DataManager.ROOT_URL + "LoginServlet",
									new Listener<String>() {

										@Override
										public void onResponse(String arg0) {
											switch (Integer.parseInt(arg0)) {
											case 1:
												startActivity(new Intent(
														ModifyPasswordActivity.this,
														LoginActivity.class));
												ActivityManager.getInstance()
														.finishAllActivity();
												break;
											case 0:
												Toast.makeText(
														ModifyPasswordActivity.this,
														"密码修改失败",
														Toast.LENGTH_SHORT)
														.show();
												break;
											}
										}
									}, new Response.ErrorListener() {

										@Override
										public void onErrorResponse(
												VolleyError arg0) {
											Toast.makeText(
													ModifyPasswordActivity.this,
													"网络连接错误",
													Toast.LENGTH_SHORT).show();
										}
									});
							post.setParams("userName", SchoolApplication
									.getInstance().getStudent().getStuName());
							post.setParams("pwd", newpass.getText().toString()
									+ "");
							post.setParams("update", "1");
							SchoolApplication.getInstance().getRequestQueue()
									.add(post);
						} else {
							Toast.makeText(ModifyPasswordActivity.this,
									"新密码与原密码相同", Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(ModifyPasswordActivity.this, "原密码不正确",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(ModifyPasswordActivity.this, "新密码与确认密码不符",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
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
