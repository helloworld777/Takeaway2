package com.lu.takeaway.view.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lu.takeaway.R;
import com.lu.takeaway.view.fragment.RegisterPhoneNumberFragment;
import com.lu.takeaway.view.fragment.RegisterUserPwdFragment;

import com.lu.takeaway.bean.UserBean;
import util.Constants;
import util.SaveDataUtil;

@ContentView(R.layout.activity_register)
public class RegisterActivity extends BaseFragmentActivity implements Constants {

	@ViewInject(R.id.tvTitle)
	private TextView tvTitle;
	
	@ViewInject(R.id.btnNext)
	private Button btnNext;
	@ViewInject(R.id.btnCancel)
	private Button btnCancel;
	
	private Fragment fragment;
	
	
	private FragmentManager fragmentManager;
	private FragmentTransaction transaction;
	
	private String username;
	private String pwd,phoneNumber;
	@OnClick({ R.id.btnCancel, R.id.btnNext })
	public void viewClick(View view) {
		switch (view.getId()) {
		case R.id.btnCancel:
			if(fragment instanceof RegisterUserPwdFragment){
				finish();
			}else{
				bindData();
				btnNext.setText(getString(R.string.next));
				btnCancel.setText(getString(R.string.cancel));
			}
			
			break;
		case R.id.btnNext:
			
			if(fragment instanceof RegisterUserPwdFragment){
				username=((RegisterUserPwdFragment)fragment).getUsername();
				pwd=((RegisterUserPwdFragment)fragment).getPwd();
				replaceFragment();
				btnNext.setText(getString(R.string.finish));
				btnCancel.setText(getString(R.string.pre));
			}else{
				phoneNumber=((RegisterPhoneNumberFragment)fragment).getPhone();
				Intent intent=new Intent();
//				intent.putExtra("username", username);
//				intent.putExtra("pwd", pwd);
//				intent.putExtra("phoneNumber", phoneNumber);
				
				UserBean userBean=new UserBean(username, pwd, phoneNumber);
				SaveDataUtil.setString(this, USERNAME, username);
				SaveDataUtil.setString(this, PWD, pwd);
				SaveDataUtil.setString(this, PHONE_NUMBER, phoneNumber);
				
				intent.putExtra("userbean", userBean);
				setResult(0, intent);
				finish();
			}
			
			break;
		default:
			break;
		}
	}

	@Override
	protected void bindData() {
		tvTitle.setText(getString(R.string.register));
		fragmentManager=getSupportFragmentManager();
		transaction=fragmentManager.beginTransaction();
		fragment=new RegisterUserPwdFragment();
		transaction.replace(R.id.mainFrame, fragment);
		transaction.commit();
	}
	
	private void replaceFragment(){
		transaction=fragmentManager.beginTransaction();
		fragment=new RegisterPhoneNumberFragment();
		transaction.addToBackStack(null);
		transaction.replace(R.id.mainFrame, fragment);
		transaction.commit();
	}
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		btnNext.setText(getString(R.string.next));
		btnCancel.setText(getString(R.string.cancel));
	}
}
