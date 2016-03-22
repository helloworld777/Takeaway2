package com.lu.takeaway.model;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import util.Constants;

/**
 * Created by lenovo on 2016/3/21.
 */
public class BaseModel implements Constants {
    protected HttpUtils httpUtils;
    int TIME_OUT=3000;
    protected RequestParams params;
    public BaseModel(){
        httpUtils=new HttpUtils();
        httpUtils.configTimeout(TIME_OUT);
    }
    protected void postRequest(String url,RequestParams params ,RequestCallBack<String> callBack){


        httpUtils.send(HttpRequest.HttpMethod.POST,
                url,
                params,
                callBack);
    }
    protected void getRequest(String url,RequestParams params ,RequestCallBack<String> callBack){
        httpUtils.send(HttpRequest.HttpMethod.GET,
                url,
                callBack);
    }

}
