package com.lu.takeaway.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lu.takeaway.R;
import com.lu.takeaway.view.app.DingDanApplication;

import org.json.JSONException;
import org.json.JSONObject;

import bean.Manager.UserBeanManager;
import com.lu.takeaway.bean.UserBean;
import db.UsersDBManager;
import util.Constants;
import util.DialogUtil;
import util.LogUtil;

@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseFragmentActivity implements Constants {

	private static final String TAG = "LoginActivity";
	@ViewInject(R.id.tvTitle)
	private TextView tvTitle;
	@ViewInject(R.id.et_username)
	private EditText et_username;
	@ViewInject(R.id.et_password)
	private EditText et_password;
	@ViewInject(R.id.cb_remember)
	private CheckBox cb_remember;

	private SharedPreferences preferences;
	private String username, password;
	private boolean isRemember;
	private UsersDBManager usersDBManager;
	private UserBeanManager userBeanManager;

	private UserBean userBean = null;

	private boolean isData = false;

	@Override
	protected void bindData() {
		usersDBManager = new UsersDBManager(this);
		userBeanManager=new UserBeanManager();
		// username = preferences.getString("username", "");
		// password = preferences.getString("password", "");
		// isRemember = preferences.getBoolean("isRemember", false);
		// cb_remember.setChecked(isRemember);
		// if ("".equals(username.trim())) {
		// return;
		// }
		// et_username.setText(username);
		// if (isRemember) {
		// et_password.setText(password);
		// }
		et_username.setText("张三");
		et_password.setText("123456");
	}

	@OnClick({ R.id.btn_register, R.id.btn_login })
	@Override
	protected void viewClick(View view) {
		// TODO Auto-generated method stub
		switch (view.getId()) {
		case R.id.btn_register:
			register();
			break;
		case R.id.btn_login:
			login();
			break;

		default:
			break;
		}
	}

	private void login() {
		username = et_username.getText().toString().trim();
		password = et_password.getText().toString().trim();
//		LogUtil.d(TAG, ""+usersDBManager.queryUser(username, password));
//		LogUtil.d(TAG, "username:"+username);
//		LogUtil.d(TAG, "password:"+password);
//		LogUtil.d(TAG, "username2:"+SaveDataUtil.getString(this, USERNAME));
//		LogUtil.d(TAG, "password2:"+SaveDataUtil.getString(this, PWD));
		userBean=new UserBean();
		userBean.setUsername(username);
		userBean.setPassword(password);
		
		userBeanManager.login(userBean, new RequestCallBack<String>() {
			
			@Override
			public void onSuccess(ResponseInfo<String> result) {
				String data=result.result;
				JSONObject jsonObject2;
				try {
					jsonObject2 = new JSONObject(data);
					if("success".equals(jsonObject2.getString("message"))){
						LogUtil.d(TAG, jsonObject2.getJSONObject("userBean").toString());
						UserBean userBean=new Gson().fromJson(jsonObject2.getJSONObject("userBean").toString(), UserBean.class);
						DingDanApplication.getDefault().setCurrenUserBean(userBean);
						userBeanManager.saveLoginedUser(getApplicationContext(), username,password);
						
						Intent intent = new Intent(LoginActivity.this, MainActivity.class);
						intent.putExtra("userbean", userBean);
						startActivityTransition(intent);
						finish();
					}else{
						DialogUtil.showToast(LoginActivity.this, getString(R.string.login_faild));
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
			
			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				DialogUtil.showToast(LoginActivity.this, getString(R.string.login_faild));
			}
		});
		
//		if (usersDBManager.queryUser(username, password)) {
//			SaveDataUtil.setBoolean(this, IS_LOGIN, true);
//			LogUtil.d(TAG, "isData:"+isData);
//			if (isData) {
//				Intent intent = new Intent();
//				intent.putExtra("userbean", userBean);
//				setResult(0, intent);
//				finish();
//			} else {
//				Intent intent = new Intent(this, MainActivity.class);
//				intent.putExtra("userbean", userBean);
//				startActivityTransition(intent);
//				finish();
//			}
//		}else{
//			DialogUtil.showToast(this, getString(R.string.login_faild));
//		}
	}
	
//	ona
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, data);

		
		
		switch (arg1) {
		case REQUESTCODE_MAIN_LOGIN:
			isData=true;
			break;
		case REQUESTCODE_REGISTER_LOGIN:
			
			if (data != null) {
				userBean = (UserBean) data.getSerializableExtra("userbean");
				et_username.setText(userBean.getUsername());
				et_password.setText(userBean.getPassword());
				isData=false;
			}
			break;
		default:
			break;
		}
		

	}

	private void register() {
		startActivityForResultTransition(RegisterActivity.class);
	}

}
