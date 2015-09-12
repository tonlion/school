package com.example.adapter;

import java.util.List;

import com.example.entity.Contection;
import com.example.school.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class ContectionAdapter extends BaseAdapter {

	private List<Contection> students;
	private Context context;
	private OnSelectStudentListener listener;

	public void setListener(OnSelectStudentListener listener) {
		this.listener = listener;
	}

	public ContectionAdapter(List<Contection> students, Context context) {
		super();
		this.students = students;
		this.context = context;
	}

	@Override
	public int getCount() {
		return students.size();
	}

	@Override
	public Object getItem(int position) {
		return students.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View v = LayoutInflater.from(context).inflate(
				R.layout.layout_send_message, null);
		final Contection c = students.get(position);
		TextView messName = (TextView) v.findViewById(R.id.single_name);
		messName.setText(c.getItemValue());
		CheckBox checked = (CheckBox) v.findViewById(R.id.single_checked);
		if (c.getType() == Contection.ALL_MAJOR
				|| c.getType() == Contection.ALL_ROLE) {
			checked.setVisibility(IGNORE_ITEM_VIEW_TYPE);
		}
		if (c.isChecked()) {
			checked.setChecked(true);
		} else {
			checked.setChecked(false);
		}
		checked.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CheckBox cb = (CheckBox) v;
				c.setChecked(cb.isChecked());
				if (listener != null) {
					listener.selectStudentListener(c);
				}
			}
		});
		return v;
	}

	public interface OnSelectStudentListener {
		public void selectStudentListener(Contection student);
	}

}
