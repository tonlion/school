package com.example.school;

import java.util.ArrayList;

import com.example.adapter.NoticeListAdapter;
import com.example.application.SchoolApplication;
import com.example.data.DataManager;
import com.example.data.HTTPTools;
import com.example.entity.Notice;
import com.example.entity.Topic;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class TopicItemDetailsActivity extends Activity {
	private ListView item;
	private ArrayList<Notice> notices;
	private ImageView image;
	private TextView title;
	private TextView content;

	//此方法可能出错，原因：加载首部出错
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_view);
		item = (ListView) findViewById(R.id.listview);
		Topic topic = initListView();
		notices = new ArrayList<Notice>();
		NoticeListAdapter adapter = new NoticeListAdapter(notices, this);
		DataManager manager = new DataManager(adapter, notices, this, null);
		SchoolApplication.getInstance().getRequestQueue()
				.add(manager.getTopicItemData(topic.getSubject_id()));
		item.setAdapter(adapter);
	}

	private Topic initListView() {
		View v = LayoutInflater.from(this).inflate(
				R.layout.activity_topic_list, null);
		image = (ImageView) v.findViewById(R.id.topic_details_image);
		title = (TextView) v.findViewById(R.id.topic_details_title);
		content = (TextView) v.findViewById(R.id.topic_details_content);
		Intent intent = getIntent();
		Topic topic = (Topic) intent.getSerializableExtra("topicSe");
		image.setImageResource(R.drawable.cc_default_news_img);
		title.setText(topic.getSubject_title());
		content.setText(topic.getSubject_detail());
		MyAsyncTask async = new MyAsyncTask();
		String imageAddress = topic.getSubject_url();
		String url = "";
		if (imageAddress.length() > 0) {
			url = DataManager.ROOT_URL + imageAddress;
		}
		async.execute(url);
		item.addHeaderView(v);
		return topic;
	}

	private class MyAsyncTask extends AsyncTask<String, Void, Bitmap> {

		@Override
		protected Bitmap doInBackground(String... params) {
			HTTPTools tools = new HTTPTools();
			return tools.loadBitmap(params[0]);
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			if (result != null) {
				image.setImageBitmap(result);
			}
		}

	}
}
