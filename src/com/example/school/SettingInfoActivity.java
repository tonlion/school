package com.example.school;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class SettingInfoActivity extends Activity implements OnClickListener {
	private boolean flag = false;// �����Զ���¼��ͼƬת��

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting_info);
		// ��ҳ��õ��������ݣ�ͨ��intent������һ��ҳ�棬������һ����������
		// ͨ��������ʵõ��û��Ļ�����Ϣ���������û���ͷ��ͻ�����Ϣ
		findViewById(R.id.pheader);// �õ��û�ͷ��
		findViewById(R.id.pname);// �õ��û���
		findViewById(R.id.pmess);// �õ��û��ĸ���ǩ��
		// Ϊ�����޸ġ��汾��⡢�����ղ���ӵ���¼�
		// Ϊ���������Ŀ��ӵ���¼�
		findViewById(R.id.person_info).setOnClickListener(this);
		findViewById(R.id.setting_change_pass).setOnClickListener(this);
		findViewById(R.id.icon_switch).setOnClickListener(this);
		// �޸Ĳ˵���
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle("����");
		getActionBar().setDisplayShowHomeEnabled(false);
	}

	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.person_info:
			startActivity(new Intent(this, PersonInfoActivity.class));
			break;
		case R.id.setting_change_pass:
			startActivity(new Intent(this, ModifyPasswordActivity.class));
			break;
		case R.id.icon_switch:
			// �����Զ���¼����任ͼƬ
			ImageView icon = (ImageView) findViewById(R.id.icon_switch);
			if (flag) {
				icon.setImageResource(R.drawable.icon_switch_on);
				flag = false;
			} else {
				icon.setImageResource(R.drawable.icon_switch_off);
				flag = true;
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
