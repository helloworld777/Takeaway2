package com.lu.takeaway.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.lu.takeaway.R;

public class RegisterPhoneNumberFragment extends Fragment {
	private EditText etPhone;
	
	public String getPhone(){
		return etPhone.getText().toString().trim();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.fragment_register_phone_number, container,false);
		etPhone=(EditText) view.findViewById(R.id.etPhone);
		return view;
	}
}	
