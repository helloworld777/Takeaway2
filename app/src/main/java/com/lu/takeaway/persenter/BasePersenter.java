package com.lu.takeaway.persenter;

import com.lu.takeaway.util.LogUtil;

/**
 * Created by lenovo on 2016/3/22.
 */
public class BasePersenter {
    protected void d(String msg){
        LogUtil.d(this, "............." + msg);
    }
}
