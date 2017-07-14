/*
 * Copyright 2010 Rapture Egde, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gogocarpon.gogocarpon._app.baseclass;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;

import com.gogocarpon.gogocarpon._app.AboutActivity;
import com.gogocarpon.gogocarpon._app.AllDealActivity;
import com.gogocarpon.gogocarpon._app.CityListActivity;
import com.gogocarpon.gogocarpon._app.DealDetailActivity;
import com.gogocarpon.gogocarpon._app.DiscussActivity;
import com.gogocarpon.gogocarpon._app.EditProfileActivity;
import com.gogocarpon.gogocarpon._app.ExpiredDealActivity;
import com.gogocarpon.gogocarpon._app.FeatureDealActivity;
import com.gogocarpon.gogocarpon._app.HomeActivity;
import com.gogocarpon.gogocarpon._app.LoginActivity;
import com.gogocarpon.gogocarpon._app.PurchaseActivity;
import com.gogocarpon.gogocarpon._app.PurchaseHistoryActivity;
import com.gogocarpon.gogocarpon._app.PurchaseNotifyActivity;
import com.gogocarpon.gogocarpon._app.PurchaseReviewActivity;
import com.gogocarpon.gogocarpon._app.SignupActivity;
import com.gogocarpon.gogocarpon._app.SignupAgreeActivity;
import com.gogocarpon.gogocarpon._app.SplashActivity;
import com.gogocarpon.gogocarpon._app.UpcomingDealActivity;
import com.gogocarpon.gogocarpon.map.MapViewFragmentActivity;

import java.util.List;


public class IntentManager {

	public static final int EMPTY_ACTIVITY = 0;
	public static final int SPLASH_SCREEN_ACTIVITY = EMPTY_ACTIVITY + 1;
	public static final int HOME_ACTIVITY = SPLASH_SCREEN_ACTIVITY + 1;
	public static final int FEATURE_DEAL_ACTIVITY = HOME_ACTIVITY + 1;
	public static final int ALL_DEAL_ACTIVITY = FEATURE_DEAL_ACTIVITY + 1;

	public static final int UPCOMING_DEAL_ACTIVITY = ALL_DEAL_ACTIVITY + 1;
	public static final int EXPIRED_DEAL_ACTIVITY = UPCOMING_DEAL_ACTIVITY + 1;
	public static final int LOGIN_ACTIVITY = EXPIRED_DEAL_ACTIVITY + 1;
	public static final int LOGOUT_ACTIVITY = LOGIN_ACTIVITY + 1;
	public static final int SIGNUP_ACTIVITY = LOGOUT_ACTIVITY + 1;
	public static final int SIGNUP_AGREE_ACTIVITY = SIGNUP_ACTIVITY + 1;
	public static final int CHANGE_PW_ACTIVITY = SIGNUP_AGREE_ACTIVITY + 1;
	public static final int CHANGE_PROFILE_ACTIVITY = CHANGE_PW_ACTIVITY + 1;
	public static final int PURCHASE_HISTORY_ACTIVITY = CHANGE_PROFILE_ACTIVITY + 1;

	public static final int DEAL_DETAIL_ACTIVITY = PURCHASE_HISTORY_ACTIVITY + 1;
	public static final int BUY_DEAL_ACTIVITY = DEAL_DETAIL_ACTIVITY + 1;
	public static final int REVIEW_BUY_DEAL_ACTIVITY = BUY_DEAL_ACTIVITY + 1;
	public static final int NOTIFY_BUY_DEAL_ACTIVITY = REVIEW_BUY_DEAL_ACTIVITY + 1;
	public static final int MAP_LOCATION_ACTIVITY = NOTIFY_BUY_DEAL_ACTIVITY + 1;
	
	public static final int DISCUSS_ACTIVITY = MAP_LOCATION_ACTIVITY + 1;
	public static final int DISCUSS_FORM_ACTIVITY = DISCUSS_ACTIVITY + 1;
	
	public static final int CITY_ACTIVITY = DISCUSS_FORM_ACTIVITY + 1;

    public static final int ABOUT_ACTIVITY = CITY_ACTIVITY +1;
	
	protected String LOG_TAG = "Enmasse's User";
	private String DEFAULT_INTENT_PARAMS = "DEFAULT_INTENT_PARAMS";
	
	private Activity _activity;
	
	public IntentManager(Activity activity) {
		this._activity = activity;
	}
	
	public ActParameter getIntentParams() {
		// Extra intent params
		Bundle extras = _activity.getIntent().getExtras();
		if (extras != null) {
			return (ActParameter) extras
					.getSerializable(DEFAULT_INTENT_PARAMS);
		}
		
		return null;
	}
	
	public void OpenActivity(int Request) {
		this.OpenActivity(Request, null);
	}	
	
	public void OpenActivity(int Request, ActParameter params) {
		Intent myIntent = this.getAppIntent(Request);
		myIntent.putExtra(DEFAULT_INTENT_PARAMS, params);
		this._activity.startActivity(myIntent);
	}
	
	public Intent getAppIntent(int Request) {
		Intent myIntent = null;

		if (Request == SPLASH_SCREEN_ACTIVITY) {
			myIntent = new Intent(_activity, SplashActivity.class);
		} else if (Request == HOME_ACTIVITY) {
			myIntent = new Intent(_activity, HomeActivity.class);
			// Clear all history intent
			myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			// finish();
		} else if (Request == LOGIN_ACTIVITY) {
			myIntent = new Intent(_activity, LoginActivity.class);
		} else if (Request == SIGNUP_ACTIVITY) {
			myIntent = new Intent(_activity, SignupActivity.class);
		} else if (Request == SIGNUP_AGREE_ACTIVITY) {
			myIntent = new Intent(_activity, SignupAgreeActivity.class);
		} else if (Request == CHANGE_PROFILE_ACTIVITY) {
			myIntent = new Intent(_activity, EditProfileActivity.class);
		} else if (Request == PURCHASE_HISTORY_ACTIVITY) {
			myIntent = new Intent(_activity, PurchaseHistoryActivity.class);
		} else if (Request == FEATURE_DEAL_ACTIVITY) {
			myIntent = new Intent(_activity, FeatureDealActivity.class);
		} else if (Request == ALL_DEAL_ACTIVITY) {
			myIntent = new Intent(_activity, AllDealActivity.class);
		} else if (Request == UPCOMING_DEAL_ACTIVITY) {
			myIntent = new Intent(_activity, UpcomingDealActivity.class);
		} else if (Request == EXPIRED_DEAL_ACTIVITY) {
			myIntent = new Intent(_activity, ExpiredDealActivity.class);
		} else if (Request == DEAL_DETAIL_ACTIVITY) {
			myIntent = new Intent(_activity, DealDetailActivity.class);
		} else if (Request == BUY_DEAL_ACTIVITY) {
			myIntent = new Intent(_activity, PurchaseActivity.class);
		} else if (Request == REVIEW_BUY_DEAL_ACTIVITY) {
			myIntent = new Intent(_activity, PurchaseReviewActivity.class);
		} else if (Request == NOTIFY_BUY_DEAL_ACTIVITY) {
			myIntent = new Intent(_activity, PurchaseNotifyActivity.class);
		} else if (Request == MAP_LOCATION_ACTIVITY) {
			myIntent = new Intent(_activity, MapViewFragmentActivity.class);
		} else if (Request == DISCUSS_ACTIVITY) {
			myIntent = new Intent(_activity, DiscussActivity.class);
		} else if (Request == CITY_ACTIVITY) {
			myIntent = new Intent(_activity, CityListActivity.class);
		} else if (Request == ABOUT_ACTIVITY) {
            myIntent = new Intent(_activity, AboutActivity.class);
        }
		return myIntent;
	}

	public boolean isIntentAvailable(Context context, String action) {
		final PackageManager packageManager = context.getPackageManager();
		final Intent intent = new Intent(action);
		List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(
				intent, PackageManager.MATCH_DEFAULT_ONLY);
		if (resolveInfo.size() > 0) {
			return true;
		}
		return false;
	}

}
