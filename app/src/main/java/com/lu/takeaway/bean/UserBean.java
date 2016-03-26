package com.lu.takeaway.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

public class UserBean extends BmobObject implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String username;
	public String password;
	public String phone;
	public String address;

	public String date;
	public String header_img;
	
	public String lusername,lpwd;
	
	private int id;
	public UserBean(){
		setTableName("LUser");
	}


	public UserBean(String username, String password, String phone) {
		this();
		this.username = username;
		this.password = password;
		this.phone = phone;
	}
	public UserBean(String lusername, String lpwd, String phone,String address,String date) {
		this();
		this.lusername = lusername;
		this.lpwd = lpwd;
		this.phone = phone;
		this.address=address;
		this.date=date;
	}

	
	
}
