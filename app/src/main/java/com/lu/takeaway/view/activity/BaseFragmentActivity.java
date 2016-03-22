package com.lu.takeaway.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;

import com.lidroid.xutils.ViewUtils;
import com.lu.takeaway.R;

public abstract class BaseFragmentActivity extends FragmentActivity {
	protected final int  requestCode=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		ViewUtils.inject(this);
		bindData();
	}
	
	protected abstract void viewClick(View view);
	protected abstract void bindData();
	protected void startActivityTransition(Class<?> class1){
		startActivity(new Intent(this, class1));
		overridePendingTransition(R.anim.out_to_left, R.anim.in_from_right);
	}
	protected void startActivityTransition(Intent intent){
		startActivity(intent);
		overridePendingTransition(R.anim.out_to_left, R.anim.in_from_right);
	}
	protected void startActivityForResultTransition(Class<?> class1) {
		startActivityForResult(new Intent(this,class1), requestCode);
	}
	protected void startActivityForResultTransition(Intent intent) {
		startActivityForResult(intent, requestCode);
	}
}
