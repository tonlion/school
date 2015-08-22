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
		// ��ҳ��õ��������ݣ�ͨ��intent������һ��ҳ�棬������һ����������
		// ͨ��������ʵõ��û��Ļ�����Ϣ���������û���ͷ��ͻ�����Ϣ
		findViewById(R.id.pheader);// �õ��û�ͷ��
		findViewById(R.id.pname);// �õ��û���
		findViewById(R.id.pmess);// �õ��û��ĸ���ǩ��
		// Ϊ�����޸ġ��汾��⡢�����ղ���ӵ���¼�
		// Ϊ���������Ŀ��ӵ���¼�
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
