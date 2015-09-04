package com.example.school;

import com.example.application.SchoolApplication;
import com.example.async.MyAsyncTask;
import com.example.entity.Student;

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
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class PersonInfoActivity extends Activity implements OnClickListener {
	private ImageView stuImg;
	private TextView stuInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person_info);
		Student student = SchoolApplication.getInstance().getStudent();
		stuImg = (ImageView) findViewById(R.id.person_image);
		MyAsyncTask task = new MyAsyncTask(stuImg);
		task.execute(student.getImg());
		stuImg.setOnClickListener(this);
		// 修改菜单栏
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle("个人信息");
		getActionBar().setDisplayShowHomeEnabled(false);
		// 请求数据得到姓名以及其余个人信息
		stuInfo = (TextView) findViewById(R.id.person_role);
		stuInfo.setText(student.getRole());
		stuInfo = (TextView) findViewById(R.id.person_class);
		stuInfo.setText(student.getClassName());
		stuInfo = (TextView) findViewById(R.id.person_domain);
		stuInfo.setText(student.getMajorName());
		stuInfo = (TextView) findViewById(R.id.person_role);
		stuInfo.setText(student.getRole());
		stuInfo = (TextView) findViewById(R.id.person_role);
		stuInfo.setText(student.getRole());
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
			// 此方法以后要提取出，因为有三个点击事件
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
