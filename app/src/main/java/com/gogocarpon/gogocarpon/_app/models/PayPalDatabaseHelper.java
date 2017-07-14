package com.gogocarpon.gogocarpon._app.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PayPalDatabaseHelper extends SQLiteOpenHelper {

	static final String formatDate = "%Y-%m-%d %H:%M:%S";
	static final String dbName = "enmasse_user_db";
	static final String tableName = "paypal";
	
	public static final String colEmail = "merchant_email";
	public static final String colName = "api_username";
	public static final String colSignature = "signature";
	
	public static final String colCountryCode = "country_code";
	public static final String colCurrencyCode = "currency_code";

	// public SQLiteDatabase myDataBase;
	// private String DB_PATH ;
	// private Context myContext;

	public PayPalDatabaseHelper(Context context) {
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
		db.execSQL("CREATE TABLE 'paypal' ('merchant_email' VARCHAR PRIMARY KEY ,'api_username' VARCHAR,'signature' VARCHAR,'country_code' VARCHAR,'currency_code' VARCHAR);");		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

		Log.d("My Log", "database : onUpgrade");
		db.execSQL("DROP TABLE IF EXISTS deals");
		db.execSQL("DROP TABLE IF EXISTS categories");
		db.execSQL("DROP TABLE IF EXISTS paypal");

		//
		// db.execSQL("DROP TRIGGER IF EXISTS dept_id_trigger");
		// db.execSQL("DROP TRIGGER IF EXISTS dept_id_trigger22");
		// db.execSQL("DROP TRIGGER IF EXISTS fk_empdept_deptid");
		// db.execSQL("DROP VIEW IF EXISTS "+viewEmps);
		onCreate(db);
	}

	public void addDeal(PayPal c) {
		
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();

		cv.put(colEmail, c.merchant_email);
		cv.put(colName, c.api_username);
		cv.put(colSignature, c.signature);
		cv.put(colCountryCode, c.country_code);
		cv.put(colCurrencyCode, c.currency_code);

		db.insert(tableName, null, cv);
		db.close();
	}
	
	public Cursor getAll() {
		SQLiteDatabase db = this.getWritableDatabase();

		// Cursor cur=
		// db.rawQuery("Select "+colID+" as _id , "+colName+", "+colAge+" from "+employeeTable,
		// new String [] {});

		// Cursor cur = db.rawQuery("SELECT *, strftime('"+ formatDate
		// +"',scan_date,'localtime') as scan_date FROM " + tableName, null);
		// return cur;

		return db.rawQuery("SELECT * FROM " + tableName, null);
	}

	public PayPal getRowById(int ID) {
		SQLiteDatabase db = this.getReadableDatabase();

		String[] params = new String[] { String.valueOf(ID) };
		// Cursor c =
		// db.rawQuery("SELECT history_id, qr_code, account_id, strftime('"+
		// formatDate +"',scan_date,'localtime') as scan_date FROM " +
		// historyTable + " WHERE "
		// + colID + "=?", params);

		Cursor c = db.rawQuery("SELECT * FROM " + tableName + " WHERE " + colEmail
				+ "= ?", params);

		c.moveToFirst();

		PayPal cat = new PayPal();

		cat.merchant_email = c.getInt(c.getColumnIndex(colEmail)) + "";
		cat.api_username = c.getString(c.getColumnIndex(colName));
		cat.signature = c.getString(c.getColumnIndex(colSignature));
		cat.country_code = c.getString(c.getColumnIndex(colCountryCode));
		cat.currency_code = c.getString(c.getColumnIndex(colCurrencyCode));

		c.close();
		return cat;
	}

	public void delete(PayPal cat) {
		SQLiteDatabase db = this.getWritableDatabase();

		db.delete(tableName, colEmail + "= ?",
				new String[] { String.valueOf(cat.merchant_email) });
		db.close();
	}

	public void deleteAll() {
		SQLiteDatabase db = this.getWritableDatabase();

		db.delete(tableName, null, null);
		db.close();
	}

	
}
