package com.example.adapter;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.example.application.SchoolApplication;
import com.example.data.DataManager;
import com.example.entity.Clzss;
import com.example.entity.Major;
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

public class SingleMessageAdapter extends BaseAdapter {
	private List<Major> items;
	private Context context;
	private ListView listView;
	private List<Clzss> clzsses;
	private LinearLayout ll;

	public SingleMessageAdapter(List<Major> items, Context context,
			ListView listView, LinearLayout ll) {
		super();
		this.items = items;
		this.context = context;
		this.listView = listView;
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
			holder.singleName = (TextView) convertView
					.findViewById(R.id.single_name);
			holder.choose = (CheckBox) convertView
					.findViewById(R.id.single_checked);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.singleName.setText(items.get(position).getName());
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				clzsses = new ArrayList<Clzss>();
				//listView.removeAllViews();
				final ClzssAdapter cAdapter = new ClzssAdapter(clzsses,
						context, listView, ll);
				listView.setAdapter(cAdapter);
				addTextView(items.get(position).getName(), ll);
				// 获取学院下的班级
				PostRequest post = new PostRequest(DataManager.ROOT_URL
						+ "NewsPushServlet", new Listener<String>() {

					@Override
					public void onResponse(String arg0) {
						Gson gson = new Gson();
						List<Clzss> list = gson.fromJson(arg0,
								new TypeToken<List<Clzss>>() {
								}.getType());
						clzsses.clear();
						clzsses.addAll(list);
						cAdapter.notifyDataSetChanged();
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
					}
				});
				post.setParams("dataType", "majortoclass");
				post.setParams("majorId", items.get(position).getId() + "");
				SchoolApplication.getInstance().getRequestQueue().add(post);
			}
		});
		return convertView;
	}

	private class ViewHolder {
		private TextView singleName;
		private CheckBox choose;
	}

	private void addTextView(String text, LinearLayout ll) {
		TextView t = (TextView) LayoutInflater.from(context).inflate(
				R.layout.text_view, null);
		t.setText(text);
		ll.addView(t);
	}
}
