package com.gogocarpon.gogocarpon;

import java.io.IOException;
import java.io.Serializable;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

//http://stackoverflow.com/questions/5816695/android-sharedpreferences-with-serializable-object

public class LocalStore {

	private static final String TAG = "LocalStore";
	private static final String PREF_FILE_NAME = "userprefs";

	public static void clear(Context context) {
		clear(context, "unknown");
	}

	public static void clear(Context context, String caller) {
		Editor editor = context.getSharedPreferences(PREF_FILE_NAME,
				Context.MODE_PRIVATE).edit();
		editor.clear();
		editor.commit();
		Log.d(TAG, "caller:" + caller + "|clear LocalStore");
	}

	public static boolean setCustomBooleanData(String key, boolean value,
			Context context) {
		Editor editor = context.getSharedPreferences(PREF_FILE_NAME,
				Context.MODE_PRIVATE).edit();
		editor.putBoolean(key, value);

		return editor.commit();
	}

	public static boolean getCustomBooleanData(String key, Context context) {
		SharedPreferences savedSession = context.getSharedPreferences(
				PREF_FILE_NAME, Context.MODE_PRIVATE);

		return (savedSession.getBoolean(key, false));
	}

	public static boolean setCustomStringData(String key, String value,
			Context context) {
		Editor editor = context.getSharedPreferences(PREF_FILE_NAME,
				Context.MODE_PRIVATE).edit();
		editor.putString(key, value);

		return editor.commit();
	}

	public static String getCustomStringData(String key, Context context) {
		SharedPreferences savedSession = context.getSharedPreferences(
				PREF_FILE_NAME, Context.MODE_PRIVATE);

		return (savedSession.getString(key, null));
	}

	public static boolean isCustomStringExistInLocal(String customKey,
			Context context) {
		SharedPreferences savedSession = context.getSharedPreferences(
				PREF_FILE_NAME, Context.MODE_PRIVATE);

		return (savedSession.getString(customKey, null)) == null ? false : true;
	}

	public static boolean saveObject(String objKey, Serializable dataObj,
			Context context) {
		Editor editor = context.getSharedPreferences(PREF_FILE_NAME,
				Context.MODE_PRIVATE).edit();
		try {
			editor.putString(objKey, ObjectSerializer.serialize(dataObj));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Log.d(TAG, "savedObject| objKey:" + objKey + "/" + dataObj.toString());

		return editor.commit();
	}

	public static Object getObject(String objKey, Context context) {
		SharedPreferences savedSession = context.getSharedPreferences(
				PREF_FILE_NAME, Context.MODE_PRIVATE);

		Object dataObj = null;
		try {
			dataObj = ObjectSerializer.deserialize(savedSession.getString(
					objKey, null));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return dataObj;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
