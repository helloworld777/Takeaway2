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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lu.takeaway.R;
import com.lu.takeaway.bean.UserBean;
import com.lu.takeaway.view.activity.LoginActivity;
import com.lu.takeaway.view.activity.OrderActivity;
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
	private RelativeLayout rlOrder,rlBook,rlAbout;
	public UserFragment(){
//		EventBus.getDefault().register(this);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		isLogin= SaveDataUtil.getBoolean(getActivity(), Constants.IS_LOGIN);
		View view=inflater.inflate(R.layout.fragment_user, container,false);
		initWidget(view);
		return view;
	}

	private void initWidget(View view) {
		llLogin=(LinearLayout) view.findViewById(R.id.llLogin);
		tvLogin=(TextView) view.findViewById(R.id.tvLogin);
		tvLogin.setOnClickListener(this);
//		btnOrder=(Button) view.findViewById(R.id.btnOrder);
//		btnOrder.setOnClickListener(this);
		rlOrder=findViewById(view,R.id.rlOrder);
		rlOrder.setOnClickListener(this);
		rlBook=findViewById(view,R.id.rlBook);
		rlBook.setOnClickListener(this);
		rlAbout=findViewById(view,R.id.rlAbout);
		rlAbout.setOnClickListener(this);


		isLogin=mContext.isLogin();
		if(isLogin){
			userBean= DingDanApplication.getDefault().getCurrenUserBean();
			tvLogin.setText(userBean.lusername);
			isLogin=true;
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tvLogin:
				if(!isLogin){
					startActivityForResultTransition(new Intent(getActivity(),LoginActivity.class), REQUESTCODE_MAIN_LOGIN);
				}
				break;
			case R.id.rlAbout:
				break;
			case R.id.rlBook:
				break;
			case R.id.rlOrder:
				if(!isLogin){
					startActivityForResultTransition(LoginActivity.class, REQUESTCODE_MAIN_LOGIN);
				}else{
					startActivityTransition(OrderActivity.class);
				}
				break;
		default:
			break;
		}
	}
	public void update(UserBean userBean){
		tvLogin.setText(userBean.lusername);
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
