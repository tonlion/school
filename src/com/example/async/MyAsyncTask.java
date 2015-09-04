package com.example.async;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.example.data.HTTPTools;
import com.example.school.R;

public class MyAsyncTask extends AsyncTask<String, Void, Bitmap> {

	private ImageView image;

	public MyAsyncTask(ImageView image) {
		super();
		this.image = image;
	}

	@Override
	protected Bitmap doInBackground(String... params) {
		HTTPTools tools = new HTTPTools();
		return tools.loadBitmap(params[0]);
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		if (result != null) {
			image.setImageBitmap(result);
		} else {
			image.setImageResource(R.drawable.cc_user);
		}
		super.onPostExecute(result);
	}

}