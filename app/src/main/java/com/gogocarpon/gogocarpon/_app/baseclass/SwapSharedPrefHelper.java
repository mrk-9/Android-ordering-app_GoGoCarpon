package com.gogocarpon.gogocarpon._app.baseclass;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/***************************************************************************
 * This class will hold all variables which are sharing by other clients   *	
 * To modify these variable, must go thru SharedPreferences.Editor         * 																		   	
 ***************************************************************************/

public class SwapSharedPrefHelper {
	
	static final String PREFERENCE 							= 	"com.raptureedge.swapanywhere";
	static final String NUMBER_QUESTION_ASKED 				= 	"iNumberQuestionAsked";
	static final String	TIME_AT_FIFTH_QUESTION 				= 	"timeAtFifthQuestion";
	static final String DELTA_TIME_OF_SERVER_AND_CLIENT 	= 	"lDeltaTimeOfServerAndClient";
	static final String USER_ID								= 	"USERID" ;
	static final String NOTIFICATION_NUMBER					= 	"NOTIFICATION_NUMBER" ;
	static final String SERVER_TIMEZONE					    = 	"SERVER_TIMEZONE" ;
	
	
	static final String DISPLAY_MY_QUESTIONS			    = 	"DISPLAY_MY_QUESTIONS" ;
	static final String DISPLAY_MY_QBOTHERS					= 	"DISPLAY_MY_QBOTHERS" ;
	static final String DISPLAY_MY_ASKERING					= 	"DISPLAY_MY_ASKERING" ;
	
	static final String SHOW_NOTIFICATION_QUESTION_BY_OTHER =   "SHOW_NOTIFICATION_QUESTION_BY_OTHER";
	static final String SHOW_NOTIFICATION_MY_QUESTION       =   "SHOW_NOTIFICATION_MY_QUESTION";
	static final String ENABLE_NOTIFICATION_SOUND           =   "ENABLE_NOTIFICATION_SOUND";

	
	/* Get/set  for Notification Sound Setting */
	public static Boolean getEnableNotificationSound(Context context) {
		final SharedPreferences prefs = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);	
		return prefs.getBoolean(ENABLE_NOTIFICATION_SOUND, true);
	}
	
	public static void setEnableNotificationSound(Context context,  boolean info) {
		final SharedPreferences prefs = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putBoolean(ENABLE_NOTIFICATION_SOUND, info);	
		editor.commit();
		
	}
	
	
	/* Get/set bool value : is show notification on question by other */
	public static Boolean getShowNotificationQuestionByOther(Context context) {
		final SharedPreferences prefs = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);	
		return prefs.getBoolean(SHOW_NOTIFICATION_QUESTION_BY_OTHER, true);
	}
	
	public static void setShowNotificationQuestionByOther(Context context,  boolean info) {
		final SharedPreferences prefs = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
		Editor editor = prefs.edit();
		editor.putBoolean(SHOW_NOTIFICATION_QUESTION_BY_OTHER, info);	
		editor.commit();
		
	}

	
}
