package com.gogocarpon.gogocarpon._app;

import java.io.UnsupportedEncodingException;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.gogocarpon.gogocarpon.R;
import com.gogocarpon.gogocarpon._app.baseclass.AppConfig;
import com.gogocarpon.gogocarpon._app.baseclass.BaseActivity;
import com.gogocarpon.gogocarpon._app.baseclass.GrabRequestUrl;
import com.gogocarpon.gogocarpon._app.baseclass.IntentManager;
import com.gogocarpon.gogocarpon._app.baseclass.Utils;


public class SignupAgreeActivity extends BaseActivity {

	// Properties
	private static SignupAgreeActivity SignupAgreeActivityRef = null;

	// Constant
	public static final int REQUEST_SIGNUP = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_signup_agree);

		SignupAgreeActivityRef = this;
		
		setTitle(getString(R.string.em_term_condition));

		Button btnSignup = (Button) findViewById(R.id.btnSignup);
		btnSignup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				MultipartEntity reqEntity = new MultipartEntity();

				try {
					reqEntity.addPart("username", new StringBody(
							(String) _intentParams.get("txtUsername")));
					reqEntity.addPart("password", new StringBody(
							(String) _intentParams.get("txtPassword")));
					reqEntity.addPart("fullname", new StringBody(
							(String) _intentParams.get("txtName")));					
					reqEntity.addPart("email", new StringBody(
							(String) _intentParams.get("txtEmail")));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				GrabRequestUrl urlrequest = new GrabRequestUrl(
						SignupAgreeActivity.this, SignupAgreeActivityRef, reqEntity);

				Log.d("My Log", "Request server loadDealFromWebService :"
						+ AppConfig.URI_LOGIN);

				urlrequest.executeRequest(AppConfig.URI_CREATE_ACCOUNT, REQUEST_SIGNUP);

			}
		});

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		super.initActionBarControl(menu);
		return super.onCreateOptionsMenu(menu);
	}
	

	@Override
	public void onAsyncTaskComplete(String contentData, Integer messageID) {

		if (messageID == REQUEST_SIGNUP) {

			Utils.ShowNotification(SignupAgreeActivityRef, getString(R.string.notify),
                    getString(R.string.notify_create_account_success),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Open Home Page
                            _intentManager
                                    .OpenActivity(IntentManager.LOGIN_ACTIVITY);
                            return;
                        }
                    });

		}

		super.onAsyncTaskComplete(contentData, messageID);
	}

	@Override
	public void onAsyncTaskError(String contentData, Integer messageID) {

		if (messageID == REQUEST_SIGNUP) {
			Utils.ShowNotification(SignupAgreeActivityRef, getString(R.string.error), contentData);
			return;
		}

		super.onAsyncTaskError(contentData, messageID);
	}

}
