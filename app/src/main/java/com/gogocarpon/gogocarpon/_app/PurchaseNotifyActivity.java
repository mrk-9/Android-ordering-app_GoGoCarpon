package com.gogocarpon.gogocarpon._app;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.gogocarpon.gogocarpon.R;
import com.gogocarpon.gogocarpon._app.baseclass.AppConfig;
import com.gogocarpon.gogocarpon._app.baseclass.BaseActivity;
import com.gogocarpon.gogocarpon._app.baseclass.GrabRequestUrl;
import com.gogocarpon.gogocarpon._app.baseclass.IntentManager;
import com.gogocarpon.gogocarpon._app.baseclass.UserInfo;
import com.gogocarpon.gogocarpon._app.models.Deal;
import com.gogocarpon.gogocarpon._app.models.DealType;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;

import java.io.UnsupportedEncodingException;


public class PurchaseNotifyActivity extends BaseActivity {

	// Properties
	private static PurchaseNotifyActivity PurchaseNotifyActivityRef = null;

	// Constant
	public static final int REQUEST_PAYMENT_TRACKING = 1;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_purchase_notify);

		PurchaseNotifyActivityRef = this;

		Button btnOther = (Button) findViewById(R.id.btnOther);
		btnOther.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				_intentManager.OpenActivity(IntentManager.HOME_ACTIVITY);
			}
		});

        Drawable colorDrawable = new ColorDrawable(getResources().getColor(R.color.blue_light));
        Drawable bottomDrawable = new ColorDrawable(getResources().getColor(android.R.color.transparent));
        LayerDrawable ld = new LayerDrawable(new Drawable[]{colorDrawable, bottomDrawable});
        getSupportActionBar().setBackgroundDrawable(ld);

		TextView lblNotify = (TextView) findViewById(R.id.lblNotify);

		boolean payment_status = (Boolean) _intentParams.get("payment_status");

		if (!payment_status) {
			lblNotify.setText(getString(R.string.error_payment_failed));
			return;
		}
		
		lblNotify.setText(getString(R.string.notify_payment_success) + _intentParams.get("transaction_id"));

		// Post data to server
		MultipartEntity reqEntity = new MultipartEntity();

		Deal rowDeal = (Deal) _intentParams.get("rowDeal");
		DealType rowDealType = (DealType) _intentParams.get("rowDealType");

		try {
			reqEntity.addPart("user_id", new StringBody(
					UserInfo.getInstance().id));
			reqEntity.addPart("token", new StringBody(
					UserInfo.getInstance().token));
			reqEntity.addPart("deal_id", new StringBody(rowDeal._id));

			reqEntity.addPart("buy4friend", new StringBody(
					(String) _intentParams.get("buyType")));
			reqEntity.addPart("quantity",
					new StringBody((String) _intentParams.get("quantity")));
			reqEntity.addPart("tracking_content", new StringBody(
					(String) _intentParams.get("tracking_content")));

			reqEntity.addPart("receiver_address", new StringBody(
					(String) _intentParams.get("receiverAddress")));
			reqEntity.addPart("receiver_phone", new StringBody(
					(String) _intentParams.get("receiverPhone")));

			reqEntity.addPart("receiver_name", new StringBody(
					(String) _intentParams.get("receiverName")));
			reqEntity.addPart("receiver_email", new StringBody(
					(String) _intentParams.get("receiverEmail")));
			reqEntity.addPart("receiver_msg", new StringBody(
					(String) _intentParams.get("receiverMsg")));
			
			reqEntity.addPart("deal_type_id", new StringBody(""));
			reqEntity.addPart("price_buy", new StringBody(rowDeal._price_buy + ""));

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		GrabRequestUrl urlrequest = new GrabRequestUrl(
				PurchaseNotifyActivity.this, PurchaseNotifyActivityRef,
				reqEntity);

		Log.d("My Log", "Request server loadDealFromWebService :"
				+ AppConfig.URI_PAYMENT_TRACKING);

		urlrequest.executeRequest(AppConfig.URI_PAYMENT_TRACKING, REQUEST_PAYMENT_TRACKING);
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
	// Catch Back button on phone
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			_intentManager.OpenActivity(IntentManager.HOME_ACTIVITY);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onAsyncTaskComplete(String contentData, Integer messageID) {

		if (messageID == REQUEST_PAYMENT_TRACKING) {
			
//			try {
//				UserInfo info = UserInfo.getInstance();
//
//				JSONObject jsonObj = new JSONObject(contentData);
//
//				info.username = txtUsername.getText().toString();
//				info.password = txtPassword.getText().toString();
//
//				info.id = jsonObj.getString("id");
//				info.email = jsonObj.getString("email");
//				info.name = jsonObj.getString("name");
//				info.token = jsonObj.getString("token");
//				
//				info.setLogin();
//
//				Utils.ShowNotification(LoginActivityRef, "Notify",
//						"Login successfully",
//						new DialogInterface.OnClickListener() {
//							public void onClick(DialogInterface dialog,
//									int which) {
//								// Open Home Page
//								_intentManager.OpenActivity(IntentManager.HOME_ACTIVITY);
//								return;	
//							}
//						});
//
//			} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}

		}

		super.onAsyncTaskComplete(contentData, messageID);
        finish();
	}

	@Override
	public void onAsyncTaskError(String contentData, Integer messageID) {

		if (messageID == REQUEST_PAYMENT_TRACKING) {
		}

		super.onAsyncTaskError(contentData, messageID);
	}

    @Override
    public void onBackPressed() {
        // your code.
    }
	
}