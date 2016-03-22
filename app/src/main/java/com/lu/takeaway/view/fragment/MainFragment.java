package com.lu.takeaway.view.fragment;

import android.annotation.SuppressLint;
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

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bean.EventBean;
import bean.Manager.FoodManager;
import db.OrderManager;
import de.greenrobot.event.EventBus;
import util.DateUtil;
import util.LogUtil;

@SuppressLint("NewApi")
public class MainFragment extends BaseFragment implements IFoodView {
	protected static final String TAG = "MainFragment";

	private XListView mineList;

	private FoodAdapter luAdapter;
//	private List<FoodBean> foodBeans;
//
	private List<FoodBean> allfoods=new ArrayList<>();

//	private int count = 2;
	private OrderManager orderManager;

	private FoodManager foodManager;
	private boolean refresh=true;
	private BitmapUtils bitmapUtils;
	private boolean isRefresh=false;
	private FoodPersenter foodPersenter;
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {

		};
	};

	public MainFragment() {
		EventBus.getDefault().register(this);
		foodPersenter=new FoodPersenter(this);

		initData();
		LogUtil.d(this,"MainFragment()..........");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_main, container, false);
		mineList = (XListView) view.findViewById(R.id.mineList);
//		orderManager = OrderManager.getDefault();
//		foodManager = FoodManager.getDefault();
//		foodBeans = new ArrayList<FoodBean>();
//		allfoods=foodManager.getAllfoods();
		luAdapter = new FoodAdapter(getActivity(), allfoods, R.layout.item_food_listview);
		mineList.setAdapter(luAdapter);
		mineList.setPullLoadEnable(false);
		mineList.setXListViewListener(new MyXListViewListener());
		bitmapUtils=new BitmapUtils(getActivity());
		LogUtil.d(this,"onCreateView()..........");

		return view;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		LogUtil.d(TAG, "onResume");
		luAdapter.notifyDataSetChanged();
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
//		foodManager.queyrFood(0, new RequestCallBackUtil(getActivity(), this));
//		luAdapter.notifyDataSetChanged();
		isRefresh=false;
		foodPersenter.queryAllFood(null);
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

		showToast("loadDataSucess");
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
		showToast("loadDataFaild");
	}

	class MyXListViewListener implements XListView.IXListViewListener {
		@Override
		public void onRefresh() {
//			foodManager.refreshData(true);
			refresh=true;
//			foodManager.queyrFood(allfoods.size(),new RequestCallBackUtil(getActivity(), MainFragment.this));
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

		public FoodAdapter(Context context, List<FoodBean> datas, int mItemLayoutId) {
			super(context, datas, R.layout.item_food_listview);

		}

		@Override
		public void convert(ViewHolder helper, final FoodBean item) {
			helper.setString(R.id.itemName, item.name);
//			helper.setBackgroundResource(R.id.itemPicture, item.getRes());
			helper.setString(R.id.itemPrice, "$" + item.price);

//			double d=1;
//			double price=1;
//			try{
//				price=Double.parseDouble(item.price);
//				d=Double.parseDouble(item.sale);
//			}catch (Exception e){
//				e.printStackTrace();
//			}

			helper.setString(R.id.itemSalePrice, "$" + item.sale*item.price);
			((TextView) helper.getView(R.id.itemPrice)).getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

			final Button btnDianCai = helper.getView(R.id.btnDianCai);

			if(!TextUtils.isEmpty(item.pictureUrl)&&item.pictureUrl.length()>10){
				LogUtil.d(TAG, "item.getPicture():"+item.pictureUrl);
				bitmapUtils.configDefaultLoadFailedImage(R.mipmap.menu_icon_home);
				bitmapUtils.display(helper.getView(R.id.itemPicture), item.pictureUrl);
			}
			boolean isBooked=false;
			for(OrderBean orderBean:mContext.getSelectedOrderBean()){
				if(orderBean.ofoodname.equals(item.name)){
					isBooked=true;
					break;
				}
			}

//			btnDianCai.setText(getString(item.isOrdered() ? R.string.ordered : R.string.dingcai));
			btnDianCai.setText(isBooked ? R.string.ordered : R.string.dingcai);
			btnDianCai.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					if (btnDianCai.getText().equals(getString(R.string.dingcai))) {
//
						OrderBean orderBean = new OrderBean();

						orderBean.oid= mContext.getMaxOrderId()+1;
						mContext.setMaxOrderId(orderBean.oid);
						orderBean.oprice=item.price*item.sale;
						orderBean.onumber=1;
						orderBean.odate=DateUtil.formateDate();
						orderBean.ofoodname=item.name;
						orderBean.ousername=mContext.getCurrenUserBean().getUsername();
						mContext.getSelectedOrderBean().add(orderBean);
//						orderBean.setFoodBean(item);
//						LogUtil.d(TAG, "id:"+item.getId());
//						orderBean.setId(orderManager.getOrders().size());
//						orderBean.setNumber(1);
//						orderBean.setDate(DateUtil.formateDate());
//						orderBean.setNote("加急");
//						orderBean.setFinished("未配送");
//						orderBean.setUser(DingDanApplication.getDefault().getCurrenUserBean());
//
						btnDianCai.setText(getString(R.string.ordered));
//						item.setOrdered(true);
//
//						orderManager.getOrders().add(orderBean);
////						orderManager.getOrderBean().getGoods().add(arg0)
					}
				}
			});
//		@Override
//		public void convert(ViewHolder helper, final FoodBean item) {
//			helper.setString(R.id.itemName, item.getName());
////			helper.setBackgroundResource(R.id.itemPicture, item.getRes());
//			helper.setString(R.id.itemPrice, "$" + item.getPrice());
//
//			helper.setString(R.id.itemSalePrice, "$" + item.getSalePrice());
//			((TextView) helper.getView(R.id.itemPrice)).getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
//
//			final Button btnDianCai = helper.getView(R.id.btnDianCai);
//
//			LogUtil.d(TAG, "item.getPicture():"+item.getPicture());
//			bitmapUtils.display(helper.getView(R.id.itemPicture), item.getPicture());
//			btnDianCai.setText(getString(item.isOrdered() ? R.string.ordered : R.string.dingcai));
//			btnDianCai.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View arg0) {
//					// TODO Auto-generated method stub
//					if (btnDianCai.getText().equals(getString(R.string.dingcai))) {
//
//						OrderBean orderBean = new OrderBean();
//						orderBean.setFoodBean(item);
//						LogUtil.d(TAG, "id:"+item.getId());
//						orderBean.setId(orderManager.getOrders().size());
//						orderBean.setNumber(1);
//						orderBean.setDate(DateUtil.formateDate());
//						orderBean.setNote("加急");
//						orderBean.setFinished("未配送");
//						orderBean.setUser(DingDanApplication.getDefault().getCurrenUserBean());
//
//						btnDianCai.setText(getString(R.string.ordered));
//						item.setOrdered(true);
//
//						orderManager.getOrders().add(orderBean);
////						orderManager.getOrderBean().getGoods().add(arg0)
//					}
//				}
//			});

		}

		@Override
		public void convert(ViewHolder helper, int position) {
			// TODO Auto-generated method stub
//			super.convert(helper, position);
		}
	}

	public void onSuccess(String result) {
//		Gson gson=new Gson();
//		List<FoodBean> foodBeans=gson.fromJson(result, new TypeToken<List<FoodBean>>(){}.getType());
//		foodManager.addFoodBeans(refresh,foodBeans);
//		luAdapter.notifyDataSetChanged();
	}

	@Override
	public void currentSelected() {
		// TODO Auto-generated method stub
		LogUtil.d(TAG, "currentSelected");


		luAdapter.notifyDataSetChanged();
	}
}
