package com.lu.takeaway.view.activity;

import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lu.takeaway.R;
import com.lu.takeaway.bean.UserBean;
import com.lu.takeaway.persenter.UserPersenter;
import com.lu.takeaway.view.IUserLoginView;
import com.lu.takeaway.view.app.DingDanApplication;

import util.Constants;
import util.SPUtils;

@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseFragmentActivity implements Constants,IUserLoginView {

	private static final String TAG = "LoginActivity";
	@ViewInject(R.id.tvTitle)
	private TextView tvTitle;
	@ViewInject(R.id.et_username)
	private EditText et_username;
	@ViewInject(R.id.et_password)
	private EditText et_password;
	@ViewInject(R.id.cb_remember)
	private CheckBox cb_remember;

	private String username, password;

	private boolean isData = false;
	private boolean isJump=false;
	private UserPersenter userPersenter;
	@Override
	protected void bindData() {
//		Intent intent=getIntent();
//		if(intent!=null){
			isJump=(getIntent()!=null);
//		}
		et_username.setText("lyw");
		et_password.setText("123456");
		userPersenter=new UserPersenter(this);

	}

	@OnClick({ R.id.btn_register, R.id.btn_login })
	@Override
	protected void viewClick(View view) {
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
		userPersenter.login(username,password);

	}
	
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent data) {
		super.onActivityResult(arg0, arg1, data);
		switch (arg1) {
		case REQUESTCODE_MAIN_LOGIN:
			isData=true;
			isJump=true;
			break;
		case REQUESTCODE_REGISTER_LOGIN:

//			if (data != null) {
//				userBean = (UserBean) data.getSerializableExtra("userbean");
//				et_username.setText(userBean.username);
//				et_password.setText(userBean.getPassword());
//				isData=false;
//			}
			break;
		default:
			break;
		}
		

	}

	private void register() {
		startActivityTransition(UserRegisterActivity.class);
		finish();
	}

	@Override
	public void loginSuccess(UserBean user) {

		SPUtils.saveUserInfo(mActivity,user);
		DingDanApplication.getDefault().setCurrenUserBean(user);
		if(!isJump){
			startActivityForResultTransition(MainActivity.class);
		}
		finish();
	}

	@Override
	public void loginFaild() {
		showToast(getString(R.string.login_faild));
	}
}
