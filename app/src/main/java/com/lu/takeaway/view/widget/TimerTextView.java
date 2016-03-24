package com.lu.takeaway.view.widget;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.widget.TextView;

import com.lu.takeaway.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by lenovo on 2016/3/24.
 */
public class TimerTextView extends TextView{
    private Timer timer;
    private int count=10;
    private Resources resources;
    public TimerTextView(Context context) {
        this(context,null);
    }
    public TimerTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        resources=context.getResources();
    }

    private void init() {
        timer=new Timer();
    }
    public void closeTimer(){
        if(timer!=null){
            timer.cancel();
            timer=null;
        }

    }
    public void startTimer(){
        if(timer==null){
            timer=new Timer();
        }
        setEnabled(false);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                count--;
                if(count<0){
                    closeTimer();
                    post(new Runnable() {
                        @Override
                        public void run() {
                            count=10;
                            setEnabled(true);
                            setText(resources.getString(R.string.sendYan));
                        }
                    });
                }else{
                    post(new Runnable() {
                        @Override
                        public void run() {
                            setText("("+count+"s)后重新发送");
                        }
                    });
                }
            }
        },0,1000);
    }
}
