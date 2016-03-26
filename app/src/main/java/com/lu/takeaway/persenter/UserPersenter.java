package com.lu.takeaway.persenter;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lu.takeaway.bean.User;
import com.lu.takeaway.bean.UserBean;
import com.lu.takeaway.model.UserModel;
import com.lu.takeaway.view.IUserLoginView;
import com.lu.takeaway.view.IUserRegisterView;
import com.lu.takeaway.view.IView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.exception.BmobException;
import cn.bmob.sms.listener.VerifySMSCodeListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import util.Debug;
import util.JSONHelpUtil;

/**
 * Created by lenovo on 2016/3/21.
 */
public class UserPersenter extends  BasePersenter{

    UserModel userModel;
    IUserLoginView userView;
    private IView iView;
    private IUserRegisterView iUserRegisterView;
    public UserPersenter(){
        userModel=new UserModel();
    }
    public UserPersenter(IUserLoginView userView){
        userModel=new UserModel();
        this.userView=userView;
    }
    public void updateUser(UserBean userBean,Context context){
        userModel.updateUser(userBean, context, new UpdateListener() {
            @Override
            public void onSuccess() {
                if(iView!=null){
                    iView.loadDataSuccess();
                }
            }

            @Override
            public void onFailure(int i, String s) {
                if(iView!=null){
                    iView.loadDataFaild();
                    d("i:"+i+",s:"+s);
                }
            }
        });
    }
    public static void save(Context context){
        final User user = new User();
        user.setUsername("123123");
        user.setPassword("123123");

        user.save(context, new SaveListener() {

            @Override
            public void onSuccess() {
                System.out.println("onSuccess");
            }

            @Override
            public void onFailure(int arg0, String arg1) {
                System.out.println("onFailure");
                System.out.println("arg1:"+arg1);
            }
        });
    }
    public void login(String username,String pwd) {
        userModel.isExsit(username,(pwd),new RequestCallBack<String>(){

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

                String data=responseInfo.result;
                Debug.d(UserPersenter.this,"onSuccess...............data:"+data);
                List<UserBean> userBeanList=resolveUserBean(data);
                if(userBeanList.isEmpty()){
                    userView.loginFaild();
                }else{
                    userView.loginSuccess(userBeanList.get(0));
                }

            }
            @Override
            public void onFailure(HttpException e, String s) {
                e.printStackTrace();
                Debug.d(UserPersenter.this,"onFailure...............data:"+s);
                userView.loginFaild();
            }
        });
    }
    public List<UserBean> resolveUserBean(String json){
        List<UserBean> userBeanList=new ArrayList<>();
        try {
            JSONHelpUtil jsonHelpUtil=new JSONHelpUtil(new JSONObject(json));
            JSONArray array=jsonHelpUtil.getJSONArray("results");
            if(array.length()>0){
                for(int i=0,size=array.length();i<size;i++){
                    JSONObject object=array.getJSONObject(i);
                    UserBean userBean=new Gson().fromJson(object.toString(), UserBean.class);
                    userBeanList.add(userBean);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return userBeanList;
    }

    public void registerUser(Context context, String smscode,final UserBean userBean){
        BmobSMS.verifySmsCode(context,userBean.phone, smscode, new VerifySMSCodeListener() {
            @Override
            public void done(BmobException ex) {
                if(ex==null){//短信验证码已验证成功
                    Log.i("bmob", "验证通过");
                userModel.registerUser(userBean, new RegisterUsrCallBack());
                }else{
                    Log.i("bmob", "验证失败：code ="+ex.getErrorCode()+",msg = "+ex.getLocalizedMessage());
                    if(iUserRegisterView !=null){
                        iUserRegisterView.smsError();
                    }
                }
            }
        });
    }

    public IView getiView() {
        return iView;
    }

    public void setiView(IView iView) {
        this.iView = iView;
    }

    class RegisterUsrCallBack extends RequestCallBack<String> {
        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) {
            if(iUserRegisterView !=null){
                iUserRegisterView.registerSuccess();
            }
        }
        @Override
        public void onFailure(HttpException e, String s) {
            e.printStackTrace();
            if(iUserRegisterView !=null){
                iUserRegisterView.registerFaild();
            }
        }
    }


    public void setiUserRegisterView(IUserRegisterView iUserRegisterView) {
        this.iUserRegisterView = iUserRegisterView;
    }
}
