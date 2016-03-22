package com.lu.takeaway.view.activity;

import android.content.Context;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lu.takeaway.R;
import com.lu.takeaway.view.adapter.LuAdapter;
import com.lu.takeaway.view.adapter.ViewHolder;
import com.lu.takeaway.view.app.DingDanApplication;

import java.util.ArrayList;
import java.util.List;

import bean.Manager.OrderBeanManager;
import bean.OrderBean;
import util.DateUtil;
import util.DialogUtil;
import util.LogUtil;

@ContentView(R.layout.activity_order)
public class OrderActivity extends BaseFragmentActivity{
	
	@ViewInject(R.id.listview)
	private ListView listView;
	
	
	private LuAdapter<OrderBean> orderAdapter;
	
	private List<OrderBean> orderBeans;
	private OrderBeanManager orderBeanManager;


	public String TAG="OrderActivity";
	
	@ViewInject(R.id.tvNoOrderTips)
	private TextView tvNoOrderTips;
	@ViewInject(R.id.tvTitle)
	private TextView tvTitle;
	
	@OnClick({R.id.ivBack})
	@Override
	protected void viewClick(View view) {
		switch (view.getId()) {
		case R.id.ivBack:
			finish();
			break;
		default:
			break;
		}
	}

	@Override
	protected void bindData() {
		// TODO Auto-generated method stub
		tvTitle.setText(getString(R.string.title_activity_order));
		orderBeans=new ArrayList<OrderBean>();
		orderAdapter=new OrderAdapter(getApplicationContext(), orderBeans, R.layout.item_order_listview);
		listView.setAdapter(orderAdapter);
		
		orderBeanManager=new OrderBeanManager();
		int uid= DingDanApplication.getDefault().getCurrenUserBean().getId();
		orderBeanManager.queryOrder(uid, new MyRequestCallBack());
	}
	class MyRequestCallBack extends RequestCallBack<String>{

		@Override
		public void onFailure(HttpException arg0, String arg1) {
			DialogUtil.showToast(getApplicationContext(), getString(R.string.load_data_faild));
		}

		@Override
		public void onSuccess(ResponseInfo<String> arg0) {
			String result=arg0.result;
			LogUtil.d(TAG, "resutl:"+result);
			Gson gson=new Gson();
			orderBeans.clear();
			List<OrderBean> tempOrderBeans=gson.fromJson(result, new TypeToken<List<OrderBean>>(){}.getType());
			if(tempOrderBeans.size()>0){
				orderBeans.addAll(tempOrderBeans);
				tvNoOrderTips.setVisibility(View.GONE);
				listView.setVisibility(View.VISIBLE);
				orderAdapter.notifyDataSetChanged();
			}else{
				tvNoOrderTips.setVisibility(View.VISIBLE);
				listView.setVisibility(View.GONE);
			}
		}
		
	}
	class OrderAdapter extends LuAdapter<OrderBean>{

		public OrderAdapter(Context context, List<OrderBean> datas, int mItemLayoutId) {
			super(context, datas, mItemLayoutId);
		}
		@Override
		public void convert(ViewHolder helper, OrderBean item) {
			helper.setString(R.id.tvFoodName, item.getFoodBean().getName());
			helper.setString(R.id.tvFoodCount, ""+item.getFoodBean().getNumber());
			helper.setString(R.id.tvDate, DateUtil.orderDate(item.getDingdandate()));
			
			DingDanApplication.getDefault().getBitmapUtils().display(helper.getView(R.id.image), item.getFoodBean().getPicture());
			TextView textView=helper.getView(R.id.tvFinished);
			helper.setString(R.id.tvFinished, item.isFinished());
			if(item.isFinished().equals("已派送")){
				textView.setTextColor(getResources().getColor(R.color.orderFinished));
			}else{
				textView.setTextColor(getResources().getColor(R.color.orderUnfinished));
			}
		}
	}
}
