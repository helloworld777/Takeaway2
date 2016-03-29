package com.lu.takeaway.view.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lu.takeaway.R;
import com.lu.takeaway.bean.UserBean;
import com.lu.takeaway.persenter.OrderPersenter;
import com.lu.takeaway.persenter.UserPersenter;
import com.lu.takeaway.view.IUserLoginView;
import com.lu.takeaway.view.adapter.ViewPagerAdapter;
import com.lu.takeaway.view.app.DingDanApplication;
import com.lu.takeaway.view.fragment.BaseFragment;
import com.lu.takeaway.view.fragment.BookFragment;
import com.lu.takeaway.view.fragment.MainFragment;
import com.lu.takeaway.view.fragment.UserFragment;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXTextObject;

import java.util.Arrays;
import java.util.List;

import bean.EventBean;
import cn.bmob.push.BmobPush;
import cn.bmob.sms.BmobSMS;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import de.greenrobot.event.EventBus;
import util.Constants;
import util.DialogUtil;
import util.LogUtil;

@ContentView(value = R.layout.activity_main_new)
public class MainActivity extends BaseFragmentActivity implements Constants, IUserLoginView {
    @ViewInject(android.R.id.tabhost)
    private FragmentTabHost mTabHost;
    private MainFragment mainFragment;
    private UserFragment userFragment;
    private BookFragment orderFragment;
    private int[] selectedImage = {R.mipmap.takeout_ic_poi_selected, R.mipmap.takeout_ic_order_selected, R.mipmap.takeout_ic_user_selected};
    private int[] unSelectedImage = {R.mipmap.takeout_ic_poi_normal, R.mipmap.takeout_ic_order_normal, R.mipmap.takeout_ic_user_normal};
    private String mTextArray[];

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
        initVariable();
        new OrderPersenter().queryOrderMaxId(userBean.lusername);
        viewpager.setAdapter(adapter);
        viewpager.addOnPageChangeListener(new MyOnPageChangeListener());
        setMenuSeleted();
        initBmob();

//        sharedWeiXin();
    }

    private class MyOnPageChangeListener implements OnPageChangeListener {
        @Override
        public void onPageSelected(int arg0) {
            position = arg0;
            setMenuSeleted();
            fragments[arg0].currentSelected();
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
    }

    private void initVariable() {
        userPersenter = new UserPersenter(this);
        ivBack.setVisibility(View.GONE);
        userBean = DingDanApplication.getDefault().getCurrenUserBean();
        mTextArray = new String[]{getString(R.string.title_activity_main), getString(R.string.title_activity_book), getString(R.string.user)};
        mainFragment = new MainFragment();
        userFragment = new UserFragment();
        orderFragment = new BookFragment();

        fragments = new BaseFragment[]{mainFragment, orderFragment, userFragment};
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        menuViewHolders = Arrays.asList(new MenuViewHolder[]{new MenuViewHolder(ivHome, tvHome), new MenuViewHolder(ivOrder, tvOrder), new MenuViewHolder(ivUser, tvUser)});
    }

    private void initBmob() {
        Bmob.initialize(this, Constants.Bmob_APPID);
        BmobSMS.initialize(this, Constants.Bmob_APPID);
        // 使用推送服务时的初始化操作
        BmobInstallation.getCurrentInstallation(this).save();
        // 启动推送服务
        BmobPush.startWork(this);
    }


    @OnClick({R.id.rlHome, R.id.rlOrder, R.id.rlUser, R.id.ivSearch})
    public void viewClick(View view) {
        switch (view.getId()) {
            case R.id.rlHome:
                position = 0;
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

        if (position == 0) {
            searchFood();
        } else if (position == 1) {
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
        ivSearch.setVisibility(position == 0 ? View.VISIBLE : View.INVISIBLE);
    }

    private void replaceFragment() {
        viewpager.setCurrentItem(position);
        setMenuSeleted();
    }

    public void onEventMainThread(EventBean bean) {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.i(getClass(), "onActivityResult..............resultCode="
                + resultCode);
//        switch (requestCode) {
        UserFragment userFragments = (UserFragment) fragments[position];
        userFragments.chooseHeaderImg(requestCode, resultCode,
                data, this);
//            break;
//        }

    }

    private IWXAPI api;
    private String smg;
    public void sharedWeiXin() {

        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, "wxdbe2c330d4cfb36e", false);
        final EditText editor = new EditText(this);
        editor.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        editor.setText("这是takeaway应用分享的哦");
        smg=editor.getText().toString();
        showAlert(this, "title text",smg, new MyOnClickListener());
    }


    private class MyOnClickListener implements  DialogInterface.OnClickListener{
        @Override
        public void onClick(DialogInterface dialog, int which) {
            String text = smg;
            if (text == null || text.length() == 0) {
                return;
            }

            // 初始化一个WXTextObject对象
            WXTextObject textObj = new WXTextObject();
            textObj.text = text;

            // 用WXTextObject对象初始化一个WXMediaMessage对象
            WXMediaMessage msg = new WXMediaMessage();
            msg.mediaObject = textObj;
            // 发送文本类型的消息时，title字段不起作用
            // msg.title = "Will be ignored";
            msg.description = text;

            // 构造一个Req
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = buildTransaction("text"); // transaction字段用于唯一标识一个请求
            req.message = msg;
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
//                req.scene = isTimelineCb.isChecked() ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;

            // 调用api接口发送数据到微信
            api.sendReq(req);
        }
    }
    public static AlertDialog showAlert(final Context context, final String msg, final String title,DialogInterface.OnClickListener c) {
        if (context instanceof Activity && ((Activity) context).isFinishing()) {
            return null;
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setPositiveButton("ok", c);
        builder.setNegativeButton("cancel",new DialogInterface.OnClickListener() {

            @Override
            public void onClick(final DialogInterface dialog, final int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alert = builder.create();
        alert.show();
        return alert;
    }

    @Override
    public void loginSuccess(UserBean user) {
        userBean = user;
        DingDanApplication.getDefault().setCurrenUserBean(userBean);

    }

    @Override
    public void loginFaild() {

        DialogUtil.showToast(getApplicationContext(), "loadDataFaild");
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

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
}
