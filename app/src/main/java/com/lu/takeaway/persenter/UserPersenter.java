package com.lu.takeaway.persenter;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
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

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindCallback;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import util.Debug;
import util.JSONHelpUtil;
import util.MD5Util;

/**
 * Created by lenovo on 2016/3/21.
 */
public class UserPersenter extends BasePersenter {

    UserModel userModel;
    IUserLoginView userView;
    private IView iView;
    private IUserRegisterView iUserRegisterView;

    public UserPersenter() {
        userModel = new UserModel();
    }

    public UserPersenter(IUserLoginView userView) {
        userModel = new UserModel();
        this.userView = userView;
    }

    public void updateUser(UserBean userBean, Context context) {
        userModel.updateUser(userBean, context, new UpdateListener() {
            @Override
            public void onSuccess() {
                if (iView != null) {
                    iView.loadDataSuccess();
                }
            }

            @Override
            public void onFailure(int i, String s) {
                if (iView != null) {
                    iView.loadDataFaild();
                    d("i:" + i + ",s:" + s);
                }
            }
        });
    }

    //    public static void save(Context context){
//        final User user = new User();
//        user.setUsername("123123");
//        user.setPassword("123123");
//
//        user.save(context, new SaveListener() {
//
//            @Override
//            public void onSuccess() {
//                System.out.println("onSuccess");
//            }
//
//            @Override
//            public void onFailure(int arg0, String arg1) {
//                System.out.println("onFailure");
//                System.out.println("arg1:"+arg1);
//            }
//        });
//    }
    public void queryMaxUid(Context context, final MaxUidListener uidListener) {

        BmobQuery query = new BmobQuery("LUser");
        query.findObjects(context, new FindCallback() {

            @Override
            public void onSuccess(JSONArray arg0) {
                d("object:" + arg0.toString());
                Gson gson = new Gson();
                List<UserBean> object = gson.fromJson(arg0.toString(), new TypeToken<List<UserBean>>() {
                }.getType());

                int maxId = 0;//

                for (UserBean userBean : object) {
                    maxId = (maxId > userBean.id) ? maxId : userBean.id;
                }
                maxId++;
                if (uidListener != null) {
                    uidListener.getMaxUid(maxId);
                }
                d("maxId:" + maxId);
            }

            @Override
            public void onFailure(int i, String s) {
                d("queryMaxUid   onFailure,i" + i + ",s:" + s);
            }
        });


//        BmobQuery<UserBean> query = new BmobQuery<>();
////        query.max(new String[]{"id"});
//        query.addQueryKeys("id");
//        query.findObjects(context, new FindListener<UserBean>() {
//            @Override
//            public void onSuccess(List<UserBean> object) {
//                d("object:"+object.toString());
//                JSONArray ary = (JSONArray) object;
//                if (ary != null) {//
//                    try {
//                        JSONObject obj = ary.getJSONObject(0);
//
//                        int maxId = obj.getInt("_maxId");//_(关键字)+首字母大写的列名
//                        if(uidListener!=null){
//                            uidListener.getMaxUid(maxId);
//                        }
//                        d("maxId:"+maxId);
////                        DingDanApplication.getDefault().setMaxUserId(maxId);
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                        d("JSONException  ");
//                    }
////                }
//                int maxId = 0;
//                for (UserBean userBean : object) {
//                    maxId = (maxId > userBean.id) ? maxId : userBean.id;
//                }
//                maxId++;
//                if (uidListener != null) {
//                    uidListener.getMaxUid(maxId);
//                }
//            }
//
//            @Override
//            public void onError(int i, String s) {
//                d("queryMaxUid   ,i" + i + ",s:" + s);
//            }
//        });
    }

    interface MaxUidListener {
        void getMaxUid(int maxUid);
    }

    public void login(String username, String pwd) {
        userModel.isExsit(username, (MD5Util.MD5(pwd)), new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

                String data = responseInfo.result;
                Debug.d(UserPersenter.this, "onSuccess...............data:" + data);
                List<UserBean> userBeanList = resolveUserBean(data);
                if (userBeanList.isEmpty()) {
                    userView.loginFaild();
                } else {
                    userView.loginSuccess(userBeanList.get(0));
                }

            }

            @Override
            public void onFailure(HttpException e, String s) {
                e.printStackTrace();
                Debug.d(UserPersenter.this, "onFailure...............data:" + s);
                userView.loginFaild();
            }
        });
    }

    public List<UserBean> resolveUserBean(String json) {
        List<UserBean> userBeanList = new ArrayList<>();
        try {
            JSONHelpUtil jsonHelpUtil = new JSONHelpUtil(new JSONObject(json));
            JSONArray array = jsonHelpUtil.getJSONArray("results");
            if (array.length() > 0) {
                for (int i = 0, size = array.length(); i < size; i++) {
                    JSONObject object = array.getJSONObject(i);
                    UserBean userBean = new Gson().fromJson(object.toString(), UserBean.class);
                    userBeanList.add(userBean);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return userBeanList;
    }

    public void registerUser(final Context context, String smscode, final UserBean userBean) {
//        BmobSMS.verifySmsCode(context, userBean.phone, smscode, new VerifySMSCodeListener() {
//            @Override
//            public void done(BmobException ex) {
//                if (ex == null) {//短信验证码已验证成功
//                    Log.i("bmob", "验证通过");
//                    int maxUid = DingDanApplication.getDefault().getMaxUserId();
//                    maxUid++;
        queryMaxUid(context, new MaxUidListener() {
            @Override
            public void getMaxUid(int maxUid) {
//                maxUid++;
//                DingDanApplication.getDefault().setMaxUserId(maxUid);
                userBean.id = maxUid;
                userBean.save(context, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        if (iUserRegisterView != null) {
                            iUserRegisterView.registerSuccess();
                        }
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        d("i" + i + ",s:" + s);
                        if (iUserRegisterView != null) {
                            iUserRegisterView.registerFaild();
                        }
                    }
                });
            }
        });


//                } else {
//                    Log.i("bmob", "验证失败：code =" + ex.getErrorCode() + ",msg = " + ex.getLocalizedMessage());
//                    if (iUserRegisterView != null) {
//                        iUserRegisterView.smsError();
//                    }
//                }
//            }

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
            if (iUserRegisterView != null) {
                iUserRegisterView.registerSuccess();
            }
        }

        @Override
        public void onFailure(HttpException e, String s) {
            e.printStackTrace();
            if (iUserRegisterView != null) {
                iUserRegisterView.registerFaild();
            }
        }
    }


    public void setiUserRegisterView(IUserRegisterView iUserRegisterView) {
        this.iUserRegisterView = iUserRegisterView;
    }
}
