package com.lu.takeaway.model;

import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.callback.RequestCallBack;

/**
 * Created by lenovo on 2016/3/21.
 */
public class FoodModel extends BaseModel{


    public void queryAllFoods(String name,RequestCallBack<String> callBack){
        params = new RequestParams();
        params.addBodyParameter("name", name);
        postRequest(QUERY_FOOD_URL,params,callBack);
    }
    public void querySomeFoods(String name,int page,RequestCallBack<String> callBack){
        params = new RequestParams();
        params.addBodyParameter("name", name);
        params.addBodyParameter("page", String.valueOf(page));
        postRequest(QUERY_FOOD_URL,params,callBack);
    }
}
