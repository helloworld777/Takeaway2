package com.lu.takeaway.bean;

import cn.bmob.v3.BmobObject;
import util.StringUtil;

/**
 * Created by lenovo on 2016/3/22.
 */
public class OrderBean extends BmobObject {
    public int uid,fid,oid,onumber,id;
    public String odate,ousername,ofoodname,opicture,ofinished;
    public double oprice;
    public OrderBean(){
        setTableName("lorder2");
    }

    public String getTotalPriceToString(){
        return StringUtil.formatNumber(onumber*oprice);
    }
    public double getTotalPrice(){
        return Double.parseDouble(getTotalPriceToString());
    }
}
