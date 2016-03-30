package com.lu.takeaway.model;

import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lu.takeaway.bean.OrderBean;

/**
 * Created by lenovo on 2016/3/22.
 */
public class OrderModel extends BaseModel{

    public void queryOrder(String uid,RequestCallBack<String>callBack){
        params=new RequestParams();
        params.addBodyParameter("uid",uid);
        postRequest(QUERY_ORDER_URL,params,callBack);
    }
    public void commitOrder(String ousername,OrderBean order,RequestCallBack<String>callBack){
        params=new RequestParams();
        params.addBodyParameter("ousername",ousername);
        params.addBodyParameter("odate",order.odate);
        params.addBodyParameter("ofoodname",order.ofoodname);
        params.addBodyParameter("oprice",String.valueOf(order.oprice));
        params.addBodyParameter("onumber",String.valueOf(order.onumber));
        params.addBodyParameter("oid",String.valueOf(order.oid));
        params.addBodyParameter("opicture",String.valueOf(order.opicture));
        params.addBodyParameter("ofinished",String.valueOf(order.ofinished));
        params.addBodyParameter("uid",String.valueOf(order.uid));
        postRequest(INSERT_ORDER_URL,params,callBack);
    }
}
