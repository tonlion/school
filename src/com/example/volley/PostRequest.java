package com.example.volley;

import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;

public class PostRequest extends StringRequest {

	private Map<String, String> posts;

	public PostRequest(String url, Listener<String> listener,
			ErrorListener errorListener) {
		super(Request.Method.POST, url, listener, errorListener);
		posts = new HashMap<String, String>();
	}

	@Override
	protected Map<String, String> getParams() throws AuthFailureError {
		return posts;
	}

	public void setParams(String key, String value) {
		posts.put(key, value);
	}
}
