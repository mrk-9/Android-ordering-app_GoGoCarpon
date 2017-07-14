package com.gogocarpon.gogocarpon._app.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CategoryDatabaseHelper extends SQLiteOpenHelper {

	static final String formatDate = "%Y-%m-%d %H:%M:%S";
	static final String dbName = "enmasse_user_db";
	static final String tableName = "categories";
	
	public static final String colID = "id";
	public static final String colName = "name";
	public static final String colDest = "description";
	
	public static final String colCreatedAt = "created_at";
	public static final String colUpdatedAt = "updated_at";

	// public SQLiteDatabase myDataBase;
	// private String DB_PATH ;
	// private Context myContext;

	public CategoryDatabaseHelper(Context context) {
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

	public void addDeal(Category c) {
		
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();

		cv.put(colID, c._id);
		cv.put(colName, c._name);
		cv.put(colDest, c._desc);
		cv.put(colCreatedAt, c._created_at);
		cv.put(colUpdatedAt, c._updated_at);

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

	public Category getRowById(int ID) {
		SQLiteDatabase db = this.getReadableDatabase();

		String[] params = new String[] { String.valueOf(ID) };
		// Cursor c =
		// db.rawQuery("SELECT history_id, qr_code, account_id, strftime('"+
		// formatDate +"',scan_date,'localtime') as scan_date FROM " +
		// historyTable + " WHERE "
		// + colID + "=?", params);

		Cursor c = db.rawQuery("SELECT * FROM " + tableName + " WHERE " + colID
				+ "= ?", params);

		c.moveToFirst();

		Category cat = new Category();

		cat._id = c.getInt(c.getColumnIndex(colID)) + "";
		cat._name = c.getString(c.getColumnIndex(colName));
		cat._desc = c.getString(c.getColumnIndex(colDest));
		cat._created_at = c.getString(c.getColumnIndex(colCreatedAt));
		cat._updated_at = c.getString(c.getColumnIndex(colUpdatedAt));

		c.close();
		return cat;
	}

	public void delete(Category cat) {
		SQLiteDatabase db = this.getWritableDatabase();

		db.delete(tableName, colID + "= ?",
				new String[] { String.valueOf(cat._id) });
		db.close();
	}

	public void deleteAll() {
		SQLiteDatabase db = this.getWritableDatabase();

		db.delete(tableName, null, null);
		db.close();
	}

	
}
