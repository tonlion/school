package com.example.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.example.application.SchoolApplication;
import com.example.data.DataManager;
import com.example.entity.Clzss;
import com.example.entity.Major;
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

public class ConnectionAdapter extends BaseAdapter {

	private List<?> items;
	private Context context;
	private OnContectChooseListener listener;
	private List<Major> majors;
	private List<Role> roles;
	private List<Clzss> clzsses;
	private List<Student> students;
	private ListView lv;
	private LinearLayout ll;

	public ConnectionAdapter(List<?> items, Context context, ListView lv,
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
				if (listener != null) {
					listener.contectChooseListener(items.get(position),
							cb.isChecked());
				}
			}
		});
		// 判断时那种类型
		if (items.get(position) instanceof Major) {
			holder.tv.setText(((Major) items.get(position)).getName());
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					clzsses = new ArrayList<Clzss>();
					ConnectionAdapter cAdapter = new ConnectionAdapter(clzsses,
							context, lv, ll);
					lv.setAdapter(cAdapter);
					getClassMess(cAdapter,
							((Major) items.get(position)).getId());
					addTextView(((Major) items.get(position)).getName(), ll);
				}
			});
		} else if (items.get(position) instanceof Clzss) {
			holder.tv.setText(((Clzss) items.get(position)).getClassName());
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					students = new ArrayList<Student>();
					ConnectionAdapter cAdapter = new ConnectionAdapter(
							students, context, lv, ll);
					lv.setAdapter(cAdapter);
					getStudentMess(cAdapter,
							((Clzss) items.get(position)).getId());
					addTextView(((Clzss) items.get(position)).getClassName(),
							ll);
				}
			});
		} else if (items.get(position) instanceof Student) {
			holder.tv.setText(((Student) items.get(position)).getStuName());
		} else if (items.get(position) instanceof Role) {
			holder.tv.setText(((Role) items.get(position)).getRole());
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					students = new ArrayList<Student>();
					ConnectionAdapter cAdapter = new ConnectionAdapter(
							students, context, lv, ll);
					lv.setAdapter(cAdapter);
					getRoleToStuMess(cAdapter,
							((Role) items.get(position)).getRole());
					addTextView(((Role) items.get(position)).getRole(), ll);
				}
			});
		} else if (items.get(position) instanceof Map) {
			// 第一次点击，即专业，职务页面
			holder.tv.setText(((Map<String, String>) items.get(position))
					.get("typeName"));
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					lv.removeAllViewsInLayout();
					majors = new ArrayList<Major>();
					roles = new ArrayList<Role>();
					ConnectionAdapter cAdapter;
					if (((Map<String, String>) items.get(position)).get(
							"typeName").equals("专业")) {
						cAdapter = new ConnectionAdapter(majors, context, lv,
								ll);
						lv.setAdapter(cAdapter);
						getMajorMess(cAdapter, "major");
					} else {
						cAdapter = new ConnectionAdapter(roles, context, lv, ll);
						lv.setAdapter(cAdapter);
						getRoleMess(cAdapter, "role");
					}
					addTextView(((Map<String, String>) items.get(position))
							.get("typeName"), ll);
				}
			});
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

	/**
	 * 得到major信息
	 */
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

	/**
	 * 得到Role信息
	 * 
	 * @param sAdapter
	 * @param type
	 */
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

	/**
	 * 得到clzss信息
	 * 
	 * @param sAdapter
	 * @param majorId
	 */
	private void getClassMess(final BaseAdapter sAdapter, int majorId) {
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
				sAdapter.notifyDataSetChanged();
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
			}
		});
		post.setParams("dataType", "majortoclass");
		post.setParams("majorId", majorId + "");
		SchoolApplication.getInstance().getRequestQueue().add(post);
	}

	/**
	 * 得到学生信息
	 * 
	 * @param sAdapter
	 * @param classNo
	 */
	private void getStudentMess(final BaseAdapter sAdapter, int classNo) {
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
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
			}
		});
		post.setParams("dataType", "classtostudent");
		post.setParams("classNo", classNo + "");
		SchoolApplication.getInstance().getRequestQueue().add(post);
	}

	/**
	 * 得到role小的学生
	 * 
	 * @param sAdapter
	 * @param role
	 */
	private void getRoleToStuMess(final BaseAdapter sAdapter, String role) {
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
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
			}
		});
		post.setParams("dataType", "roletostudent");
		post.setParams("role", role);
		SchoolApplication.getInstance().getRequestQueue().add(post);
	}

	/**
	 * 添加头部标签样式
	 * 
	 * @param text
	 * @param ll
	 */
	private void addTextView(String text, LinearLayout ll) {
		TextView t = (TextView) LayoutInflater.from(context).inflate(
				R.layout.text_view, null);
		t.setText(text);
		ll.addView(t);
	}
}
