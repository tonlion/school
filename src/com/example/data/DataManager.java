package com.example.data;

import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Response.Listener;
import com.example.adapter.NoticeListAdapter;
import com.example.adapter.TopicListAdapter;
import com.example.async.ThreadManager;
import com.example.dao.NoticeDao;
import com.example.dao.TopicDao;
import com.example.entity.Notice;
import com.example.entity.Topic;
import com.example.volley.PostRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class DataManager {

	public final static String IP_URL = "http://172.23.223.1/";// IP地址
	public final static String ROOT_URL = IP_URL + "SchoolLife/";// 根目录
	public final static String NEWSREQUEST_URL = ROOT_URL// 数据访问目录
			+ "NewsRequestServlet";
	private List<Topic> topics;
	private List<Notice> notices;
	private Context context;
	private TopicListAdapter tAdapter;
	private NoticeListAdapter nAdapter;
	private PullToRefreshListView listView;

	public DataManager() {
		super();

	}

	/**
	 * 
	 * @param tAdapter
	 *            主题的adapter
	 * @param topics
	 *            主题集合
	 * @param context
	 *            上下文
	 * @param listView
	 *            当出现下拉刷新时使用，即listview的类型为PullToRefreshListView
	 */
	public DataManager(TopicListAdapter tAdapter, List<Topic> topics,
			Context context, PullToRefreshListView listView) {
		super();
		this.tAdapter = tAdapter;
		this.topics = topics;
		this.context = context;
		this.listView = listView;
	}

	/**
	 * 
	 * @param tAdapter
	 *            动态的adapter
	 * @param topics
	 *            动态集合
	 * @param context
	 *            上下文
	 * @param listView
	 *            当出现下拉刷新时使用，即listview的类型为PullToRefreshListView
	 */
	public DataManager(NoticeListAdapter nAdapter, List<Notice> notices,
			Context context, PullToRefreshListView listView) {
		super();
		this.nAdapter = nAdapter;
		this.notices = notices;
		this.context = context;
		this.listView = listView;
	}

	public PostRequest getTopicData(int pageCount) {
		PostRequest post = new PostRequest(NEWSREQUEST_URL,
				new Listener<String>() {
					@Override
					public void onResponse(String arg0) {
						if (listView != null)
							listView.onRefreshComplete();
						if (arg0 != null) {
							Gson gson = new Gson();
							List<Topic> list = gson.fromJson(arg0,
									new TypeToken<List<Topic>>() {
									}.getType());
							topics.clear();
							topics.addAll(list);
							// 缓存数据到本地
							ThreadManager.getInetance().execute(new Runnable() {

								@Override
								public void run() {
									TopicDao tDao = new TopicDao(context);
									tDao.addMoreTopic(topics);
								}
							});
							tAdapter.notifyDataSetChanged();
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						if (listView != null)
							listView.onRefreshComplete();
						Toast.makeText(context, "内容加载出错", Toast.LENGTH_LONG)
								.show();
					}
				});
		post.setParams("dataType", "subject");
		post.setParams("pageCount", pageCount + "");
		return post;
	}

	// true代表显示头条
	// false代表不显示头条
	// 数据显示出问题是应该找一下这里
	public PostRequest getNoticeData(final boolean isTop) {
		PostRequest post = new PostRequest(NEWSREQUEST_URL,
				new Listener<String>() {

					@Override
					public void onResponse(String arg0) {
						if (listView != null)
							listView.onRefreshComplete();
						if (arg0 != null) {
							Gson gson = new Gson();
							List<Notice> list = gson.fromJson(arg0,
									new TypeToken<List<Notice>>() {
									}.getType());
							if (isTop)
								notices.clear();
							notices.addAll(list);
							// 数据缓存到本地
							ThreadManager.getInetance().execute(new Runnable() {

								@Override
								public void run() {
									NoticeDao nDao = new NoticeDao(context);
									nDao.addMoreNotice(notices);
								}
							});
							nAdapter.notifyDataSetChanged();
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						if (listView != null)
							listView.onRefreshComplete();
						Toast.makeText(context, "内容加载出错", Toast.LENGTH_LONG)
								.show();
					}
				});
		post.setParams("dataType", "news");
		if (isTop)
			post.setParams("isTop", "1");
		return post;
	}

	// 得到的大类的所有数据
	public PostRequest getTopicItemData(int subjectId) {
		PostRequest post = new PostRequest(NEWSREQUEST_URL,
				new Listener<String>() {
					@Override
					public void onResponse(String arg0) {
						if (listView != null)
							listView.onRefreshComplete();
						if (arg0 != null) {
							Gson gson = new Gson();
							List<Notice> list = gson.fromJson(arg0,
									new TypeToken<List<Notice>>() {
									}.getType());
							notices.clear();
							notices.addAll(list);
							nAdapter.notifyDataSetChanged();
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						if (listView != null)
							listView.onRefreshComplete();
						Toast.makeText(context, "内容加载出错", Toast.LENGTH_LONG)
								.show();
					}
				});
		post.setParams("dataType", "news");
		post.setParams("pageTag", subjectId + "");
		post.setParams("pageTagFlag", "1");
		return post;
	}

	// 只为加载相同数据类的不同小类时调用
	public PostRequest getNoticeTypeData(int category) {
		if (listView != null)
			listView.setRefreshing(); // 设置进入页面时自动刷新
		PostRequest post = new PostRequest(NEWSREQUEST_URL,
				new Listener<String>() {
					@Override
					public void onResponse(String arg0) {
						// 之前加载太快，所以加上handler
						if (listView != null) {
							new Handler().postDelayed(new Runnable() {
								public void run() {
									listView.onRefreshComplete();
								}
							}, 1000);

						}
						if (arg0 != null) {
							Gson gson = new Gson();
							List<Notice> list = gson.fromJson(arg0,
									new TypeToken<List<Notice>>() {
									}.getType());
							notices.clear();
							notices.addAll(list);
							nAdapter.notifyDataSetChanged();
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						if (listView != null)
							listView.onRefreshComplete();
						Toast.makeText(context, "内容加载出错", Toast.LENGTH_LONG)
								.show();
					}
				});
		post.setParams("dataType", "news");
		post.setParams("pageTag", category + "");
		post.setParams("pageTagFlag", "0");
		post.setParams("pageNum", "0");// 默认设置设置为0了，以后建议传值修改
		return post;
	}

	/**
	 * 只是为下拉添加更多数据是调用
	 * 
	 * @return
	 */
	public PostRequest getClearNoticeData() {
		PostRequest post = new PostRequest(NEWSREQUEST_URL,
				new Listener<String>() {

					@Override
					public void onResponse(String arg0) {
						listView.onRefreshComplete();
						if (arg0 != null) {
							Gson gson = new Gson();
							List<Notice> list = gson.fromJson(arg0,
									new TypeToken<List<Notice>>() {
									}.getType());
							notices.addAll(list);
							nAdapter.notifyDataSetChanged();
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError arg0) {
						listView.onRefreshComplete();
						Toast.makeText(context, "内容加载出错", Toast.LENGTH_LONG)
								.show();
					}
				});
		post.setParams("dataType", "news");
		return post;
	}

}
