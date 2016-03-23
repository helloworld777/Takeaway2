package com.lu.takeaway.persenter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lu.takeaway.bean.FoodBean;
import com.lu.takeaway.model.FoodModel;
import com.lu.takeaway.view.IFoodView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import util.JSONHelpUtil;

/**
 * Created by lenovo on 2016/3/21.
 */
public class FoodPersenter extends BasePersenter{

    FoodModel foodModel;
    IFoodView iFoodView;
    public FoodPersenter(IFoodView iFoodView){
        foodModel=new FoodModel();
        this.iFoodView=iFoodView;
    }

    public void queryAllFood(String name){

        foodModel.queryAllFoods(name,new QueryAllFoodCallBack());
    }
    class QueryAllFoodCallBack extends RequestCallBack<String>{

        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            iFoodView.loadDataSucess(resolveFoodBean(responseInfo.result));
        }

        @Override
        public void onFailure(HttpException e, String s) {
            e.printStackTrace();
            iFoodView.loadDataFaild();
        }
    }
    public List<FoodBean> resolveFoodBean(String json){
        List<FoodBean> foodBeens=null;
        try {
            JSONHelpUtil jsonHelpUtil=new JSONHelpUtil(new JSONObject(json));
            JSONArray array=jsonHelpUtil.getJSONArray("results");
            Gson gson=new Gson();
            foodBeens=gson.fromJson(array.toString(), new TypeToken<List<FoodBean>>(){}.getType());

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return foodBeens;
    }
}
