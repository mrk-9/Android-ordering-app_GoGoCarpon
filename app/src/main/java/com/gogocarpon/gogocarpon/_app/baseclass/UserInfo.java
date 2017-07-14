package com.gogocarpon.gogocarpon._app.baseclass;

import com.gogocarpon.gogocarpon._app.models.Category;
import com.gogocarpon.gogocarpon._app.models.Deal;

import java.util.ArrayList;
import java.util.Hashtable;


public class UserInfo {

//	// App ID for C2DM server registrations
//	public static String strEmail = AppConfig.STR_EMPTY;
//
//	// Network communication
//	// For app engine SDK
//	public static String strUID = AppConfig.STR_EMPTY; // current userId
//	// public static String receiverId = SwapConfig.STR_EMPTY;
//	// public static String senderId = SwapConfig.STR_EMPTY;
//
//	public static String strIMEI = AppConfig.STR_EMPTY;
//
//	// User info File
//	public static String strRegitrationID = AppConfig.STR_EMPTY;
//
//	public static Double dLatitude = 0.0;
//	public static Double dLongtitude = 0.0;
//
//	public static String strLocation = "GPS Location is unavailable";
//	public static String strServerTimeZone = AppConfig.STR_EMPTY;
//	public static Long DeltaTimeBetweenServerAndClient;
//	public static String strToken = "";
//	public static String strUsername = "";
//
//	// Save categories systems, get from run program
//	public static Map<String, String> data;
//	/*
//	 * user_id => name => username => email => usertype => block =>
//	 * current_points token =>
//	 */

	public String id;
	public String name;
	public String username;
	public String email;
	public String password;
	public String token;
	public String city_id;
	public String cat_id = "0"; // Default cat = 0 (Show All)
	public String lastUpdateData;
	private String lastUpdateDb;
	
	private boolean logined = false;
	private static UserInfo instance;
	
	public ArrayList<Deal> arrFeatureDeals;
	public ArrayList<Deal> arrAllDeals;
	public ArrayList<Deal> arrUpcomingDeals;
	public ArrayList<Deal> arrExpiredDeals;
	
	public Hashtable<String, ArrayList<Deal>> cacheDeal;
	
	public ArrayList<Category> arrCats;
	
	public static synchronized UserInfo getInstance() {
		if (instance == null) {
			instance = new UserInfo();
		}
		return instance;
	}

	private UserInfo() {
		// ...
	}

	public void setLogin() {
		logined = true;
	}
	
	public boolean isLogin() {
		return logined;
	}
	
	public void setLogout() {
		logined = false;
	}
	
	public void reset() {
		id = "";
		name = "";
		username = "";
		email = "";
		password = "";
		token = "";
	}

	public void setLastUpdateDb(String datetime) {
		lastUpdateDb = datetime;
	}
	
	public String getLastUpdateDb() {
		return lastUpdateDb;
	}

}
