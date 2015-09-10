package com.example.school;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.adapter.ConnectionAdapter;
import com.example.adapter.ConnectionAdapter.OnContectChooseListener;
import com.example.entity.Student;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ConnectionManActivity2 extends Activity implements
		OnContectChooseListener {

	private List<Map<String, String>> items;
	private LinearLayout topMenu;
	private ListView lv;
	private TextView choose;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_connection_man);
		topMenu = (LinearLayout) findViewById(R.id.menu_item);
		choose = (TextView) findViewById(R.id.choose_mess);
		addTextView("校园通", topMenu);
		// 为顶部添加点击按钮
		initData();
		View v = LayoutInflater.from(getApplicationContext()).inflate(
				R.layout.c_list_view, null);
		lv = (ListView) v.findViewById(R.id.c_listview);
		ConnectionAdapter cAdapter = new ConnectionAdapter(items,
				getApplicationContext(), lv, topMenu);
		lv.setAdapter(cAdapter);
		cAdapter.setListener(this);
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

	List<Student> students = new ArrayList<Student>();

	@Override
	public void contectChooseListener(Object obj, boolean select) {
		Student s = (Student) obj;
		if (select) {
			students.add(s);
		} else {
			students.remove(s);
		}
		StringBuilder builder = new StringBuilder();
		for (Student stu : students) {
			builder.append(stu.getStuName());
		}
		choose.setText(builder.toString());
	}

}
