package com.example.application;

import android.app.Activity;
import android.os.Bundle;

/**
 * 基类，所有activity的基类
 * 
 * @author Owner
 * 
 */
public class BaseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActivityManager.getInstance().pushActivity(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ActivityManager.getInstance().finishAllActivity();
	}
}
