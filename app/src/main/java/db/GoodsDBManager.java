package db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import bean.Goods;


public class GoodsDBManager {
	private DBHelper helper;
	private SQLiteDatabase db;
	public GoodsDBManager(Context context){
		helper=new DBHelper(context);
		db=helper.getWritableDatabase();
	}
	public void insertGoods(Goods goods){
		String sqlString="insert into goods(title,quantity,price,img_url,description)values(" +
				"'"+goods.get_title()+"',"+goods.get_number()+","+
				goods.get_sell_price()+",'"+goods.get_img_url()+"','"
				+goods.get_content()+"')";
		db.execSQL(sqlString);
		
	}
	public void updateGoods(Goods goods){
		String sqlString="update goods set img_url='"+goods.get_img_url()+"'";
		db.execSQL(sqlString);
	}
	public void updateImageUrl(String img,int id){
		String sqlString="update goods set img_url='"+img+"'"+"where _id="+id;
		db.execSQL(sqlString);
	}
}
