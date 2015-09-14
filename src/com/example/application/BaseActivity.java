package com.example.application;

import android.app.Activity;
import android.os.Bundle;

/**
 * ���࣬����activity�Ļ���
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
