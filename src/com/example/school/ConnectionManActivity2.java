package com.example.school;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.example.adapter.ConnectionAdapter;
import com.example.application.SchoolApplication;
import com.example.data.DataManager;
import com.example.entity.Clzss;
import com.example.entity.Major;
import com.example.entity.Role;
import com.example.entity.Student;
import com.example.volley.PostRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

public class ConnectionManActivity2 extends Activity {

	private ScrollView messTitle;
	private List<Map<String, String>> items;
	private List<Major> majors;
	private List<Role> roles;
	private List<Clzss> clzsses;
	private List<Student> students;
	private LinearLayout topMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_connection_man);
		topMenu = (LinearLayout) findViewById(R.id.menu_item);
		// 为顶部添加点击按钮
		addTextView("校园通", topMenu);
		initData();
		View v = LayoutInflater.from(getApplicationContext()).inflate(
				R.layout.c_list_view, null);
		final ListView lv = (ListView) v.findViewById(R.id.c_listview);
		ConnectionAdapter cAdapter = new ConnectionAdapter(items,
				getApplicationContext());
		lv.setAdapter(cAdapter);
		LinearLayout l = (LinearLayout) findViewById(R.id.messages);
		l.addView(v);
		// 设置actionbar
		getActionBar().setTitle("联系人");
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowHomeEnabled(false);
	}

	private void addTextView(String text, LinearLayout ll) {
		TextView t = (TextView) LayoutInflater.from(getApplicationContext())
				.inflate(R.layout.text_view, null);
		t.setText(text);
		ll.addView(t);
	}

	private void getMajorMess(final BaseAdapter sAdapter, String type) {
		PostRequest post = new PostRequest(DataManager.ROOT_URL
				+ "NewsPushServlet", new Listener<String>() {

			@Override
			public void onResponse(String arg0) {
				Gson gson = new Gson();
				List<Major> list = gson.fromJson(arg0,
						new TypeToken<List<Major>>() {
						}.getType());
				majors.clear();
				majors.addAll(list);
				sAdapter.notifyDataSetChanged();
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
			}
		});
		post.setParams("dataType", type);
		SchoolApplication.getInstance().getRequestQueue().add(post);
	}

	private void getRoleMess(final BaseAdapter sAdapter, String type) {
		PostRequest post = new PostRequest(DataManager.ROOT_URL
				+ "NewsPushServlet", new Listener<String>() {

			@Override
			public void onResponse(String arg0) {
				Gson gson = new Gson();
				List<Role> list = gson.fromJson(arg0,
						new TypeToken<List<Role>>() {
						}.getType());
				roles.clear();
				roles.addAll(list);
				sAdapter.notifyDataSetChanged();
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
			}
		});
		post.setParams("dataType", type);
		SchoolApplication.getInstance().getRequestQueue().add(post);
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

	private void initData() {
		items = new ArrayList<Map<String, String>>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("typeName", "专业");
		items.add(map);
		map = new HashMap<String, String>();
		map.put("typeName", "职务");
		items.add(map);
	}
}
