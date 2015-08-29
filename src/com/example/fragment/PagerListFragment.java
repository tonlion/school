package com.example.fragment;

import java.util.ArrayList;
import java.util.List;

import com.example.adapter.NoticeListAdapter;
import com.example.application.SchoolApplication;
import com.example.data.DataManager;
import com.example.entity.Notice;
import com.example.school.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class PagerListFragment extends Fragment {

	private List<Notice> notices;
	private int itemId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle b = getArguments();
		itemId = b.getInt("id");
		notices = new ArrayList<Notice>();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.list_view, null);
		ListView lView = (ListView) view.findViewById(R.id.listview);
		NoticeListAdapter adapter = new NoticeListAdapter(notices,
				getActivity());
		DataManager manager = new DataManager(adapter, notices, getActivity(),
				null);
		SchoolApplication.getInstance().getRequestQueue()
				.add(manager.getNoticeTypeData(itemId));
		lView.setAdapter(adapter);
		return view;
	}
}
