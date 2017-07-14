package com.gogocarpon.gogocarpon._app.baseclass;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class AppPreferences
{
	 private static final String APP_SHARED_PREFS = "com.emmasse"; //  Name of the file -.xml
     private SharedPreferences appSharedPrefs;
     private Editor prefsEditor;
	private static final String MERCHANTEMAIL="merchant_email";
    private static final String APIUSERNAME="api_username";
    private static final String SIGNATURE="signature";
    private static final String COUNTRYCODE="country_code";
    private static final String CURRENCYCODE="currency_code";
    public AppPreferences(Context context)
    {
        this.appSharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS, Activity.MODE_PRIVATE);
        this.prefsEditor = appSharedPrefs.edit();
    }
    public String getMerchantEmail() {
        return appSharedPrefs.getString(MERCHANTEMAIL, "");
    }
    public String getAPIUsernamed() {
        return appSharedPrefs.getString(APIUSERNAME, "");
    }
    public String getSignture() {
        return appSharedPrefs.getString(SIGNATURE, "");
    }

    public String getCountryCode() {
        return appSharedPrefs.getString(COUNTRYCODE, "");
    }

    public String getCurrentCode() {
        return appSharedPrefs.getString(CURRENCYCODE, "");
    }

    
    public void savePaypal(String merchant,String api, String sign, String country, String currency) {
        prefsEditor.putString(MERCHANTEMAIL, merchant);
        prefsEditor.putString(APIUSERNAME, api);
        prefsEditor.putString(SIGNATURE, sign);
        prefsEditor.putString(COUNTRYCODE, country);
        prefsEditor.putString(CURRENCYCODE, currency);
        
        prefsEditor.commit();
    }
}
