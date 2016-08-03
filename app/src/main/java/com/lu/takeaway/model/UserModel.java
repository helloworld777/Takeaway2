package com.lu.takeaway.model;

import android.content.Context;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lu.takeaway.bean.UserBean;

import cn.bmob.v3.listener.UpdateListener;
import com.lu.takeaway.util.Constants;

/**
 * Created by lenovo on 2016/3/21.
 */
public class UserModel extends BaseModel implements IUserModel  , Constants {

    HttpUtils httpUtils;
    public UserModel(){
        httpUtils=new HttpUtils();
    }
    @Override
    public void isExsit(String username, String pwd,RequestCallBack<String> callBack) {
        String myurl = SERVER_BASE_URL+QUERY_USER_URL + "username=" + username+ "&password=" + pwd;
        httpUtils.send(HttpRequest.HttpMethod.GET, myurl, callBack);
    }
    public void registerUser(UserBean userBean,RequestCallBack<String> callBack){
        params=new RequestParams();
        params.addBodyParameter("lusername",userBean.lusername);
        params.addBodyParameter("lpwd",userBean.lpwd);
        params.addBodyParameter("address",userBean.address);
        params.addBodyParameter("phone",userBean.phone);
        params.addBodyParameter("date", userBean.date);
        postRequest(SERVER_BASE_URL+INSERT_USER_URL,params,callBack);
    }
    public void updateUser(UserBean userBean, Context context, UpdateListener updateListener){
//        UserBean userServer=new UserBean();

        userBean.update(context,userBean.getObjectId(),updateListener);
    }
//    public void queryAllUser(){
//        String myurl = SERVER_BASE_URL+QUERY_USER_URL;
//        httpUtils.send(HttpRequest.HttpMethod.GET, myurl, callBack);
//    }
}
