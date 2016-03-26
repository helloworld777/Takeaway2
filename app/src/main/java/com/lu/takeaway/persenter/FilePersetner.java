package com.lu.takeaway.persenter;

import android.content.Context;

import com.lu.takeaway.bean.FileBean;
import com.lu.takeaway.model.FileModel;
import com.lu.takeaway.view.IView;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by lenovo on 2016/3/25.
 */
public class FilePersetner {
    private IView iView;

    public void saveFile(FileBean fileBean, Context context){
        FileModel.saveFile(fileBean, context, new SaveListener() {
            @Override
            public void onSuccess() {
                if(getiView() !=null){
                    getiView().loadDataSuccess();
                }

            }

            @Override
            public void onFailure(int i, String s) {
                if(getiView() !=null) {
                    getiView().loadDataFaild();
                }
            }
        });
    }

    public IView getiView() {
        return iView;
    }

    public void setiView(IView iView) {
        this.iView = iView;
    }
}
