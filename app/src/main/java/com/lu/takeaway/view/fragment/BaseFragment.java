package com.lu.takeaway.view.fragment;

import android.support.v4.app.Fragment;
import android.view.View;

import com.lu.takeaway.view.app.DingDanApplication;

import util.Debug;
import util.TopNoticeDialog;

public abstract class BaseFragment extends Fragment {
	protected DingDanApplication mContext;
	public abstract void initData();
	public BaseFragment(){
		mContext=DingDanApplication.getDefault();
		Debug.d(this,"BaseFragment()........................");
	}
	public abstract void currentSelected();
	protected void showToast(String msg){
		TopNoticeDialog.showToast(getActivity(),msg);
	}
	protected <T extends View> T  findViewById(View view,int res){
		return (T)view.findViewById(res);
	}
}
