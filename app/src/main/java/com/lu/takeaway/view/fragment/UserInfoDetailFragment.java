package com.lu.takeaway.view.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lu.takeaway.R;
import com.lu.takeaway.bean.UserBean;
import com.lu.takeaway.view.activity.UserInfoDetailActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by lenovo on 2016/3/26.
 */
public class UserInfoDetailFragment extends BaseFragment implements View.OnClickListener{
    private UserBean userBean;
    public UserInfoDetailFragment(){
        userBean=mContext.getCurrenUserBean();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_user_info_detail,container,false);
        initWidget(view);
        return view;
    }

    @Override
    protected void initWidget(View view) {
        RelativeLayout relativeLayout=findViewById(view,R.id.rlMyName);
        relativeLayout.setOnClickListener(this);
        RelativeLayout rlMyPhone=findViewById(view,R.id.rlMyPhone);
        rlMyPhone.setOnClickListener(this);
        RelativeLayout rlMyAddress=findViewById(view,R.id.rlMyAddress);
        rlMyAddress.setOnClickListener(this);
        RelativeLayout rlMyDate=findViewById(view,R.id.rlMyDate);
        rlMyDate.setOnClickListener(this);
        RelativeLayout rlExit=findViewById(view,R.id.rlExit);
        rlExit.setOnClickListener(this);

        TextView tvMyName=findViewById(view,R.id.tvMyName);
        tvMyName.setText(userBean.lusername);
        TextView tvMyPhone=findViewById(view,R.id.tvMyPhone);
        tvMyPhone.setText(userBean.phone);
        TextView tvMyAddress=findViewById(view,R.id.tvMyAddress);
        tvMyAddress.setText(userBean.address);
        TextView tvMyDate=findViewById(view,R.id.tvMyDate);
        tvMyDate.setText(userBean.date);

        ImageView ciMyHeaderImg=findViewById(view,R.id.ciMyHeaderImg);
        ImageLoader.getInstance().displayImage(userBean.header_img,ciMyHeaderImg);
    }
    UserInfoDetailActivity userInfoDetailActivity;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        userInfoDetailActivity= (UserInfoDetailActivity) activity;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case  R.id.rlMyName:
                userInfoDetailActivity.replaceFragment(true,userBean.lusername,getString(R.string.my_person_info_nickname));
                break;
            case  R.id.rlMyPhone:
                userInfoDetailActivity.replaceFragment(true,userBean.phone,getString(R.string.my_person_info_phone));
                break;
            case  R.id.rlMyAddress:
                userInfoDetailActivity.replaceFragment(true,userBean.address,getString(R.string.my_person_info_address));
                break;
            case  R.id.rlMyDate:
                userInfoDetailActivity.replaceFragment(true,userBean.date,getString(R.string.my_person_info_date));
                break;
            case  R.id.rlExit:
                showToast("exit");
                break;

        }
    }
}
