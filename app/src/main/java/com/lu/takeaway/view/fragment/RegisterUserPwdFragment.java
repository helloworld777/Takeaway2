package com.lu.takeaway.view.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.lu.takeaway.R;

import com.lu.takeaway.util.MD5Util;

public class RegisterUserPwdFragment extends BaseFragment{
	
	private EditText etUsername,etPassword,etPassword2;
	public String getUsername(){
		return etUsername.getText().toString().trim();
	}
	public String getPwd(){
		return MD5Util.MD5(etPassword.getText().toString().trim());
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment_register_user_pwd, container,false);
		etUsername=(EditText) view.findViewById(R.id.etUsername);
		etPassword=(EditText) view.findViewById(R.id.etPassword);
		return view;
	}
	
}	
