package com.lu.takeaway.view.activity;

import android.content.Context;
import android.graphics.Paint;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lu.takeaway.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.lu.takeaway.view.adapter.LuAdapter;
import com.lu.takeaway.view.adapter.ViewHolder;
import bean.EventBean;
import bean.FoodBean;
import bean.OrderBean;
import com.lu.takeaway.model.db.FoodManager;
import com.lu.takeaway.model.db.OrderManager;
import de.greenrobot.event.EventBus;
import com.lu.takeaway.util.Constants;
import com.lu.takeaway.util.DateUtil;
import com.lu.takeaway.util.LogUtil;
import com.lu.takeaway.util.SaveDataUtil;

@ContentView(R.layout.activity_search)
public class SearchActivity extends BaseFragmentActivity {

	protected static final String TAG = "SearchActivity";
	@ViewInject(R.id.tvTitle)
	private TextView tvTitle;
	@ViewInject(R.id.etKey)
	private AutoCompleteTextView etKey;

	@ViewInject(R.id.ivSearch)
	private ImageView ivSearch;

	@ViewInject(R.id.listview)
	private ListView listview;

	@ViewInject(R.id.tvNoSearchTips)
	private TextView tvNoSearchTips;

	private SearchFoodAdapter searchFoodAdapter;

	private FoodManager foodManager;

	private List<String> searchKey;
	private LuAdapter<String> av;
	private String history;

	private String[] historys;
	
	private InputMethodManager imm;
	private SearchHistoryAdater searchHistoryAdater;

	@OnClick({R.id.ivSearch,R.id.ivBack})
	@Override
	protected void viewClick(View view) {
		switch (view.getId()) {
		case R.id.ivSearch:
			search();
			break;
		case R.id.ivBack:
			finish();
			break;
		default:
			break;
		}
	}

	private void search() {
		String key = etKey.getText().toString().trim();
		foodManager.searchFoodBean(key);
		saveHistory();
		searchFoodAdapter.notifyDataSetChanged();
		if (foodManager.getSearchFoodBeans().size() == 0) {
			tvNoSearchTips.setVisibility(View.VISIBLE);
			listview.setVisibility(View.GONE);
		} else {
			tvNoSearchTips.setVisibility(View.GONE);
			listview.setVisibility(View.VISIBLE);
		}

		imm.hideSoftInputFromWindow(etKey.getWindowToken(), 0);
		etKey.clearFocus();
	}

	@Override
	protected void bindData() {
		imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		
		searchKey = new ArrayList<String>();
		etKey.setVisibility(View.VISIBLE);
//		historys = readHistory();
		searchKey.addAll(Arrays.asList(readHistory()));
		;searchHistoryAdater=new SearchHistoryAdater(this, searchKey, R.layout.item_search_history);
		etKey.setAdapter(searchHistoryAdater);
		etKey.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
				// TODO Auto-generated method stub
				// historys=readHistory();
				// av = new ArrayAdapter<String>(SearchActivity.this,
				// android.R.layout.simple_dropdown_item_1line, historys);
				// etKey.setAdapter(av);
				LogUtil.d(TAG, "onEditorAction");
				// etKey.showDropDown();
				return true;
			}
		});
		etKey.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				etKey.setText(historys[arg2]);
			}
		});
		etKey.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				searchHistoryAdater.getFilter().filter(arg0);
				LogUtil.d(TAG, "onTextChanged arg0:"+arg0+",arg1:"+arg1+",arg2:"+arg2+",arg3:"+arg3);
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
			}
		});
		etKey.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				LogUtil.d(TAG, "setOnFocusChangeListener");
				LogUtil.d(TAG, "hasFocus:"+hasFocus);
				if(hasFocus){
					etKey.showDropDown();
				}
			}
		});
		tvTitle.setText(getString(R.string.food_search));
		foodManager = FoodManager.getDefault();
		searchFoodAdapter = new SearchFoodAdapter(this, foodManager.getSearchFoodBeans(), R.layout.item_food_listview);
		listview.setAdapter(searchFoodAdapter);
		imm.hideSoftInputFromWindow(etKey.getWindowToken(), 0);
	}

	private String[] readHistory() {
		history = SaveDataUtil.getString(this, Constants.HISTORY);
		String[] historys = history.split(",");
		if (historys.length > 5) {
			String[] newArraysStrings = new String[5];
			System.arraycopy(historys, 0, newArraysStrings, 0, 5);
			return newArraysStrings;
		}
		return historys;
	}

	private void saveHistory() {
		String addText = etKey.getText().toString();
		if (!history.contains(addText + ",")) {
			StringBuilder sb = new StringBuilder(history);
			sb.insert(0, addText + ",");
			searchKey.add(addText);
			SaveDataUtil.setString(this, Constants.HISTORY, sb.toString());
		}
	}
	class SearchHistoryAdater extends LuAdapter<String> implements Filterable {

		public SearchHistoryAdater(Context context, List<String> datas, int mItemLayoutId) {
			super(context, datas, mItemLayoutId);
		}
		@Override
		public void convert(ViewHolder helper, String item) {
			// TODO Auto-generated method stub
			helper.setString(R.id.tvSearchHistory, item);
		}
		@Override
		public Filter getFilter() {
			// TODO Auto-generated method stub
			return new Filter(){

				@Override
				protected void publishResults(CharSequence arg0, FilterResults arg1) {
					notifyDataSetChanged();
					LogUtil.d(TAG, "publishResults arg0:"+arg0);
					LogUtil.d(TAG, "publishResults arg1:"+arg1);
				}
				
				@Override
				protected FilterResults performFiltering(CharSequence arg0) {
					// TODO Auto-generated method stub
					LogUtil.d(TAG, "performFiltering arg0:"+arg0);
					FilterResults filterResults=new FilterResults();
//					if(!searchKey.contains(arg0)){
//						searchKey.add(arg0.toString());
//					}
					filterResults.values=searchKey;
					filterResults.count=searchKey.size();
					return filterResults;
				}
				
			};
		}
		
	}
	class SearchFoodAdapter extends LuAdapter<FoodBean> {

		public SearchFoodAdapter(Context context, List<FoodBean> datas, int mItemLayoutId) {
			super(context, datas, mItemLayoutId);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void convert(ViewHolder helper, final FoodBean item) {
			// TODO Auto-generated method stub
			helper.setString(R.id.itemName, item.getName());
			helper.setBackgroundResource(R.id.itemPicture, item.getRes());
			helper.setString(R.id.itemPrice, "$" + item.getPrice());

			helper.setString(R.id.itemSalePrice, "$" + item.getSalePrice());
			((TextView) helper.getView(R.id.itemPrice)).getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

			final Button btnDianCai = helper.getView(R.id.btnDianCai);

			btnDianCai.setText(getString(item.isOrdered() ? R.string.ordered : R.string.dingcai));
			btnDianCai.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					if (btnDianCai.getText().equals(getString(R.string.dingcai))) {
						OrderBean orderBean = new OrderBean();
						orderBean.setFoodBean(item);
						orderBean.setId(OrderManager.getDefault().getOrders().size());
						orderBean.setNumber(1);
						orderBean.setDate(DateUtil.formateDate());
						OrderManager.getDefault().getOrders().add(orderBean);
						btnDianCai.setText(getString(R.string.ordered));
						item.setOrdered(true);
						EventBus.getDefault().post(new EventBean(EventBean.EventType.ADD_ORDER, null));
					}
				}
			});
		}
	}
}
