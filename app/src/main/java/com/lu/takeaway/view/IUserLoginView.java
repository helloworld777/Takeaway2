package com.lu.takeaway.view;

import com.lu.takeaway.bean.UserBean;

/**
 * Created by lenovo on 2016/3/21.
 */
public interface IUserLoginView {
    void loginSuccess(UserBean user);
    void loginFaild();
}
