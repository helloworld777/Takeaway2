package com.lu.takeaway.model;

import com.lidroid.xutils.http.callback.RequestCallBack;

/**
 * Created by lenovo on 2016/3/21.
 */
public interface  IUserModel {
    void isExsit(String username,String pwd,RequestCallBack<String> callBack);
}
