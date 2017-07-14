package com.gogocarpon.gogocarpon._app.baseclass;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;

import java.util.HashMap;
import java.util.Map;

public class AppConfig {

    public static boolean isAbout;
    public static boolean isHistory;
    public static boolean isEditProfile;

	public static final int RELEASE_VERSION = 0;
	public static final int DEBUG_VERSION = RELEASE_VERSION + 1;
	public static final int VERSION = DEBUG_VERSION;

	public static final String TEMPLATE_WEB_SOURCE = "<html><meta name='viewport' content='minimum-scale=0.6; maximum-scale=5;  initial-scale=1; user-scalable=yes; width=290'></head><style> body { background: none repeat scroll 0 0 #FFFFFF; color: #000000;     font: 400 12px/1.3em Arial,Tahoma,Verdana,Helvetica; margin: 0;  padding: 0;	} .container { text-align:left; line-height: 20px; padding: 0px } </style> <body><center><div id='main_div' class='container'>[%1@]<h3>Note specific</h3>[%2@]<h3>Terms and Conditions</h3>[%3@]</div></center></body></html>";
	// public static final String TEMPLATE_WEB_SOURCE =
	// "<html><head><meta name='viewport' content='minimum-scale=0.6; maximum-scale=5;  initial-scale=1; user-scalable=yes; width=290'></head><style> body, html { height: 100%; } body { background: none repeat scroll 0 0 #FFFFFF; color: #000000;     font: 400 12px/1.3em Arial,Tahoma,Verdana,Helvetica; margin: 0;  padding: 0;	} .container { text-align:left; line-height: 20px; padding: 0px } </style> <body><center><div id='main_div' class='container'>%@</div></center></body></html>";

	// Real site
	//public static final String APP_BASE_URI = "http://samuel-test.sales.matamko.com";
    //public static final String APP_BASE_URI = "http://dealguru.info";
//	public static final String APP_BASE_URI = "http://scontipronti.it";
    public static final String APP_BASE_URI = "http://gogocarpon.com";
	
//	public static final String APP_BASE_URI = "http://10.0.2.2/enmasse30";
	
	public static final String URI_SHARE_ACTION = APP_BASE_URI
	+ "/index.php?option=com_enmasse&controller=deal&task=view&";		

	public static final String URI_CHECK_VALIDATE_ACCOUNT = APP_BASE_URI
			+ "/index.php?option=com_enmasse&controller=webserviceuser&task=validateUserAccount";

	public static final String URI_LOGIN = APP_BASE_URI
			+ "/index.php?option=com_enmasse&controller=webserviceuser&task=userLogin";

	public static final String URI_LOGOUT = APP_BASE_URI
			+ "/index.php?option=com_enmasse&controller=webserviceuser&task=userLogout";

	public static final String URI_CREATE_ACCOUNT = APP_BASE_URI
			+ "/index.php?option=com_enmasse&controller=webserviceuser&task=createUserAccount";

	public static final String URI_EDIT_ACCOUNT = APP_BASE_URI
			+ "/index.php?option=com_enmasse&controller=webserviceuser&task=editUserAccount";

	public static final String URI_CHANGE_PW = APP_BASE_URI
			+ "/index.php?option=com_enmasse&controller=webserviceuser&task=changePassword";

	public static final String URI_ORDER_HISTORY = APP_BASE_URI
			+ "/index.php?option=com_enmasse&controller=webserviceuser&task=getOrderHistory";

	public static final String URI_GET_CITIES = APP_BASE_URI
			+ "/index.php?option=com_enmasse&controller=webserviceuser&task=getCitites";

	public static final String URI_DEALS = APP_BASE_URI
			+ "/index.php?option=com_enmasse&controller=webserviceuser&task=getAllDeal";

	public static final String URI_DEAL_DETAIL = APP_BASE_URI
			+ "/index.php?option=com_enmasse&controller=webserviceuser&task=getDealDetail";

	public static final String URI_DEAL_DESCRIPTION = APP_BASE_URI
			+ "/index.php?option=com_enmasse&controller=webserviceuser&task=getDealDescription";

	public static final String URI_DEAL_COMMENTS = APP_BASE_URI
			+ "/index.php?option=com_enmasse&controller=webserviceuser&task=getDealCommentList";

	public static final String URI_DEAL_COMMENT_DETAIL = APP_BASE_URI
			+ "/index.php?option=com_enmasse&controller=webserviceuser&task=getComment";

	public static final String URI_DEAL_COMMENT_ADD = APP_BASE_URI
			+ "/index.php?option=com_enmasse&controller=webserviceuser&task=postComment";

	public static final String URI_PAYPAL_SETTINGS = APP_BASE_URI
			+ "/index.php?option=com_enmasse&controller=webserviceuser&task=getPaypalConf";

	public static final String URI_PAYMENT_TRACKING = APP_BASE_URI
			+ "/index.php?option=com_enmasse&controller=webserviceuser&task=postPaymentTracking";
	
	public static final String URI_ALL_LOAD_DATA = APP_BASE_URI
			+ "/index.php?option=com_enmasse&controller=webserviceuser&task=getcatsanddeals";
	
	public static final String FILENAME = "u-info";

	public static final int NORMAL_CODE = 0;
	public static final int ERROR_CODE = NORMAL_CODE + 1;

	public static final int REFRESH_DATA = 900; // define a time to refresh Deal
												// data (second)
	public static final int MILI_PER_SECOND = 500; // Display splash screen :
													// 1000 miliseconds
	public static final int A_HALF_MINUTE = 30; // 30 secs
	public static final int NOTIFICATION_START_ID = 991; // 30 secs
	public static final String STR_ERROR = "-1"; // 30 secs
	public static final String STR_EMPTY = ""; // 30 secs
	public static final String STR_FILTER = "[^a-zA-Z 0-9?!#$%@*;,.)(]+";
	public static final String JsonMapData = "";
	public static final int MAX_QUESTION_BY_OTHERS_DISPLAYING = 5;
	public static final int MAX_QUESTION_ASKED_BY_ME = 10;
	public static final int TIMEOUT_FOR_QUESTION_BY_OTHER_SECS = 5 * 2 * A_HALF_MINUTE;
	public static final int TIMEOUT_FOR_QUESTION = 5 * 60 * MILI_PER_SECOND;

	public static final String SERVER_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String SQLITE_TIME_FORMAT = "%Y-%m-%d %H:%M:%S";
	
	public static final String DECIMAL_NUMBER_FORMAT = "#,##0.00";
	public static final char DECIMAL_SEPARATOR_FORMAT = ',';
	public static final char DECIMAL_GROUPING_FORMAT = '.';

	// Config Sqlite DB
	public static final String DB_NAME = "enmasse_user_db";
	public static final int DB_VERSION = 1;

	public static final int MANUAL = 1;
	public static final int AUTO = 0;

	// Paypal User Informations
//	public static final String PP_CURRENCY = "EUR"; //  EUR
//	public static final String PP_LANG = "it"; // Lang Itali : it
//	public static final String PP_MERCHANT_NAME = "ScontiPronti";
//	public static final String PP_MERCHANT_DESCRIPTION = "";

//	public static final int PP_ENVIRONMENT = PayPal.ENV_SANDBOX;
//	public static final String PP_RECIPIENT = "us_biz_1183069476_biz@paypal.com";	
//	public static final String PP_APP_ID = "APP-80W284485P519543T";

    public static final String PAYPAL_CLIENT_ID = "AbLgy0hRsq0PmoGK-ws2-jlBIeBVKUUU0xRjbfW1-GAckylz_TDNsh1cMrIiSksc2wpqYC2PisTrKhko";
    public static final String PAYPAL_CLIENT_SECRET = "";

    public static final String PAYPAL_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_NO_NETWORK;
    public static final String PAYMENT_INTENT = PayPalPayment.PAYMENT_INTENT_SALE;
    public static final String DEFAULT_CURRENCY = "USD";
    private static final String CONFIG_RECEIVER_EMAIL = "your@email.com";

    // PayPal server urls
    public static final String URL_PRODUCTS = "http://192.168.0.103/PayPalServer/v1/products";
    public static final String URL_VERIFY_PAYMENT = "http://192.168.0.103/PayPalServer/v1/verifyPayment";

	// Release payment info
	public static final String PP_LANG = "en_US";
	public static final String PP_MERCHANT_NAME = "GoGoCarpon";
	public static final String PP_MERCHANT_DESCRIPTION = "";

    // note that these credentials will differ between live & sandbox environments.
    public static final int REQUEST_CODE_PAYMENT = 1;
    public static final int REQUEST_CODE_FUTURE_PAYMENT = 2;
    public static final int REQUEST_CODE_PROFILE_SHARING = 3;


    public static final HashMap<String , String> ORDER_STATUS = new HashMap<String , String>() {/**
		 * 
		 */
		private static final long serialVersionUID = -8461519690747962879L;
        {
	    put("pending",	"Peding");
	    put("paid", 	"Paid");
	    put("unpaid",   "Unpaid");
	    put("delivered",	"Delivered");
	    put("refunded", 	"Refunded");
	    put("waiting_for_refund",	"Waiting for refund");
	    put("holding_by_deliverer",	"Holding by deliverer");	    
	}};	
	
	// Cac chuc nang dung de luu tru bien cho ung dung
	public static final String PREFERENCE_ID = "com.beyondedge.enmasseuser";

	public static final String VAR_TEN_CUA_BIEN = "VAR_TEN_CUA_BIEN"; // Key;

	public static void putApplicationVar(Context context, String key, Object obj) {

		final SharedPreferences prefs = context.getSharedPreferences(
				PREFERENCE_ID, Context.MODE_PRIVATE);
		Editor editor = prefs.edit();

		if (obj instanceof Integer) {
			editor.putInt(key, ((Integer) obj).intValue());
			// Log.i("LOG APP", "La Integer : " + obj.getClass().getName());
		} else if (obj instanceof Float) {
			editor.putFloat(key, ((Float) obj).floatValue());
			// Log.i("LOG APP", "La Float : " + obj.getClass().getName());
		} else if (obj instanceof Boolean) {
			editor.putBoolean(key, ((Boolean) obj).booleanValue());
		} else if (obj instanceof Long) {
			editor.putLong(key, ((Long) obj).longValue());
		} else if (obj instanceof String) {
			editor.putString(key, obj.toString());
		}

		editor.commit();
	}

	public static Object getApplicationVar(Context context, String key) {

		final SharedPreferences prefs = context.getSharedPreferences(
				PREFERENCE_ID, Context.MODE_PRIVATE);
		return prefs.getAll().get(key);		
//		return AppConfig.getPrefs(context).getAll().get(key);
	}

	// Test cac gia tri luu trong ung dung
	public static String dump(Context context) {
		final SharedPreferences prefs = context.getSharedPreferences(
				PREFERENCE_ID, Context.MODE_PRIVATE);

		String output = "";
		output = output + "Dumping shared preferences...\n";
		for (Map.Entry<String, ?> entry : prefs.getAll().entrySet()) {
			Object val = entry.getValue();
			if (val == null) {
				output += String.format("%s = <null>%n", entry.getKey());
			} else {
				output += String.format("%s = %s (%s)%n", entry.getKey(),
						String.valueOf(val), val.getClass().getSimpleName());
			}
		}
		return output + "Dump complete\n";
	}

	public static SharedPreferences getPrefs(Context context) {
		return context
				.getSharedPreferences(PREFERENCE_ID, Context.MODE_PRIVATE);
	}

}
