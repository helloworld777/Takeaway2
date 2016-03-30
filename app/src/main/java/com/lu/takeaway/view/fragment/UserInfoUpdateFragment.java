package com.lu.takeaway.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lu.takeaway.R;
import com.lu.takeaway.bean.UserBean;
import com.lu.takeaway.view.activity.UserInfoDetailActivity;
import com.lu.takeaway.view.app.DingDanApplication;

import cn.bmob.v3.listener.UpdateListener;
import util.DataVaildUtil;
import util.SPUtils;

/**
 * Created by lenovo on 2016/3/26.
 */
public class UserInfoUpdateFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chang_user_info, container, false);
        initWidget(view);
        initData();
        d("onCreateView.................");
        return view;
    }

    private TextInputLayout textInput_layout_name;
    private TextInputEditText etEditInfo;

    @Override
    protected void initWidget(View view) {
        super.initWidget(view);
        textInput_layout_name = findViewById(view, R.id.textInput_layout_name);
        etEditInfo = findViewById(view, R.id.etEditInfo);
    }

    public void setData(String data, String updateMsg) {
        d("data:" + data + ",updateMsg:" + updateMsg);
        this.updateMeg = updateMsg;
        this.data = data;

    }

    private String updateMeg, data;

    @Override
    public void initData() {
        if ("我的号码".equals(updateMeg)) {
            etEditInfo.setMaxWidth(11);
            etEditInfo.setInputType(InputType.TYPE_CLASS_PHONE);
        }
        etEditInfo.setText(data);
    }
    public void updateUserInfo(){
        final UserBean userBean= DingDanApplication.getDefault().getCurrenUserBean();
        String msg=etEditInfo.getText().toString();
        if(!DataVaildUtil.isDataVailed(getActivity(),new String[]{msg},new String[]{updateMeg.replaceAll("我的"," ")})){
            return;
        }
        if(updateMeg.equals(getString(R.string.my_person_info_nickname))){
            userBean.lusername=msg;
        }else if(updateMeg.equals(getString(R.string.my_person_info_phone))){
            userBean.phone=msg;
        }else if(updateMeg.equals(getString(R.string.my_person_info_address))){
            userBean.address=msg;
        }

        userBean.update(getActivity(),new UpdateListener(){
            @Override
            public void onSuccess() {
                DingDanApplication.getDefault().setCurrenUserBean(userBean);
                SPUtils.saveUserInfo(getActivity(),userBean);
                closeSoftInput();
                showToast("update success");
                ((UserInfoDetailActivity)getActivity()).backHome();
            }
            @Override
            public void onFailure(int i, String s) {
                showToast("update failure");
            }
        });
    }
}
