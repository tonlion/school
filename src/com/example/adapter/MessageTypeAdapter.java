package com.example.adapter;

import java.util.List;
import java.util.Map;

import com.example.school.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class MessageTypeAdapter extends BaseAdapter {

	private List<Map<String, String>> types;
	private Context context;

	public MessageTypeAdapter(List<Map<String, String>> types, Context context) {
		super();
		this.types = types;
		this.context = context;
	}

	@Override
	public int getCount() {
		return types.size();
	}

	@Override
	public Object getItem(int position) {
		return types.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = LayoutInflater.from(context).inflate(
				R.layout.layout_send_message, null);
		TextView typeName = (TextView) v.findViewById(R.id.single_name);
		CheckBox checked = (CheckBox) v.findViewById(R.id.single_checked);
		checked.setVisibility(IGNORE_ITEM_VIEW_TYPE);
		typeName.setText(types.get(position).get("typeName"));
		return v;
	}

}
