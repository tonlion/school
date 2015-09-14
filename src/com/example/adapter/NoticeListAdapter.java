package com.example.adapter;

import java.util.List;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.example.application.SchoolApplication;
import com.example.data.DataManager;
import com.example.entity.Notice;
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

public class NoticeListAdapter extends BaseAdapter {

	private List<Notice> notices;
	private Context mContext;
	private ImageLoader loader;

	public NoticeListAdapter(List<Notice> notices, Context mContext) {
		super();
		this.notices = notices;
		this.mContext = mContext;
		this.loader = new ImageLoader(SchoolApplication.getInstance()
				.getRequestQueue(), SchoolApplication.getInstance()
				.getImageCache());
	}

	@Override
	public int getCount() {
		return this.notices == null ? 0 : this.notices.size();
	}

	@Override
	public Object getItem(int position) {
		return this.notices == null ? null : this.notices.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		NoticeHolder holder;
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			convertView = inflater.inflate(R.layout.layout_notice_list, null);
			holder = new NoticeHolder();
			holder.image = (ImageView) convertView
					.findViewById(R.id.notice_img);
			holder.title = (TextView) convertView
					.findViewById(R.id.notice_title);
			holder.createTime = (TextView) convertView
					.findViewById(R.id.notice_create_time);
			convertView.setTag(holder);
		} else {
			holder = (NoticeHolder) convertView.getTag();
		}
		// 绑定数据
		// 利用imageloader加载图片
		String imageAddress = notices.get(position).getImg();

		String url = "";
		if (imageAddress.length() > 0) {
			url = DataManager.ROOT_URL
					+ imageAddress.substring(31, imageAddress.length());
		}
		ImageListener listener = ImageLoader.getImageListener(holder.image,
				R.drawable.cc_default_news_img,
				R.drawable.cc_default_news_img_fail);
		loader.get(url, listener);
		// ImageLoaderUtils.display(url, holder.image);
		holder.title.setText(notices.get(position).getTitle());
		holder.createTime.setText(notices.get(position).getTime());
		// 为添加的数据设置一个padding
		convertView.setPadding(10, 0, 10, 0);
		// 为每条动态设置点击事件
		convertView.setOnClickListener(new OnClickListener() {
			// 暂时默认的跳转传值方式
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext,
						NoticeDetailsActivity.class);
				intent.putExtra("noticeSe", notices.get(position));
				mContext.startActivity(intent);
			}
		});
		convertView.setBackgroundResource(R.drawable.backgroud);
		((Activity) mContext).overridePendingTransition(R.anim.rignt_in,
				R.anim.left_out);
		return convertView;
	}

	private class NoticeHolder {
		private ImageView image;
		private TextView title;
		private TextView createTime;
	}

}
