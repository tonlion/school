package com.example.school;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LoginActivity extends Activity implements OnClickListener {

	private Button mButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		mButton = (Button) findViewById(R.id.login_btn);
		mButton.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		startActivity(new Intent(this,MainActivity.class));
	}
	
}
