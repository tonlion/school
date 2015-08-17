package com.example.adapter;

import java.util.List;

import com.example.entity.Topic;
import com.example.school.NoticeDetailsActivity;
import com.example.school.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TopicListAdapter extends BaseAdapter {

	private List<Topic> topics;
	private Context context;;

	public TopicListAdapter(List<Topic> topics, Context context) {
		super();
		this.topics = topics;
		this.context = context;
	}

	@Override
	public int getCount() {
		return this.topics == null ? 0 : this.topics.size();
	}

	@Override
	public Object getItem(int position) {
		return this.topics == null ? null : this.topics.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TopicHoleder holeder;
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.layout_topic_item, null);
			holeder = new TopicHoleder();
			holeder.pic = (ImageView) convertView
					.findViewById(R.id.topic_item_pic);
			holeder.title = (TextView) convertView
					.findViewById(R.id.topic_item_title);
			holeder.content = (TextView) convertView
					.findViewById(R.id.topic_item_con);
			convertView.setTag(holeder);
		} else {
			holeder = (TopicHoleder) convertView.getTag();
		}
		holeder.pic.setImageResource(topics.get(position).getImage());
		holeder.title.setText(topics.get(position).getTitle());
		holeder.content.setText(topics.get(position).getContent());
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				context.startActivity(new Intent(context,
						NoticeDetailsActivity.class));
			}
		});
		convertView.setBackgroundResource(R.drawable.backgroud);
		((Activity) context).overridePendingTransition(R.anim.rignt_in,
				R.anim.left_out);
		return convertView;
	}

	private class TopicHoleder {
		private ImageView pic;
		private TextView title;
		private TextView content;
	}

}
