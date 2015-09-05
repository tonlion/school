package com.example.school;

import java.io.File;

import com.example.application.ActivityManager;
import com.example.application.SchoolApplication;
import com.example.async.MyAsyncTask;
import com.example.entity.Student;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class SettingInfoActivity extends Activity implements OnClickListener {
	// private boolean flag;// 设置自动登录的图片转换
	SharedPreferences sp;
	boolean flag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting_info);
		// 通过网络访问得到用户的基本信息，并设置用户的头像和基本信息
		Student student = SchoolApplication.getInstance().getStudent();
		ImageView header = (ImageView) findViewById(R.id.pheader);// 得到用户头像
		MyAsyncTask task = new MyAsyncTask(header);
		task.execute(student.getImg());
		TextView name = (TextView) findViewById(R.id.pname);// 得到用户名
		name.setText(student.getStuName());
		TextView mess = (TextView) findViewById(R.id.pmess);// 得到用户的个性签名
		mess.setText(student.getSmsright());
		// 为密码修改、版本检测、个人收藏添加点击事件
		// 为点击个人条目添加点击事件
		findViewById(R.id.person_info).setOnClickListener(this);
		findViewById(R.id.setting_change_pass).setOnClickListener(this);
		ImageView icon = (ImageView) findViewById(R.id.icon_switch);
		icon.setOnClickListener(this);
		sp = getSharedPreferences("stuInfo", MODE_PRIVATE);
		// 设置默认图片
		flag = sp.getBoolean("loginSelf", false);
		if (flag) {
			icon.setImageResource(R.drawable.icon_switch_on);
		} else {
			icon.setImageResource(R.drawable.icon_switch_off);
		}
		findViewById(R.id.setting_exit).setOnClickListener(this);
		// 修改菜单栏
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle("设置");
		getActionBar().setDisplayShowHomeEnabled(false);
		// 压入栈
		ActivityManager.getInstance().pushActivity(this);
	}

	@SuppressLint({ "NewApi", "SdCardPath" })
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.person_info:
			startActivity(new Intent(this, PersonInfoActivity.class));
			break;
		case R.id.setting_change_pass:
			// 修改密码成功后，结束所有activity
			startActivity(new Intent(this, ModifyPasswordActivity.class));
			break;
		case R.id.icon_switch:
			// 设置自动登录点击变换图片
			Editor editor = sp.edit();
			ImageView icon = (ImageView) findViewById(R.id.icon_switch);
			sp = getSharedPreferences("stuInfo", MODE_PRIVATE);
			flag = sp.getBoolean("loginSelf", false);
			if (flag) {
				icon.setImageResource(R.drawable.icon_switch_on);
				editor.putBoolean("loginSelf", false);
			} else {
				icon.setImageResource(R.drawable.icon_switch_off);
				editor.putBoolean("loginSelf", true);
			}
			editor.commit();
			break;
		case R.id.setting_exit:
			// 点击退出时，结束所有进程，结束所有activity
			File file = new File("/data/data/" + getPackageName().toString()
					+ "/shared_prefs", "stuInfo.xml");
			if (file.exists()) {
				file.delete();
				startActivity(new Intent(SettingInfoActivity.this,
						LoginActivity.class));
				// 删除所有的activity
				ActivityManager.getInstance().finishAllActivity();
				// 结束进程
				android.os.Process.killProcess(android.os.Process.myPid());
			}
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
