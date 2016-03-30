package com.lu.takeaway.view.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lu.takeaway.R;
import com.lu.takeaway.bean.UserBean;
import com.lu.takeaway.persenter.UserPersenter;
import com.lu.takeaway.view.IUserRegisterView;
import com.lu.takeaway.view.app.DingDanApplication;
import com.lu.takeaway.view.fragment.RegisterPhoneNumberFragment;
import com.lu.takeaway.view.fragment.RegisterUserPwdFragment;

import util.Constants;
import util.DataVaildUtil;
import util.DateUtil;
import util.Debug;
import util.MD5Util;

@ContentView(R.layout.activity_register)
public class UserRegisterActivity extends BaseFragmentActivity implements Constants, IUserRegisterView {

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
    private String pwd;
    private UserPersenter userPersenter;
    private UserBean userBean;


    @ViewInject(R.id.rlBack)
    private RelativeLayout rlBack;

    @OnClick({R.id.btnCancel, R.id.btnNext, R.id.rlBack})
    public void viewClick(View view) {
        switch (view.getId()) {
            case R.id.btnCancel:
                if (fragment instanceof RegisterUserPwdFragment) {
                    startActivityTransition(LoginActivity.class);
                    finish();
                } else {
                    bindData();
                    btnNext.setText(getString(R.string.next));
                    btnCancel.setText(getString(R.string.cancel));
                }
                break;
            case R.id.btnNext:
                if (fragment instanceof RegisterUserPwdFragment) {
                    username = ((RegisterUserPwdFragment) fragment).getUsername();
                    pwd = ((RegisterUserPwdFragment) fragment).getPwd();
                    Debug.d(this, "pwd" + pwd);
                    if (isDataValide(username, pwd)) {
                        replaceFragment();
                        btnNext.setText(getString(R.string.finish));
                        btnCancel.setText(getString(R.string.pre));
                    }
                } else {
                    String phoneNumber = ((RegisterPhoneNumberFragment) fragment).getPhone();
                    String smscode = ((RegisterPhoneNumberFragment) fragment).getSmsCode();


                    if (DataVaildUtil.isDataVailed(this, phoneNumber, getString(R.string.phone_empty)) && DataVaildUtil.checkMobileNumber(this, phoneNumber)) {
                        d("username:" + username + "pwd:" + pwd);
                        userBean = new UserBean(username, MD5Util.MD5(pwd), phoneNumber, "火星", DateUtil.formateDate());
                        userPersenter.registerUser(this, smscode, userBean);
                    }

//                    if (isDataValide2(phoneNumber, smscode)) {
//                        Debug.d(this, "username:" + username + "pwd:" + pwd);
//
//                    }

                }
                break;
            case R.id.rlBack:
                break;
        }

    }

    private boolean isDataValide(String username, String pwd) {
        if (TextUtils.isEmpty(username)) {
            showToast("用户名不能为空");
            return false;
        }
        if (TextUtils.isEmpty(pwd)) {
            showToast("密码不能为空");
            return false;
        }
        return true;
    }

    private boolean isDataValide2(String phoneNumber, String smscode) {
        if (TextUtils.isEmpty(phoneNumber)) {
            showToast("手机号码不能为空");
            return false;
        }
        if (TextUtils.isEmpty(smscode)) {
            showToast("验证码不能为空");
            return false;
        }
        return true;
    }

    @Override
    protected void bindData() {
        tvTitle.setText(getString(R.string.register));
        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        fragment = new RegisterUserPwdFragment();
        transaction.replace(R.id.mainFrame, fragment);
//		transaction.setCustomAnimations(R.anim.activity_back_in,R.anim.activity_back_out);
        transaction.commit();
        userPersenter = new UserPersenter();
        userPersenter.setiUserRegisterView(this);
    }

    private void replaceFragment() {
        transaction = fragmentManager.beginTransaction();
        fragment = new RegisterPhoneNumberFragment();
        transaction.setCustomAnimations(R.anim.activity_back_in, R.anim.activity_back_out);
        transaction.addToBackStack(null);
//		transaction.add(fragment,"");
        transaction.replace(R.id.mainFrame, fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        btnNext.setText(getString(R.string.next));
        btnCancel.setText(getString(R.string.cancel));
    }

    @Override
    public void smsError() {
        showToast(getString(R.string.smscode_error));
    }

    @Override
    public void registerSuccess() {
        showToast(getString(R.string.register_success));
        DingDanApplication.getDefault().setCurrenUserBean(userBean);
        startActivityTransition(MainActivity.class);
    }

    @Override
    public void registerFaild() {
        showToast(getString(R.string.register_faild));
    }
}
