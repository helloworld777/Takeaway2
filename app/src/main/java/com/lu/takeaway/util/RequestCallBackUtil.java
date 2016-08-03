package com.lu.takeaway.util;

import android.app.Activity;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lu.takeaway.R;

public class RequestCallBackUtil extends RequestCallBack<String>{
	private Activity activity;
	private RequestSuccess success;
	public RequestCallBackUtil(Activity activity,RequestSuccess success){
		this.activity=activity;
		this.success=success;
	}
	@Override
	public void onFailure(HttpException arg0, String arg1) {
		// TODO Auto-generated method stub
		DialogUtil.showToast(activity, activity.getString(R.string.load_data_faild));
	}

	@Override
	public void onSuccess(ResponseInfo<String> arg0) {
		success.onSuccess(arg0.result);
	}
	public interface RequestSuccess{
		void onSuccess(String result);
	}
}
