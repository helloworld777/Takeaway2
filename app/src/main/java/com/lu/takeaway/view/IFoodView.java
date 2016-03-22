package com.lu.takeaway.view;

import com.lu.takeaway.bean.FoodBean;

import java.util.List;


/**
 * Created by lenovo on 2016/3/21.
 */
public interface IFoodView {

    public void loadDataSucess(List<FoodBean> foodBeanList);
    public void loadDataFaild();
}
