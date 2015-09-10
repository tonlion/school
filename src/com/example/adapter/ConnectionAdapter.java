package com.example.adapter;

import java.util.List;
import java.util.Map;

import com.example.entity.Clzss;
import com.example.entity.Major;
import com.example.entity.Role;
import com.example.entity.Student;
import com.example.school.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class ConnectionAdapter extends BaseAdapter {

	private List<?> items;
	private Context context;
	private OnContectChooseListener listener;

	public ConnectionAdapter(List<?> items, Context context) {
		super();
		this.items = items;
		this.context = context;
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

	@SuppressWarnings("unchecked")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.layout_send_message, null);
			holder = new ViewHolder();
			holder.tv = (TextView) convertView.findViewById(R.id.single_name);
			holder.cb = (CheckBox) convertView
					.findViewById(R.id.single_checked);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.cb.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CheckBox cb = (CheckBox) v;
				listener.contectChooseListener(items.get(position),
						cb.isChecked());
			}
		});
		if (items.get(position) instanceof Major) {
			holder.tv.setText(((Major) items.get(position)).getName());
		} else if (items.get(position) instanceof Clzss) {
			holder.tv.setText(((Clzss) items.get(position)).getClassName());
		} else if (items.get(position) instanceof Student) {
			holder.tv.setText(((Student) items.get(position)).getStuName());
		} else if (items.get(position) instanceof Role) {
			holder.tv.setText(((Role) items.get(position)).getRole());
		} else if (items.get(position) instanceof Map) {
			holder.tv.setText(((Map<String, String>) items.get(position))
					.get("typeName"));
		}
		return convertView;
	}

	private class ViewHolder {
		private TextView tv;
		private CheckBox cb;
	}

	public interface OnContectChooseListener {
		public void contectChooseListener(Object obj, boolean select);
	}

	public void setListener(OnContectChooseListener listener) {
		this.listener = listener;
	}

}
