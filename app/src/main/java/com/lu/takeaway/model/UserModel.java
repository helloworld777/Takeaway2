package com.lu.takeaway.model;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import util.Constants;

/**
 * Created by lenovo on 2016/3/21.
 */
public class UserModel implements IUserModel  , Constants {

    HttpUtils httpUtils;
    public UserModel(){
        httpUtils=new HttpUtils();
    }
    @Override
    public void isExsit(String username, String pwd,RequestCallBack<String> callBack) {
        String myurl = SERVER_BASE_URL+QUERY_USER_URL + "username" + username+ "&password=" + pwd;
        httpUtils.send(HttpRequest.HttpMethod.GET, myurl, callBack);
    }

}
