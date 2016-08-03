package com.lu.takeaway.view.activity;

import android.text.TextUtils;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.RelativeLayout;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lu.takeaway.R;
import com.lu.takeaway.bean.UserBean;
import com.lu.takeaway.persenter.UserPersenter;
import com.lu.takeaway.view.IUserLoginView;
import com.lu.takeaway.view.app.DingDanApplication;

import com.lu.takeaway.util.Constants;
import com.lu.takeaway.util.SPUtils;

@ContentView(value=R.layout.activity_launch)
public class LaunchActivity extends BaseFragmentActivity implements IUserLoginView, Constants {
	@ViewInject(value= R.id.rl_bg)
	private RelativeLayout rl_bg;
	private UserPersenter userPersenter;
	@Override
	protected void bindData() {
		userPersenter=new UserPersenter(this);
		AlphaAnimation alphaAnimation = new AlphaAnimation(0.5f, 1.0f);
		alphaAnimation.setDuration(3000);
		alphaAnimation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				String username= (String) SPUtils.get(mActivity,USERNAME,"");
				String password=(String)SPUtils.get(mActivity,PASSWORD,"");

				if(TextUtils.isEmpty(username)||TextUtils.isEmpty(password)){
					startActivityTransition(LoginActivity.class);
					finish();
				}else{
					userPersenter.login(username,password,false);

//					User user=new User();
//					user.setUsername("lyw");
//					user.setPassword("123456");
//
//					user.login(mActivity, new SaveListener() {
//						@Override
//						public void onSuccess() {
//							d("onSuccess");
//						}
//
//						@Override
//						public void onFailure(int i, String s) {
//							d("onFailure i:"+i+",s:"+s);
//						}
//					});
				}
			}
		});
		rl_bg.setAnimation(alphaAnimation);

	}

	@Override
	public void loginSuccess(UserBean user) {
		d("loginSuccess user.header_img:"+user.header_img);
		DingDanApplication.getDefault().setCurrenUserBean(user);
		SPUtils.saveUserInfo(mActivity,user);
		startActivityTransition(MainActivity.class);
		finish();
	}

	@Override
	public void loginFaild() {
		d("loginFaild");
		startActivityTransition(LoginActivity.class);
		finish();
	}
}
