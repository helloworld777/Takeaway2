package com.lu.takeaway.view.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.lu.takeaway.R;
import com.lu.takeaway.view.widget.TimerTextView;

import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.exception.BmobException;
import cn.bmob.sms.listener.RequestSMSCodeListener;

public class RegisterPhoneNumberFragment extends BaseFragment implements View.OnClickListener{
	private EditText etPhone,etSMSCode;
	private TimerTextView tvSendSmsCode;
	public String getPhone(){
		return etPhone.getText().toString().trim();
	}
	public String getSmsCode(){
		return etSMSCode.getText().toString().trim();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.fragment_register_phone_number, container,false);
		etPhone=(EditText) view.findViewById(R.id.etPhone);
		tvSendSmsCode=findViewById(view,R.id.tvSendSmsCode);
		etSMSCode=findViewById(view,R.id.etSMSCode);
		tvSendSmsCode.setOnClickListener(this);
		return view;
	}
	public void onClick(View view){
		switch (view.getId()){
			case R.id.tvSendSmsCode:
				etSMSCode.setVisibility(View.VISIBLE);
				tvSendSmsCode.startTimer();
				sendSmsCode();
				break;
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		tvSendSmsCode.closeTimer();
	}

	private void sendSmsCode() {
		BmobSMS.requestSMSCode(getActivity(), etPhone.getText().toString(), "模板名称",new RequestSMSCodeListener() {
			@Override
			public void done(Integer smsId,BmobException ex) {
				if(ex==null){//验证码发送成功
					Log.i("bmob", "短信id："+smsId);//用于查询本次短信发送详情
				}else{
					ex.printStackTrace();
				}
			}
		});
//		BmobSMS.verifySmsCode(getActivity(),"11位手机号码", "验证码", new VerifySMSCodeListener() {
//
//			@Override
//			public void done(BmobException ex) {
//				// TODO Auto-generated method stub
//				if(ex==null){//短信验证码已验证成功
//					Log.i("bmob", "验证通过");
//				}else{
//					Log.i("bmob", "验证失败：code ="+ex.getErrorCode()+",msg = "+ex.getLocalizedMessage());
//				}
//			}
//		});
	}
}	
