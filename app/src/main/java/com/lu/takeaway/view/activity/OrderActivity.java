package com.lu.takeaway.view.activity;

import android.content.Context;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lu.takeaway.R;
import com.lu.takeaway.bean.OrderBean;
import com.lu.takeaway.persenter.OrderPersenter;
import com.lu.takeaway.view.IOrderView;
import com.lu.takeaway.view.adapter.LuAdapter;
import com.lu.takeaway.view.adapter.ViewHolder;
import com.lu.takeaway.view.app.DingDanApplication;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_order)
public class OrderActivity extends BaseFragmentActivity implements IOrderView{
	
	@ViewInject(R.id.listview)
	private ListView listView;
	
	
	private LuAdapter<OrderBean> orderAdapter;
	
	private List<OrderBean> orderBeans;
//	private OrderBeanManager orderBeanManager;

	private OrderPersenter orderPersenter;
	public String TAG="OrderActivity";
	
	@ViewInject(R.id.tvNoOrderTips)
	private TextView tvNoOrderTips;
	@ViewInject(R.id.tvTitle)
	private TextView tvTitle;
	
	@OnClick({R.id.rlBack})
	@Override
	protected void viewClick(View view) {
		switch (view.getId()) {
		case R.id.rlBack:
			finish();
			break;
		default:
			break;
		}
	}

	@Override
	protected void bindData() {
		tvTitle.setText(getString(R.string.title_activity_order));
		orderBeans=new ArrayList<OrderBean>();
		orderAdapter=new OrderAdapter(getApplicationContext(), orderBeans, R.layout.item_order_listview);
		listView.setAdapter(orderAdapter);
		orderPersenter=new OrderPersenter(this);
		orderPersenter.queryOrder(DingDanApplication.getDefault().getCurrenUserBean().lusername);
		setListViewEmptyView(listView,null);
//		orderBeanManager=new OrderBeanManager();
//		int uid= DingDanApplication.getDefault().getCurrenUserBean().getId();
//		orderBeanManager.queryOrder(uid, new MyRequestCallBack());
	}

	@Override
	public void getOrderBeanSuccess(List<com.lu.takeaway.bean.OrderBean> orderBeanList) {
		orderAdapter.replaceAll(orderBeanList);
//		if(orderBeanList.size()>0){
//			tvNoOrderTips.setVisibility(View.GONE);
//			listView.setVisibility(View.VISIBLE);
//		}else{
//			tvNoOrderTips.setVisibility(View.VISIBLE);
//			listView.setVisibility(View.GONE);
//		}
//		listView.setEmptyView();
	}

	@Override
	public void getOrderBeanFaild() {
		showToast(getString(R.string.load_data_faild));
	}

	class OrderAdapter extends LuAdapter<OrderBean>{

		public OrderAdapter(Context context, List<OrderBean> datas, int mItemLayoutId) {
			super(context, datas, R.layout.item_order_listview);
		}
		@Override
		public void convert(ViewHolder helper, OrderBean item) {
			helper.setString(R.id.tvFoodName, item.ofoodname);
			helper.setString(R.id.tvFoodCount, ""+item.onumber);
			helper.setString(R.id.tvDate, item.odate);
			helper.setString(R.id.tvFoodTotalPrice, item.getTotalPriceToString());

			DingDanApplication.getDefault().getBitmapUtils().display(helper.getView(R.id.image), item.opicture);
			TextView textView=helper.getView(R.id.tvFinished);
			helper.setString(R.id.tvFinished, item.ofinished);
			if(item.ofinished.equals(getString(R.string.orderFinished))){
				textView.setTextColor(getResources().getColor(R.color.orderFinished));
			}else if(item.ofinished.equals(getString(R.string.orderFinishing))){
				textView.setTextColor(getResources().getColor(R.color.orderFinishing));
			}else if(item.ofinished.equals(getString(R.string.orderUnFinish))){
				textView.setTextColor(getResources().getColor(R.color.orderUnfinish));
			}
		}
	}
}
