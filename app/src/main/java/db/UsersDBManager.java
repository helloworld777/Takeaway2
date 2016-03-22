package db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import util.Constants;
import util.SaveDataUtil;

public class UsersDBManager implements Constants {
	private DBHelper helper;
	private SQLiteDatabase db;
	public String strwhere = "";
	private Context mContext;

	public UsersDBManager(Context context) {
		helper = new DBHelper(context);
		db = helper.getWritableDatabase();
		mContext = context;
	}

	/***
	 * 
	 * @param id
	 * @return
	 */
	public boolean queryUser(String username, String password) {

		if (username.equals(SaveDataUtil.getString(mContext, USERNAME)) && password.equals(SaveDataUtil.getString(mContext, PWD))) {
			return true;
		}
		return false;
		// ;
		//
		// String susername = username;
		// Cursor c = db.rawQuery("select * from user where username = '" +
		// susername + "' and password='" + password + "'", null);
		// if (c.moveToNext()) {
		// c.moveToFirst();
		// c.close();
		// return true;
		// }
		// c.close();
		// return false;
	}

	/**
	 * 
	 * @param dingdanString
	 */
	public void insertUser(String username, String password, String phone) {
//		StringBuilder stringBuilder = new StringBuilder();
//		stringBuilder.append("insert into user (username,password,phone) values('" + username + "','" + password + "','" + phone + "')");
//		db.execSQL(stringBuilder.toString());
		SaveDataUtil.setString(mContext, USERNAME, username);
		SaveDataUtil.setString(mContext, PWD, password);
		SaveDataUtil.setString(mContext, PHONE_NUMBER, phone);
	}

	/**
	 * 
	 */
	public void dbclose() {
		db.close();
		helper.close();
	}
}
