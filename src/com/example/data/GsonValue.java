package com.example.data;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.example.application.SchoolApplication;
import com.example.entity.Clzss;
import com.example.entity.Contection;
import com.example.entity.Major;
import com.example.entity.Role;
import com.example.entity.Student;
import com.example.volley.PostRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GsonValue {

	private List<Contection> contections;
	private final static String NewsPushServlet = DataManager.ROOT_URL
			+ "NewsPushServlet";

	public GsonValue() {
		super();
		contections = new ArrayList<Contection>();
	}

	/**
	 * 得到专业
	 */
	public void getMajorMess() {

		PostRequest post = new PostRequest(NewsPushServlet,
				new Listener<String>() {

					@Override
					public void onResponse(String arg0) {
						Gson gson = new Gson();
						List<Major> list = gson.fromJson(arg0,
								new TypeToken<List<Major>>() {
								}.getType());
						for (Major c : list) {
							// contections.add(new Contection(c.getId(), c
							// .getName(), "major"));
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
					}
				});
		post.setParams("dataType", "major");
		SchoolApplication.getInstance().getRequestQueue().add(post);
	}

	/**
	 * 得到专业下的班级
	 * 
	 * @param majorId
	 */
	public void getClzssMess(int majorId) {
		PostRequest post = new PostRequest(NewsPushServlet,
				new Listener<String>() {

					@Override
					public void onResponse(String arg0) {
						Gson gson = new Gson();
						List<Clzss> list = gson.fromJson(arg0,
								new TypeToken<List<Clzss>>() {
								}.getType());
						for (Clzss clzss : list) {
							// contections.add(new Contection(clzss.getId(),
							// clzss
							// .getClassName(), "class"));
						}
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
	 * 得到班级下面的学生
	 * 
	 * @param classNo
	 */
	public void getStudentMess(int classNo) {
		PostRequest post = new PostRequest(NewsPushServlet,
				new Listener<String>() {

					@Override
					public void onResponse(String arg0) {
						Gson gson = new Gson();
						List<Student> list = gson.fromJson(arg0,
								new TypeToken<List<Student>>() {
								}.getType());
						for (Student s : list) {
							// contections.add(new Contection(s.getClassNO(), s
							// .getClassName(), "student"));
						}
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
					}
				});
		post.setParams("dataType", "classtostudent");
		post.setParams("majorId", classNo + "");
		SchoolApplication.getInstance().getRequestQueue().add(post);
	}

	/**
	 * 得到角色
	 */
	public void getRoleMess() {
		PostRequest post = new PostRequest(NewsPushServlet,
				new Listener<String>() {

					@Override
					public void onResponse(String arg0) {
						Gson gson = new Gson();
						List<Role> list = gson.fromJson(arg0,
								new TypeToken<List<Role>>() {
								}.getType());
						for (Role s : list) {
							// contections.add(new Contection(1, s.getRole(),
							// "role"));
						}
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
					}
				});
		post.setParams("dataType", "role");
		SchoolApplication.getInstance().getRequestQueue().add(post);
	}

	/**
	 * 得到角色下的人
	 * 
	 * @param role
	 */
	public void getRoleToStuMess(String role) {
		PostRequest post = new PostRequest(NewsPushServlet,
				new Listener<String>() {

					@Override
					public void onResponse(String arg0) {
						Gson gson = new Gson();
						List<Student> list = gson.fromJson(arg0,
								new TypeToken<List<Student>>() {
								}.getType());
						for (Student s : list) {

							contections.add(new Contection());
						}
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
}
