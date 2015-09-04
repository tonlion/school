package com.example.application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.data.SchoolImageCache;
import com.example.entity.Student;

import android.app.Application;

public class SchoolApplication extends Application {

	private RequestQueue queue;
	private static SchoolApplication school;
	private SchoolImageCache cache;
	private Student student;

	@Override
	public void onCreate() {
		super.onCreate();
		school = this;
		queue = Volley.newRequestQueue(this);
		cache = new SchoolImageCache();
	}

	public static SchoolApplication getInstance() {
		return school;
	}

	public RequestQueue getRequestQueue() {
		return this.queue;
	}

	public SchoolImageCache getImageCache() {
		return this.cache;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
}
