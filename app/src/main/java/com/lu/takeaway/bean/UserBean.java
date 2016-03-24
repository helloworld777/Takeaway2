package com.lu.takeaway.bean;

import java.io.Serializable;

public class UserBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String username;
	public String password;
	public String phone;
	public String address;

	public String date;
	
	
	public String lusername,lpwd;
	
	private int id;
	public UserBean(){
		
	}

	public UserBean(String username, String password, String phone) {
		super();
		this.username = username;
		this.password = password;
		this.phone = phone;
	}
	public UserBean(String lusername, String lpwd, String phone,String address,String date) {
		super();
		this.lusername = lusername;
		this.lpwd = lpwd;
		this.phone = phone;
		this.address=address;
		this.date=date;
	}

	
	
}
