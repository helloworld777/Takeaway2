package com.lu.takeaway.view.app;

import android.app.Application;

import com.lidroid.xutils.BitmapUtils;
import com.lu.takeaway.bean.OrderBean;
import com.lu.takeaway.bean.UserBean;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.push.BmobPush;
import cn.bmob.sms.BmobSMS;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import util.Constants;
import util.Debug;
import util.FileUtils;

public class DingDanApplication extends Application {
	private static DingDanApplication danApplication;
	private UserBean currenUserBean;
	private BitmapUtils bitmapUtils;
	private List<OrderBean> selectedOrderBean;
	private int maxOrderId;
	private int maxUserId;
	@Override
	public void onCreate() {
		super.onCreate();
		danApplication=this;
		setBitmapUtils(new BitmapUtils(getApplicationContext(), FileUtils.imgPathPath()));
		setSelectedOrderBean(new ArrayList<OrderBean>());

		Bmob.initialize(this, Constants.Bmob_APPID);
		BmobSMS.initialize(this,Constants.Bmob_APPID);
		// 使用推送服务时的初始化操作
		BmobInstallation.getCurrentInstallation(this).save();
		// 启动推送服务
		BmobPush.startWork(this);
		Debug.d(this,"onCreate..............");
	}
	public static DingDanApplication getDefault(){
		return danApplication;
	}
	public UserBean getCurrenUserBean() {
		return currenUserBean;
	}
	public  boolean isLogin(){
		return  currenUserBean!=null;
	}
	public void setCurrenUserBean(UserBean currenUserBean) {
		this.currenUserBean = currenUserBean;
	}
	public BitmapUtils getBitmapUtils() {
		return bitmapUtils;
	}
	public void setBitmapUtils(BitmapUtils bitmapUtils) {
		this.bitmapUtils = bitmapUtils;
	}

	public List<OrderBean> getSelectedOrderBean() {
		return selectedOrderBean;
	}

	public void setSelectedOrderBean(List<OrderBean> selectedOrderBean) {
		this.selectedOrderBean = selectedOrderBean;
	}

	public int getMaxOrderId() {
		return maxOrderId;
	}

	public void setMaxOrderId(int maxOrderId) {
		this.maxOrderId = maxOrderId;
	}

	public int getMaxUserId() {
		return maxUserId;
	}

	public void setMaxUserId(int maxUserId) {
		this.maxUserId = maxUserId;
	}
}
