package com.example.school;

import java.io.File;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.example.application.ActivityManager;
import com.example.application.SchoolApplication;
import com.example.data.DataManager;
import com.example.data.ImageLoaderUtils;
import com.example.entity.Student;
import com.example.service.UpdateService;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SettingInfoActivity extends Activity implements OnClickListener {
	// private boolean flag;// �����Զ���¼��ͼƬת��
	SharedPreferences sp;
	boolean flag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting_info);
		// ͨ��������ʵõ��û��Ļ�����Ϣ���������û���ͷ��ͻ�����Ϣ
		Student student = SchoolApplication.getInstance().getStudent();
		ImageView header = (ImageView) findViewById(R.id.pheader);// �õ��û�ͷ��
		// MyAsyncTask task = new MyAsyncTask(header);
		// task.execute(student.getImg());
		ImageLoaderUtils.display(student.getImg(), header);
		TextView name = (TextView) findViewById(R.id.pname);// �õ��û���
		name.setText(student.getStuName());
		TextView mess = (TextView) findViewById(R.id.pmess);// �õ��û��ĸ���ǩ��
		mess.setText(student.getSmsright());
		// Ϊ�����޸ġ��汾��⡢�����ղ���ӵ���¼�
		// Ϊ���������Ŀ��ӵ���¼�
		findViewById(R.id.person_info).setOnClickListener(this);
		findViewById(R.id.setting_change_pass).setOnClickListener(this);
		findViewById(R.id.person_collection).setOnClickListener(this);
		findViewById(R.id.versions).setOnClickListener(this);
		ImageView icon = (ImageView) findViewById(R.id.icon_switch);
		icon.setOnClickListener(this);
		sp = getSharedPreferences("stuInfo", MODE_PRIVATE);
		// ����Ĭ��ͼƬ
		flag = sp.getBoolean("loginSelf", false);
		if (flag) {
			icon.setImageResource(R.drawable.icon_switch_on);
		} else {
			icon.setImageResource(R.drawable.icon_switch_off);
		}
		findViewById(R.id.setting_exit).setOnClickListener(this);
		// �޸Ĳ˵���
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle("����");
		getActionBar().setDisplayShowHomeEnabled(false);
		// ѹ��ջ
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
			// �޸�����ɹ��󣬽�������activity
			startActivity(new Intent(this, ModifyPasswordActivity.class));
			break;
		case R.id.icon_switch:
			// �����Զ���¼����任ͼƬ
			Editor editor = sp.edit();
			ImageView icon = (ImageView) findViewById(R.id.icon_switch);
			sp = getSharedPreferences("stuInfo", MODE_PRIVATE);
			flag = sp.getBoolean("loginSelf", false);
			if (flag) {
				icon.setImageResource(R.drawable.icon_switch_off);
				editor.putBoolean("loginSelf", false);
			} else {
				icon.setImageResource(R.drawable.icon_switch_on);
				editor.putBoolean("loginSelf", true);
			}
			editor.commit();
			break;
		case R.id.setting_exit:
			// ����˳�ʱ���������н��̣���������activity
			File file = new File("/data/data/" + getPackageName().toString()
					+ "/shared_prefs", "stuInfo.xml");
			if (file.exists()) {
				file.delete();
				startActivity(new Intent(SettingInfoActivity.this,
						LoginActivity.class));
				// ɾ�����е�activity
				ActivityManager.getInstance().finishAllActivity();
				// ��������
				android.os.Process.killProcess(android.os.Process.myPid());
			}
			break;
		case R.id.person_collection:
			startActivity(new Intent(getApplicationContext(),
					CollectionActivity.class));
			break;
		case R.id.versions:
			alertDialog();
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

	public void alertDialog() {
		final ProgressDialog dialog = ProgressDialog.show(this, "",
				"���������......");
		StringRequest request = new StringRequest(DataManager.ROOT_URL + "apk",
				new Listener<String>() {
					@Override
					public void onResponse(String arg0) {
						try {
							JSONObject jsonO = new JSONObject(arg0);
							String url = jsonO.getString("url");
							int vid = jsonO.getInt("vid");
							PackageManager pManager = getPackageManager();
							PackageInfo info = pManager.getPackageInfo(
									"com.example.school", 0);
							if (info.versionCode < vid) {
								createDialog(url);
							} else {
								Toast.makeText(getApplicationContext(),
										"�Ѿ������°���", Toast.LENGTH_SHORT).show();
							}
							dialog.dismiss();
						} catch (JSONException e) {
							e.printStackTrace();
						} catch (NameNotFoundException e) {
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						Toast.makeText(getApplicationContext(), "����ʧ��",
								Toast.LENGTH_SHORT).show();
						dialog.dismiss();
					}
				});
		SchoolApplication.getInstance().getRequestQueue().add(request);
	}

	public void createDialog(final String url) {
		final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setCancelable(false);
		dialog.setTitle("������ʾ");
		View views = LayoutInflater.from(this).inflate(
				R.layout.layout_alertdialog, null);
		final AlertDialog alert = dialog.setView(views).create();
		views.findViewById(R.id.ok).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(SettingInfoActivity.this,
						UpdateService.class);
				i.putExtra("url", DataManager.ROOT_URL + url);
				startService(i);
				alert.dismiss();
			}
		});
		views.findViewById(R.id.no).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				alert.dismiss();
			}
		});
		alert.show();
	}
}
