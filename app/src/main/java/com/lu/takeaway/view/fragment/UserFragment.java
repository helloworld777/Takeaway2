package com.lu.takeaway.view.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lu.takeaway.R;
import com.lu.takeaway.bean.UserBean;
import com.lu.takeaway.view.activity.LoginActivity;
import com.lu.takeaway.view.app.DingDanApplication;

import util.Constants;
import util.SaveDataUtil;

@SuppressLint("NewApi") 
public class UserFragment extends BaseFragment implements OnClickListener, Constants {
	private LinearLayout llLogin;
	private TextView tvLogin;
	private boolean isLogin=false;
	private UserBean userBean;
	private Button btnOrder;
	public UserFragment(){
//		EventBus.getDefault().register(this);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		isLogin= SaveDataUtil.getBoolean(getActivity(), Constants.IS_LOGIN);
		View view=inflater.inflate(R.layout.fragment_user, container,false);
		llLogin=(LinearLayout) view.findViewById(R.id.llLogin);
		tvLogin=(TextView) view.findViewById(R.id.tvLogin);
		tvLogin.setOnClickListener(this);
//		btnOrder=(Button) view.findViewById(R.id.btnOrder);
//		btnOrder.setOnClickListener(this);
		
		userBean= DingDanApplication.getDefault().getCurrenUserBean();
		if(userBean!=null){
			tvLogin.setText(userBean.getUsername());
			isLogin=true;
		}
		return view;
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tvLogin:
			if(!isLogin){
				getActivity().startActivityForResult(new Intent(getActivity(),LoginActivity.class), REQUESTCODE_MAIN_LOGIN);
			}
			break;
//		case R.id.btnOrder:
//			if(!isLogin){
//				getActivity().startActivityForResult(new Intent(getActivity(),LoginActivity.class), REQUESTCODE_MAIN_LOGIN);
//			}else{
//				startActivity(new Intent(getActivity(),OrderActivity.class));
//			}
//
//			break;
		default:
			break;
		}
	}
	public void update(UserBean userBean){
		tvLogin.setText(userBean.getUsername());
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
//		EventBus.getDefault().unregister(this);
	}
	@Override
	public void initData() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void currentSelected() {
		// TODO Auto-generated method stub
		
	}
}
