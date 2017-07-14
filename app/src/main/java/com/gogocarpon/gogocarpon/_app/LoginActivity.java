package com.gogocarpon.gogocarpon._app;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.gogocarpon.gogocarpon.R;
import com.gogocarpon.gogocarpon._app.baseclass.AppConfig;
import com.gogocarpon.gogocarpon._app.baseclass.BaseActivity;
import com.gogocarpon.gogocarpon._app.baseclass.GrabRequestUrl;
import com.gogocarpon.gogocarpon._app.baseclass.IntentManager;
import com.gogocarpon.gogocarpon._app.baseclass.UserInfo;
import com.gogocarpon.gogocarpon._app.baseclass.Utils;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;


public class LoginActivity extends BaseActivity {

	// Properties
	private static LoginActivity LoginActivityRef = null;

	// Constant
	public static final int REQUEST_LOGIN = 1;
    LinearLayout signup_Button;

	private EditText txtUsername;
	private EditText txtPassword;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_login);
		LoginActivityRef = this;
		
		setTitle(getString(R.string.em_login));

		txtUsername = (EditText) findViewById(R.id.txtUsername);
		txtPassword = (EditText) findViewById(R.id.txtPassword);
        signup_Button = (LinearLayout)findViewById(R.id.btnSignup_Login);

        InputMethodManager imm = (InputMethodManager)getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(txtPassword.getWindowToken(), 0);

        Drawable colorDrawable = new ColorDrawable(getResources().getColor(R.color.blue_light));
        Drawable bottomDrawable = new ColorDrawable(getResources().getColor(android.R.color.transparent));
        LayerDrawable ld = new LayerDrawable(new Drawable[]{colorDrawable, bottomDrawable});
        getSupportActionBar().setBackgroundDrawable(ld);

        signup_Button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                _intentManager.OpenActivity(IntentManager.SIGNUP_ACTIVITY);
            }
        });

		Button btnLogin = (Button) findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// Validate data
				if (checkValidateFields() == false) {
					return;
				}

				MultipartEntity reqEntity = new MultipartEntity();

				try {
					reqEntity.addPart("username", new StringBody(txtUsername
							.getText().toString()));
					reqEntity.addPart("password", new StringBody(txtPassword
							.getText().toString()));
					
//					reqEntity.addPart("username", new StringBody("demo1"));
//					reqEntity.addPart("password", new StringBody("demo123"));
					
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				GrabRequestUrl urlrequest = new GrabRequestUrl(
						LoginActivity.this, LoginActivityRef, reqEntity);

				Log.d("My Log", "Request server loadDealFromWebService :"
						+ AppConfig.URI_LOGIN);

				urlrequest.executeRequest(AppConfig.URI_LOGIN, REQUEST_LOGIN);

			}
		});

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public void onAsyncTaskComplete(String contentData, Integer messageID) {

		if (messageID == REQUEST_LOGIN) {
			
			try {
				UserInfo info = UserInfo.getInstance();

				JSONObject jsonObj = new JSONObject(contentData);

				info.username = txtUsername.getText().toString();
				info.password = txtPassword.getText().toString();

				info.id = jsonObj.getString("id");
				info.email = jsonObj.getString("email");
				info.name = jsonObj.getString("name");
				info.token = jsonObj.getString("token");
				
				info.setLogin();
				Utils.ShowNotification(LoginActivityRef, getString(R.string.notify),
                        getString(R.string.notify_login_success),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // Open Home Page
                                finish();
                                return;
                            }
                        });
				

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		super.onAsyncTaskComplete(contentData, messageID);
	}

	@Override
	public void onAsyncTaskError(String contentData, Integer messageID) {

		if (messageID == REQUEST_LOGIN) {
			Utils.ShowNotification(LoginActivityRef, getString(R.string.error), getString(R.string.error_account_invalied));
			return;
		}

		super.onAsyncTaskError(contentData, messageID);
	}

	// Private Function
	private boolean checkValidateFields() {

		if (Utils.isEmpty(txtUsername.getText().toString())) {
			Utils.ShowNotification(LoginActivityRef, getString(R.string.error),
					getString(R.string.error_input_username_empty));
			return false;
		}
		if (Utils.isEmpty(txtPassword.getText().toString())) {
			Utils.ShowNotification(LoginActivityRef, getString(R.string.error),
					getString(R.string.error_input_pw_empty));
			return false;
		}
		return true;
	}

}
