package com.example.school;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.example.adapter.ContectionAdapter;
import com.example.adapter.ContectionAdapter.OnSelectStudentListener;
import com.example.application.SchoolApplication;
import com.example.data.DataManager;
import com.example.entity.Contection;
import com.example.volley.PostRequest;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ConnectionManActivity extends Activity implements
		OnItemClickListener, OnSelectStudentListener {

	private LinearLayout topMenu;
	private List<Contection> contections;
	private ContectionAdapter adapter;
	private ListView lv;
	private TextView choose;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_connection_man);
		topMenu = (LinearLayout) findViewById(R.id.menu_item);
		choose = (TextView) findViewById(R.id.choose_mess);
		// 为顶部添加点击按钮
		View v = LayoutInflater.from(getApplicationContext()).inflate(
				R.layout.c_list_view, null);
		lv = (ListView) v.findViewById(R.id.c_listview);
		initData();
		lv.setOnItemClickListener(this);
		LinearLayout l = (LinearLayout) findViewById(R.id.messages);
		l.addView(v);
		// 设置actionbar
		getActionBar().setTitle("联系人");
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowHomeEnabled(false);
	}

	private void initData() {
		Contection con = new Contection(null, "校园通", 0);
		List<Contection> nodes = new ArrayList<Contection>();
		addTextView(con);
		contections = new ArrayList<Contection>();
		Contection c = new Contection();
		c.setItemValue("职务");
		c.setType(Contection.ALL_ROLE);
		contections.add(c);
		nodes.add(c);
		c = new Contection();
		c.setItemValue("专业");
		c.setType(Contection.ALL_MAJOR);
		contections.add(c);
		nodes.add(c);
		con.setNodes(nodes);
		adapter = new ContectionAdapter(contections, getApplicationContext());
		lv.setAdapter(adapter);
		adapter.setListener(this);
	}

	private void addTextView(Contection con) {
		TextView t = (TextView) LayoutInflater.from(getApplicationContext())
				.inflate(R.layout.text_view, null);
		t.setText(con.getItemValue());
		t.setTag(con);
		t.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int index = topMenu.indexOfChild(v);
				int length = topMenu.getChildCount() - index - 1;
				topMenu.removeViews(index + 1, length);
				Contection contection = (Contection) v.getTag();
				List<Contection> list = contection.getNodes();
				contections.clear();
				contections.addAll(list);
				adapter.notifyDataSetChanged();
			}
		});
		topMenu.addView(t);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		final Contection c = (Contection) adapter.getItem(position);

		if (c.getType() != Contection.STUDENT) {
			addTextView(c);
		}
		PostRequest post = reLoadData(c);
		SchoolApplication.getInstance().getRequestQueue().add(post);
	}

	private ProgressDialog dialog = null;

	private PostRequest reLoadData(final Contection c) {

		if (c.getType() != Contection.STUDENT) {
			dialog = ProgressDialog.show(ConnectionManActivity.this, "",
					"数据加载中......");
		}
		dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					dialog.dismiss();
				}
				return false;
			}
		});
		PostRequest post = new PostRequest(DataManager.ROOT_URL
				+ "NewsPushServlet", new Listener<String>() {
			List<Contection> list = new ArrayList<Contection>();

			@Override
			public void onResponse(String arg0) {
				switch (c.getType()) {
				case Contection.ALL_MAJOR:
					list = getMajor(arg0);
					break;
				case Contection.ALL_ROLE:
					list = getRole(arg0);
					break;
				case Contection.ROLE:
				case Contection.CLZSS:
					list = getStudent(arg0);
					break;
				case Contection.MAJOR:
					list = getClassList(arg0);
					break;

				}
				if (list != null && list.size() > 0) {
					contections.clear();
					contections.addAll(list);
					adapter.notifyDataSetChanged();
					dialog.dismiss();
					c.setNodes(list);
				}
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				dialog.dismiss();
				Toast.makeText(getApplicationContext(), "联网失败",
						Toast.LENGTH_SHORT).show();
			}
		});
		switch (c.getType()) {
		case Contection.ALL_MAJOR:
			post.setParams("dataType", "major");
			break;
		case Contection.ALL_ROLE:
			post.setParams("dataType", "role");
			break;
		case Contection.MAJOR:
			post.setParams("dataType", "majortoclass");
			post.setParams("majorId", c.getId());
			break;
		case Contection.CLZSS:
			post.setParams("dataType", "classtostudent");
			post.setParams("classNo", c.getId());
			break;
		case Contection.ROLE:
			post.setParams("dataType", "roletostudent");
			post.setParams("role", c.getItemValue());
			break;
		}
		return post;
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

	/**
	 * 得到major
	 * 
	 * @param json
	 * @return
	 */
	private List<Contection> getMajor(String json) {
		List<Contection> list = new ArrayList<Contection>();
		try {
			JSONArray array = new JSONArray(json);
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = (JSONObject) array.get(i);
				list.add(new Contection(obj.getString("id"), obj
						.getString("name"), Contection.MAJOR));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 
	 * @param json
	 * @return
	 */
	private List<Contection> getClassList(String json) {
		List<Contection> list = new ArrayList<Contection>();
		try {
			JSONArray array = new JSONArray(json);
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = (JSONObject) array.get(i);
				list.add(new Contection(obj.getString("id"), obj
						.getString("className"), Contection.CLZSS));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 
	 * @param json
	 * @return
	 */
	private List<Contection> getStudent(String json) {
		List<Contection> list = new ArrayList<Contection>();
		try {
			JSONArray array = new JSONArray(json);
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = (JSONObject) array.get(i);
				list.add(new Contection(obj.getString("uno"), obj
						.getString("stuName"), Contection.STUDENT));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 
	 * @param json
	 * @return
	 */
	private List<Contection> getRole(String json) {
		List<Contection> list = new ArrayList<Contection>();
		try {
			JSONArray array = new JSONArray(json);
			for (int i = 0; i < array.length(); i++) {
				JSONObject obj = (JSONObject) array.get(i);
				Contection c = new Contection();
				c.setItemValue(obj.getString("role"));
				c.setType(Contection.ROLE);
				list.add(c);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}

	ArrayList<Contection> cs = new ArrayList<Contection>();

	@Override
	public void selectStudentListener(Contection student) {
		if (student.isChecked()) {
			cs.add(student);
		} else {
			cs.remove(student);
		}
		StringBuilder builder = new StringBuilder();
		for (Contection contection : cs) {
			builder.append(contection.getItemValue() + ",");
		}
		choose.setText(builder.toString());
		ActionBar bar = getActionBar();
		bar.setDisplayShowCustomEnabled(true);
		TextView t = new TextView(ConnectionManActivity.this);
		LayoutParams p = new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.MATCH_PARENT);
		p.gravity = Gravity.RIGHT;
		t.setText("确定(" + cs.size() + ")");
		t.setTextSize(20);
		t.setPadding(0, 0, 20, 0);
		t.setGravity(Gravity.CENTER_VERTICAL);
		bar.setCustomView(t, p);
		t.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						AddNewMessActivity.class);
				intent.putParcelableArrayListExtra("types", cs);
				setResult(2, intent);
				finish();
			}
		});
	}
}
