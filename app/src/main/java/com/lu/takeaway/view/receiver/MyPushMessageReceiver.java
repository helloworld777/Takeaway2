package com.lu.takeaway.view.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyPushMessageReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        Log.d("bmob", "客户端收到推送内容："+intent.getStringExtra("msg"));
        if(intent.getAction().equals("msg")){
            Log.d("bmob", "客户端收到推送内容："+intent.getStringExtra("msg"));
        }
    }

}