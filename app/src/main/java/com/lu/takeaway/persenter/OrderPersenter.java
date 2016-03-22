package com.lu.takeaway.persenter;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lu.takeaway.bean.OrderBean;
import com.lu.takeaway.model.OrderModel;
import com.lu.takeaway.view.IOrderView;
import com.lu.takeaway.view.IView;
import com.lu.takeaway.view.app.DingDanApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.listener.SaveListener;
import util.DateUtil;
import util.Debug;
import util.JSONHelpUtil;

/**
 * Created by lenovo on 2016/3/22.
 */
public class OrderPersenter {

    OrderModel orderModel=new OrderModel();
    IOrderView iOrderView;
    private IView iView;
    public OrderPersenter(){

    }
    public OrderPersenter(IOrderView iOrderView){
        this.iOrderView=iOrderView;
    }
    public void queryOrder(String username){
        orderModel.queryOrder(username,new QueryOrderCallBack());
    }
    public void queryOrderMaxId(String username){
        orderModel.queryOrder(username, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                List<OrderBean> orderBeens=resolveOrderFromJson(responseInfo.result);
                int maxId=0;
                for(OrderBean orderBean:orderBeens){
                    maxId=(maxId>orderBean.id?maxId:orderBean.id);
                }
                Debug.d(OrderPersenter.this,"maxId:"+maxId);
                DingDanApplication.getDefault().setMaxOrderId(maxId);
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }
    public void commitOrder(String username,OrderBean orderBean){
        orderModel.commitOrder(username,orderBean ,new CommitOrderCallBack());
    }
    public void commitOrder(Context context,String username){
        List<OrderBean> orderBeanList=DingDanApplication.getDefault().getSelectedOrderBean();
        if(orderBeanList.isEmpty()){
            return;
        }
        List<BmobObject> objects=new ArrayList<>();
        for(OrderBean bean:orderBeanList){
            bean.odate= DateUtil.formateDate();
            objects.add(bean);
        }
        new BmobObject().insertBatch(context,objects,new SaveListener() {
            @Override
            public void onSuccess() {
                if(getiView() !=null){
                    getiView().loadDataSuccess();
                }
                Debug.d(OrderPersenter.this,"批量添加成功");
            }
            @Override
            public void onFailure(int code, String msg) {
                if(getiView() !=null){
                    getiView().loadDataFaild();
                }
                Debug.d(OrderPersenter.this,"批量添加失败:"+msg);
            }
        });


    }

    public IView getiView() {
        return iView;
    }

    public void setiView(IView iView) {
        this.iView = iView;
    }

    class QueryOrderCallBack extends RequestCallBack<String> {
        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            if(iOrderView!=null){
                iOrderView.getOrderBeanSuccess(resolveOrderFromJson(responseInfo.result));
            }

        }

        @Override
        public void onFailure(HttpException e, String s) {
            if(iOrderView!=null)
            iOrderView.getOrderBeanFaild();
        }
    }
    class CommitOrderCallBack extends RequestCallBack<String> {
        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            iOrderView.getOrderBeanSuccess(resolveOrderFromJson(responseInfo.result));
        }

        @Override
        public void onFailure(HttpException e, String s) {
            iOrderView.getOrderBeanFaild();
        }
    }
    private List<OrderBean> resolveOrderFromJson(String json){
        List<OrderBean> orderBeenList=new ArrayList<>();
        try {
            JSONHelpUtil jsonHelpUtil=new JSONHelpUtil(new JSONObject(json));
            JSONArray array=jsonHelpUtil.getJSONArray("results");
            Gson gson=new Gson();
            orderBeenList=gson.fromJson(array.toString(), new TypeToken<List<OrderBean>>(){}.getType());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return orderBeenList;
    }
}