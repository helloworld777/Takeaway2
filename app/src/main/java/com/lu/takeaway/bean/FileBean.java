package com.lu.takeaway.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by lenovo on 2016/3/25.
 */
public class FileBean extends BmobObject{
    private BmobFile name;
    private String foodname;
    public FileBean() {
        this.setTableName("myfile");
    }

    public BmobFile getName() {
        return name;
    }

    public void setName(BmobFile name) {
        this.name = name;
    }

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }
}
