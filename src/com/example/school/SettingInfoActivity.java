package com.example.school;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class SettingInfoActivity extends Activity implements OnClickListener {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting_info);
		// 此页面得到基本数据，通过intent传给下一个页面，就少了一步请求数据
		// 通过网络访问得到用户的基本信息，并设置用户的头像和基本信息
		findViewById(R.id.pheader);// 得到用户头像
		findViewById(R.id.pname);// 得到用户名
		findViewById(R.id.pmess);// 得到用户的个性签名
		// 为密码修改、版本检测、个人收藏添加点击事件
		// 为点击个人条目添加点击事件
		findViewById(R.id.person_info).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.person_info:
			startActivity(new Intent(this, PersonInfoActivity.class));
			break;
		}
	}
}
