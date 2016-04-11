package com.lu.takeaway.view.fragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lu.photolib.ImageFactoryActivity;
import com.lu.photolib.PhotoUtils;
import com.lu.takeaway.R;
import com.lu.takeaway.bean.UserBean;
import com.lu.takeaway.persenter.UserPersenter;
import com.lu.takeaway.view.IView;
import com.lu.takeaway.view.activity.LoginActivity;
import com.lu.takeaway.view.activity.MainActivity;
import com.lu.takeaway.view.activity.OrderActivity;
import com.lu.takeaway.view.activity.UserInfoDetailActivity;
import com.lu.takeaway.view.app.DingDanApplication;
import com.lu.takeaway.view.popwindow.BPopupWindow;
import com.lu.takeaway.view.popwindow.BottomPopupWindow;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UploadFileListener;
import cn.sharesdk.tencent.qzone.QZone;
import util.Constants;
import util.Debug;
import util.FileUtils;
import util.SaveDataUtil;

@SuppressLint("NewApi")
public class UserFragment extends BaseFragment implements OnClickListener, Constants, IView {
    private LinearLayout llLogin;
    private ImageView iv_login;
    private TextView tvLogin;
    private boolean isLogin = false;
    private UserBean userBean;
    private Button btnOrder;
    private RelativeLayout rlOrder, rlBook, rlAbout;
    private UserPersenter userPersenter;
    public static final int INTENT_REQUEST_CODE_CROP = 2;
    public UserFragment() {
        userPersenter = new UserPersenter();
        userPersenter.setiView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        isLogin = SaveDataUtil.getBoolean(getActivity(), Constants.IS_LOGIN);
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        initWidget(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        d("onResume");
        initData();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    protected void initWidget(View view) {
        llLogin = (LinearLayout) view.findViewById(R.id.llLogin);
        tvLogin = (TextView) view.findViewById(R.id.tvLogin);
        tvLogin.setOnClickListener(this);
//		btnOrder=(Button) view.findViewById(R.id.btnOrder);
//		btnOrder.setOnClickListener(this);
        iv_login = findViewById(view, R.id.iv_login);
        iv_login.setOnClickListener(this);
        rlOrder = findViewById(view, R.id.rlOrder);
        rlOrder.setOnClickListener(this);
        rlBook = findViewById(view, R.id.rlBook);
        rlBook.setOnClickListener(this);
        rlAbout = findViewById(view, R.id.rlAbout);
        rlAbout.setOnClickListener(this);

        LinearLayout llUserInfo = findViewById(view, R.id.llUserInfo);
        llUserInfo.setOnClickListener(this);
        initPopupWindow(view);
    }

    @Override
    public void onClick(View v) {
        if (!isLogin) {
            startActivityForResultTransition(new Intent(getActivity(), LoginActivity.class), REQUESTCODE_MAIN_LOGIN);
            return;
        }
        switch (v.getId()) {
            case R.id.tvLogin:
                if (!isLogin) {
                    startActivityForResultTransition(new Intent(getActivity(), LoginActivity.class), REQUESTCODE_MAIN_LOGIN);
                }
                break;
            case R.id.rlAbout:
//                ((MainActivity)getActivity()).sharedWeiXin();
                ((MainActivity)getActivity()).showShare(getActivity(), QZone.NAME,false);
                break;
            case R.id.llUserInfo:
                startActivityTransition(UserInfoDetailActivity.class);
                break;
            case R.id.rlBook:
                break;
            case R.id.rlOrder:
                if (!isLogin) {
                    startActivityForResultTransition(LoginActivity.class, REQUESTCODE_MAIN_LOGIN);
                } else {
                    startActivityTransition(OrderActivity.class);
                }
                break;
            case R.id.iv_login:

                showPopWindow();
                break;
            default:
                break;
        }
    }

    public void chooseHeaderImg(int requestCode, int resultCode, Intent data, Activity context) {
        if (requestCode == PhotoUtils.INTENT_REQUEST_CODE_ALBUM) {
            if (data == null)
                return;

            if (resultCode == Activity.RESULT_OK) {
                if (data.getData() == null) {
                    return;
                }
                String sdStatus = Environment.getExternalStorageState();
                if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
                    Log.i("TestFile",
                            "SD card is not avaiable/writeable right now.");
                    return;
                }
            }
            Uri uri = data.getData();
            String[] proj = {MediaStore.Images.Media.DATA};
            Cursor cursor = context.managedQuery(uri, proj, null, null, null);
            if (cursor != null) {
                int column_index = cursor
                        .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                if (cursor.getCount() > 0 && cursor.moveToFirst()) {
                    String path = cursor.getString(column_index);
                    Bitmap bitmap = BitmapFactory.decodeFile(path);
                    if (PhotoUtils.bitmapIsLarge(bitmap)) {
                        cropPhoto(context, context, path);
                    } else {
                        iv_login.setImageBitmap(bitmap);
                        saveBitmap(path);
                    }
                }
            }
            //拍照
        } else if (requestCode == PhotoUtils.INTENT_REQUEST_CODE_CAMERA) {

            if (resultCode == Activity.RESULT_OK) {

                String path = userHeaderImg;
                Bitmap bitmap = BitmapFactory.decodeFile(path);
                if (PhotoUtils.bitmapIsLarge(bitmap)) {
                    PhotoUtils.cropPhoto(context, context, path);
                } else {
                    iv_login.setImageBitmap(bitmap);
                    saveBitmap(path);
                }
            }

        } else if (requestCode == PhotoUtils.INTENT_REQUEST_CODE_CROP) {

            if (resultCode == Activity.RESULT_OK) {
                String path = data.getStringExtra("path");
                if (path != null) {
                    Bitmap bitmap = BitmapFactory.decodeFile(path);
                    if (bitmap != null) {
                        iv_login.setImageBitmap(bitmap);
                        saveBitmap(path);
                    }
                }
            }


        }
    }

    private void cropPhoto(Activity context, Activity context1, String path) {

        Intent intent = new Intent(context, ImageFactoryActivity.class);
        intent.putExtra("path", path);
//            dlIntent.p
        intent.putExtra(ImageFactoryActivity.TYPE,
                ImageFactoryActivity.CROP);
        context.startActivityForResult(intent, INTENT_REQUEST_CODE_CROP);
    }

    private void saveBitmap(String filepath) {
        File file = new File(filepath);
        final BmobFile bmobFile = new BmobFile(file);
        d(",bmobFile.getFilename():" + bmobFile.getFilename());
        d(",bmobFile.getUrl():" + bmobFile.getUrl());
        bmobFile.upload(getActivity(), new UploadFileListener() {
            @Override
            public void onSuccess() {
                Debug.d(getActivity(), "upload onSuccess.....................:");
                userBean.header_img = FILE_BASE_URL + bmobFile.getUrl();
                userPersenter.updateUser(userBean, mContext);
            }

            @Override
            public void onFailure(int i, String s) {
                Debug.d(getActivity(), "uploadonFailure code:" + i + ",.....................s:" + s);
            }
        });
    }

    BottomPopupWindow bottomPopupWindow;

    private void initPopupWindow(View view) {
        Activity MyPhotoActivity = getActivity();
        bottomPopupWindow = new BottomPopupWindow(getActivity(), 0, view);
        bottomPopupWindow
                .addItem(
                        "phone",
                        new BPopupWindow.onClickItemListener() {
                            @Override
                            public void clickItem(View v) {

                                // 从相机获取图片
                                getImageFromCamera();
                                bottomPopupWindow.dismiss();
                            }
                        }, false);
        bottomPopupWindow
                .addItem(
                        "xiangce",
                        new BPopupWindow.onClickItemListener() {

                            @Override
                            public void clickItem(View v) {
                                // 从相册获取图片
                                getImageFromAlbum();
                                bottomPopupWindow.dismiss();
                            }
                        }, false);

        bottomPopupWindow.addItem(
                "cancel",
                new BPopupWindow.onClickItemListener() {

                    @Override
                    public void clickItem(View v) {
                        bottomPopupWindow.dismiss();
                    }
                }, true);
        bottomPopupWindow.setBackgroundDrawable(new BitmapDrawable());
    }

    String userHeaderImg;

    private void getImageFromAlbum() {
        userHeaderImg = PhotoUtils
                .takePicture(FileUtils.imgPathPath(), getActivity());
    }

    private void showPopWindow() {

        bottomPopupWindow.show();
    }

    protected void getImageFromCamera() {


        PhotoUtils.selectPhoto(getActivity());
    }

    public void update(UserBean userBean) {
        tvLogin.setText(userBean.lusername);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void initData() {
        d("onResume");
        isLogin = mContext.isLogin();
        if (isLogin) {
            userBean = DingDanApplication.getDefault().getCurrenUserBean();
            tvLogin.setText(userBean.lusername);
            isLogin = true;
        }
        mContext.getBitmapUtils().display(iv_login, userBean.header_img);
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .displayer(new RoundedBitmapDisplayer(20)).showImageOnFail(R.mipmap.default_header)
                .build();
//        options.getImageOnFail(getResources());
        ImageLoader.getInstance().displayImage(userBean.header_img,iv_login,options);
    }

    @Override
    public void currentSelected() {
        initData();
    }

    @Override
    public void loadDataSuccess() {
//		showToast("update success");
        d("update success");
    }

    @Override
    public void loadDataFaild() {
        showToast("update faild");
    }
}
