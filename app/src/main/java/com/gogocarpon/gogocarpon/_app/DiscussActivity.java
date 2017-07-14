package com.gogocarpon.gogocarpon._app;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.gogocarpon.gogocarpon.R;
import com.gogocarpon.gogocarpon._app.baseclass.AppConfig;
import com.gogocarpon.gogocarpon._app.baseclass.BaseActivity;
import com.gogocarpon.gogocarpon._app.baseclass.GrabRequestUrl;
import com.gogocarpon.gogocarpon._app.baseclass.IntentManager;
import com.gogocarpon.gogocarpon._app.baseclass.UserInfo;
import com.gogocarpon.gogocarpon._app.baseclass.Utils;
import com.gogocarpon.gogocarpon._app.models.Deal;
import com.gogocarpon.gogocarpon._app.ui.adapters.DiscussListAdapter;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;


public class DiscussActivity extends BaseActivity {

	// Properties
	private static DiscussActivity DiscussActivityRef = null;

	// Constant
	public static final int REQUEST_COMMENT = 1;

	private ListView lstDiscuss;
	private DiscussListAdapter listAdapter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_discuss);

		DiscussActivityRef = this;
		
		setTitle(getString(R.string.em_comment_title));

		Button btnComment = (Button) findViewById(R.id.btnDiscuss);
		btnComment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Deal row = (Deal) _intentParams.get("rowDeal");
				Log.i("LOG APP", row._id);
				Log.i("LOG APP", row._name);

				DiscussPopupWindow popup = new DiscussPopupWindow(
						DiscussActivity.this, row);
				popup.show();

			}
		});

		if (UserInfo.getInstance().isLogin()) {
			btnComment.setVisibility(View.VISIBLE);
		}

		// Find the ListView resource.
		lstDiscuss = (ListView) findViewById(R.id.lstDiscuss);

		MultipartEntity reqEntity = new MultipartEntity();

		try {
			Deal row = (Deal) _intentParams.get("rowDeal");
			Log.i("LOG APP", row._id);
			reqEntity.addPart("deal_id", new StringBody(row._id));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		GrabRequestUrl urlrequest = new GrabRequestUrl(DiscussActivity.this,
				DiscussActivityRef, reqEntity);

		Log.d("My Log", "Request server loadDealFromWebService :"
				+ AppConfig.URI_DEAL_COMMENTS);

		urlrequest.executeRequest(AppConfig.URI_DEAL_COMMENTS, REQUEST_COMMENT);

        Drawable colorDrawable = new ColorDrawable(getResources().getColor(R.color.blue_light));
        Drawable bottomDrawable = new ColorDrawable(getResources().getColor(android.R.color.transparent));
        LayerDrawable ld = new LayerDrawable(new Drawable[]{colorDrawable, bottomDrawable});
        getSupportActionBar().setBackgroundDrawable(ld);

		// Demo data
		// Create and populate a List of planet names.
//		ArrayList<HashMap<String, String>> planetList = new ArrayList<HashMap<String, String>>();
		//
		// for (int i = 0; i < 20; i++) {
		// HashMap<String, String> obMap = new HashMap<String, String>();
		// obMap.put("content", "id (" + i +
		// ") $48.80 for 9-Course Japanese Wagyu Beef Premium Set for One at Hokkaido Sushi Restaurant in M Hotel (Worth $153). Two Pax Option Available.");
		// obMap.put("name", "ducbui");
		// obMap.put("date", "2012/03/24 12:09:34");
		// planetList.add(obMap);
		// }
		// ------------------------------------------------

//		// Create ArrayAdapter using the planet list.
//		listAdapter = new DiscussListAdapter(this, planetList);
//
//		// Set the ArrayAdapter as the ListView's adapter.
//		lstDiscuss.setAdapter(listAdapter);

	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            super.initActionBarControl(menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Overwrite button home function
            //menu.toggle();
//	    	Toast.makeText(getApplicationContext(), "Overwrite button home function",Toast.LENGTH_LONG).show();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
	@Override
	public void onAsyncTaskComplete(String contentData, Integer messageID) {

		if (messageID == REQUEST_COMMENT) {
			
			try {
				
				if(contentData.equals(""))
					return;
				
				JSONArray jsonArr = new JSONArray(contentData);
				
				if(jsonArr.length() <= 0 ) 
					return;

				ArrayList<HashMap<String, String>> planetList = new ArrayList<HashMap<String, String>>();
				
				for (int i = 0; i < jsonArr.length(); ++i) {

					JSONObject rec = jsonArr.getJSONObject(i);
					
					 HashMap<String, String> obMap = new HashMap<String, String>();
					 obMap.put("content", rec.getString("comment"));
					 obMap.put("name", rec.getString("name"));
					 obMap.put("date", rec.getString("created_at"));
					 planetList.add(obMap);
				}
				
				// Create ArrayAdapter using the planet list.
				listAdapter = new DiscussListAdapter(this, planetList);

				// Set the ArrayAdapter as the ListView's adapter.
				lstDiscuss.setAdapter(listAdapter);

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	@Override
	public void onAsyncTaskError(String contentData, Integer messageID) {

		if (messageID == REQUEST_COMMENT) {
			Utils.ShowNotification(DiscussActivityRef, getString(R.string.error), getString(R.string.error_get_comment));
			return;
		}

		super.onAsyncTaskError(contentData, messageID);
	}

    @Override
    public void onBackPressed() {
        // your code.
        finish();
    }
}