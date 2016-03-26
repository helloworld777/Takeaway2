package com.lu.takeaway.view.popwindow;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.lu.takeaway.R;


//调用方式
/*popwindow=new BottomPopupWindow(GroupMember.this, 0, findViewById(R.id.test));
 * 
 //添加普通item
 popwindow.addItem("添加成员", new onClickItemListener() {

 @Override
 public void clickItem(View v) {
 // TODO Auto-generated method stub
 Toast.makeText(getApplicationContext(), "你单击了添加成员", Toast.LENGTH_SHORT).show();
 }
 });

 //添加特殊的取消项
 popwindow.addCancelItem("取消", new onClickItemListener() {

 @Override
 public void clickItem(View v) {
 // TODO Auto-generated method stub
 Toast.makeText(getApplicationContext(), "你单击了取消", Toast.LENGTH_SHORT).show();
 popwindow.dismiss();
 }
 });

 // popwindow的显示与隐藏
 public void onClick(View v) {
 // TODO Auto-generated method stub
 if(popwindow.isShowing()){
 popwindow.dismiss();
 }
 else{
 popwindow.show();
 }
 }*/

/**
 * @ClassName: BottomPopupWindow
 * @Description: TODO 从底部弹出的PopupWindow ,用于拍照，选相册图片
 * @author hepengcheng
 * @date 2014-10-31 下午3:21:16
 * 
 */
public class BottomPopupWindow extends BPopupWindow {
	boolean islast = false;
	private int bgDrawableId = R.drawable.selector_bottom_popwindow;
	private int textColorResId = R.color.black_need;
	private int default_textSize =14;
	protected GetPopwindow mGetPopwindow;
	private Context context;
	/**
	 * //是否show双层popupwindow //默认show android4.4以上
	 * show双层popupwindow以后，锁屏或者点击home键进入主页 会层次颠倒
	 * */
	private boolean isShowBg = true;

	public BottomPopupWindow(Context context, int bgColor, View relyview) {
		super(context, relyview, bgColor);
		// TODO Auto-generated constructor stub
		this.context = context;
		setAnimationStyle(R.style.popu_animation);
		setBackgroudPopwindow(context, relyview);
	}

	public BottomPopupWindow(Context context, int bgColor, View relyview,
							 String tag) {
		super(context, relyview, bgColor);
		// TODO Auto-generated constructor stub
		this.context = context;
		setBackgroudPopwindow(context, relyview);
	}

	private void setBackgroudPopwindow(Context context, View relyview) {
		mGetPopwindow = new GetPopwindow(context);
		mGetPopwindow.getPopwindowView(this, relyview);
	}

	public void setBackgroudClickable(boolean isTouch) {
		mGetPopwindow.setClickable(isTouch);
	}

	@Override
	protected void showlocation(View relyview, int gravity, int y) {
		showAtLocation(relyview, gravity, 0, y);
	}

	public void showlocation(View relyview, int gravity, int x, int y) {
		showAtLocation(relyview, gravity, x, y);
	}

	private View getItemView(Bitmap drawable, String title, int bgDrawableId,
			int textColorResId, int textSize) {
		// TODO Auto-generated method stub
		TextView name = new TextView(context);
		LayoutParams lp = new LayoutParams(
				android.view.ViewGroup.LayoutParams.MATCH_PARENT, (int) context
						.getResources().getDimension(R.dimen.size48dp));
		if (islast) {
			lp.setMargins(0,
					(int) context.getResources().getDimension(R.dimen.size8dp),
					0,
					(int) context.getResources().getDimension(R.dimen.size16dp));
		}
		name.setLayoutParams(lp);
		name.setText(title);
		name.setGravity(Gravity.CENTER);
		name.setTextSize(default_textSize);
		name.setBackgroundResource(bgDrawableId);
		name.setTextColor(context.getResources().getColorStateList(
				textColorResId));
		// name.setPadding(0, 25, 0, 25);

		return name;
	}

	// 增加一个view视图
	public void addViewItem(View view) {
		addViewItemParent(view);
	}

	public void addItem(String title, onClickItemListener listener,
			boolean islast) {
		addItem(title, listener, null, islast);
	}

	public void addItem(String title, onClickItemListener listener,
			Bitmap drawable, boolean islast) {

		addItem(title, listener, bgDrawableId, textColorResId, drawable, islast);
	}

	public void addItem(String title, onClickItemListener listener,
			int bgDrawableId, int textColorResId, Bitmap drawable,
			boolean islast) {

		this.bgDrawableId = bgDrawableId;

		this.textColorResId = textColorResId;
		addItem(title, listener, bgDrawableId, textColorResId,
				default_textSize, drawable, islast);
	}

	public void addItem(String title, final onClickItemListener listener,
			int bgDrawableId, int textColorResId, int textSize,
			Bitmap drawable, boolean islast) {
		View item = null;
		this.islast = islast;
		item = getItemView(drawable, title, bgDrawableId, textColorResId,
				textSize);
		if (item == null) {
			return;
		}
		item.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// APopupWindow.this.dismiss();
				dismiss();
				if (listener != null) {
					listener.clickItem(v);
				}
			}
		});
		TextView mTextView = new TextView(context);
		LayoutParams lp = new LayoutParams(
				android.view.ViewGroup.LayoutParams.MATCH_PARENT,
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		mTextView.setLayoutParams(lp);
		mTextView.setWidth(LayoutParams.MATCH_PARENT);
		mTextView.setHeight(1);
		mTextView.setBackgroundResource(R.color.gay_need);
		if (islast) {
//			addViewItemParent(mTextView);
			addViewItemParent(item);

		} else {
			addViewItemParent(item);
			addViewItemParent(mTextView);

		}
//		addViewItemParent(item);
	}

	@Override
	public void dismiss() {
		// TODO Auto-generated method stub
		super.dismiss();
//		MyApplication.getApplication().did = "";
		mGetPopwindow.dismissButtomPopwindow();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
//		Debug.d("----", "----popupwindow show");
		if (isShowBg) {
			mGetPopwindow.showButtomPopwindow(Gravity.BOTTOM, false);
		}
		super.show();
	}

	public void show(int gravity, boolean isDown, int y) {
		if (isShowBg) {
			mGetPopwindow.showButtomPopwindow(gravity, isDown);
		}
		super.show(gravity, y);
	}

	public void dismissGetPop() {
		if (mGetPopwindow != null) {
			mGetPopwindow.dismissButtomPopwindow();
		}
	}

	public boolean isShowBg() {
		return isShowBg;
	}

	public void setShowBg(boolean isShowBg) {
		this.isShowBg = isShowBg;
	}

}
