package com.gogocarpon.gogocarpon._app;

import android.content.DialogInterface;
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
import android.widget.EditText;

import com.gogocarpon.gogocarpon.R;
import com.gogocarpon.gogocarpon._app.baseclass.AppConfig;
import com.gogocarpon.gogocarpon._app.baseclass.BaseActivity;
import com.gogocarpon.gogocarpon._app.baseclass.GrabRequestUrl;
import com.gogocarpon.gogocarpon._app.baseclass.IntentManager;
import com.gogocarpon.gogocarpon._app.baseclass.UserInfo;
import com.gogocarpon.gogocarpon._app.baseclass.Utils;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;

import java.io.UnsupportedEncodingException;


public class EditProfileActivity extends BaseActivity {
    
	// Properties
	private static EditProfileActivity EditProfileActivityRef = null;

	// Constant
	public static final int REQUEST_PROFILE = 1;
	public static final int REQUEST_CHANGE_PW = 2;
	
	private EditText txtName;
	private EditText txtEmail;

	private EditText txtPassword;
	private EditText txtRePassword;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_edit_profile);

        AppConfig.isEditProfile = true;

        EditProfileActivityRef = this;
        
        setTitle(getString(R.string.em_update_profile_title));

        Drawable colorDrawable = new ColorDrawable(getResources().getColor(R.color.blue_light));
        Drawable bottomDrawable = new ColorDrawable(getResources().getColor(android.R.color.transparent));
        LayerDrawable ld = new LayerDrawable(new Drawable[]{colorDrawable, bottomDrawable});
        getSupportActionBar().setBackgroundDrawable(ld);
        
		txtName = (EditText) findViewById(R.id.txtName);
		txtEmail = (EditText) findViewById(R.id.txtEmail);
		
		txtName.setText(UserInfo.getInstance().name);
		txtEmail.setText(UserInfo.getInstance().email);
		
		txtPassword = (EditText) findViewById(R.id.txtPassword);
		txtRePassword = (EditText) findViewById(R.id.txtRePassword);
        
		Button btnEditProfile = (Button) findViewById(R.id.btnEditProfile);
		btnEditProfile.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// Validate data
				if (checkValidateProfileFields() == false) {
					return;
				}

				UserInfo info = UserInfo.getInstance();
				
				MultipartEntity reqEntity = new MultipartEntity();

				try {
					reqEntity.addPart("user_id", new StringBody(info.id));
					reqEntity.addPart("token", new StringBody(info.token));
					reqEntity.addPart("fullname", new StringBody(txtName
							.getText().toString()));
					reqEntity.addPart("email", new StringBody(txtEmail
							.getText().toString()));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				GrabRequestUrl urlrequest = new GrabRequestUrl(
						EditProfileActivity.this, EditProfileActivityRef, reqEntity);

				Log.d("My Log", "Request server loadDealFromWebService :"
						+ AppConfig.URI_EDIT_ACCOUNT);

				urlrequest.executeRequest(AppConfig.URI_EDIT_ACCOUNT, REQUEST_PROFILE);

			}
		});
		
		Button btnChangePw = (Button) findViewById(R.id.btnChangePw);
		btnChangePw.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// Validate data
				if (checkValidateChangePwFields() == false) {
					return;
				}
				
				UserInfo info = UserInfo.getInstance();

				MultipartEntity reqEntity = new MultipartEntity();

				try {
					reqEntity.addPart("user_id", new StringBody(info.id));
					reqEntity.addPart("token", new StringBody(info.token));
					reqEntity.addPart("new_password", new StringBody(txtPassword
							.getText().toString()));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				GrabRequestUrl urlrequest = new GrabRequestUrl(
						EditProfileActivity.this, EditProfileActivityRef, reqEntity);

				Log.d("My Log", "Request server loadDealFromWebService :"
						+ AppConfig.URI_CHANGE_PW);

				urlrequest.executeRequest(AppConfig.URI_CHANGE_PW, REQUEST_CHANGE_PW);

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

		if (messageID == REQUEST_PROFILE) {
			
			UserInfo.getInstance().name = txtName.getText().toString();
			UserInfo.getInstance().email = txtEmail.getText().toString();
			Utils.ShowNotification(EditProfileActivityRef, getString(R.string.notify),
                    getString(R.string.notify_update_profile_success),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            _intentManager
                                    .OpenActivity(IntentManager.HOME_ACTIVITY);
                        }
                    });
			
		} else if (messageID == REQUEST_CHANGE_PW) {
			
			Utils.ShowNotification(EditProfileActivityRef, getString(R.string.notify),
					getString(R.string.notify_change_pw_success),
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							_intentManager
									.OpenActivity(IntentManager.HOME_ACTIVITY);
						}
					});			
			
		}

		super.onAsyncTaskComplete(contentData, messageID);
	}

	@Override
	public void onAsyncTaskError(String contentData, Integer messageID) {

		if (messageID == REQUEST_PROFILE || messageID == REQUEST_CHANGE_PW) {
			
			Utils.ShowNotification(EditProfileActivityRef, getString(R.string.error), contentData);
			return;
			
		}

		super.onAsyncTaskError(contentData, messageID);
	}
    
	// Private Function
	private boolean checkValidateProfileFields() {

		if (Utils.isEmpty(txtName.getText().toString())) {
			Utils.ShowNotification(EditProfileActivityRef, getString(R.string.error),
					getString(R.string.error_input_display_name_empty));
			return false;
		}
		if (!Utils.isEmail(txtEmail.getText().toString())) {
			Utils.ShowNotification(EditProfileActivityRef, getString(R.string.error),
					getString(R.string.error_input_email_invalied));
			return false;
		}
		return true;
	}

	// Private Function
	private boolean checkValidateChangePwFields() {

		if (Utils.isEmpty(txtPassword.getText().toString())) {
			Utils.ShowNotification(EditProfileActivityRef, getString(R.string.error),
					getString(R.string.error_input_pw_empty));
			return false;
		}
		if (! txtRePassword.getText().toString().equals(txtPassword.getText().toString())) {
			Utils.ShowNotification(EditProfileActivityRef, getString(R.string.error),
					getString(R.string.error_input_verify_pw));
			return false;
		}
//		if (!Utils.isEmail(txtRePassword.getText().toString())) {
//			Utils.ShowNotification(EditProfileActivityRef, getString(R.string.err1or),
//					getString(R.string.error_input_verify_pw));
//			return false;
//		}
		return true;
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // Overwrite button home function
            //menu.toggle();
//	    	Toast.makeText(getApplicationContext(), "Overwrite button home function",Toast.LENGTH_LONG).show();

            AppConfig.isEditProfile = false;
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            AppConfig.isAbout = false;
            finish();
            // perform your actons.
        }
        return false;
    }
}
