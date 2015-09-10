package com.example.adapter;

import java.util.List;

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

public class StudentAdapter extends BaseAdapter {

	private List<Student> students;
	private Context context;
	private OnSelectStudentListener listener;

	public void setListener(OnSelectStudentListener listener) {
		this.listener = listener;
	}

	public StudentAdapter(List<Student> students, Context context) {
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
		TextView messName = (TextView) v.findViewById(R.id.single_name);
		messName.setText(students.get(position).getStuName());
		CheckBox checked = (CheckBox) v.findViewById(R.id.single_checked);
		checked.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				CheckBox cb = (CheckBox) v;
				if (listener != null) {
					listener.selectStudentListener(students.get(position),
							cb.isChecked());
				}
			}
		});
		return v;
	}

	public interface OnSelectStudentListener {
		public void selectStudentListener(Student student, boolean isCheck);
	}

}
