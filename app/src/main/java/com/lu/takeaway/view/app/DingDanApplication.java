package com.lu.takeaway.view.app;

import android.annotation.SuppressLint;
import android.app.Application;

import com.lidroid.xutils.BitmapUtils;
import com.lu.takeaway.bean.OrderBean;
import com.lu.takeaway.bean.UserBean;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import util.Constants;
import util.Debug;

public class DingDanApplication extends Application {
	private static DingDanApplication danApplication;
	private UserBean currenUserBean;
//	private OrderBean orderBean;
	private BitmapUtils bitmapUtils;
	@SuppressLint("SdCardPath") 
	private String diskCachePath="/sdcard/lu/dingdan/images";
	private List<OrderBean> selectedOrderBean;
	private int maxOrderId;
	@Override
	public void onCreate() {
		super.onCreate();
		danApplication=this;
		setBitmapUtils(new BitmapUtils(getApplicationContext(),diskCachePath));
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
	public void setCurrenUserBean(UserBean currenUserBean) {
		this.currenUserBean = currenUserBean;
	}
//	public OrderBean getOrderBean() {
//		return orderBean;
//	}
//	public void setOrderBean(OrderBean orderBean) {
//		this.orderBean = orderBean;
//	}
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
