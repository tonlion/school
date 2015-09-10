package com.example.adapter;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.example.application.SchoolApplication;
import com.example.data.DataManager;
import com.example.entity.Role;
import com.example.entity.Student;
import com.example.school.R;
import com.example.volley.PostRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class RoleAdapter extends BaseAdapter {
	private List<Role> items;
	private Context context;
	private List<Student> students;
	private ListView lv;
	private LinearLayout ll;

	public RoleAdapter(List<Role> items, Context context, ListView lv,
			LinearLayout ll) {
		super();
		this.items = items;
		this.context = context;
		this.lv = lv;
		this.ll = ll;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.layout_send_message, null);
			holder = new ViewHolder();
			holder.ItemName = (TextView) convertView
					.findViewById(R.id.single_name);
			holder.choose = (CheckBox) convertView
					.findViewById(R.id.single_checked);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.ItemName.setText(items.get(position).getRole());
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				students = new ArrayList<Student>();
				// lv.removeAllViews();
				final StudentAdapter sAdapter = new StudentAdapter(students,
						context);
				lv.setAdapter(sAdapter);
				addTextView(items.get(position).getRole(), ll);
				PostRequest post = new PostRequest(DataManager.ROOT_URL
						+ "NewsPushServlet", new Listener<String>() {

					@Override
					public void onResponse(String arg0) {
						Gson gson = new Gson();
						List<Student> list = gson.fromJson(arg0,
								new TypeToken<List<Student>>() {
								}.getType());
						students.clear();
						students.addAll(list);
						sAdapter.notifyDataSetChanged();
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
					}
				});
				post.setParams("dataType", "roletostudent");
				post.setParams("role", items.get(position).getRole());
				SchoolApplication.getInstance().getRequestQueue().add(post);
			}
		});
		return convertView;
	}

	private class ViewHolder {
		private TextView ItemName;
		private CheckBox choose;
	}

	private void addTextView(String text, LinearLayout ll) {
		TextView t = (TextView) LayoutInflater.from(context).inflate(
				R.layout.text_view, null);
		t.setText(text);
		ll.addView(t);
	}

}
