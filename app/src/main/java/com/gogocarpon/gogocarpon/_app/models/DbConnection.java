package com.gogocarpon.gogocarpon._app.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbConnection extends SQLiteOpenHelper {


	static final String formatDate = "%Y-%m-%d %H:%M:%S";
	static final String dbName = "enmasse_user_db";
	static final int dbVersion = 1;
	
	
	// Override at child class
	protected String tableName;
	protected String colID;

	// public SQLiteDatabase myDataBase;
	// private String DB_PATH ;
	// private Context myContext;

	public DbConnection(Context context) {
		
		// TODO Auto-generated constructor stub
		super(context, dbName, null, dbVersion);
	}
	

	@Override
	public void onCreate(SQLiteDatabase db) {

		// TODO : Add define table
		db.execSQL("CREATE TABLE 'deals' ('id' INTEGER PRIMARY KEY ,'deal_code' VARCHAR,'name' text,'highlight' TEXT,'short_desc' text,'pic_dir' VARCHAR,'origin_price' FLOAT,'price' float,'discount' float,'prepay_percent' float,'start_at' DATETIME,'end_at' DATETIME);");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		// TODO : Add update table
		db.execSQL("DROP TABLE IF EXISTS deals");
		onCreate(db);
	}
	
	// Function
	
	public int getCount() {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cur = db.rawQuery("Select * from " + tableName, null);
		int x = cur.getCount();
		cur.close();
		return x;
	}

	public Cursor getByQuery(String query) {
		SQLiteDatabase db = this.getWritableDatabase();
		return db.rawQuery(query, null);
	}
	
	public Cursor getAll() {
		return this.getByQuery("SELECT * FROM " + tableName);
	}
	
	public Cursor getRowById(int ID) {
		SQLiteDatabase db = this.getReadableDatabase();

		String[] params = new String[] { String.valueOf(ID) };

		Cursor c = db.rawQuery("SELECT * FROM " + tableName + " WHERE "
				+ colID + "=?", params);
		
		c.moveToFirst();

		return c;
	}
	
	public Cursor getRowByCondition(String condition) {
		SQLiteDatabase db = this.getReadableDatabase();
		String[] params = new String[] { String.valueOf(condition) };
		return db.rawQuery("SELECT * FROM " + tableName + " WHERE ?", params);
	}

	public void delete(int ID) 
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(tableName, colID + "= ?",
				new String[] { String.valueOf(ID) });
		db.close();
	}
	
	public void deleteAll() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(tableName, null, null);
		db.close();
	}
	
	public long insert(HashMap<String, String> data) {
		
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		
		for (Map.Entry<String, String> entry : data.entrySet()) {
			cv.put(entry.getKey(), entry.getValue());
		}

		long result = db.insert(tableName, null, cv);
		db.close();		
		return result;
	}
	
	public int update(HashMap<String, String> data, String condition) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues cv = new ContentValues();

		for (Map.Entry<String, String> entry : data.entrySet()) {
			cv.put(entry.getKey(), entry.getValue());
		}

		int result =  db.update(tableName, cv, " ? ",
				new String[] { String.valueOf(condition) });
		
		db.close();
		return result;
	}

	public int update(HashMap<String, String> data, int ID) {
		return this.update(data, colID + " = " + ID);
	}
	
	@SuppressWarnings("unused")
	private void convertCursor(Cursor c) {
		String[] colNames = c.getColumnNames();
		ArrayList<HashMap<String, String>> returnData = new ArrayList<HashMap<String, String>>();
		
		while (c.moveToNext()) {
			HashMap<String, String> row = new HashMap<String, String>();
			for (String	name : colNames) {
				row.put(name, c.getString(c.getColumnIndex(name)));
			}
			returnData.add(row);
		}
		
	}
	

}
