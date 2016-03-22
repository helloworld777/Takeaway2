package com.lu.takeaway.persenter;

import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lu.takeaway.bean.UserBean;
import com.lu.takeaway.model.UserModel;
import com.lu.takeaway.view.IUserView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import util.Debug;
import util.JSONHelpUtil;

/**
 * Created by lenovo on 2016/3/21.
 */
public class UserPersenter extends  BasePersenter{

    UserModel userModel;
    IUserView userView;
    public UserPersenter(IUserView userView){
        userModel=new UserModel();
        this.userView=userView;
    }
    public void login(String username,String pwd) {

        userModel.isExsit(username,pwd,new RequestCallBack<String>(){

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

                String data=responseInfo.result;
                Debug.d(UserPersenter.this,"onSuccess...............data:"+data);
                userView.loginSuccess(resolveUserBean(data));
            }
            @Override
            public void onFailure(HttpException e, String s) {
                e.printStackTrace();
                Debug.d(UserPersenter.this,"onFailure...............data:"+s);
                userView.loginFaild();
            }
        });
    }
    public UserBean resolveUserBean(String json){
        UserBean userBean=null;
        try {
            JSONHelpUtil jsonHelpUtil=new JSONHelpUtil(new JSONObject(json));
            JSONArray array=jsonHelpUtil.getJSONArray("results");
            if(array.length()>0){
//                userBean=new UserBean();
                for(int i=0,size=array.length();i<size;i++){
                    JSONObject object=array.getJSONObject(i);
                    userBean=new Gson().fromJson(object.toString(), UserBean.class);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return userBean;
    }
}
