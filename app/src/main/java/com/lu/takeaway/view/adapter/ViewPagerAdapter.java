package com.lu.takeaway.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

//	private static final String TAG = "ViewPagerAdapter";
	private Fragment[] list;

	public ViewPagerAdapter(FragmentManager fm, Fragment[] list) {
		super(fm);
		this.list = list;
	}

	public Fragment getItem(int arg0) {
		return list[arg0];
	}

	public int getCount() {
		return list.length;
	}

}
