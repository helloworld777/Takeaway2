package com.lu.takeaway.persenter;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lu.takeaway.bean.OrderBean;
import com.lu.takeaway.model.OrderModel;
import com.lu.takeaway.util.DateUtil;
import com.lu.takeaway.util.JSONHelpUtil;
import com.lu.takeaway.view.IOrderView;
import com.lu.takeaway.view.IView;
import com.lu.takeaway.view.app.DingDanApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;

/**
 * Created by lenovo on 2016/3/22.
 */
public class OrderPersenter extends BasePersenter{

    OrderModel orderModel=new OrderModel();
    IOrderView iOrderView;
    private IView iView;
    public OrderPersenter(){

    }
    public OrderPersenter(IOrderView iOrderView){
        this.iOrderView=iOrderView;
    }
    public void queryOrder(int uid){
        orderModel.queryOrder(String.valueOf(uid),new QueryOrderCallBack());
    }
    public void queryOrderMaxId(String username){
        orderModel.queryOrder(username, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                List<OrderBean> orderBeens=resolveOrderFromJson(responseInfo.result);
                int maxId=0;
                for(OrderBean orderBean:orderBeens){
                    maxId=(maxId>orderBean.oid?maxId:orderBean.oid);
                }
                d( "maxId:" + maxId);
                d("data:"+responseInfo.result);
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

            commitOrder("lyw",bean);
        }
//        new BmobObject().insertBatch(context,objects,new SaveListener() {
//            @Override
//            public void onSuccess() {
//                if(getiView() !=null){
//                    getiView().loadDataSuccess();
//                }
//                Debug.d(OrderPersenter.this,"批量添加成功");
//            }
//            @Override
//            public void onFailure(int code, String msg) {
//                if(getiView() !=null){
//                    getiView().loadDataFaild();
//                }
//                Debug.d(OrderPersenter.this,"批量添加失败:"+msg);
//            }
//        });


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
//                List<OrderBean> orderBeanList=resolveOrderFromJson(responseInfo.result);
//                List<OrderBean> sortOrderBeanList=sortOrder(orderBeanList);
                iOrderView.getOrderBeanSuccess(sortOrder(resolveOrderFromJson(responseInfo.result)));
            }
        }

        @Override
        public void onFailure(HttpException e, String s) {
            if(iOrderView!=null)
            iOrderView.getOrderBeanFaild();
        }
    }
    public  static void main(String[] arges){
        List<String> list=new ArrayList<>() ;

    }
    private List<OrderBean> sortOrder(List<OrderBean> orderBeanList){

        List<OrderBean> tempOrder=new ArrayList<>();
        boolean isRemove=false;
        for(int i=0,size=orderBeanList.size();i<orderBeanList.size();i=(isRemove?i:i+1)){

//            if(isRemove){
//                i--;
//            }
            if(orderBeanList.get(i).ofinished.equals("未派送")){
                tempOrder.add(orderBeanList.remove(i));
                isRemove=true;
            }else{
                isRemove=false;
            }
        }
        isRemove=false;
        for(int i=0,size=orderBeanList.size();i<orderBeanList.size();i=(isRemove?i:i+1) ){
            if(orderBeanList.get(i).ofinished.equals("已派送")){
                tempOrder.add(orderBeanList.remove(i));
                isRemove=true;
            }else{
                isRemove=false;
            }
        }
        tempOrder.addAll(orderBeanList);

        return tempOrder;
    }
    class CommitOrderCallBack extends RequestCallBack<String> {
        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            d("data:"+responseInfo.result);
            if(iView!=null){
                iView.loadDataSuccess();
            }
        }

        @Override
        public void onFailure(HttpException e, String s) {
            d("data:"+s);
            e.printStackTrace();
            if(iView!=null)
                iView.loadDataFaild();
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
