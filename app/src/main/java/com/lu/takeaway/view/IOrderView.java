package com.lu.takeaway.view;

import com.lu.takeaway.bean.OrderBean;

import java.util.List;

/**
 * Created by lenovo on 2016/3/22.
 */
public interface IOrderView {
     void getOrderBeanSuccess(List<OrderBean> orderBeanList);
     void getOrderBeanFaild();
     void commitOrder(OrderBean orderBean);
//     void
}
