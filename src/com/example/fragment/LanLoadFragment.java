package com.example.fragment;

import android.support.v4.app.Fragment;

public abstract class LanLoadFragment extends Fragment {

	protected boolean isVisible;// ���ڱ�����Ǯ��fragment�Ƿ�ɼ�

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// TODO Auto-generated method stub
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser) {
			isVisible = true;
			loadData();
		} else {
			isVisible = false;
		}
	}

	public abstract void loadData();
}
