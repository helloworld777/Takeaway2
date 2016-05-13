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
import com.lu.takeaway.view.IView;
import com.lu.takeaway.view.adapter.LuAdapter;
import com.lu.takeaway.view.adapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import util.Debug;
import util.DialogUtil;
import util.LogUtil;
import util.StringUtil;

@SuppressLint("NewApi")
public class BookFragment extends BaseFragment implements OnClickListener,IView {
	protected static final String TAG = "BookFragment";
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
	private List<OrderBean> orderBeanList=new ArrayList<>();
	public BookFragment(){

		orderPersenter=new OrderPersenter();
		orderPersenter.setiView(this);
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
		setListViewEmptyView(listView,null);
		listView.setAdapter(adapter);

		Debug.d(this,"onCreateView..............");

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
//		orderBeanList=mContext.getSelectedOrderBean();
		adapter.replaceAll(mContext.getSelectedOrderBean());
		isExistBook();
		for (int i = 0; i < orderBeanList.size(); i++) {
			OrderBean orderBean=orderBeanList.get(i);
			foodNumber += orderBean.onumber;
			totalPrice += orderBean.onumber*orderBean.oprice ;
		}
		tvTotalFoodNumber.setText("" + foodNumber);
		tvTotalPrice.setText("$" + totalPrice);
	}
	private void isExistBook(){
		if (orderBeanList.size() == 0) {
//			tvNoOrderTips.setVisibility(View.VISIBLE);
			rlDiancai.setVisibility(View.GONE);
//			listView.setVisibility(View.GONE);
		} else {
//			tvNoOrderTips.setVisibility(View.GONE);
			rlDiancai.setVisibility(View.VISIBLE);
//			listView.setVisibility(View.VISIBLE);
		}

	}
	@Override
	public void loadDataSuccess() {
		showToast("loadDataSuccess");
		mContext.getSelectedOrderBean().clear();
		initData();
//		showToast( "支付成功" + "$" + totalPrice);
	}

	@Override
	public void loadDataFaild() {
		showToast("loadDataFaild");
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		d("onHiddenChanged hidden:"+hidden);
	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
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
			mContext.getBitmapUtils().display(helper.getView(R.id.ivFoodPic),item.opicture);
		}

		@Override
		public void convert(ViewHolder helper, final int position) {
			// TODO Auto-generated method stub
			Button add = helper.getView(R.id.btnAdd);
			Button delete = helper.getView(R.id.btnDelete);
			add.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					addNumber(position);
				}
			});
			delete.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					subNumber(position);
				}
			});
		}
	}
	private void subNumber(int position){
		OrderBean bean = mContext.getSelectedOrderBean().get(position);
		if (bean.onumber == 1) {
			mContext.getSelectedOrderBean().remove(position);
			orderBeanList.remove(position);
			isExistBook();
		} else {
			bean.onumber--;
		}
		foodNumber--;
		totalPrice -= bean.getTotalPrice();
		tvTotalFoodNumber.setText("" + foodNumber);
		tvTotalPrice.setText("$" + StringUtil.formatNumber(totalPrice));
		adapter.notifyDataSetChanged();
	}
	private void addNumber(int position) {
		OrderBean bean =mContext.getSelectedOrderBean().get(position);
		bean.onumber++;

		foodNumber++;
		totalPrice += bean.getTotalPrice();
		tvTotalFoodNumber.setText("" + foodNumber);
		tvTotalPrice.setText("$" + totalPrice);

		adapter.notifyDataSetChanged();
	}
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btnDingDan:
			DialogUtil.showMessageAlertDialog(getActivity(), "提交订单", "点击确定去支付", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					orderPersenter.commitOrder(getActivity(),"");
				}

			});
			break;
		default:
			break;
		}
	}

	@Override
	public void currentSelected() {
		LogUtil.d(TAG, "currentSelected");
		initData();
		adapter.notifyDataSetChanged();
	}
}
