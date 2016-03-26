package com.lu.takeaway.view.popwindow;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;

import com.lu.takeaway.R;


/**
* @ClassName: APopupWindow 
* @Description: TODO 自定义PopupWindow 父类，可以实现子类的item添加扩展
* @author hepengcheng
* @date 2014-10-31 下午2:24:53 
*  
*/
public abstract class BPopupWindow extends PopupWindow {
	
	private LinearLayout container;
	
	protected View rely;
	protected int containerbgColor= R.color.gay_need;
	/**
	 * @param context
	 * @param relyview
	 */
	public BPopupWindow(Context context, View relyview, int bgColor) {
		// TODO Auto-generated constructor stub
		super(context);
	
		this.rely=relyview;
		
	 	container=new LinearLayout(context);
		LayoutParams params=new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		container.setLayoutParams(params);
		container.setBackgroundColor(context.getResources().getColor(containerbgColor));
//		container.setPadding(20, 20, 20, 20);
		container.setOrientation(LinearLayout.VERTICAL);
		if(bgColor!=0){
		//	container.setBackgroundColor(context.getResources().getColor(bgColor));
		}else{
	//		container.setBackgroundColor(context.getResources().getColor(R.color.gray));
		}
		this.setContentView(container);
	 	this.setWidth(LayoutParams.MATCH_PARENT);
		 this.setHeight(LayoutParams.WRAP_CONTENT);
        this.setOutsideTouchable(true); 
		ColorDrawable dw = new ColorDrawable(0x0000ff00);
		setBackgroundDrawable(dw);
 	}
	
	protected void addViewItemParent(View view) {
		this.container.addView(view);
	}
	
	protected abstract void showlocation(View relyview,int gravity,int y);
	
	public void show(){
		showlocation(this.rely,Gravity.BOTTOM,0);
	}
	
	public void show(int gravity,int y){
		showlocation(this.rely,gravity,y);
	}
	
	public interface onClickItemListener{
		void clickItem(View v);
	}
	
	protected LinearLayout getContainer(){
		return this.container;
	}
	
	protected View getRelyView(){
		return this.rely;
	}

}

