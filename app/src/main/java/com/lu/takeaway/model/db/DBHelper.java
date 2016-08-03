package com.lu.takeaway.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "mydb";
	private static final int DATABASE_VERSION = 1;
	private static final String TAB_USER_SQL = "CREATE TABLE user ("
			+ "_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
			+ "username VARCHAR(500)," + "password VARCHAR(100),"
			+ "phone VARCHAR(50));";
	/**��Ʒ��
	 * _id,
	 * title,
	 * quantity,
	 * price,
	 * img_url,
	 * description
	 * 
	 * */
	private static final String TAB_GOODS_SQL="CREATE TABLE goods (" +
			"_id INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL," +
			"title VARCHAR(500)  ," +
			"quantity INTEGER  ," +
			"price VARCHAR(500)  ," +
			"img_url VARCHAR(500)  ," +
			"description VARCHAR(500))" ;
	/**
	 * 
	 * ������
	 * ��������
	 * �����û�
	 * ��������
	 * */
	private static final String TAB_ORDER_SQL="CREATE TABLE orders ("
			+ "_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
			+ "uid INTEGER," + 
			"date VARCHAR(100));";
	/**������Ʒ��ϵ��
	 * ���� n----n ��Ʒ
	 * 
	 * �����ţ���Ʒ����Ʒ����
	 * */
	private static final String TAB_ORDER_GOODS_SQL="create table orders_goods(" +
			"_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
			"oid integer not null," +
			"gid integer not null," +
			"ogquantity INTEGER  NULL"+
			")";
	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		System.out.println("onCreate()");
		// ����ı���һ��Ĭ�ϵĹ��ﳵ������ϵע�᲻ע��
		// db.execSQL("CREATE TABLE goods ([id] INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL,[_id] INTEGER  NULL,[_channel_id] INTEGER  NULL,[_category_id] INTEGER  NULL,[_title] VARCHAR(500)  NULL,[_goods_no] VARCHAR(500)  NULL,[_stock_quantity] INTEGER  NULL,[_market_price] VARCHAR(500)  NULL,[_sell_price] VARCHAR(500)  NULL,[_point] INTEGER  NULL,[_link_url] TEXT  NULL,[_img_url] TEXT  NULL,[_seo_title] TEXT  NULL,[_seo_keywords] TEXT  NULL,[_seo_description] TEXT  NULL,[_content] TEXT  NULL,[_sort_id] INTEGER  NULL,[_click] INTEGER  NULL,[_is_msg] INTEGER  NULL,[_is_top] INTEGER  NULL,[_is_red] INTEGER  NULL,[_is_hot] INTEGER  NULL,[_is_slide] INTEGER  NULL,[_is_lock] INTEGER  NULL,[_user_id] INTEGER  NULL,[_add_time] VARCHAR(500)  NULL,[_digg_good] INTEGER  NULL,[_digg_bad] INTEGER  NULL,[_buynumber] INTEGER  NULL,[_user] VARCHAR(500)  NULL)");
		// ��������ע��
		db.execSQL(TAB_USER_SQL);
		
		db.execSQL(TAB_ORDER_SQL);
		db.execSQL(TAB_GOODS_SQL);
		db.execSQL(TAB_ORDER_GOODS_SQL);
		// ��������ǵ�ע�Ტ�ҵ��ﶩ��ʱ��dt_goods�а�id���뵽��������棬������dt_goods��submit����
		// db.execSQL("CREATE TABLE [dingnum] ([id] INTEGER  PRIMARY KEY NOT NULL,[ding_id] INTEGER  NULL,[buynumber] INTEGER  NULL,[user_name] VARCHAR(500)  NULL,[submitnum] VARCHAR(500)  NULL,[date] DATE  NULL)");
		// ��������Ƕ���
		// db.execSQL("CREATE TABLE [submit] ([id] INTEGER  PRIMARY KEY NOT NULL,[submitnum] varcHAR(500)  NULL,[username] VARCHAR(500)  NULL,[tel] vARCHAR(500)  NULL,[renshu] INTEGER  NULL,[cantingname] varcHAR(500)  NULL,[daodiantime] daTE  NULL,[contract] TEXT  NULL,[fukuan] BOOLEAN  NULL,[queding] BOOLEAN  NULL,[totalmoney] FLOAT  NULL,[adddate] DATE  NULL)");
		// ϲ����
		// db.execSQL("CREATE TABLE [dinglike] ([id] INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT,[goodsid] INTEGER  NULL,[users] VARCHAR(500)  NULL)");
		// ���������ϵͳ����,��ǰ����ip��ַ
		// db.execSQL("CREATE TABLE [system] ([id] INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL,[localhost] VARCHAR(500) DEFAULT 'http://172.198.1.50' NULL)");
		// db.execSQL("insert into [system] (localhost) values('http://postdep.yw.wh-baidu.com')");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		System.out.println("onUpgrade");
		// TODO Auto-generated method stub
		// db.execSQL("drop table if exists [dt_goods]");
		// db.execSQL("drop table if exists [dt_users]");
		// db.execSQL("drop table if exists [dingnum]");
		// db.execSQL("drop table if exists [submit]");
		// db.execSQL("drop table if exists [dinglike]");
		// db.execSQL("drop table if exists [system]");
		// onCreate(db);
		db.execSQL(TAB_ORDER_SQL);
		db.execSQL(TAB_GOODS_SQL);
		db.execSQL(TAB_ORDER_GOODS_SQL);
	}

}
