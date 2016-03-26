package com.lu.takeaway.model;

import android.content.Context;

import com.lu.takeaway.bean.FileBean;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by lenovo on 2016/3/25.
 */
public class FileModel extends BaseModel{
   public static void saveFile(FileBean fileBean, Context context, SaveListener saveListener){
       fileBean.save(context,saveListener);
   }
}
