package com.lu.takeaway.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by lenovo on 2016/3/22.
 */
public class OrderBean extends BmobObject {
    public int uid,fid,oid,onumber,id;
    public String odate,ousername,ofoodname;
    public double oprice;
    public OrderBean(){
        setTableName("lorder2");
    }
}
