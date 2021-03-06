package com.lu.takeaway.view.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lu.takeaway.R;
import com.lu.takeaway.bean.OrderBean;
import com.lu.takeaway.persenter.OrderPersenter;
import com.lu.takeaway.util.DialogUtil;
import com.lu.takeaway.util.LogUtil;
import com.lu.takeaway.util.SaveDataUtil;
import com.lu.takeaway.view.IOrderView;
import com.lu.takeaway.view.adapter.LuAdapter;
import com.lu.takeaway.view.adapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("NewApi")
public class OrderFragment extends BaseFragment implements OnClickListener,IOrderView {
	protected static final String TAG = "OrderFragment";
	private ListView listView;
	private OrderAdapter adapter;
//	private OrderManager orderManager;

	private TextView tvTotalFoodNumber;
	private TextView tvTotalPrice, tvNoOrderTips;
	private Button btnDingDan;

	private int foodNumber;
	private double totalPrice;

	private RelativeLayout rlDiancai;
	private int _id = 0;
	private static final String ORDER_ID = "orderId";
	private OrderPersenter orderPersenter;
	private List<com.lu.takeaway.bean.OrderBean> orderBeanList=new ArrayList<>();
	public OrderFragment(){

		orderPersenter=new OrderPersenter(this);

	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//		orderManager = OrderManager.getDefault();
		View view = inflater.inflate(R.layout.fragment_order, container, false);
		listView = (ListView) view.findViewById(R.id.listview);
		tvTotalFoodNumber = (TextView) view.findViewById(R.id.tvTotalFoodNumber);
		tvTotalPrice = (TextView) view.findViewById(R.id.tvTotalPrice);
		btnDingDan = (Button) view.findViewById(R.id.btnDingDan);

		tvNoOrderTips = (TextView) view.findViewById(R.id.tvNoOrderTips);
		rlDiancai = (RelativeLayout) view.findViewById(R.id.rlDiancai);

		btnDingDan.setOnClickListener(this);
		adapter = new OrderAdapter(getActivity(), orderBeanList, R.layout.item_menu_listview);
		listView.setAdapter(adapter);
		LogUtil.d(this, "onCreateView..............");


		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		LogUtil.d(TAG, "onResume");
		initData();
	}

	@Override
	public void onStart() {
		super.onStart();
		LogUtil.d(TAG, "onStart");
	}

	public void initData() {
//		orderPersenter.queryOrder("lyw");
		foodNumber = 0;
		totalPrice = 0;

	}

	@Override
	public void getOrderBeanSuccess(List<com.lu.takeaway.bean.OrderBean> orderBeanList) {
		adapter.replaceAll(orderBeanList);

		if (orderBeanList.size() == 0) {

			tvNoOrderTips.setVisibility(View.VISIBLE);
			rlDiancai.setVisibility(View.GONE);
			listView.setVisibility(View.GONE);
		} else {
			tvNoOrderTips.setVisibility(View.GONE);
			rlDiancai.setVisibility(View.VISIBLE);
			listView.setVisibility(View.VISIBLE);
		}
		for (int i = 0; i < orderBeanList.size(); i++) {
			OrderBean orderBean=orderBeanList.get(i);
			foodNumber += orderBean.onumber;
			totalPrice += orderBean.onumber*orderBean.oprice ;
		}
		tvTotalFoodNumber.setText("" + foodNumber);
		tvTotalPrice.setText("$" + totalPrice);

		_id = SaveDataUtil.getInt(getActivity(), ORDER_ID);
	}

	@Override
	public void getOrderBeanFaild() {
		showToast("getOrderBeanFaild");
	}


	class OrderAdapter extends LuAdapter<OrderBean> {
		public OrderAdapter(Context context, List<OrderBean> datas, int mItemLayoutId) {
			super(context, datas, R.layout.item_menu_listview);
		}

		@Override
		public void convert(ViewHolder helper, OrderBean item) {
			helper.setString(R.id.tvFoodName, item.ofoodname);
			helper.setString(R.id.tvFoodPrice, "$" + item.oprice);
			helper.setString(R.id.tvFoodNum, "" + item.onumber);
			// helper.setBackgroundResource(R.id.ivFoodPic,
			// item.getFoodBean().getRes());
//			if(!TextUtils.isEmpty(item.pictureUrl)&&item.pictureUrl.length()>10){
//				LogUtil.d(TAG, "item.getPicture():"+item.pictureUrl);
//				bitmapUtils.configDefaultLoadFailedImage(R.mipmap.menu_icon_home);
//				bitmapUtils.display(helper.getView(R.id.itemPicture), item.pictureUrl);
//			}
//			DingDanApplication.getDefault().getBitmapUtils().display(helper.getView(R.id.ivFoodPic), item.getFoodBean().getPicture());
		}

		@Override
		public void convert(ViewHolder helper, final int position) {
			// TODO Auto-generated method stub
			Button add = helper.getView(R.id.btnAdd);
			Button delete = helper.getView(R.id.btnDelete);

//			add.setOnClickListener(new OnClickListener() {
//				@Override
//				public void onClick(View arg0) {
//					OrderBean bean = orderManager.getOrders().get(position);
//					bean.setNumber(bean.getNumber() + 1);
//					adapter.notifyDataSetChanged();
//					foodNumber++;
//					totalPrice += bean.getFoodBean().getSalePrice();
//					tvTotalFoodNumber.setText("" + foodNumber);
//					tvTotalPrice.setText("$" + totalPrice);
//				}
//			});
//			delete.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View arg0) {
//					// TODO Auto-generated method stub
//					OrderBean bean = orderManager.getOrders().get(position);
//					if (bean.getNumber() == 1) {
//						orderManager.getOrders().remove(position);
//						int index = FoodManager.getDefault().getAllfoods().indexOf(bean.getFoodBean());
//						LogUtil.d(TAG, "index:" + index);
//						FoodManager.getDefault().getAllfoods().get(index).setOrdered(false);
//
//						EventBus.getDefault().post(new EventBean(EventBean.EventType.REMOVE_ORDER, null));
//					} else {
//						bean.setNumber(bean.getNumber() - 1);
//					}
//					totalPrice -= bean.getFoodBean().getSalePrice();
//					tvTotalFoodNumber.setText("" + foodNumber);
//					tvTotalPrice.setText("$" + totalPrice);
//					adapter.notifyDataSetChanged();
//				}
//			});
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btnDingDan:
			DialogUtil.showMessageAlertDialog(getActivity(), "提交订单", "点击确定去支付", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					DialogUtil.showToast(getActivity(), "支付成功" + "$" + totalPrice);

//					OrderBeanManager manager = new OrderBeanManager();
//					for (OrderBean bean : orderManager.getOrders()) {
//						bean.setId(_id++);
//						System.out.println(bean.getId());
//						manager.commitOrder(bean, new RequestCallBack<String>() {
//							@Override
//							public void onSuccess(ResponseInfo<String> arg0) {
//								// TODO Auto-generated method stub
//								DialogUtil.showToast(getActivity(), "success");
//								orderManager.getOrders().clear();
//								FoodManager.getDefault().setFoodBeansNoOrder();
//								initData();
//							}
//
//							@Override
//							public void onFailure(HttpException arg0, String arg1) {
//								// TODO Auto-generated method stub
//								DialogUtil.showToast(getActivity(), "faild");
//								arg0.printStackTrace();
//							}
//						});
//					}
//					SaveDataUtil.putInt(getActivity(), ORDER_ID, _id);
				}

			});
			break;

		default:
			break;
		}
	}

	@Override
	public void currentSelected() {
		// TODO Auto-generated method stub
		LogUtil.d(TAG, "currentSelected");
//		LogUtil.d(TAG, " orderManager.getOrders():"+ orderManager.getOrders().size());
		initData();
		adapter.notifyDataSetChanged();
	}
}
