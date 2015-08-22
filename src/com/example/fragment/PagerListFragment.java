package com.example.fragment;

import java.util.ArrayList;
import java.util.List;

import com.example.adapter.NoticeListAdapter;
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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle b = getArguments();
		int a = b.getInt("id");
		initData1();
		notices = getType(a);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.list_view, null);
		ListView lView = (ListView) view.findViewById(R.id.listview);

		NoticeListAdapter adapter = new NoticeListAdapter(notices,
				getActivity());
		lView.setAdapter(adapter);
		return view;
	}

	private List<Notice> getType(int checkId) {
		List<Notice> n = new ArrayList<Notice>();
		for (int i = 0; i < notices.size(); i++) {
			if (notices.get(i).getNoticeType() == checkId) {
				n.add(notices.get(i));
			}
		}
		return n;

	}

	public void initData1() {
		notices = new ArrayList<Notice>();
		notices.add(new Notice(R.drawable.cc_contact_signle, "你好",
				"2015-01-01", 1));
		notices.add(new Notice(R.drawable.cc_school_sxyl, "你好", "2015-01-01", 2));
		notices.add(new Notice(R.drawable.cc_contact_muitl, "你好", "2015-01-01",
				3));
		notices.add(new Notice(R.drawable.cc_school_sxyl, "你好", "2015-01-01", 4));
		notices.add(new Notice(R.drawable.cc_contact_signle, "你好",
				"2015-01-01", 5));
		notices.add(new Notice(R.drawable.cc_school_sxyl, "你好", "2015-01-01", 1));
		notices.add(new Notice(R.drawable.cc_school_tool, "你好", "2015-01-01", 0));
		notices.add(new Notice(R.drawable.cc_school_sxyl, "你好", "2015-01-01", 2));
		notices.add(new Notice(R.drawable.cc_school_sxyl, "你好", "2015-01-01", 3));
		notices.add(new Notice(R.drawable.cc_school_sxyl, "你好", "2015-01-01", 4));
		notices.add(new Notice(R.drawable.cc_school_sxyl, "你好", "2015-01-01", 5));
	}
}
