package com.gogocarpon.gogocarpon._app.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DealDatabaseHelper extends SQLiteOpenHelper {

	static final String formatDate = "%Y-%m-%d %H:%M:%S";
	static final String dbName = "enmasse_user_db";
	static final String tableName = "deals";
	
	public static final String colID = "id";
	public static final String colDealCode = "deal_code";
	public static final String colDealStatus = "deal_status";
	public static final String colName = "name";
	public static final String colHighlight = "highlight";

	public static final String colDest = "description";
	public static final String colTerms = "terms";	
	public static final String colShortDect = "short_desc";
	public static final String colPicDir = "pic_dir";
	public static final String colOriginPrice = "origin_price";
	public static final String colPrice = "price";

	public static final String colDiscount = "discount";
	public static final String colPrepayPercent = "prepay_percent";
	public static final String colStartAt = "start_at";
	public static final String colEndAt = "end_at";

	public static final String colMerchantID = "merchant_id";
	public static final String colMerchantName = "merchant_name";
	public static final String colMerchantAddress = "merchant_address";
	public static final String colMerchantTelephone = "merchant_telephone";
	public static final String colMerchantLong = "merchant_long";
	public static final String colMerchantLat = "merchant_lat";
	
	public static final String colUserDynamic = "use_dynamic";
	public static final String colPriceBuy = "price_buy";
	public static final String colHotDeal = "hot_deal";
	public static final String colVideoDesc = "video_desc";
	public static final String colCatIds = "cat_ids";
	
	public static final String colPriceStep = "price_step";
	public static final String colDealTypes = "deal_types";
	public static final String currency_code = "currency_code";
	
	

	// public SQLiteDatabase myDataBase;
	// private String DB_PATH ;
	// private Context myContext;

	public DealDatabaseHelper(Context context) {
		super(context, dbName, null, 2);

		// DB_PATH = "/data/data/" +
		// context.getApplicationContext().getPackageName() + "/databases/";
		// myContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

		// db.execSQL("CREATE TABLE "+deptTable+" ("+colDeptID+
		// " INTEGER PRIMARY KEY , "+
		// colDeptName+ " TEXT)");

		Log.d("My Log", "database: onCreate");
		db.execSQL("CREATE TABLE 'deals' ('id' INTEGER PRIMARY KEY ,'deal_status' INTEGER ,'deal_code' VARCHAR,'name' text," +
				"'highlight' TEXT,'description' TEXT,'terms' TEXT,'short_desc' text,'pic_dir' VARCHAR,'origin_price' FLOAT," +
				"'price' float,'discount' float,'prepay_percent' float,'start_at' DATETIME,'end_at' DATETIME, 'merchant_id' INTEGER, " +
				"'merchant_name' VARCHAR, 'merchant_address' VARCHAR, 'merchant_telephone' VARCHAR, 'merchant_lat' VARCHAR, " +
				"'merchant_long' VARCHAR, 'cat_ids' VARCHAR, 'use_dynamic' INTEGER, 'price_buy' FLOAT, 'hot_deal' INTEGER, " +
				"'video_desc' VARCHAR, 'price_step' TEXT, 'deal_types' TEXT);");
		
		db.execSQL("CREATE TABLE 'categories' ('id' INTEGER PRIMARY KEY ,'name' VARCHAR,'description' TEXT,'created_at' DATETIME,'updated_at' DATETIME);");
//		db.execSQL("CREATE TABLE 'paypal' ('merchant_email' VARCHAR PRIMARY KEY ,'api_username' VARCHAR,'signature' VARCHAR,'country_code' VARCHAR,'currency_code' VARCHAR);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

		Log.d("My Log", "database : onUpgrade");
		db.execSQL("DROP TABLE IF EXISTS deals");
		db.execSQL("DROP TABLE IF EXISTS categories");

		//
		// db.execSQL("DROP TRIGGER IF EXISTS dept_id_trigger");
		// db.execSQL("DROP TRIGGER IF EXISTS dept_id_trigger22");
		// db.execSQL("DROP TRIGGER IF EXISTS fk_empdept_deptid");
		// db.execSQL("DROP VIEW IF EXISTS "+viewEmps);
		onCreate(db);
	}

	public void addDeal(Deal d) {
		
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();

		cv.put(colID, d._id);
		cv.put(colDealStatus, d._deal_status);
		cv.put(colDealCode, d._deal_code);
		cv.put(colName, d._name);
		cv.put(colHighlight, d._highlight);
		
		cv.put(colDest, d._desc);
		cv.put(colTerms, d._terms);
		cv.put(colShortDect, d._short_desc);
		
		cv.put(colPicDir, d._pic_dir);
		cv.put(colOriginPrice, d._origin_price);
		cv.put(colPrice, d._price);
		cv.put(colDiscount, d._discount);
		cv.put(colPrepayPercent, d._prepay_percent);
		cv.put(colStartAt, d._start_at);
		cv.put(colEndAt, d._end_at);

		cv.put(colMerchantID, d._merchant_id);
		cv.put(colMerchantName, d._merchant_name);
		cv.put(colMerchantAddress, d._merchant_address);
		cv.put(colMerchantTelephone, d._merchant_telephone);
		cv.put(colMerchantLong, d._merchant_long);
		cv.put(colMerchantLat, d._merchant_lat);
		
		cv.put(colUserDynamic, d._use_dynamic);
		cv.put(colPriceBuy, d._price_buy);
		cv.put(colHotDeal, d._hot_deal);
		cv.put(colVideoDesc, d._video_desc);
		cv.put(colCatIds, d._cat_ids);
		
		cv.put(colPriceStep, d._price_step_json);
		cv.put(colDealTypes, d._deal_types_json);
//		cv.put(currency_code, d._currency_code);

		db.insert(tableName, null, cv);
		db.close();
	}
	
	public int getCount() {
		return this.getCount(0);
	}

	public int getCount(int dealStatus) {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cur = db.rawQuery("SELECT * FROM " + tableName + " WHERE deal_status = " + dealStatus, null);
		int x = cur.getCount();
		cur.close();
		return x;
	}
	
	public Cursor getAll() {
		return this.getAll(0);
	}

	public Cursor getAll(int dealStatus) {
//		SQLiteDatabase db = this.getWritableDatabase();

		// Cursor cur=
		// db.rawQuery("Select "+colID+" as _id , "+colName+", "+colAge+" from "+employeeTable,
		// new String [] {});

		// Cursor cur = db.rawQuery("SELECT *, strftime('"+ formatDate
		// +"',scan_date,'localtime') as scan_date FROM " + tableName, null);
		// return cur;

//		return db.rawQuery("SELECT * FROM " + tableName + " WHERE deal_status = " + dealStatus, null);
		return getAll(dealStatus, 0);
	}
	
	public Cursor getAll(int dealStatus,int cat_id) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		String sql = "SELECT * FROM " + tableName + " WHERE deal_status = " + dealStatus;
		
		if (cat_id != 0) {
			sql += " AND cat_ids LIKE '%;"+cat_id+";%'";
		} 

		return db.rawQuery(sql, null);
	}
	

	public Deal getRowById(int ID) {
		SQLiteDatabase db = this.getReadableDatabase();

		String[] params = new String[] { String.valueOf(ID) };
		// Cursor c =
		// db.rawQuery("SELECT history_id, qr_code, account_id, strftime('"+
		// formatDate +"',scan_date,'localtime') as scan_date FROM " +
		// historyTable + " WHERE "
		// + colID + "=?", params);

		Cursor c = db.rawQuery("SELECT * FROM " + tableName + " WHERE " + colID
				+ "=?", params);

		c.moveToFirst();

		Deal d = new Deal();

		d._id = c.getInt(c.getColumnIndex(colID)) + "";
		d._deal_status = c.getString(c.getColumnIndex(colDealStatus));
		d._deal_code = c.getString(c.getColumnIndex(colDealCode));
		d._name = c.getString(c.getColumnIndex(colName));

		d._highlight = c.getString(c.getColumnIndex(colHighlight));
		d._desc = c.getString(c.getColumnIndex(colDest));
		d._terms = c.getString(c.getColumnIndex(colTerms));
		d._short_desc = c.getString(c.getColumnIndex(colShortDect));
		d._pic_dir = c.getString(c.getColumnIndex(colPicDir));

		d._origin_price = c.getFloat(c.getColumnIndex(colOriginPrice));
		d._price = c.getFloat(c.getColumnIndex(colPrice));
		d._discount = c.getFloat(c.getColumnIndex(colDiscount));
		d._prepay_percent = c.getFloat(c.getColumnIndex(colPrepayPercent));

		d._start_at = c.getString(c.getColumnIndex(colStartAt));
		d._end_at = c.getString(c.getColumnIndex(colEndAt));

		d._merchant_id = c.getString(c.getColumnIndex(colMerchantID));
		d._merchant_name = c.getString(c.getColumnIndex(colMerchantName));
		d._merchant_address = c.getString(c.getColumnIndex(colMerchantAddress));
		d._merchant_telephone = c.getString(c
				.getColumnIndex(colMerchantTelephone));
		d._merchant_long = c.getString(c.getColumnIndex(colMerchantLong));
		d._merchant_lat = c.getString(c.getColumnIndex(colMerchantLat));
		
		
		d._price_buy = c.getFloat(c.getColumnIndex(colPriceBuy));
		d._use_dynamic = c.getInt(c.getColumnIndex(colUserDynamic));
		d._hot_deal = c.getInt(c.getColumnIndex(colHotDeal));
		d._video_desc = c.getString(c.getColumnIndex(colVideoDesc));
		d._cat_ids = c.getString(c.getColumnIndex(colCatIds));
		
		d._price_step_json = c.getString(c.getColumnIndex(colPriceStep));
		d._deal_types_json = c.getString(c.getColumnIndex(colDealTypes));
//		d._currency_code = c.getString(c.getColumnIndex(currency_code));

		c.close();
		return d;
	}

	public void delete(int dealStatus) {
		SQLiteDatabase db = this.getWritableDatabase();

		db.delete(tableName, colDealStatus + "= ?",
				new String[] { String.valueOf(dealStatus) });
		db.close();

	}
	
	public void delete(Deal d) {
		SQLiteDatabase db = this.getWritableDatabase();

		db.delete(tableName, colID + "= ?",
				new String[] { String.valueOf(d._id) });
		db.close();

	}

	public void deleteAll() {
		SQLiteDatabase db = this.getWritableDatabase();

		db.delete(tableName, null, null);
		db.close();

	}

}
