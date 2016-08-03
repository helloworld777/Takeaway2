package com.lu.takeaway.model.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.Goods;
import bean.OrderBean;


public class OrderDBManager {
	private DBHelper helper;
	private SQLiteDatabase db;
	public String strwhere = "";

	public OrderDBManager(Context context) {
		helper = new DBHelper(context);
		db = helper.getWritableDatabase();
	}

	public List<Map<String, Object>> queryOrder(int uid) {
		List<Map<String, Object>> orders = new ArrayList<Map<String, Object>>();

		Cursor cursor = db
				.rawQuery(
						"select o._id oid,g.title,g.img_url,g._id gid,og.ogquantity, o.date from orders o,goods g ,orders_goods  og where "
								+ "og.oid = o._id and og.gid=g._id and o.uid="
								+ uid, null);
		if(cursor.moveToNext()){
			while (!cursor.isAfterLast()) {
				Map<String, Object> map=new HashMap<String, Object>();
				int oid = cursor.getInt(cursor.getColumnIndex("oid"));
				OrderBean order = new OrderBean();
//				map.put("oid", oid);
				String date = cursor.getString(cursor.getColumnIndex("date"));
//				map.put("date", date);
				order.setDate(date);
				order.setId(oid);
				map.put("order", order);
				
				String title = cursor.getString(cursor.getColumnIndex("title"));
//				map.put("date", date);
				String img_url = cursor.getString(cursor.getColumnIndex("img_url"));
//				map.put("date", date);
				int _number = cursor.getInt(cursor.getColumnIndex("ogquantity"));
//				map.put("date", date);
				int gid = cursor.getInt(cursor.getColumnIndex("gid"));
//				map.put("date", date);
				Goods goods = new Goods();
				goods.set_id(gid);
				goods.set_title(title);
				goods.set_img_url(img_url);
				goods.set_number(_number);
				
				map.put("goods", goods);
				orders.add(map);
//				if(!orders.contains(order)){
//					orders.add(order);
//					List<Goods> goodsList=new ArrayList<Goods>();
//					goodsList.add(goods);
//					order.setGoods(goodsList);
//				}else{
//					List<Goods> goodsList=new ArrayList<Goods>();
//					goodsList.add(goods);
//					int index=orders.indexOf(order);
//					orders.get(index).setGoods(goodsList);
//					
//				};
				
				
				cursor.moveToNext();

			}
		}
		

		return orders;
	}

	/**
	 * 
	 * @param dingdanString
	 */
	public void insertOrder(OrderBean order) {/*
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("insert into orders (uid,date) values('"
				+ order.getUser().getId() + "','" + DateUtil.formateDate()
				+ "')");

		db.execSQL(stringBuilder.toString());
		 int oid=maxId();
		 for(Goods goods:order.getGoods()){
			 intsertOrdersGoods(oid,goods.get_id(),goods.get_number());
		 }

	*/}

	public void intsertOrdersGoods(int oid, int gid, int ogquantity) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
				.append("insert into orders_goods (oid,gid,ogquantity) values("
						+ oid + "," + gid + "," + ogquantity + ")");

		db.execSQL(stringBuilder.toString());
	}

	/**
	 * 
	 */
	public int maxId() {
		int maxId = 0;
		Cursor cursor = db.rawQuery("select max(_id) id from orders", null);
		if (cursor.moveToNext()) {
			maxId = cursor.getInt(cursor.getColumnIndex("id"));
		}
		cursor.close();
		return maxId;
	}

	/**
	 * 
	 */
	public void dbclose() {
		db.close();
		helper.close();
	}
}
