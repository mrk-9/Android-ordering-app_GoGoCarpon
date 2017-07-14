package com.gogocarpon.gogocarpon._app;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.gogocarpon.gogocarpon.R;
import com.gogocarpon.gogocarpon._app.baseclass.ActParameter;
import com.gogocarpon.gogocarpon._app.baseclass.AppConfig;
import com.gogocarpon.gogocarpon._app.baseclass.BaseActivity;
import com.gogocarpon.gogocarpon._app.baseclass.GrabRequestUrl;
import com.gogocarpon.gogocarpon._app.baseclass.IntentManager;
import com.gogocarpon.gogocarpon._app.baseclass.UserInfo;

import org.apache.http.entity.mime.MultipartEntity;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

public class CityListActivity extends BaseActivity {

	protected ListView mainListView;
	protected ArrayAdapter<String> adapter;
	protected HashMap<String, String> dataCity;
	protected String[] values;

	protected CityListActivity CityListActivityRef = null;

	public static final int REQUEST_CITY = 1;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_city_list);
        Log.i("LOG APP","City Activity OnCreate");
		CityListActivityRef = this;
		
		setTitle(getString(R.string.em_city_title));

		// Find the ListView resource.
		mainListView = (ListView) findViewById(R.id.mainListView);

		// String[] values = new String[] { "Android", "iPhone",
		// "WindowsMobile",
		// "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
		// "Linux", "OS/2" };

		dataCity = new HashMap<String, String>();

		// values = (String[]) dataCity.values().toArray(new
		// String[dataCity.size()]);

		mainListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				String[] ids = (String[]) dataCity.keySet().toArray(
						new String[dataCity.size()]);

				ActParameter params = new ActParameter();
				params.put("city_id", ids[position]);

				UserInfo.getInstance().city_id = ids[position];
				
				// If user choose old city => refresh data 
				_intentManager.OpenActivity(
						IntentManager.SPLASH_SCREEN_ACTIVITY, params);
				finish();
			}
		});

		MultipartEntity reqEntity = new MultipartEntity();

		GrabRequestUrl urlrequest = new GrabRequestUrl(CityListActivityRef,
				CityListActivity.this, reqEntity);

		Log.d("My Log", "Request server " + AppConfig.URI_GET_CITIES);

		urlrequest.executeRequest(AppConfig.URI_GET_CITIES, REQUEST_CITY);
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		UserInfo info = UserInfo.getInstance();

		if (info.city_id != null) 
		{
			ActionBar ab = getSupportActionBar();
			
			ab.setHomeButtonEnabled(true);
			ab.setDisplayHomeAsUpEnabled(true); // Hien thi bieu tuong nut back
			getSupportActionBar().setIcon(R.drawable.emu_home);

		} else {
//			actionBar = (ActionBar) findViewById(R.id.actionbar);
//			actionBar.setTitle(getString(R.string.em_city_title));
		}
		
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
//	    if (item.getItemId() == android.R.id.home) {
//	    	// Overwrite button home function
//	    	Toast.makeText(getApplicationContext(), "Overwrite button home function",Toast.LENGTH_LONG).show();
//	    	return true;
//	    }
        if (item.getItemId() == android.R.id.home)  {
            finish();
            return true;
        }
	    return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onAsyncTaskComplete(String contentData, Integer messageID) {

		if (messageID == REQUEST_CITY) {

			try {
				JSONArray jsonArr = new JSONArray(contentData);

				for (int i = 0; i < jsonArr.length(); ++i) {

					JSONObject rec = jsonArr.getJSONObject(i);

					Log.i("LOG APP",
							"rec.getString('id') : " + rec.getString("id"));
					Log.i("LOG APP",
							"rec.getString('name') : " + rec.getString("name"));

					dataCity.put(rec.getString("id"), rec.getString("name"));

				}

				values = (String[]) dataCity.values().toArray(
						new String[dataCity.size()]);

				Log.i("LOG APP", "So dong : " + values.length);

				// First paramenter - Context
				// Second parameter - Layout for the row
				// Third parameter - ID of the View to which the data is written
				// Forth - the Array of data
				adapter = new ArrayAdapter<String>(this,
						android.R.layout.simple_list_item_1,
						android.R.id.text1, values);

				// Assign adapter to ListView
				mainListView.setAdapter(adapter);

				// adapter.notifyDataSetChanged();

			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			return;
		}

		// TODO Auto-generated method stub
		super.onAsyncTaskComplete(contentData, messageID);
	}

	@Override
	public void onAsyncTaskError(String contentData, Integer messageID) {

		if (messageID == REQUEST_CITY) {
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
	}

}
