package com.lu.takeaway.view.widget;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.lu.takeaway.R;
import com.lu.takeaway.util.LogUtil;


/**
 *
 */
public class DialogLoading extends AlertDialog {
	private Context mContext;
	private TextView tvDialogLoading;
	private String message = null;
	private ProgressWheel progressWheel ;

	public DialogLoading(Context context) {
		super(context);
		message = context.getString(R.string.dialog_loading);
		this.mContext = context;
		this.setCancelable(true);
		this.setCanceledOnTouchOutside(false);
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.dialog_loading);
		progressWheel= (ProgressWheel) findViewById(R.id.progress_wheel);

        //设置边缘Bar条颜色
        progressWheel.setBarColor(Color.RED);
//        progressWheel.setBarColor(getContext().getResources().getColor(R.color.main_color));
        
        //设置wheel颜色
        progressWheel.setRimColor(Color.LTGRAY);
        
		tvDialogLoading = (TextView) findViewById(R.id.tvDialogLoading);
		tvDialogLoading.setText(this.message);
		//自动旋转
		progressWheel.spin();
	}

	@Override
	public void show() {
		if(mContext instanceof Activity){

			Activity activity= (Activity) mContext;
			if(!activity.isFinishing()){
				LogUtil.d(this, "show..............");
				super.show();
			}

		}

	}
	
	@Override
	public void dismiss() {
		// TODO Auto-generated method stub
		if(mContext instanceof Activity){
			Activity activity= (Activity) mContext;
			if(!activity.isFinishing()){
				LogUtil.d(this,"dismiss..............");
				super.dismiss();
				if(progressWheel!=null){
					progressWheel.stopSpinning();
					progressWheel=null;
				}
			}
		}

	}

	public void setText(String message) {
		try {
			if (message != null && tvDialogLoading != null) {
				this.message = message;
				tvDialogLoading.setText(this.message);
			}
		} catch (Exception e) {
		}
	}

	public void setText(int resId) {
		setText(getContext().getResources().getString(resId));
	}

}
