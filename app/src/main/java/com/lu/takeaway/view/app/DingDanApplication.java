package com.lu.takeaway.view.app;

import android.app.Application;

import com.lidroid.xutils.BitmapUtils;
import com.lu.takeaway.bean.OrderBean;
import com.lu.takeaway.bean.UserBean;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import util.Constants;
import util.Debug;
import util.FileUtils;

public class DingDanApplication extends Application {
	private static DingDanApplication danApplication;
	private UserBean currenUserBean;
	private BitmapUtils bitmapUtils;
	private List<OrderBean> selectedOrderBean;
	private int maxOrderId;
	@Override
	public void onCreate() {
		super.onCreate();
		danApplication=this;
		setBitmapUtils(new BitmapUtils(getApplicationContext(), FileUtils.imgPathPath()));
		setSelectedOrderBean(new ArrayList<OrderBean>());

		Bmob.initialize(this, Constants.Bmob_APPID);
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
}
