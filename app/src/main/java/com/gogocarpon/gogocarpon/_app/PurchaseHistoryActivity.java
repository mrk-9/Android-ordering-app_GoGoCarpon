package com.gogocarpon.gogocarpon._app;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.gogocarpon.gogocarpon.R;
import com.gogocarpon.gogocarpon._app.baseclass.AppConfig;
import com.gogocarpon.gogocarpon._app.baseclass.BaseActivity;
import com.gogocarpon.gogocarpon._app.baseclass.GrabRequestUrl;
import com.gogocarpon.gogocarpon._app.baseclass.IntentManager;
import com.gogocarpon.gogocarpon._app.baseclass.UserInfo;
import com.gogocarpon.gogocarpon._app.ui.adapters.PurchaseHistoryListAdapter;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

public class PurchaseHistoryActivity extends BaseActivity {

	// Properties
	private static PurchaseHistoryActivity PurchaseHistoryActivityRef = null;

	// Constant
	public static final int REQUEST_HISTORY = 1;

	private ListView lstPurchases;
	private PurchaseHistoryListAdapter listAdapter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_purchase_history);
		PurchaseHistoryActivityRef = this;

        AppConfig.isHistory = true;

        Drawable colorDrawable = new ColorDrawable(getResources().getColor(R.color.blue_light));
        Drawable bottomDrawable = new ColorDrawable(getResources().getColor(android.R.color.transparent));
        LayerDrawable ld = new LayerDrawable(new Drawable[]{colorDrawable, bottomDrawable});
        getSupportActionBar().setBackgroundDrawable(ld);
		
		setTitle(getString(R.string.em_purchase_history));

		// Find the ListView resource.
		lstPurchases = (ListView) findViewById(R.id.lstPurchases);

		MultipartEntity reqEntity = new MultipartEntity();

		try {
			reqEntity.addPart("user_id", new StringBody(
					UserInfo.getInstance().id));
			reqEntity.addPart("token", new StringBody(
					UserInfo.getInstance().token));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		GrabRequestUrl urlrequest = new GrabRequestUrl(
				PurchaseHistoryActivity.this, PurchaseHistoryActivityRef,
				reqEntity);

		Log.d("My Log", "Request server loadDealFromWebService :"
				+ AppConfig.URI_ORDER_HISTORY);

		urlrequest.executeRequest(AppConfig.URI_ORDER_HISTORY, REQUEST_HISTORY);

	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        super.initActionBarControl(menu);
        ActionBar ab = getSupportActionBar();

        ab.setHomeButtonEnabled(true);
        getSupportActionBar().setIcon(R.drawable.emu_category);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void invalidateOptionsMenu() {
        // TODO Auto-generated method stub
        super.invalidateOptionsMenu();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            AppConfig.isHistory = false;
            finish();
            // perform your actons.
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Overwrite button home function
            //menu.toggle();
//	    	Toast.makeText(getApplicationContext(), "Overwrite button home function",Toast.LENGTH_LONG).show();

            AppConfig.isHistory = false;
            finish();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
	public void onAsyncTaskComplete(String contentData, Integer messageID) {

		if (messageID == REQUEST_HISTORY) {

			try {
				JSONArray jsonArr = new JSONArray(contentData);

				if (jsonArr.length() <= 0)
					return;

				ArrayList<HashMap<String, String>> planetList = new ArrayList<HashMap<String, String>>();

				for (int i = 0; i < jsonArr.length(); ++i) {

					JSONObject rec = jsonArr.getJSONObject(i);

					HashMap<String, String> obMap = new HashMap<String, String>();
					obMap.put("name", rec.getString("deal_name"));
					obMap.put("date", rec.getString("created_at"));
					obMap.put("status", rec.getString("status"));

					planetList.add(obMap);
				}

				// Create ArrayAdapter using the planet list.
				listAdapter = new PurchaseHistoryListAdapter(this, planetList);

				// Set the ArrayAdapter as the ListView's adapter.
				lstPurchases.setAdapter(listAdapter);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		super.onAsyncTaskComplete(contentData, messageID);
	}

	@Override
	public void onAsyncTaskError(String contentData, Integer messageID) {

//		if (messageID == REQUEST_HISTORY) {
//			Utils.ShowNotification(PurchaseHistoryActivityRef, "Error",
//					"Account invalied");
//			return;
//		}

		super.onAsyncTaskError(contentData, messageID);
	}
}