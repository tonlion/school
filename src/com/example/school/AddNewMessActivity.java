package com.example.school;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.example.adapter.ContectionAdapter;
import com.example.adapter.ContectionAdapter.OnSelectStudentListener;
import com.example.application.SchoolApplication;
import com.example.data.DataManager;
import com.example.entity.Contection;
import com.example.volley.PostRequest;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

public class AddNewMessActivity extends Activity implements OnClickListener,
		OnSelectStudentListener {

	private EditText searchMess;
	private EditText sendMess;
	private List<Contection> students;
	private ListView chooseList;
	private ContectionAdapter sAdapter;
	private ContectionAdapter cAdapter;
	DisplayMetrics metrics;
	@SuppressWarnings("unused")
	private int sWidth;
	private int sHeight;
	private List<Contection> students2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_new_mess);
		students = new ArrayList<Contection>();
		students2 = new ArrayList<Contection>();
		sAdapter = new ContectionAdapter(students, getApplicationContext());
		cAdapter = new ContectionAdapter(students2, getApplicationContext());
		searchMess = (EditText) findViewById(R.id.search_mess);
		sendMess = (EditText) findViewById(R.id.input_mess);
		chooseList = (ListView) findViewById(R.id.choose_list);
		// 点击搜索
		findViewById(R.id.search_icon).setOnClickListener(this);
		// 点击发送
		findViewById(R.id.send_mess).setOnClickListener(this);
		// 设置actionbar
		getActionBar().setTitle("新建消息");
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowHomeEnabled(false);
		// 将手机屏幕的属性赋值到metrics中
		// 最好不要在oncreate中调用，因为没有绘制完成，所以坐标为0
		metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		sHeight = metrics.heightPixels;
		sWidth = metrics.widthPixels;
		int[] position = new int[2];
		sendMess.getLocationInWindow(position);
		int messHeight = sendMess.getHeight();
		@SuppressWarnings("unused")
		int popHeight = sHeight - position[1] - messHeight;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		// 点击actionbar上的图标时
		case R.id.find_person:
			startActivityForResult(new Intent(getApplicationContext(),
					ConnectionManActivity.class), 1);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.find_person, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.search_icon:
			String searchText = searchMess.getText().toString();
			final View v1 = LayoutInflater.from(AddNewMessActivity.this)
					.inflate(R.layout.c_list_view, null);
			v1.setPadding(0, 200, 0, 0);
			final PopupWindow window = new PopupWindow(v1,
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			ListView lView = (ListView) v1.findViewById(R.id.c_listview);
			// 联网查询符合的数据，popupwindiw中的数据
			lView.setAdapter(sAdapter);
			sAdapter.setListener(this);
			final ProgressDialog dialog = ProgressDialog.show(this, "",
					"数据加载中......");
			PostRequest post = new PostRequest(DataManager.ROOT_URL
					+ "NewsPushServlet", new Listener<String>() {

				@Override
				public void onResponse(String arg0) {
					List<Contection> list = new ArrayList<Contection>();
					try {
						JSONArray array = new JSONArray(arg0);
						for (int i = 0; i < array.length(); i++) {
							JSONObject obj = (JSONObject) array.get(i);
							list.add(new Contection(obj.getString("uno"), obj
									.getString("stuName"), Contection.STUDENT));
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
					students.clear();
					students.addAll(list);
					sAdapter.notifyDataSetChanged();
					dialog.dismiss();
					// popupwindow
					window.setFocusable(true);
					window.setOutsideTouchable(true);
					window.setBackgroundDrawable(new ColorDrawable());
					window.setAnimationStyle(R.style.popupstyle);
					window.showAtLocation(v1, Gravity.BOTTOM, 0, 0);
				}
			}, new Response.ErrorListener() {

				@Override
				public void onErrorResponse(VolleyError arg0) {
					Toast.makeText(getApplicationContext(), "联网出错",
							Toast.LENGTH_SHORT).show();
				}
			});
			post.setParams("dataType", "student");
			post.setParams("name", searchText);
			SchoolApplication.getInstance().getRequestQueue().add(post);
			break;
		case R.id.send_mess:
			// 发送消息，暂时不知道如何实现
			break;
		}
	}

	@Override
	public void selectStudentListener(Contection student) {
		if (student.isChecked()) {
			students2.add(student);
		} else {
			students2.remove(student);
		}

		chooseList.setAdapter(cAdapter);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		List<Contection> contections;
		if (requestCode == 1 && resultCode == 2) {
			contections = data.getParcelableArrayListExtra("types");
			if (contections != null && contections.size() != 0) {
				if (students2.size() != 0) {
					students2.addAll(contections);
					cAdapter.notifyDataSetChanged();
				} else {
					students2.addAll(contections);
					chooseList.setAdapter(cAdapter);
				}
			}
		}
	}
}
