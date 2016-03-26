package com.lu.takeaway.view.popwindow;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.lu.takeaway.R;


public class GetPopwindow {
	private  PopupWindow mPopupWindow;
	
	LinearLayout container;
	Context mContext;
	private View view;
 	
	public GetPopwindow(Context mContext) {
		super();
		this.mContext = mContext;
		container=new LinearLayout(mContext);
	}

	@SuppressWarnings("deprecation")
	public  void getPopwindowView(final PopupWindow mBottomPopupWindow,final View relyview){
		this.view=relyview;
		if(null==mPopupWindow){
 			LayoutParams params=new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			container.setLayoutParams(params);
		 	container.setBackgroundColor(mContext.getResources().getColor(R.color.black));
		 	container.getBackground().setAlpha(105);
			mPopupWindow = new PopupWindow(container,LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
 			mPopupWindow.setTouchable(true);
			mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
 		 	mPopupWindow.setAnimationStyle(R.style.popu_animation1);
 		 
		}
	
		container.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(null!=mPopupWindow&&mBottomPopupWindow!=null){
		 	 		mPopupWindow.dismiss();
		 	 		mBottomPopupWindow.dismiss();
				}
				 
			}
		});
 		 
	}
	
	public void setClickable(boolean isTouch){
		container.setClickable(isTouch);
	}
	
	public  void dismissButtomPopwindow(){
		if(null!=mPopupWindow){
 	 		mPopupWindow.dismiss();
 		}
	}
	public void showButtomPopwindow(int gravity,boolean isDown){
		if(null!=mPopupWindow){
			if(isDown){
				mPopupWindow.showAsDropDown(view);
			}else{
				mPopupWindow.showAtLocation(view, gravity, 0, 0);
			}
		}
	}
	
}
