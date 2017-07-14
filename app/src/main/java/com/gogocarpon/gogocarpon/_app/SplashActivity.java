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
package com.gogocarpon.gogocarpon._app;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.gogocarpon.gogocarpon.R;
import com.gogocarpon.gogocarpon._app.baseclass.AppConfig;
import com.gogocarpon.gogocarpon._app.baseclass.AppPreferences;
import com.gogocarpon.gogocarpon._app.baseclass.BaseActivity;
import com.gogocarpon.gogocarpon._app.baseclass.GrabRequestUrl;
import com.gogocarpon.gogocarpon._app.baseclass.ImageLoader;
import com.gogocarpon.gogocarpon._app.baseclass.IntentManager;
import com.gogocarpon.gogocarpon._app.baseclass.UserInfo;
import com.gogocarpon.gogocarpon._app.models.Category;
import com.gogocarpon.gogocarpon._app.models.CategoryDatabaseHelper;
import com.gogocarpon.gogocarpon._app.models.Deal;
import com.gogocarpon.gogocarpon._app.models.DealDatabaseHelper;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;


/**
 * @author Dylan
 * 
 */
public class SplashActivity extends BaseActivity {

	private SplashActivity SplashActivityRef = null;

	// Constant
	public static final int REQUEST_DEALS = 1;

	private int CountTimeAtSplashScreen = 0;

	private Handler mHandler = new Handler();
	private Runnable mUpdateUITask = new Runnable() {
		@Override
		public void run() {
			if (CountTimeAtSplashScreen++ > 1) {

				_intentManager.OpenActivity(IntentManager.LOGIN_ACTIVITY);

				mHandler.removeCallbacks(this);

			}
			mHandler.postDelayed(this, AppConfig.MILI_PER_SECOND);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.act_splash);
		SplashActivityRef = this;
        loadDealFromWebService();

        Drawable colorDrawable = new ColorDrawable(getResources().getColor(R.color.blue_light));
        Drawable bottomDrawable = new ColorDrawable(getResources().getColor(android.R.color.transparent));
        LayerDrawable ld = new LayerDrawable(new Drawable[]{colorDrawable, bottomDrawable});
        getSupportActionBar().setBackgroundDrawable(ld);
		// Demo user
		// UserInfo user = UserInfo.getInstance();
		// user.id = "229";
		// user.name = "Demonstration";
		// user.username = "demo1";
		// user.email = "duc.bui@beyondedge.com.sg";
		// user.token = "4401e38e25b03c8e1129dd5e46e75e8e";
		// // user.city_id = "1";
		// user.setLogin();


	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

        UserInfo info = UserInfo.getInstance();

//        if (info.city_id == null) {
//            // Chay ung dung lan dau
//            mHandler.removeCallbacks(mUpdateUITask);
//            mHandler.postDelayed(mUpdateUITask, AppConfig.MILI_PER_SECOND);
//
//            return;
//        }

        // Load data from webservice and save to db
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mHandler.removeCallbacks(mUpdateUITask);
	}

	// -------------------------------------------------------

	protected void loadDealFromWebService() {

		MultipartEntity reqEntity = new MultipartEntity();

		try {
			UserInfo info = UserInfo.getInstance();
			// Log.i("LOG APP", "info.city_id = " + info.city_id);
			reqEntity.addPart("city_id", new StringBody("2"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		GrabRequestUrl urlrequest = new GrabRequestUrl(SplashActivityRef,
				SplashActivity.this, reqEntity);

		// Log.d("My Log", "Request server loadDealFromWebService :"
		// + AppConfig.URI_ALL_LOAD_DATA);
		String url = AppConfig.URI_ALL_LOAD_DATA;
		urlrequest.executeRequest(AppConfig.URI_ALL_LOAD_DATA, REQUEST_DEALS,
				false);
	}

	@Override
	public void onAsyncTaskComplete(String contentData, Integer messageID) {

		if (messageID == REQUEST_DEALS) {

			try {
				JSONObject jsonRoot = new JSONObject(contentData);

				// Log.i("LOG APP", "" + jsonRoot.getString("cates"));

				//load merchant
				JSONObject jPaypal = jsonRoot.getJSONObject("paypal");
				this.savePaypal(jPaypal);
				// Load category into table categories
				JSONArray jArrCats = new JSONArray(jsonRoot.getString("cates"));
				this.insertCategoryIndoDb(jArrCats);
				

				// Load deal into table deals
				JSONArray jArrDeals = new JSONArray(jsonRoot.getString("deals"));
				this.insertDealIndoDb(jArrDeals);


			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// Luu lai ngay cuoi cung cap nhat db
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					AppConfig.SERVER_TIME_FORMAT, Locale.ENGLISH);
			UserInfo info = UserInfo.getInstance();
			info.setLastUpdateDb(dateFormat.format(new Date()));

			// Delete all image cache
			ImageLoader imageLoader = ImageLoader
					.getInstance(getApplicationContext());
			imageLoader.clearCache();

			// Delete cache data load from db
			Log.i("LOG APP", "Delete cache");
			UserInfo.getInstance().arrCats = null;
			UserInfo.getInstance().arrFeatureDeals = null;
			UserInfo.getInstance().arrAllDeals = null;
			UserInfo.getInstance().arrUpcomingDeals = null;
			UserInfo.getInstance().arrExpiredDeals = null;

			Hashtable<String, ArrayList<Deal>> cDeal = new Hashtable<String, ArrayList<Deal>>();
			UserInfo.getInstance().cacheDeal = null;
			UserInfo.getInstance().cacheDeal = cDeal;

			// Sau khi them du lieu vao db - Mo trang HOME
			// Log.i("LOG APP", "Da load xong tat ca");
			_intentManager.OpenActivity(IntentManager.HOME_ACTIVITY);
            finish();
			return;
		}

		super.onAsyncTaskComplete(contentData, messageID);
	}

	@Override
	public void onAsyncTaskError(String contentData, Integer messageID) {

		if (messageID == REQUEST_DEALS) {
			// Xu ly loi
			// HashMap<String, String> params = new HashMap<String, String>();
			//
			// CharSequence displayContents = UserInfo.sResultHandler
			// .getDisplayContents();
			// Log.d("My Log", "Serial error: " + displayContents.toString());
			//
			// // Add cac bien vao trong params
			// params.put("status_info", "2");
			// params.put("coupon_code", displayContents.toString());
			// params.put("message_error", contentData);
			Toast.makeText(getApplicationContext(), contentData,
					Toast.LENGTH_LONG).show();
			return;
		}

		super.onAsyncTaskError(contentData, messageID);

		// Mo trang HOME
		_intentManager.OpenActivity(IntentManager.HOME_ACTIVITY);
        finish();
	}

	// insertDealIndoDb
	private void insertDealIndoDb(JSONArray jArrDeals) {

		if (jArrDeals.length() <= 0)
			return;

		try {

			DealDatabaseHelper dealHepler = new DealDatabaseHelper(
					SplashActivityRef);

			// Clear all data
			dealHepler.deleteAll();

			for (int i = 0; i < jArrDeals.length(); ++i) {

				JSONObject rec = jArrDeals.getJSONObject(i);

				Deal obj = new Deal();

				obj._id = rec.getString("id");
				obj._deal_status = rec.getString("deal_status"); // Deal today
				obj._deal_code = rec.getString("deal_code");
				obj._name = rec.getString("name");

				obj._pic_dir = rec.getString("pic_dir");
				obj._start_at = rec.getString("start_at");
				obj._end_at = rec.getString("end_at");

				obj._price = (float) rec.getDouble("price");
				obj._origin_price = (float) rec.getDouble("origin_price");
				obj._discount = (float) rec.getDouble("discount");
				obj._prepay_percent = (float) rec.getDouble("prepay_percent");

				// Log.i("LOG APP", ") = " + rec.getString("highlight"));

				obj._short_desc = rec.getString("short_desc");
				obj._highlight = rec.getString("highlight");
				obj._desc = rec.getString("description");
				obj._terms = rec.getString("terms");

				obj._merchant_id = rec.getString("merchant_id");

				if (Integer.parseInt(obj._merchant_id) > 0) {
					JSONObject m_info = rec.getJSONObject("merchant_info");

					obj._merchant_name = m_info.getString("merchant_name");
					obj._merchant_address = m_info
							.getString("merchant_address");
					obj._merchant_telephone = m_info
							.getString("merchant_telephone");
					obj._merchant_long = m_info.getString("merchant_long");
					obj._merchant_lat = m_info.getString("merchant_lat");
				}

				obj._price_buy = (float) rec.getDouble("price_buy");
				if (rec.has("hot_deal")) {
					obj._hot_deal = (int) rec.getInt("hot_deal");
				} else {
					obj._hot_deal = 0;
				}

				obj._use_dynamic = (int) rec.getInt("use_dynamic");
				if (rec.has("video_desc")) {
					obj._video_desc = rec.getString("video_desc");
				} else {
					obj._video_desc = "0";
				}
				obj._cat_ids = rec.getString("cat_ids");

				obj._price_step_json = rec.getString("price_step");
				obj._deal_types_json = rec.getString("deal_types");
				obj._currency_code = mCurrencyCode;
				dealHepler.addDeal(obj);
			}

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	private String mCurrencyCode = "USD";

	// insertCategoryIndoDb
	private void insertCategoryIndoDb(JSONArray jArrCats) {

		if (jArrCats.length() <= 0)
			return;

		// Load category
		CategoryDatabaseHelper catHepler = new CategoryDatabaseHelper(
				SplashActivityRef);

		// Clear all data
		catHepler.deleteAll();

		try {
			for (int i = 0; i < jArrCats.length(); ++i) {

				JSONObject rec = jArrCats.getJSONObject(i);

				Category obj = new Category();

				obj._id = rec.getString("id");
				obj._name = rec.getString("name");
				obj._desc = "";
				obj._created_at = "";
				obj._updated_at = "";

				catHepler.addDeal(obj);

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void savePaypal(JSONObject jObj) {
		String merchant_email = "";
		String api_username = "";
		String signature = "";
		String country_code = "";
		String currency_code = "";
		try {
			if (jObj.has("merchant_email")) {
				merchant_email = jObj.getString("merchant_email");
			}
			if (jObj.has("api_username")) {
				api_username = jObj.getString("api_username");
			}
			if (jObj.has("signature")) {
				signature = jObj.getString("signature");
			}
			if (jObj.has("country_code")) {
				country_code = jObj.getString("country_code");
			}
			if (jObj.has("currency_code")) {
				currency_code = jObj.getString("currency_code");
			}
			AppPreferences appPres = new AppPreferences(SplashActivityRef);
			appPres.savePaypal(merchant_email, api_username, signature,
					country_code, currency_code);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// private void insertPaypalIndoDb(JSONObject jObjPaypal) {
	//
	// if (jObjPaypal.equals(""))
	// return;
	//
	// // Load category
	// PayPalDatabaseHelper catHepler = new PayPalDatabaseHelper(
	// SplashActivityRef);
	//
	// // Clear all data
	// catHepler.deleteAll();
	//
	// try {
	// for (int i = 0; i < jArrPaypal.length(); ++i) {
	//
	// JSONObject rec = jArrPaypal.getJSONObject(i);
	//
	// Category obj = new Category();
	//
	// obj._id = rec.getString("id");
	// obj._name = rec.getString("name");
	// obj._desc = "";
	// obj._created_at = "";
	// obj._updated_at = "";
	//
	// catHepler.addDeal(obj);
	//
	// }
	// } catch (JSONException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// }

}
