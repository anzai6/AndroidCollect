package com.android.collect.library.ui.main.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class ViewPagerFragmentAdapter extends FragmentPagerAdapter {

	private List<Fragment> fragments;

	public ViewPagerFragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
		super(fm);
		this.fragments = fragments;
	}

	@Override
	public Fragment getItem(int arg0) {
		return (fragments == null || fragments.size() == 0) ? null
                : fragments.get(arg0);
	}

	@Override
	public int getCount() {
		return fragments == null ? 0 : fragments.size();
	}

}
