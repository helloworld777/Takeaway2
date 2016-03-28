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

/**
 * Created by lenovo on 2016/3/26.
 */
public class UserInfoUpdateFragment extends BaseFragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_chang_user_info,container,false);
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

        textInput_layout_name=findViewById(view,R.id.textInput_layout_name);

        etEditInfo=findViewById(view,R.id.etEditInfo);
    }
    public void setData(String data,String updateMsg){
        d("data:"+data+",updateMsg:"+updateMsg);
        this.updateMeg=updateMsg;
        this.data=data;

    }
    private String updateMeg, data;
    @Override
    public void initData() {
//        Bundle bundle=getArguments();
//        if(bundle!=null){
//            updateMeg=bundle.getString("updateMeg");
//            data=bundle.getString("data");
            if("手机号码".equals(updateMeg)){
                etEditInfo.setMaxWidth(11);
                etEditInfo.setInputType(InputType.TYPE_CLASS_PHONE);
            }
//        }
        etEditInfo.setText(data);
    }
}
