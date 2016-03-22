package com.lu.takeaway.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lu.takeaway.R;

import bean.Manager.UserBeanManager;

@ContentView(value=R.layout.activity_launch)
public class LaunchActivity extends Activity {
	@ViewInject(value= R.id.rl_bg)
	private RelativeLayout rl_bg;
	@ViewInject(value=R.id.ivAdvertise)
	private ImageView imgAdvertise;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		
		
		
		AlphaAnimation alphaAnimation = new AlphaAnimation(0.1f, 1.0f);
		alphaAnimation.setDuration(1000);
		alphaAnimation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				UserBeanManager userBeanManager=new UserBeanManager();
				Class<?> class1;
				if(userBeanManager.isLogin(getApplicationContext())){
					class1= MainActivity.class;
				}else{
					class1=LoginActivity.class;
				}
				startActivity(new Intent(LaunchActivity.this, class1));
				overridePendingTransition(R.anim.out_to_left, R.anim.in_from_right);
				finish();
			}
		});
		rl_bg.setAnimation(alphaAnimation);
	}
}
