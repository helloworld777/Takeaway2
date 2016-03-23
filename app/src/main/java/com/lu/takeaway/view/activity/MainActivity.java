package com.lu.takeaway.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lu.takeaway.R;
import com.lu.takeaway.bean.UserBean;
import com.lu.takeaway.persenter.OrderPersenter;
import com.lu.takeaway.persenter.UserPersenter;
import com.lu.takeaway.view.IUserView;
import com.lu.takeaway.view.adapter.ViewPagerAdapter;
import com.lu.takeaway.view.app.DingDanApplication;
import com.lu.takeaway.view.fragment.BaseFragment;
import com.lu.takeaway.view.fragment.BookFragment;
import com.lu.takeaway.view.fragment.MainFragment;
import com.lu.takeaway.view.fragment.UserFragment;

import java.util.Arrays;
import java.util.List;

import bean.EventBean;
import de.greenrobot.event.EventBus;
import util.Constants;
import util.DialogUtil;
import util.SaveDataUtil;

@ContentView(value = R.layout.activity_main_new)
public class MainActivity extends BaseFragmentActivity implements Constants,IUserView {
	@ViewInject(android.R.id.tabhost)
	private FragmentTabHost mTabHost;
	private MainFragment mainFragment;
	private UserFragment userFragment;
	private BookFragment orderFragment;
	private int[] selectedImage = { R.mipmap.takeout_ic_poi_selected, R.mipmap.takeout_ic_order_selected, R.mipmap.takeout_ic_user_selected };
	private int[] unSelectedImage = { R.mipmap.takeout_ic_poi_normal, R.mipmap.takeout_ic_order_normal, R.mipmap.takeout_ic_user_normal };
	private String mTextArray[] ;

	@ViewInject(value = R.id.ivHome)
	private ImageView ivHome;

	@ViewInject(value = R.id.tvHome)
	private TextView tvHome;

	@ViewInject(value = R.id.ivOrder)
	private ImageView ivOrder;
	
	@ViewInject(value = R.id.ivBack)
	private ImageView ivBack;

	@ViewInject(value = R.id.tvOrder)
	private TextView tvOrder;

	@ViewInject(value = R.id.ivUser)
	private ImageView ivUser;

	@ViewInject(value = R.id.tvUser)
	private TextView tvUser;

	@ViewInject(value = R.id.tvTitle)
	protected TextView tvTitle;

	@ViewInject(value = R.id.ivSearch)
	protected ImageView ivSearch;
	
//	@ViewInject(value=R.id.etKey)
//	protected AutoCompleteTextView etKey;
//	
	
	private BaseFragment[] fragments;
	
	private int position = 0;
	
	
	@SuppressWarnings("unused")
	private UserBean userBean;
	private List<MenuViewHolder> menuViewHolders;

	@ViewInject(value = R.id.viewpager)
	private ViewPager viewpager;
	
	private ViewPagerAdapter adapter;
	private UserPersenter userPersenter;
	
	@SuppressLint("NewApi")
	@Override
	protected void bindData() {
		EventBus.getDefault().register(this);
		userPersenter=new UserPersenter(this);
		ivBack.setVisibility(View.GONE);
//		userBean=(UserBean) getIntent().getSerializableExtra("userbean");
		
		mTextArray = new String[]{ getString(R.string.title_activity_main), getString(R.string.title_activity_book), getString(R.string.user) };
		mainFragment = new MainFragment();
		userFragment = new UserFragment();
		orderFragment = new BookFragment();

		fragments = new BaseFragment[] { mainFragment, orderFragment, userFragment };
		adapter=new ViewPagerAdapter(getSupportFragmentManager(), fragments);
		viewpager.setAdapter(adapter);
		menuViewHolders = Arrays.asList(new MenuViewHolder[] { new MenuViewHolder(ivHome, tvHome), new MenuViewHolder(ivOrder, tvOrder), new MenuViewHolder(ivUser, tvUser) });
		viewpager.addOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				position=arg0;
				setMenuSeleted();
				fragments[arg0].currentSelected();
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		userPersenter.login("lyw","123456");
	}
	

	@OnClick({ R.id.rlHome, R.id.rlOrder, R.id.rlUser,R.id.ivSearch })
	public void viewClick(View view) {
		switch (view.getId()) {
		case R.id.rlHome:
			position=0;
			replaceFragment();
			break;
		case R.id.rlOrder:
			position = 1;
			replaceFragment();
			break;
		case R.id.rlUser:
			position = 2;
			replaceFragment();
			break;
		case R.id.ivSearch:
			search();
			break;
		default:
			break;
		}
	}

	private void search() {
		
		if(position==0){
			searchFood();
		}else if(position==1){
			searchOrder();
		}
	}

	private void searchOrder() {
		
	}

	private void searchFood() {
		startActivityForResultTransition(SearchActivity.class);
	}

	private void setMenuSeleted() {
		for (int i = 0; i < menuViewHolders.size(); i++) {
			if (position == i) {
				menuViewHolders.get(i).setSelected(true, i);
			} else {
				menuViewHolders.get(i).setSelected(false, i);
			}
		}
		tvTitle.setText(mTextArray[position]);

		ivSearch.setVisibility(position == 0 ?  View.VISIBLE:View.INVISIBLE);
//		etKey.setVisibility(position==0?View.VISIBLE:View.INVISIBLE);
	}

	private void replaceFragment() {
		viewpager.setCurrentItem(position);
		setMenuSeleted() ;
	}

	public void onEventMainThread(EventBean bean) {
		
	}
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		super.onActivityResult(arg0, arg1, arg2);
		
//		if(arg2!=null){
//			userBean=(UserBean)arg2.getSerializableExtra("userbean");
//			Fragment com.lu.takeaway.view.activity.fragment=adapter.getItem(viewpager.getCurrentItem());
//			((UserFragment)com.lu.takeaway.view.activity.fragment).update(userBean);
//		}
		
		
	}

	@Override
	public void loginSuccess(UserBean user) {
		userBean=user;
		DingDanApplication.getDefault().setCurrenUserBean(userBean);
		new OrderPersenter().queryOrderMaxId(userBean.getUsername());
	}

	@Override
	public void loginFaild() {

		DialogUtil.showToast(getApplicationContext(),"loadDataFaild");
		userBean=new UserBean();
		userBean.setUsername(SaveDataUtil.getString(this, USERNAME));
		userBean.setPassword("123456");
		DingDanApplication.getDefault().setCurrenUserBean(userBean);
	}

	class MenuViewHolder {
		private ImageView iv;
		private TextView tv;

		public MenuViewHolder(ImageView iv, TextView tv) {
			this.iv = iv;
			this.tv = tv;
		}

		public void setSelected(boolean isSeleted, int index) {
			iv.setBackgroundResource(isSeleted ? selectedImage[index] : unSelectedImage[index]);
			tv.setTextColor(isSeleted ? getResources().getColor(R.color.menuSelected) : Color.BLACK);
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}
}
