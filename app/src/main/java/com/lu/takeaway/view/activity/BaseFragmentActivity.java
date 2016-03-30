package com.lu.takeaway.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lu.takeaway.R;
import com.lu.takeaway.view.widget.DialogLoading;

import util.Debug;
import util.JActivityManager;
import util.TopNoticeDialog;

public abstract class BaseFragmentActivity extends FragmentActivity {
	protected final int  requestCode=0;
	protected Activity mActivity;
	protected DialogLoading dialogLoading;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		ViewUtils.inject(this);
		mActivity=this;
		dialogLoading=new DialogLoading(this);
		JActivityManager.getInstance().pushActivity(this);
		bindData();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		JActivityManager.getInstance().popActivity(this);
	}

	protected void showLoadingDialog(){
		if(!dialogLoading.isShowing()){
			dialogLoading.show();;
		}
	}
	protected void closeLoadingDialog(){
		if(dialogLoading.isShowing()){
			dialogLoading.dismiss();
		}
	}
	protected void viewClick(View view){};
	protected abstract void bindData();
	protected void startActivityTransition(Class<?> class1){
		startActivity(new Intent(this, class1));
//		overridePendingTransition(R.anim.out_to_left, R.anim.in_from_right);
	}
	protected void startActivityTransition(Intent intent){
		startActivity(intent);
//		overridePendingTransition(R.anim.out_to_left, R.anim.in_from_right);
	}
	protected void startActivityForResultTransition(Class<?> class1) {
		startActivityForResult(new Intent(this,class1), requestCode);
	}
	protected void startActivityForResultTransition(Intent intent) {
		startActivityForResult(intent, requestCode);
	}
	protected void showToast(String msg){
		TopNoticeDialog.showToast(this,msg);
	}
	protected TextView setListViewEmptyView(ListView listview, String emptyTips){
		TextView emptyView =new TextView(this);
		emptyView .setGravity(Gravity.CENTER);
		ViewGroup.LayoutParams layoutParams=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
		emptyView .setLayoutParams(layoutParams);
		if(null==emptyTips){
			emptyTips=String.valueOf(getText(R.string.empty_data));
		}
		emptyView.setTextColor(Color.BLACK);
		emptyView .setText(emptyTips);
		emptyView .setVisibility(View.GONE);
		((ViewGroup)listview.getParent()).addView(emptyView);
		listview.setEmptyView(emptyView );
		return emptyView ;
	}
	protected void openSoftInput(View view) {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
	}

	protected void closeSoftInput() {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0); //强制隐藏键盘
	}
	protected void d(String msg){
		Debug.d(this,"......................"+msg);
	}
}
