package com.lu.takeaway.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by lenovo on 2016/3/21.
 */
public class FoodBean extends BmobObject {



    public FoodBean() {
        this.setTableName("foods");
    }
    public String name,pictureUrl,note,littlePicUrl;
    public double price,sale;
    public int number;
//    public FoodBean(String name,double price,double sale,int number,String pictureUrl,String note){
//        this.name=name;
//        this.pictureUrl=pictureUrl;
//        this.price=price;
//        this.note=note;
//        this.number=number;
//        this.sale=sale;
//    }
}
