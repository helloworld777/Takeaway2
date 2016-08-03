package com.lu.takeaway.view.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lu.takeaway.R;
import com.lu.takeaway.view.fragment.UserInfoDetailFragment;
import com.lu.takeaway.view.fragment.UserInfoUpdateFragment;

import com.lu.takeaway.util.Constants;

/**
 * Created by lenovo on 2016/3/26.
 */
@ContentView(R.layout.activity_user_info_detail)
public class UserInfoDetailActivity extends BaseFragmentActivity implements Constants{
    @ViewInject(R.id.tvTitle)
    private TextView tvTitle;
    @ViewInject(R.id.ivSearch)
    private ImageView ivSearch;
    private UserInfoDetailFragment userInfoDetailFragment;
    private UserInfoUpdateFragment userInfoUpdateFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    @ViewInject(R.id.tvSave)
    private TextView tvSave;

    @ViewInject(R.id.rlBack)
    private RelativeLayout rlBack;
    private boolean isHome=true;

    private String updateMsg;
    @OnClick({R.id.rlBack,R.id.tvSave})
    public void viewClick(View view){
        switch (view.getId()) {
            case R.id.rlBack:
                if(isHome){
                    finish();
                }else{
                    replaceFragment(false,null,null);
                }
                break;
            case R.id.tvSave:
                userInfoUpdateFragment.updateUserInfo();
                break;

        }
    }
//    private void updateUserInfo(){
//        UserBean userBean=new UserBean();
//
//        if(updateMsg.equals(getString(R.string.my_person_info_nickname))){
//            userBean.lusername=
//        }
//    }
    @Override
    protected void bindData() {
        ivSearch.setVisibility(View.GONE);
        tvTitle.setText(getString(R.string.my_person_info));
        fragmentManager=getSupportFragmentManager();
        userInfoUpdateFragment=new UserInfoUpdateFragment();
        userInfoDetailFragment=new UserInfoDetailFragment();

        replaceFragment(false,null,null);
    }
    public void backHome(){
        replaceFragment(false,null,null);
    }
    public void replaceFragment(boolean isUpdate,String msg,String updateMsg){
        d("isUpdate:"+isUpdate+",msg:"+msg+",updateMsg:"+updateMsg);
        transaction=fragmentManager.beginTransaction();
        if(isUpdate){
            tvSave.setVisibility(View.VISIBLE);
            transaction.setCustomAnimations(R.anim.activity_back_in,R.anim.activity_back_out);
            isHome=false;

            this.updateMsg=updateMsg;
            tvTitle.setText("更改"+updateMsg);
            userInfoUpdateFragment=new UserInfoUpdateFragment();
            userInfoUpdateFragment.setData(msg,updateMsg);
            transaction.addToBackStack(null);
            transaction.replace(R.id.mainFrame, userInfoUpdateFragment);
        }else{
            tvSave.setVisibility(View.GONE);
            isHome=true;
            transaction.replace(R.id.mainFrame, userInfoDetailFragment);
            tvTitle.setText(getString(R.string.my_person_info));
        }

        transaction.commit();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        tvTitle.setText(getString(R.string.my_person_info));
    }
}
