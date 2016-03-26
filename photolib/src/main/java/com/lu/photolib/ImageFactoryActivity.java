package com.lu.photolib;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;



/**
 * @author Administrator
 *
 */
//@ContentView(R.layout.activity_imagefactory)
public class ImageFactoryActivity extends Activity implements OnClickListener{
//	private HeaderLayout mHeaderLayout;
	private ViewFlipper mVfFlipper;
//	private Button mBtnLeft;
	private Button mBtnRight;

	private ImageFactoryCrop mImageFactoryCrop;
	private ImageFactoryFliter mImageFactoryFliter;
	private String mPath;
	private String mNewPath;
	private int mIndex = 0;
	private String mType;

	public static final String TYPE = "type";
	public static final String CROP = "crop";
	public static final String FLITER = "fliter";
	
	
//	@ViewInject(value = R.id.iv_search)
	private ImageView iv_search;
//	@ViewInject(value = R.id.iv_more)
	private ImageView iv_more;
//	@ViewInject(value = R.id.iv_back)
	private ImageView iv_back;

//	@ViewInject(value = R.id.tv_title)
	private TextView tv_title; 


	private Button btn_back,btn_next;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		ViewUtils.inject(this);
		setContentView(R.layout.activity_imagefactory);
		initViews();
		initWidget();
		init();

	}

	
	private void initWidget() {
//		iv_more.setVisibility(View.GONE);
		iv_search= (ImageView) findViewById(R.id.iv_search);
		iv_more= (ImageView) findViewById(R.id.iv_more);
		iv_back= (ImageView) findViewById(R.id.iv_back);
		tv_title= (TextView) findViewById(R.id.tv_title);
		iv_search.setVisibility(View.GONE);
		tv_title.setText("aa");
		iv_more.setOnClickListener(new OnRightImageButtonClickListener());

		iv_back.setOnClickListener(this);
		btn_next= (Button) findViewById(R.id.btn_next);
		btn_next.setOnClickListener(this);
		btn_back= (Button) findViewById(R.id.btn_back);
		btn_back.setOnClickListener(this);
	}
	protected void initViews() {
//		mHeaderLayout = (HeaderLayout) findViewById(R.id.imagefactory_header);
//		mHeaderLayout.init(HeaderStyle.TITLE_RIGHT_IMAGEBUTTON);
		mVfFlipper = (ViewFlipper) findViewById(R.id.imagefactory_vf_viewflipper);
//		mBtnLeft = (Button) findViewById(R.id.btn_back);
		mBtnRight = (Button) findViewById(R.id.btn_next);
	}


	
//	@OnClick({R.id.btn_back,R.id.btn_next,R.id.iv_back})
	public void onClick(View view){

		int i = view.getId();
		if (i == R.id.btn_back) {
			if (mIndex == 0) {
				setResult(RESULT_CANCELED);
				finish();
			} else {
				if (FLITER.equals(mType)) {
					setResult(RESULT_CANCELED);
					finish();
				} else {
					mIndex = 0;
					initImageFactory();
					mVfFlipper.setInAnimation(ImageFactoryActivity.this,
							R.anim.push_right_in);
					mVfFlipper.setOutAnimation(ImageFactoryActivity.this,
							R.anim.push_right_out);
					mVfFlipper.showPrevious();
				}
			}

		} else if (i == R.id.btn_next) {
			if (mIndex == 1) {
				mNewPath = PhotoUtils.savePhotoToSDCard(mImageFactoryFliter
						.getBitmap());
				Intent intent = new Intent();
				intent.putExtra("path", mNewPath);
				setResult(RESULT_OK, intent);
				finish();
			} else {
				mNewPath = PhotoUtils.savePhotoToSDCard(mImageFactoryCrop
						.cropAndSave());
				mIndex = 1;
				initImageFactory();
				mVfFlipper.setInAnimation(ImageFactoryActivity.this,
						R.anim.push_left_in);
				mVfFlipper.setOutAnimation(ImageFactoryActivity.this,
						R.anim.push_left_out);
				mVfFlipper.showNext();
			}

		} else if (i == R.id.iv_back) {
			finish();

		} else {
		}
	}
	@Override
	public void onBackPressed() {
		if (mIndex == 0) {
			setResult(RESULT_CANCELED);
			finish();
		} else {
			if (FLITER.equals(mType)) {
				setResult(RESULT_CANCELED);
				finish();
			} else {
				mIndex = 0;
				initImageFactory();
				mVfFlipper.setInAnimation(ImageFactoryActivity.this,
						R.anim.push_right_in);
				mVfFlipper.setOutAnimation(ImageFactoryActivity.this,
						R.anim.push_right_out);
				mVfFlipper.showPrevious();
			}
		}
	}

	private void init() {
		mPath = getIntent().getStringExtra("path");
		mType = getIntent().getStringExtra(TYPE);
		mNewPath = new String(mPath);
		if (CROP.equals(mType)) {
			mIndex = 0;
		} else if (FLITER.equals(mType)) {
			mIndex = 1;
			mVfFlipper.showPrevious();
		}
		initImageFactory();
	}

	private void initImageFactory() {
		switch (mIndex) {
		case 0:
				mImageFactoryCrop = new ImageFactoryCrop(this,
						mVfFlipper.getChildAt(0));
//			}
			mImageFactoryCrop.init(mPath, getScreenSize(this)[0], getScreenSize(this)[1]);
			mBtnRight.setText("确    认");
			tv_title.setText("裁切图片");
			break;

		case 1:
//			if (mImageFactoryFliter == null) {
				mImageFactoryFliter = new ImageFactoryFliter(this,
						mVfFlipper.getChildAt(1));
//			}
			mImageFactoryFliter.init(mNewPath);
//			mHeaderLayout.setTitleRightImageButton("ͼƬ�˾�", null,
//					R.drawable.ic_topbar_rotation,
//					new OnRightImageButtonClickListener());
//			mBtnLeft.setText("ȡ    ��");
			tv_title.setText("图片预览");
			mBtnRight.setText("完    成");
			break;
		}
	}
	public static int[] getScreenSize(Activity activity) {
		DisplayMetrics metrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

		return new int[] { metrics.widthPixels, metrics.heightPixels };
	}
	private class OnRightImageButtonClickListener implements
			OnClickListener {

		@Override
		public void onClick(View v) {
			switch (mIndex) {
			case 0:
				if (mImageFactoryCrop != null) {
					mImageFactoryCrop.Rotate();
				}
				break;

			case 1:
				if (mImageFactoryFliter != null) {
					mImageFactoryFliter.Rotate();
				}
				break;
			}
			
		}
	}
}
