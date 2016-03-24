package com.lu.takeaway.model;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lu.takeaway.bean.UserBean;

import util.Constants;

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
        String myurl = SERVER_BASE_URL+QUERY_USER_URL + "username" + username+ "&password=" + pwd;
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
//    public void queryAllUser(){
//        String myurl = SERVER_BASE_URL+QUERY_USER_URL;
//        httpUtils.send(HttpRequest.HttpMethod.GET, myurl, callBack);
//    }
}
