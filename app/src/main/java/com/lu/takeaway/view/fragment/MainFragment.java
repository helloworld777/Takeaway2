package com.lu.takeaway.view.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lu.takeaway.R;
import com.lu.takeaway.bean.FoodBean;
import com.lu.takeaway.bean.OrderBean;
import com.lu.takeaway.persenter.FoodPersenter;
import com.lu.takeaway.view.IFoodView;
import com.lu.takeaway.view.adapter.LuAdapter;
import com.lu.takeaway.view.adapter.ViewHolder;
import com.lu.takeaway.view.widget.xlistview.XListView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bean.EventBean;
import de.greenrobot.event.EventBus;
import com.lu.takeaway.util.DateUtil;
import com.lu.takeaway.util.LogUtil;
import com.lu.takeaway.util.StringUtil;

@SuppressLint("NewApi")
public class MainFragment extends BaseFragment implements IFoodView {
	protected static final String TAG = "MainFragment";

	private XListView mineList;

	private FoodAdapter luAdapter;
	private List<FoodBean> allfoods=new ArrayList<>();


	private boolean refresh=true;
	private BitmapUtils bitmapUtils;
	private boolean isRefresh=false;
	private FoodPersenter foodPersenter;
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler();

	public MainFragment() {
		EventBus.getDefault().register(this);
		foodPersenter=new FoodPersenter(this);
//		initData();
		LogUtil.d(this,"MainFragment()..........");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_main, container, false);
		mineList = (XListView) view.findViewById(R.id.mineList);
		luAdapter = new FoodAdapter(getActivity(), allfoods, R.layout.item_food_listview);
		mineList.setAdapter(luAdapter);
		mineList.setPullLoadEnable(false);
		mineList.setXListViewListener(new MyXListViewListener());
		bitmapUtils=new BitmapUtils(getActivity());
		LogUtil.d(this,"onCreateView()..........");

		return view;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
//		dialogLoading=new DialogLoading(activity);
	}

	@Override
	public void onResume() {
		super.onResume();
		LogUtil.d(TAG, "onResume");
		initData();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

	public void onEventMainThread(EventBean eventBean) {
		switch (eventBean.getEventType()) {
		case EventBean.EventType.ADD_ORDER:
		case EventBean.EventType.REMOVE_ORDER:
			luAdapter.notifyDataSetChanged();
			LogUtil.d(TAG, "onEventMainThread");
			break;
		default:
			break;
		}
	}
	public void initData() {
		isRefresh=false;
		if(allfoods.isEmpty()){
			showLoadingDialog();
			foodPersenter.queryAllFood(null);
		}

	}
	 
	@OnClick({ R.id.llShowMenu })
	public void viewClick(View view) {
		switch (view.getId()) {
		case R.id.llShowMenu:
			break;
		default:
			break;
		}
	}

	public void search(String key) {

	}


	@Override
	public void loadDataSucess(List<FoodBean> foodBeanList) {

		showToast(R.string.refresh_data_success);
		closeLoadingDialog();
		if(foodBeanList==null&&foodBeanList.isEmpty()){
			return;
		}
		if(isRefresh){
			luAdapter.replaceAll(foodBeanList);
		}else{
			luAdapter.addAll(foodBeanList);
		}

	}

	@Override
	public void loadDataFaild() {
		showToast(R.string.refresh_data_faild);
		closeLoadingDialog();
	}

	class MyXListViewListener implements XListView.IXListViewListener {
		@Override
		public void onRefresh() {
			refresh=true;
			showLoadingDialog();
			mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					isRefresh=true;
					foodPersenter.queryAllFood(null);
					mineList.setRefreshTime(DateFormat.getDateTimeInstance().format(new Date()));
					mineList.stopRefresh();
					luAdapter.notifyDataSetChanged();

				}
			}, 2000);
			
			
		}

		@Override
		public void onLoadMore() {
			refresh=false;

			mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					isRefresh=false;
					foodPersenter.queryAllFood(null);
					mineList.stopLoadMore();
					luAdapter.notifyDataSetChanged();
				}
			}, 2000);
			
		}

	}

	class FoodAdapter extends LuAdapter<FoodBean> {
		DisplayImageOptions options;
		public FoodAdapter(Context context, List<FoodBean> datas, int mItemLayoutId) {
			super(context, datas, R.layout.item_food_listview);
			options = new DisplayImageOptions.Builder()
					.displayer(new RoundedBitmapDisplayer(20))
					.build();


		}
		@Override
		public void convert(ViewHolder helper, final FoodBean item) {
			helper.setString(R.id.itemName, item.name);
//			helper.setBackgroundResource(R.id.itemPicture, item.getRes());
			helper.setString(R.id.itemPrice, "$" + item.price);

			helper.setString(R.id.itemSalePrice, "$" + StringUtil.formatNumber(item.sale*item.price));
			((TextView) helper.getView(R.id.itemPrice)).getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

			final Button btnDianCai = helper.getView(R.id.btnDianCai);

			if(!TextUtils.isEmpty(item.littlePicUrl)&&item.littlePicUrl.length()>10){
				LogUtil.d(TAG, "item.getPicture():"+item.littlePicUrl);
//				bitmapUtils.configDefaultLoadFailedImage(R.mipmap.menu_icon_home);
//				bitmapUtils.display(helper.getView(R.id.itemPicture), item.pictureUrl);
				ImageLoader.getInstance().displayImage(item.littlePicUrl,(ImageView) helper.getView(R.id.itemPicture),options);
			}
			boolean isBooked=false;
			for(OrderBean orderBean:mContext.getSelectedOrderBean()){
				if(orderBean.ofoodname.equals(item.name)){
					isBooked=true;
					break;
				}
			}
			btnDianCai.setText(isBooked ? R.string.ordered : R.string.dingcai);
			btnDianCai.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					if (btnDianCai.getText().equals(getString(R.string.dingcai))) {
						OrderBean orderBean = new OrderBean();

						orderBean.oid= mContext.getMaxOrderId()+1;
						mContext.setMaxOrderId(orderBean.oid);
						orderBean.oprice=Double.parseDouble(StringUtil.formatNumber(item.price*item.sale));
						orderBean.onumber=1;
						orderBean.odate=DateUtil.formateDate();
						orderBean.ofoodname=item.name;
						orderBean.ousername=mContext.getCurrenUserBean().lusername;
						orderBean.opicture=item.pictureUrl;
						orderBean.ofinished=getString(R.string.orderUnFinish);
						mContext.getSelectedOrderBean().add(orderBean);
						btnDianCai.setText(getString(R.string.ordered));
					}
				}
			});

		}

	}

	@Override
	public void currentSelected() {
		LogUtil.d(TAG, "currentSelected");
		initData();
		luAdapter.notifyDataSetChanged();
	}
}
